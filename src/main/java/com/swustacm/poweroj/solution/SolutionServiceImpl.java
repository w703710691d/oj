package com.swustacm.poweroj.solution;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.solution.entity.ShowSolutionParam;
import com.swustacm.poweroj.solution.entity.Solution;
import com.swustacm.poweroj.mapper.SolutionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-11
 */
@Service
public class SolutionServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements SolutionService {

    @Autowired
    SolutionMapper solutionMapper;
    @Override
    public Page<Solution> showSolution(ShowSolutionParam param,Integer status) {
        Page<Solution> solutionPage = new Page<>(param.getPage(),param.getLimit());
        return solutionMapper.solutionPage(solutionPage,param.getPid(),param.getUserName(),param.getLanguage(),param.getResult(),status);
    }

    @Override
    public Solution findSolution(Integer sid) {
        return solutionMapper.findSolution(sid);
    }

    @Override
    public String getProblemTitle(Integer cid, Integer num) {
        return  solutionMapper.getProblemTitle(cid,num);
    }

    @Override
    public String getLanguage(Integer key) {
        return solutionMapper.getLanguage(key);
    }

    @Override
    public User findUser(Integer uid) {
        return solutionMapper.getUser(uid);
    }

}
