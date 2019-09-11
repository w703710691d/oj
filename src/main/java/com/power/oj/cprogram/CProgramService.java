package com.power.oj.cprogram;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.power.oj.contest.ContestService;
import com.power.oj.contest.model.ContestModel;
import com.power.oj.contest.model.ContestSolutionModel;
import com.power.oj.core.OjConfig;
import com.power.oj.core.bean.ResultType;
import com.power.oj.cprogram.model.*;
import com.power.oj.problem.ProblemModel;
import com.power.oj.problem.ProblemService;
import com.power.oj.shiro.ShiroKit;
import com.power.oj.solution.SolutionModel;
import com.power.oj.solution.SolutionService;
import com.power.oj.user.UserModel;
import com.power.oj.user.UserService;

import java.io.*;
import java.util.*;


/**
 * Created by w7037 on 2017/6/14.
 */
public final class CProgramService {
    private static final Logger LOGGER = Logger.getLogger(CProgramController.class);

    static Page<ContestModel> getContestList(int pageNumber, int pageSize, String contestType) {
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("SELECT contest.cid,contest.title,contest.startTime,contest.endTime,");
        sqlSb.append("FROM_UNIXTIME(startTime, '%Y-%m-%d %H:%i:%s') AS startDateTime, ");
        sqlSb.append("FROM_UNIXTIME(endTime, '%Y-%m-%d %H:%i:%s') AS endDateTime ");
        if (CprogramInfoModel.TYPE_HOMEWORK.equals(contestType)) {
            sqlSb.append(",user.realName ");
        }
        if (!CprogramInfoModel.TYPE_EXPERIMENT.equals(contestType)) {
            sqlSb.append(",cprogram_info.week,cprogram_info.lecture ");
        }
        List<Object> parase = new ArrayList<>();
        StringBuilder sqlExcSb = new StringBuilder();
        sqlExcSb.append("FROM contest ");
        if (CprogramInfoModel.TYPE_HOMEWORK.equals(contestType)) {
            sqlExcSb.append("INNER JOIN user ON contest.uid = user.uid ");
        }
        sqlExcSb.append("INNER JOIN cprogram_info ON contest.cid=cprogram_info.cid ");
        sqlExcSb.append("WHERE contest.type=? AND cprogram_info.type=? ");
        parase.add(ContestModel.TYPE_ELSE);
        parase.add(contestType);

        if (!isTeacher() && CprogramInfoModel.TYPE_HOMEWORK.equals(contestType)) {
            sqlExcSb.append("AND contest.uid=? AND cprogram_info.week=? AND cprogram_info.lecture=? ");
            Integer uid = UserService.me().getCurrentUid();
            CprogramUserInfoModel CUser = CprogramUserInfoModel.dao.findById(uid);

            parase.add(CUser.getTid());
            parase.add(CUser.getClass_week());
            parase.add(CUser.getClass_lecture());
        }
        if (!isTeacher()) {
            int startTime = getStartUnixTime();
            int endTime = getEndUnixTime();
            sqlExcSb.append("AND contest.startTime BETWEEN ? AND ? ");
            parase.add(startTime);
            parase.add(endTime);
        }
        sqlExcSb.append("ORDER BY contest.startTime DESC ");
        Page<ContestModel> page = ContestModel.dao.paginate(pageNumber, pageSize, sqlSb.toString(), sqlExcSb.toString(), parase.toArray());
        return page;
    }

    static public boolean isTeacher() {
        return ShiroKit.hasPermission("teacher");
    }

    static public Integer saveContest(String title, Integer uid, String startTime, String endTime, String contestType, Integer week, Integer lecture, String commit) {
        ContestModel contestModel = new ContestModel();
        contestModel.setTitle(title);
        contestModel.setUid(uid);
        contestModel.setType(ContestModel.TYPE_ELSE);
        ContestService.me().addContest(contestModel, startTime, endTime);

        CprogramInfoModel cprogramInfoModel = new CprogramInfoModel();
        cprogramInfoModel.setCid(contestModel.getCid());
        cprogramInfoModel.setType(contestType);
        cprogramInfoModel.setLecture(lecture);
        cprogramInfoModel.setWeek(week);
        cprogramInfoModel.setCommit(commit);
        cprogramInfoModel.save();
        return contestModel.getCid();
    }

    static public void updateContest(Integer cid, String title, Integer uid, String startTime, String endTime, Integer week, Integer lecture, String commit) {
        ContestModel contestModel = ContestService.me().getContest(cid);
        contestModel.setTitle(title);
        if (uid != null && uid != 0) {
            contestModel.setUid(uid);
        }
        ContestService.me().updateContest(contestModel, startTime, endTime);
        CprogramInfoModel cprogramInfoModel = CprogramInfoModel.dao.findById(cid);
        cprogramInfoModel.setCommit(commit).setWeek(week).setLecture(lecture);
        cprogramInfoModel.update();
    }

    static public CprogramInfoModel getContest(Integer cid) {
        return CprogramInfoModel.dao.findFirst("SELECT contest.cid,title,contest.uid,startTime,endTime,FROM_UNIXTIME(startTime,'%Y-%m-%d %H:%i:%s') AS startDateTime,FROM_UNIXTIME(endTime,'%Y-%m-%d %H:%i:%s') AS endDateTime,`commit`,lecture,`week`,cprogram_info.type,commit FROM contest INNER JOIN cprogram_info ON contest.cid = cprogram_info.cid WHERE contest.cid = ?", cid);
    }

    public static boolean checkAccessContest(Integer cid) {
        if (CProgramService.isTeacher()) return true;
        CprogramInfoModel contest = CProgramService.getContest(cid);
        if (CprogramInfoModel.TYPE_HOMEWORK.equals(contest.getType()) ||
                CprogramInfoModel.TYPE_EXPERIMENT.equals(contest.getType())) {
            return true;
        } else {
            Integer uid = UserService.me().getCurrentUid();
            if (uid == null) return false;
            return Db.queryInt("select id from contest_user where uid = ? and cid = ?", uid, cid) != null;
        }
    }

    public static boolean checkContestPassword(Integer cid, String password) {
        CprogramPasswordModel passwordModel = CprogramPasswordModel.dao.findFirst("select * from cprogram_password where cid = ? and password = ? and uid = 0", cid, password);
        if (passwordModel == null) {
            return false;
        }
        Integer uid = UserService.me().getCurrentUid();
        passwordModel.setUid(uid);
        passwordModel.update();
        ContestService.me().addUser(cid, uid);
        return true;
    }

    public static void saveUser(String realName, String phone, String classes, Integer week, Integer lecture, Integer tid, String stuId) {
        UserModel user = UserService.me().getCurrentUser();
        user.setRealName(realName).setPhone(phone);
        user.update();
        CprogramUserInfoModel infoModel = new CprogramUserInfoModel();
        infoModel.setUid(user.getUid());
        infoModel.setClasses(classes);
        infoModel.setClass_week(week);
        infoModel.setClass_lecture(lecture);
        infoModel.setTid(tid);
        infoModel.setStuid(stuId);
        infoModel.setCtime(OjConfig.timeStamp);
        infoModel.save();
    }

    public static void updateUser(String realName, String phone, String classes, Integer week, Integer lecture, Integer tid, String stuId) {
        UserModel user = UserService.me().getCurrentUser();
        user.setRealName(realName).setPhone(phone);
        user.update();
        CprogramUserInfoModel infoModel = CprogramUserInfoModel.dao.findById(user.getUid());
        infoModel.setUid(user.getUid());
        infoModel.setClasses(classes);
        infoModel.setClass_week(week);
        infoModel.setClass_lecture(lecture);
        infoModel.setTid(tid);
        infoModel.setStuid(stuId);
        infoModel.setCtime(OjConfig.timeStamp);
        infoModel.update();
    }

    public static UserModel getUserInfo() {
        Integer uid = UserService.me().getCurrentUid();
        return UserModel.dao.findFirst("select " +
                "user.uid, " +
                "user.realName, " +
                "user.phone, " +
                "cprogram_user_info.classes, " +
                "cprogram_user_info.class_week, " +
                "cprogram_user_info.class_lecture, " +
                "cprogram_user_info.tid, " +
                "cprogram_user_info.stuid " +
                "from user " +
                "inner join cprogram_user_info on user.uid = cprogram_user_info.uid " +
                "where user.uid = ?", uid);
    }

    static public ContestSolutionModel getSolution(Integer cid, Integer sid) {
        String sql = "select " +
                "contest_solution.*, " +
                "user.name " +
                "from " +
                "contest_solution inner join user on user.uid = contest_solution.uid " +
                "where cid = ? and sid = ? and contest_solution.status = 1";
        ContestSolutionModel solution = ContestSolutionModel.dao.findFirst(sql, cid, sid);
        if (solution == null)
            return null;
        solution.put("alpha", (char) (solution.getNum() + 'A'));
        return solution;
    }

    static public String getContestType(Integer cid) {
        return CprogramInfoModel.dao.findById(cid).getType();
    }

    static public List<Record> getScoreList(Integer cid) {
        List<Object> parase = new ArrayList<>();
        String sql = "SELECT s.*,stuid,classes,t.realName AS teacher,u.`name`,u.realName " +
                "FROM score s " +
                "INNER JOIN `user` u ON s.uid = u.uid " +
                "INNER JOIN cprogram_user_info cu ON s.uid = cu.uid " +
                "INNER JOIN `user` t ON cu.tid = t.uid " +
                "WHERE cid = ? ";
        parase.add(cid);
        if (!isTeacher()) {
            sql += "and s.uid = ? ";
            parase.add(UserService.me().getCurrentUid());
        } else if (CprogramInfoModel.TYPE_EXPERIMENT.equals(getContestType(cid)) && !ShiroKit.hasPermission("root")) {
            sql += "and s.week = ? and s.lecture = ? ";
            int week = CProgramService.getWeek(OjConfig.timeStamp);
            int lecture = CProgramService.getLecture(OjConfig.timeStamp);
            if (lecture == 0) lecture = CProgramService.getLecture(OjConfig.timeStamp - 15 * 60);
            parase.add(week);
            parase.add(lecture);
        }
        sql += " ORDER BY cu.stuid ";

        return Db.find(sql, parase.toArray());
    }


    static public int getSolutionResult(Integer sid) {
        ContestSolutionModel solution = SolutionService.me().findContestSolution(sid);
        Integer result = Db.queryInt("select MIN(result) from contest_solution where cid = ? and pid = ? and uid = ?", solution.getCid(), solution.getPid(), solution.getUid());
        if (result == null) return 999;
        return result;
    }

    static public void updateScore(Integer cid, Integer sid, Integer result) {
        ContestSolutionModel solution = SolutionService.me().findContestSolution(sid);
        Integer uid = solution.getUid();
        Record score = Db.findFirst("select * from score where cid =? and uid = ?", cid, uid);
        Integer totProblem = ContestService.me().getContestProblems(cid, null).size();
        Integer preScore = (int) Math.round(100.0 / totProblem);
        String contestType = getContestType(cid);
        if (score == null) {
            score = new Record();
            score.set("cid", cid);
            score.set("uid", uid);
            score.set("submited", 1);
            score.set("ctime", OjConfig.timeStamp);
            score.set("week", getWeek(OjConfig.timeStamp));
            score.set("lecture", getLecture(OjConfig.timeStamp));
            score.set("accepted", 0);
            score.set("score1", 0);
            score.set("score2", 0);
            if (result == ResultType.AC) {
                score.set("accepted", 1);
                score.set("score1", preScore);
                if (!contestType.equals(CprogramInfoModel.TYPE_EXPERIMENT)) {
                    score.set("score2", preScore);
                }
            }
            Db.save("score", "rid", score);
        } else {
            score.set("submited", score.getInt("submited") + 1);
            if (result == ResultType.AC && getSolutionResult(sid) != ResultType.AC) {
                score.set("accepted", score.getInt("accepted") + 1);
                Integer newScore = score.getInt("score1") + preScore;
                if (score.getInt("accepted").equals(totProblem)) {
                    newScore = 100;
                }
                if (newScore > 100) newScore = 100;
                score.set("score1", newScore);
                if (!contestType.equals(CprogramInfoModel.TYPE_EXPERIMENT)) {
                    score.set("score2", newScore);
                }
            }
            Db.update("score", "rid", score);
        }
    }

    static public void updateFinalScore(int cid, int uid, int score) {
        Db.update("update score set score2=? where cid=? and uid=?", score, cid, uid);
    }

    static public String randomPassword() {
        String source = "QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder password = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            int index = rand.nextInt(source.length());
            password.append(source.charAt(index));
        }
        return password.toString();
    }

    static public File addPassword(int cid, int number) {
        File file = new File(OjConfig.downloadPath, "password-" + cid + ".txt");
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
            List<Record> list = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                String password = randomPassword();
                Record record = new Record();
                record.set("cid", cid);
                record.set("password", password);
                list.add(record);
                writer.write(password + "\n\n");
            }
            Db.batchSave("cprogram_password", list, list.size());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("can't creat the password file");
        }
        return file;
    }

    static public boolean isRegister() {
        if (isTeacher()) return true;
        Record record = Db.findById("cprogram_user_info", "uid", UserService.me().getCurrentUid());
        return record != null;
    }


    static public List<UserModel> getTeacherList() {
        return UserModel.dao.find("select user.realName,user.uid from user inner join user_role on user.uid=user_role.uid where user_role.rid = 4");
    }

    public static int getWeek(int unix_time) {
        Date date = new Date(unix_time * 1000L);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static int getLecture(int unix_time) {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(new Date(unix_time * 1000L));
        endDate.setTime(new Date(unix_time * 1000L));
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        endDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < CProgramConstants.startTimeHour.length; i++) {
            startDate.set(Calendar.HOUR_OF_DAY, CProgramConstants.startTimeHour[i]);
            startDate.set(Calendar.MINUTE, CProgramConstants.startTimeMin[i]);
            endDate.set(Calendar.HOUR_OF_DAY, CProgramConstants.endTimeHour[i]);
            endDate.set(Calendar.MINUTE, CProgramConstants.endTimeMin[i]);
            long startTime = startDate.getTime().getTime();
            long endTime = endDate.getTime().getTime();
            if (startTime <= unix_time * 1000L && unix_time * 1000L <= endTime) {
                return i + 1;
            }
        }
        return 0;
    }

    public static int getStartUnixTime(int ctime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(ctime * 1000L));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (2 - 1 <= calendar.get(Calendar.MONTH) && calendar.get(Calendar.MONTH) <= 7 - 1) {
            calendar.set(Calendar.MONTH, 2 - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            if (calendar.get(Calendar.MONTH) < 2 - 1) {
                int year = calendar.get(Calendar.YEAR);
                year--;
                calendar.set(Calendar.YEAR, year);
            }
            calendar.set(Calendar.MONTH, 8 - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        return (int) (calendar.getTime().getTime() / 1000);
    }

    public static int getStartUnixTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(OjConfig.timeStamp * 1000L));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (2 - 1 <= calendar.get(Calendar.MONTH) && calendar.get(Calendar.MONTH) <= 7 - 1) {
            calendar.set(Calendar.MONTH, 2 - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            if (calendar.get(Calendar.MONTH) < 2 - 1) {
                int year = calendar.get(Calendar.YEAR);
                year--;
                calendar.set(Calendar.YEAR, year);
            }
            calendar.set(Calendar.MONTH, 8 - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        return (int) (calendar.getTime().getTime() / 1000);
    }

    public static int getEndUnixTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(OjConfig.timeStamp * 1000L));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (2 - 1 <= calendar.get(Calendar.MONTH) && calendar.get(Calendar.MONTH) <= 7 - 1) {
            calendar.set(Calendar.MONTH, 7 - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 31 - 1);
        } else {
            if (calendar.get(Calendar.MONTH) <= 12 - 1) {
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            }
            calendar.set(Calendar.MONTH, 1 - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 31 - 1);
        }
        return (int) (calendar.getTime().getTime() / 1000);
    }

    public static Boolean needReSignUp() {
        if (isTeacher()) return false;
        if (isRegister()) {
            Integer ctime = Db.queryInt("select ctime from cprogram_user_info where uid=?", UserService.me().getCurrentUid());
            return getStartUnixTime() != getStartUnixTime(ctime);
        }
        return false;
    }

    public static String getStuID() {
        Integer uid = UserService.me().getCurrentUid();
        return CprogramUserInfoModel.dao.findFirst("select stuid from cprogram_user_info where uid=?", uid).getStuid();
    }

    public static CprogramExperimentReportModel getReportInfo(Integer cid, Integer uid) {
        String sql = "SELECT\n" +
                "\tu.realName,\n" +
                "\tcu.stuid,\n" +
                "\tcu.classes,\n" +
                "\tce.position,\n" +
                "\tce.machine,\n" +
                "\tt.realName AS teacher,\n" +
                "\tsc.score2 AS score,\n" +
                "\tce.times,\n" +
                "\tce.`week`,\n" +
                "\tce.lecture,\n" +
                "\tce.`commit`,\n" +
                "\tce.status,\n" +
                "\tci.`commit` AS aim\n" +
                "FROM\n" +
                "\tcprogram_experiment_report ce\n" +
                "INNER JOIN `user` u ON ce.uid = u.uid\n" +
                "INNER JOIN cprogram_user_info cu ON ce.uid = cu.uid\n" +
                "INNER JOIN `user` t ON cu.tid = t.uid\n" +
                "LEFT JOIN score sc ON ce.cid = sc.cid\n" +
                "AND ce.uid = sc.uid\n" +
                "INNER JOIN cprogram_info ci ON ce.cid = ci.cid\n" +
                "WHERE\n" +
                "\tce.cid = ?\n" +
                "AND ce.uid = ?";
        CprogramExperimentReportModel model = CprogramExperimentReportModel.dao.findFirst(sql, cid, uid);
        if(model == null) {
            model = new CprogramExperimentReportModel();
            model.setUid(uid).setCid(cid).setStatus(false);
            model.save();
            model = CprogramExperimentReportModel.dao.findFirst(sql, cid, uid);
        }
        model.put("tot", getSolutuonStatistics(uid, cid, -1));
        return model;
    }


    public static CprogramExperimentReportModel findReportInfoByContestAndUser(Integer cid, Integer uid) {
        return CprogramExperimentReportModel.dao.findFirst(
                "SELECT\n" +
                        "\t*\n" +
                        "FROM\n" +
                        "\tcprogram_experiment_report\n" +
                        "WHERE\n" +
                        "\tcid = ?\n" +
                        "AND uid = ?", cid, uid);
    }

    public static void updateReportInfo(Integer cid, Integer uid, String position, Integer machine,
                                        Integer times, Integer week, Integer lecture) {
        CprogramExperimentReportModel report = findReportInfoByContestAndUser(cid, uid);
        if (report == null) {
            report = new CprogramExperimentReportModel();
            report.setCid(cid).setUid(uid).setPosition(position).setMachine(machine).setTimes(times).setWeek(week).setLecture(lecture);
            report.setStatus(false);
            report.save();
        } else {
            report.setPosition(position).setMachine(machine).setTimes(times).setWeek(week).setLecture(lecture);
            report.update();
        }
    }

    public static CprogramExperimentReportModel findReportInfoByTimesAndPostion(
            String position, Integer machine, Integer times, Integer week, Integer lecture) {
        return CprogramExperimentReportModel.dao.findFirst(
                "SELECT\n" +
                        "\t*\n" +
                        "FROM\n" +
                        "\tcprogram_experiment_report\n" +
                        "WHERE\n" +
                        "\tposition = ?\n" +
                        "AND machine = ?\n" +
                        "AND times = ?\n" +
                        "AND `week` = ?\n" +
                        "AND lecture = ?\n",
                position, machine, times, week, lecture);
    }
/*
    public static Integer updateReportInfo(Integer cid, Integer uid, String position, Integer machine,
                                           Integer times, Integer week, Integer lecture) {
        CprogramExperimentReportModel pos = findReportInfoByTimesAndPostion(position, machine, times, week, lecture);
        if (pos == null) {
            CprogramExperimentReportModel info = findReportInfoByContestAndUser(cid, uid);
            if (info == null) {  //new Record
                CprogramExperimentReportModel report = new CprogramExperimentReportModel();
                report.setCid(cid).setUid(uid).setPosition(position).setMachine(machine);
                report.setTimes(times).setWeek(week).setLecture(lecture);
                report.save();
            } else {    //update Record
                info.setPosition(position).setMachine(machine);
                info.setTimes(times).setWeek(week).setLecture(lecture);
                info.update();
            }
            return 0;
        } else if (pos.getUid().equals(uid)) { //no Modify
            return 0;
        } else {    // this position have been ocu;
            return -1;
        }
    }
    */
//
//    public Boolean submitPositionAndTime(Integer cid, Integer uid) {
//
//
//    }

    public static LinkedHashMap<String, Integer> getSolutuonStatistics(Integer uid, Integer cid, Integer num) {
        List<SolutionModel> solutions;
        if (num != -1) {
            solutions = SolutionModel.dao.find("select result from contest_solution where uid=? AND cid=? AND num=?",
                    uid, cid, num);
        } else {
            solutions = SolutionModel.dao.find("select result from contest_solution where uid=? AND cid=?", uid, cid);
        }

        Map<Integer, Integer> buffer = new TreeMap<>();
        for (SolutionModel solution : solutions) {
            Integer result = solution.getResult();
            buffer.merge(result, 1, Integer::sum);
        }
        LinkedHashMap<String, Integer> res = new LinkedHashMap<>();
        res.put("总计", solutions.size());
        for (Map.Entry<Integer, Integer> e : buffer.entrySet()) {
            res.put(OjConfig.resultType.get(e.getKey()).getChineseName(), e.getValue());
        }
        return res;
    }

    static public ProblemModel findProblem(Integer pid) {
        ProblemModel problemModel;

        if (OjConfig.isDevMode()) {
            problemModel = ProblemModel.dao.findById(pid);
        } else {
            problemModel = ProblemModel.dao.findFirstByCache("problem", pid, "SELECT * FROM problem WHERE pid=?", pid);
        }

        return problemModel;
    }

    public static void appendStatisticsAndCommit(Integer uid, int cid, List<Record> problems) {
        for (Record problem : problems) {
            int num = problem.getInt("num");
            LinkedHashMap<String, Integer> statistics = getSolutuonStatistics(uid, cid, num);
            problem.set("statistics", statistics);
            problem.set("commit", Db.queryStr("select commit from cprogram_commit where uid=? AND cid=? AND num=?", uid, cid, num));
            problem.set("code", ContestSolutionModel.dao.findFirst(
                    "SELECT  MIN(result) as result,any_value(source) as source from contest_solution where uid=? AND cid=? AND num=?", uid, cid, num).getSource());
            ProblemModel p = findProblem(problem.getInt("pid"));
            problem.set("description", p.getDescription());
            problem.set("input", p.getInput());
            problem.set("output", p.getOutput());
        }
    }

    public static void updateCommit(Integer uid, Integer cid, Integer num, String commit) {
        CprogramCommitModel commitModel = CprogramCommitModel.dao.findFirst("select * from cprogram_commit where uid=? and cid=? and num=?",
                uid, cid, num);
        if (commitModel == null) {
            commitModel = new CprogramCommitModel();
            commitModel.setCid(cid).setUid(uid).setNum(num).setCommit(commit);
            commitModel.save();
        } else {
            commitModel.setCommit(commit);
            commitModel.update();
        }
    }

    public static Integer updateFinalCommit(Integer uid, Integer cid, String commit) {
        CprogramExperimentReportModel reportModel = CprogramExperimentReportModel.dao.findFirst(
                "select * from cprogram_experiment_report where uid=? and cid=?", uid, cid);
        if (reportModel == null) {
            return -1;
        } else {
            reportModel.setCommit(commit);
            reportModel.update();
            return 0;
        }
    }

    public static Integer submitReport(Integer uid, Integer cid) {
        CprogramExperimentReportModel myReport = findReportInfoByContestAndUser(cid, uid);
        if (myReport == null) {
            return -2;
        }
        CprogramExperimentReportModel otherReport = CprogramExperimentReportModel.dao.findFirst(
                "SELECT\n" +
                        "\t*\n" +
                        "FROM\n" +
                        "\tcprogram_experiment_report\n" +
                        "WHERE\n" +
                        "\tposition = ?\n" +
                        "AND machine = ?\n" +
                        "AND times = ?\n" +
                        "AND `week` = ?\n" +
                        "AND lecture = ?\n AND uid!=? AND status=1",
                myReport.getPosition(), myReport.getMachine(), myReport.getTimes(), myReport.getWeek(), myReport.getLecture(), uid);
        if (otherReport != null) {
            return -1;
        }
        myReport.setStatus(true);
        myReport.update();
        Db.update("update score set score2=score1 where uid=? and cid=?", uid, cid);
        return 0;
    }
}
