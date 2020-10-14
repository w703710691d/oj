package com.swustacm.poweroj.problem;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.mapper.ProblemMapper;
import com.swustacm.poweroj.problem.entity.Problem;
import com.swustacm.poweroj.problem.entity.ProblemSearchParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Override
    public Page<Problem> searchProblem(ProblemSearchParam param,Integer status) {
    Page<Problem> page = new Page<>(param.getPage(),param.getLimit());
        return problemMapper.searchAll(page,param.getSource(),param.getTitle(),status);
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
}

