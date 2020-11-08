package com.swustacm.poweroj.topic;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.topic.entity.Topic;
import com.swustacm.poweroj.topic.entity.TopicExtraParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-30
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    TopicBiz topicBiz;

    /**
     * 输入pid可以根据题目的pid查topic但是pid不是必填项
     * @param page
     * @return
     */
    @PostMapping("/index")
    public CommonResult<IPage<Topic>> index(@RequestBody @Validated TopicExtraParam page
    ){
        return CommonResult.ok(topicService.index(page));
    }

    /**
     * 根据topic id删除topic（仅仅是topic的状态改变，普通用户无法查看）
     * @param id
     * @return
     */
    @PostMapping("/deleteTopic")
    public CommonResult<Topic> deleteTopic(@RequestParam  Integer id){
        return topicBiz.deleteTopic(id);
    }


    /**
     * 此操作会彻底删除Topic
     * @param id
     * @return
     */
    @PostMapping("/removeTopic")
    public CommonResult<Topic> removeTopic(@RequestParam Integer id){
        return topicBiz.removeTopic(id);
    }

    /**
     * 查看单个topic下所有的回复
     * threadID 必须输入
     * @param param
     * @return
     */
    @PostMapping("/comment")
    public CommonResult<IPage<Topic>> comment(@RequestBody TopicExtraParam param){
        return CommonResult.ok(topicService.comment(param));
    }

    /**
     * 如果是发表topic，则不输入评论topic id，如果是回复topic，则需要传入需要回复的评topic id
     * @param topic
     * @return
     */
    @PostMapping("/addComment")
    public CommonResult addComment(@RequestBody Topic topic) {
        return CommonResult.ok(topicService.addComment(topic));
    }

}

