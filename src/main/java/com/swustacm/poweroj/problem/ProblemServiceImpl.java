package com.swustacm.poweroj.problem;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.constant.Constant;
import com.swustacm.poweroj.constant.ResultType;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.mapper.ProblemMapper;
import com.swustacm.poweroj.mapper.SolutionMapper;
import com.swustacm.poweroj.params.PageParam;
import com.swustacm.poweroj.problem.entity.Problem;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.problem.entity.ProblemStatus;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.user.UserService;
import com.swustacm.poweroj.user.entity.LogicalEnum;
import jodd.util.StringUtil;
import org.apache.poi.hssf.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    SolutionMapper solutionMapper;
    @Override
    public Page<Problem> searchProblem(ProblemSearchParam param,Integer status) {
    Page<Problem> page = new Page<>(param.getPage(),param.getLimit());
        return problemMapper.searchAll(page,param.getSource(),param.getTitle(),status,param.getPid());
    }

    @Override
    public Integer getPrevPid(Integer pid, Integer status) {
        Integer prevPid = problemMapper.getPrevPid(pid,status);
        if (prevPid == null)
            return pid;
        return prevPid;


    }

    @Override
    public Integer getNextPid(Integer pid, Integer status) {
        Integer nextPid = problemMapper.getNextPid(pid,status);
        if(nextPid == null)
            return pid;
        return nextPid;
    }

    @Override
    public Integer getRandomPid(Integer pid, Integer status) {
        List<Integer> maxAndMin = problemMapper.getMaxMInPid();
        Random r = new Random();
        Integer randomPid = r.nextInt(maxAndMin.get(0)-maxAndMin.get(1)+maxAndMin.get(0));
        randomPid =problemMapper.getPid(randomPid,status);
        if( randomPid == null)
            return pid;
        return randomPid;
    }

    @Override
    public List<Record> getTags(Integer pid) {
        List<Record> list = problemMapper.getTags(pid);
        if(list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public Integer getUserResult(Integer pid) {
        Integer uid = jwtUtil.getUserInfo().getUid();
        if(uid == null){
            return null;
        }
        return problemMapper.getUserResult(pid,uid);
    }

    @Override
    public Problem getProblemForShow(Integer pid) {
        //oj上为 从 缓冲获取 Problem
        Problem problem = this.getById(pid);
        if(problem == null){
            return null;
        }

        if((!(userService.hasRole("admin") || userService.hasRole("teacher"))) && !problem.getStatus()){
            return null;
        }
        int sampleInputRows = 1;
        if(StringUtil.isNotBlank(problem.getSampleInput())){
            sampleInputRows = StringUtil.count(problem.getSampleInput(),"\n")+1;
        }
        problem.setSampleInputRows(sampleInputRows);
        int sampleOutputRows = 1;
        if(StringUtil.isNotBlank(problem.getSampleOutput())){
            sampleOutputRows = StringUtil.count(problem.getSampleOutput(),"\n")+1;
        }
        problem.setSampleInputRows(sampleOutputRows);
        problem.setView(problem.getView()+1);
        this.updateById(problem);

        return problem;
    }

    @Override
    public Problem getProblem(Integer pid) {
        Problem problem = this.getById(pid);
        if (problem == null){
            return null;
        }
        if(userService.hasRole(Arrays.asList("admin", "teacher"), LogicalEnum.OR)){
            return problem;
        }else if(problem.getStatus()){
            return problem;
        }
        return null;
    }

    @Override
    public List<ProblemStatus> getProblemStatus(Integer pid) {
        List<ProblemStatus> list = problemMapper.getProblemStatus(pid);
        for(ProblemStatus status : list){
            ResultType resultType = Constant.resultType.get(status.getResult());
            status.setName(resultType.getName());
            status.setLongName(resultType.getLongName());
        }
        return list;
    }

    @Override
    public Page<Solution> getProblemUser(Integer pid, PageParam page) {
        Page<Solution> page1 = new Page<>(page.getPage(),page.getLimit());
        return solutionMapper.getSolutionPro(page1,pid);
    }
}

