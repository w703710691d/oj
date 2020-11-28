package com.swustacm.poweroj.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.contest.entity.Contests;
import com.swustacm.poweroj.user.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xingzi
 * @since 2020-09-20
 */
@Service
public interface UserMapper extends BaseMapper<User> {

    List<Role> getUserRole(int uid);

    List<String> getUserPermission(int id);

    String emailCheck(String email);

    String nameCheck(String name);

    int getUserId(String name);

    void addRoleById(int uid);

    void createUserExt(int uid);

    User getUserByName(String name);

    List<Permission> getRolePermission(int id);

    Integer getUserRank(int uid);

    List<UserProblemInfo.ProblemList> getUserProblem(int uid, int result);

    List<Contests> getAttendedContests(int uid);

    Integer isUserInContest(@Param(value = "uid") Integer uid, @Param(value = "cid") Integer cid);

    Page<User> getRankList(@Param(value = "page") Page<User> page, @Param(value = "rankFirst") int rankFirst);

    Page<LoginLog> getLoginlog(Page<LoginLog> pages,String userName);

    void saveLoginlog(@Param(value = "uid")int uid, @Param(value = "name")String name,@Param(value = "loginTime") Integer loginTime,@Param(value = "loginIp") String loginIP);
}
