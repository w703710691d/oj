package com.swustacm.poweroj.topic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.topic.entity.Topic;
import com.swustacm.poweroj.mapper.TopicMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.topic.entity.TopicExtraParam;
import com.swustacm.poweroj.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-30
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public IPage<Topic> index(TopicExtraParam pageParam) {
        String sql = null;
        IPage<Topic> page = new Page<>(pageParam.getPage(),pageParam.getLimit());
        if (pageParam.getPid() != null&&!userService.hasRole(GlobalConstant.ADMIN)){
            sql ="where topic.status = 1 and topic.pid = " + pageParam.getPid() + " and topic.parentId = 0";
            return topicMapper.index(page,sql);
        }else if(pageParam.getPid()!=null&&userService.hasRole(GlobalConstant.ADMIN)){
            sql = " where topic.pid = " + pageParam.getPid() + " and topic.parentId = 0";
            return topicMapper.index(page,sql);
        }else if (pageParam.getPid()==null&&!userService.hasRole(GlobalConstant.ADMIN)){
            sql = "where topic.status = 1 and topic.parentId = 0";
            return topicMapper.index(page,sql);
        }else{
            sql = "where topic.parentId = 0";
            return topicMapper.index(page,sql);
        }
    }

    @Override
    public IPage<Topic> comment(TopicExtraParam param) {
        IPage<Topic> page = new Page<>(param.getPage(),param.getLimit());
        return topicMapper.comment(page,param.getThreadId());
    }

    @Override
    public CommonResult<Topic> addComment(Topic topic) {
        topic.setStatus(true);
        topic.setUid(jwtUtil.getUserInfo().getUid());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date date = new Date();
            String ctime = sdf.format(date);
            topic.setCtime((int)(sdf.parse(ctime).getTime()/1000));
        }catch (ParseException e){
            log.error(e.getLocalizedMessage());
        }
        Integer maxId = topicMapper.getMaxId();
        if (topic.getThreadId() != 0 && topic.getThreadId() != null){
            List<Topic> topics = topicMapper.getAllCommentByThreadId(topic.getThreadId());
            for(Topic topic1 : topics){
                    topic1.setThreadId(maxId+1);
                    topicService.updateById(topic1);
            }
        }else{
            topic.setThreadId(maxId+1);
            topicService.save(topic);
            return CommonResult.ok();
        }
        topic.setThreadId(maxId+1);
        topicService.save(topic);
        return CommonResult.ok();
    }
}