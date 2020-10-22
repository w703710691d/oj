package com.swustacm.poweroj.biz;

import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.util.DateConvert;
import com.swustacm.poweroj.common.util.CollectionUtils;
import com.swustacm.poweroj.common.util.ExcelUtils;
import com.swustacm.poweroj.common.util.FileUtil;
import com.swustacm.poweroj.entity.ExperienceScore;
import com.swustacm.poweroj.mapper.ContestMapper;
import com.swustacm.poweroj.mapper.ScoreMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xingzi
 */
@Component
public class ScoreBiz {
    @Autowired
    ContestMapper contestMapper;
    @Autowired
    ScoreMapper scoreMapper;

    public CommonResult<List<Map<String, Object>>> getScoreTime(String startTime, String endTime) {
        Long start = DateConvert.coverTimeToLong(startTime, DateConvert.YEAR_DATE_TIME);
        Long end = DateConvert.coverTimeToLong(endTime, DateConvert.YEAR_DATE_TIME);
        //获取该时间段所有的Cid
        List<String> cids = contestMapper.getExperimentCid(start,end);
        //sql构建
        String sql = scoreBuild(cids,start,end);
        return CommonResult.ok(scoreMapper.getScoreByTime(sql));
    }

    public CommonResult<List<Map<String, Object>>> getSemesterByYear(String year, DateConvert semester) {
        Long start,end;
        System.out.println(semester);
        if(semester == DateConvert.AUTUMN_TERM){
              start = DateConvert.getSemesterByYear(year, DateConvert.AUTUMN_TERM_BEGIN);
              end = DateConvert.getSemesterByYear(String.valueOf(Integer.parseInt(year)+1), DateConvert.AUTUMN_TERM_END);
        }else{
            start = DateConvert.getSemesterByYear(year, DateConvert.SPRING_TERM_BEGIN);
            end = DateConvert.getSemesterByYear(year, DateConvert.SPRING_TERM_END);
        }
        System.out.println(start);
        System.out.println(end);
        //获取该时间段所有的Cid
        List<String> cids = contestMapper.getExperimentCid(start,end);
        //sql构建
        String sql = scoreBuild(cids,start,end);
        return CommonResult.ok(scoreMapper.getScoreByTime(sql));
    }










    public static String scoreBuild(List<String> cids) {
        return buildSql(cids,null,null,null,null);
    }

    public static String scoreBuild(List<String> cids,List<String> columns) {
        return buildSql(cids,columns,null,null,null);
    }
    public static String scoreBuild(List<String> cids,List<String> columns,List<String> conditions) {
        return buildSql(cids,columns,conditions,null,null);
    }

    public static String scoreBuild(List<String> cids,Long start, Long end) {
        return buildSql(cids,null,null,start,end);
    }

    public static String scoreBuild(List<String> cids, List<String> columns,Long start, Long end) {
        return buildSql(cids,columns,null,start,end);
    }
    public static String scoreBuild(List<String> cids, List<String> columns,List<String> conditions ,Long start, Long end) {
        return buildSql(cids,columns,conditions,start,end);
    }
    /**
     * 构建查询成绩的sql
     *
     * @param cids  实验题号
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    private static String buildSql(List<String> cids, List<String> columns,List<String> conditions,Long start, Long end){
        return new SQL() {{
            if(! CollectionUtils.isEmpty(columns)){
                for (String column : columns) {
                    SELECT(column);
                }
            }
            SELECT("c.tid" ,"(select realName from user where uid = c.tid)as teacher ");
            SELECT("classes", "`name`", "realName");
            for (int i = 0; i < cids.size(); i++) {
                SELECT("max(case s.cid WHEN " + cids.get(i) + " THEN score1 end) ac" + (i + 1),
                        "max(case s.cid WHEN " + cids.get(i) + " THEN score2 end) rep" + (i + 1));
            }
            FROM(" score as s ");
            LEFT_OUTER_JOIN("user as u on u.uid = s.uid");
            LEFT_OUTER_JOIN("cprogram_user_info as c ON c.uid = s.uid");
            if (!CollectionUtils.isEmpty(cids)) {
                String cidStr = StringUtils.join(cids.toArray(), ",");
                WHERE("s.cid in(" + cidStr + ")");
            }
            if (start != null && end != null) {
                WHERE("s.ctime between " + start + " and " + end);
            }
            if (! CollectionUtils.isEmpty(conditions)) {
                for (String condition : conditions) {
                    WHERE(condition);
                }
            }
            GROUP_BY("u.`name`");
        }}.toString();
    }
    public void getScoreExcel(HttpServletResponse response) throws IOException {
        String path = "D:/4455454.xlsx";
        List<ExperienceScore> experienceScoreList = new ArrayList<>();
        ExperienceScore experienceScore = new ExperienceScore();
        experienceScore.setAc1(100);
        experienceScoreList.add(experienceScore);

        List<String> header = new ArrayList<String>(){{add("班级");add("学号");add("姓名");add("第1次实验成绩");add("第1次报告成绩");add("第2次实验成绩");add("第2次报告成绩");add("第3次实验成绩");add("第3次报告成绩");add("第4次实验成绩");add("第4次报告成绩");add("第5次实验成绩");add("第5次报告成绩");add("第6次实验成绩");add("第6次报告成绩");add("第7次实验成绩");add("第7次报告成绩");add("第8次实验成绩");add("第8次报告成绩");}};
        Workbook workbook = ExcelUtils.exportData(experienceScoreList,header,ExcelUtils.XLSX);
        OutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();
        FileUtil.exportFile(response, path,"4455454");

    }
}
