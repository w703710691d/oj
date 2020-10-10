package com.swustacm.poweroj.problem.entity;

import com.swustacm.poweroj.params.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Problem页面参数
 * @author zcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemSearchParam extends PageParam {
        private String pid;
        private String title;
        private String source;
}
