package com.swustacm.poweroj.honor;

import com.swustacm.poweroj.honor.entity.Honors;
import com.swustacm.poweroj.mapper.HonorsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhihao
 * @since 2020-10-23
 */
@Service
public class HonorsServiceImpl extends ServiceImpl<HonorsMapper, Honors> implements HonorsService {


    @Autowired
    HonorsMapper honorsMapper;

    @Override
    public List<Honors> getHonors() {
        return honorsMapper.getHoners();
    }

}
