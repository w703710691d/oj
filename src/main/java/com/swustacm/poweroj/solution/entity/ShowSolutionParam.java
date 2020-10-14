package com.swustacm.poweroj.solution.entity;

import com.swustacm.poweroj.params.PageParam;
import lombok.Data;

@Data
public class ShowSolutionParam extends PageParam {
    private String result;
    private String language;
    private String pid;
    private String userName;
}
