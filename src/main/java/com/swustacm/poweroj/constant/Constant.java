package com.swustacm.poweroj.constant;

import com.swustacm.poweroj.problem.entity.ProgramLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {
    public static Map<Integer, ResultType> resultType;
    public static List<ResultType> judgeResult;
    public static List<ProgramLanguage> programLanguages;
    public static Map<Integer, ProgramLanguage> languageType;
    public static Map<Integer, String> languageName;
    public static Map<String, Integer> languageID;

    public static void initJudgeResult() {
        judgeResult = new ArrayList<>();
        judgeResult.add(new ResultType(ResultType.AC, "AC", "Accepted"));
        judgeResult.add(new ResultType(ResultType.PE, "PE", "Presentation Error"));
        judgeResult.add(new ResultType(ResultType.WA, "WA", "Wrong Answer"));
        judgeResult.add(new ResultType(ResultType.TLE, "TLE", "Time Limit Exceed"));
        judgeResult.add(new ResultType(ResultType.MLE, "MLE", "Memory Limit Exceed"));
        judgeResult.add(new ResultType(ResultType.OLE, "OLE", "Output Limit Exceed"));
        judgeResult.add(new ResultType(ResultType.CE, "CE", "Compile Error"));
        judgeResult.add(new ResultType(ResultType.RF, "RF", "Restricted Function"));
        judgeResult.add(new ResultType(ResultType.RE, "RE", "Runtime Error"));
        judgeResult.add(new ResultType(ResultType.SE, "SE", "System Error"));
        judgeResult.add(new ResultType(ResultType.VE, "VE", "Validate Error"));
        judgeResult.add(new ResultType(ResultType.RUN, "RUN", "Running"));
        judgeResult.add(new ResultType(ResultType.WAIT, "WAIT", "Waiting"));
        judgeResult.add(new ResultType(ResultType.REJUDGE, "REJUDGE", "Rejudging"));
        judgeResult.add(new ResultType(ResultType.SIM, "SIM", "Similar"));
        judgeResult.add(new ResultType(ResultType.COM, "COM", "Compiling"));
        judgeResult.add(new ResultType(ResultType.QUE, "QUE", "Queuing"));
        resultType = new HashMap<>();
        for (ResultType result : judgeResult) {
            resultType.put(result.getId(), result);
        }
    }

    public static void loadLanguage() {

        languageType = new HashMap<>();
        languageName = new HashMap<>();
        languageID = new HashMap<>();
        programLanguages = ProgramLanguage.dao.selectAll();

        for (ProgramLanguage language : programLanguages) {
            languageType.put(language.getId(), language);
            languageID.put(language.getName(), language.getId());
            languageName.put(language.getId(), language.getName());
        }
    }

}
