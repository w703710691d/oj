package com.swustacm.poweroj.mapper;

import com.swustacm.poweroj.user.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
