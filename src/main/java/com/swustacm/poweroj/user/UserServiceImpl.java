package com.swustacm.poweroj.user;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.common.util.DateConvert;
import com.swustacm.poweroj.contest.entity.Contests;
import com.swustacm.poweroj.user.entity.*;
import com.swustacm.poweroj.user.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xingzi
 * @since 2020-09-20
 */
@Service
public  class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleService userRoleService;
    @Override
    public User updateLogin(User user,String ip) {
        user.setLoginTime(DateConvert.getTime());
        if(ip != null)
            user.setLoginIP(ip);
        this.updateById(user);
        userMapper.saveLoginlog(user.getUid(),user.getName(),user.getLoginTime(),user.getLoginIP());
        user = this.getById(user.getUid());
        return user;
    }

    @Override
    public List<Role> getUserRole(int uid){
        return userMapper.getUserRole(uid);
    }

    @Override
    public List<String> getUserPermission(int id) {
        return userMapper.getUserPermission(id);
    }

    @Override
    public boolean hasRole(String admin) {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null && admin==null)
            return false;
        try{
            return subject.hasRole(admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasRole(List<String> strings, LogicalEnum str){
        Subject subject = SecurityUtils.getSubject();
        boolean[] list = subject.hasRoles(strings);
        if(str.equals(LogicalEnum.OR)){
            for (boolean flag: list) {
                if (flag) {
                    return true;
                }
            }
            return false;
        }
        if (str.equals(LogicalEnum.AND)) {

            for (boolean flag : list) {
                if (!flag) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String nameCheck(String name) {
        return userMapper.nameCheck(name);
    }

    @Override
    public int getUserId(String name) {
        return userMapper.getUserId(name);
    }

    @Override
    public void addRoleById(int uid) {
        userMapper.addRoleById(uid);
    }

    @Override
    public void createUserExt(int uid) {
        userMapper.createUserExt(uid);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);

    }

    @Override
    public List<Permission> getRolePermission(int id) {
        return userMapper.getRolePermission(id);
    }

    @Override
    public Integer getUserRank(int uid) {

        return userMapper.getUserRank(uid);

    }

    @Override
    public List<UserProblemInfo.ProblemList> getSubmittedProblem(int uid,int result) {
        return userMapper.getUserProblem(uid,result);
    }



    @Override
    public List<UserProblemInfo.ProblemList> getSubmittedProblem(int uid) {
        return this.getSubmittedProblem(uid,-1);
    }
    @Override
    public List<Contests> getAttendedContests(int uid) {

        List<Contests> list =  userMapper.getAttendedContests(uid);
        list.removeIf(contests -> contests.getType() == 4 && !canAccessTestContest(contests.getCid(), uid));
        return list;
    }

    @Override
    public Page<User> getRankList(PageParam page, int rankFirst) {
        Page<User> pages = new Page<>(page.getPage(),page.getLimit());
        return userMapper.getRankList(pages,rankFirst);
    }

    @Override
    public Page<LoginLog> getLoginLog(PageParam page,String userName) {

        Page<LoginLog> pages = new Page<>(page.getPage(),page.getLimit());
        return userMapper.getLoginlog(pages,userName);
    }

    public boolean canAccessTestContest(Integer cid,Integer uid){
        if(hasRole(GlobalConstant.ADMIN)){
            return true;
        }
        Integer id = userMapper.isUserInContest(uid,cid);
        Boolean flag;
        if(id == 0 || id == null){
            flag =false;
        }
        else{
            flag = true;
        }
        return uid != null && flag;
    }


    @Override
    public String emailCheck(String email) {

        return userMapper.emailCheck(email);
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    public List<Role> getUserRoles(Integer uid) {
        return userRoleService.getRolesByUid(uid);
    }
}
