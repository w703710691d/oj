package com.swustacm.poweroj.biz;

import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.util.DateConvert;
import com.swustacm.poweroj.common.util.ListUtils;
import com.swustacm.poweroj.mapper.ContestMapper;
import com.swustacm.poweroj.mapper.ScoreMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
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












    public static String scoreBuild(List<String> cids) {
      return buildSql(cids,null,null,null);
    }

    public static String scoreBuild(List<String> cids,List<String> columns) {
        return buildSql(cids,columns,null,null);
    }

    public static String scoreBuild(List<String> cids,Long start, Long end) {
        return buildSql(cids,null,start,end);
    }

    public static String scoreBuild(List<String> cids, List<String> columns,Long start, Long end) {
        return buildSql(cids,columns,start,end);
    }
    /**
     * 构建查询成绩的sql
     *
     * @param cids  实验题号
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    private static String buildSql(List<String> cids, List<String> columns,Long start, Long end){
        return new SQL() {{
            if(! ListUtils.isEmpty(columns)){
                for (String column : columns) {
                    SELECT(column);
                }
            }
            SELECT("classes", "`name`", "realName");
            for (int i = 0; i < cids.size(); i++) {
                SELECT("max(case s.cid WHEN " + cids.get(i) + " THEN score1 end) ac" + (i + 1),
                        "max(case s.cid WHEN " + cids.get(i) + " THEN score2 end) rep" + (i + 1));
            }
            FROM(" score as s ");
            LEFT_OUTER_JOIN("user as u on u.uid = s.uid");
            LEFT_OUTER_JOIN("cprogram_user_info as c ON c.uid = s.uid");
            if (!ListUtils.isEmpty(cids)) {
                String cidStr = StringUtils.join(cids.toArray(), ",");
                WHERE("s.cid in(" + cidStr + ")");
            }
            if (start != null && end != null) {
                WHERE("s.ctime between " + start + " and " + end);
            }
            GROUP_BY("u.`name`");
        }}.toString();
    }

}
