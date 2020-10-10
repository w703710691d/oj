package com.swustacm.poweroj.problem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.mapper.ProblemMapper;
import com.swustacm.poweroj.problem.entity.Problem;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张成云
 * @since 2020-10-09
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Autowired
    ProblemMapper problemMapper;
    @Override
    public Page<Problem> searchProblem(ProblemSearchParam param,Integer status) {
    Page<Problem> page = new Page<>(param.getPage(),param.getLimit());
        return problemMapper.searchAll(page,param.getSource(),param.getTitle(),status);
    }
}
