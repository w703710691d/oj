package com.swustacm.poweroj.common.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页 参数
 * @author zcy
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageParam {

    @NotNull(message = "当前页数不能为空")
    @Min(value = 1, message = "当前页数不能小于{value}")
    private Integer page;


    @NotNull(message = "每页显示条数不能为空")
    @Min(value = 1, message = "每页显示条数不能小于{value}")
    @Max(value = 100, message = "每页显示条数不能大于{value}")
    private Integer limit;

    public PageParam getPageParam() {
        PageParam pageParam = new PageParam();
        pageParam.setLimit(this.getLimit());
        pageParam.setPage(this.getPage());
        return pageParam;
    }

    public PageParam() {
    }

    public PageParam(@NotNull(message = "当前页数不能为空") @Min(value = 1, message = "当前页数不能小于{value}") Integer page, @NotNull(message = "每页显示条数不能为空") @Min(value = 1, message = "每页显示条数不能小于{value}") @Max(value = 200, message = "每页显示条数不能大于{value}") Integer limit) {
        this.page = page;
        this.limit = limit;
    }
}

