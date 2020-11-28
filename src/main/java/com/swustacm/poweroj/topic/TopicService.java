package com.swustacm.poweroj.topic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.topic.entity.Topic;
import com.swustacm.poweroj.topic.entity.TopicExtraParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-30
 */
public interface TopicService extends IService<Topic> {
    /**
     * 评论首页
     * @return
     */
    IPage<Topic> index(TopicExtraParam page);

    /**
     * 根据threadID查Topic
     * @param param
     * @return
     */
    IPage<Topic> comment(TopicExtraParam param);

    /**
     * 追加评论
     * @param topic
     * @return
     */
    CommonResult<Topic> addComment(Topic topic);

}
