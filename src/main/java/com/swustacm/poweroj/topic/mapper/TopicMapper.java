package com.swustacm.poweroj.topic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.topic.entity.Topic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-30
 */
@Component
public interface TopicMapper extends BaseMapper<Topic> {
    /**
     * topic首页
     * @param page
     * @return
     */
    IPage<Topic> index(IPage<Topic> page,@Param("sql") String sql);

    /**
     * 查看评论
     * @param page
     * @param threadId
     * @return
     */
    IPage<Topic> comment(IPage<Topic> page,@Param("threadId") Integer threadId);

    /**
     * 根据输入的threadId，查出所有一致的评论
     * @param threadId
     * @return
     */
    List<Topic> getAllCommentByThreadId(@Param("threadId") Integer threadId);

}