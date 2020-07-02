package com.danbai.ys.service.impl;

import com.danbai.ys.entity.Config;
import com.danbai.ys.entity.Feedback;
import com.danbai.ys.mapper.FeedbackMapper;
import com.danbai.ys.service.Comm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author danbai
 * @date 2019-11-07 09:57
 */
@Service
public class CommImpl implements Comm {
    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public HashMap getAllComm() {
        HashMap<String, Object> map = new HashMap(20);
        map.put(Config.GG, adminService.getConfig(Config.GG));
        map.put(Config.YLINK, adminService.getYlink());
        map.put(Config.AD, adminService.getConfig(Config.AD));
        map.put(Config.FOOTER, adminService.getConfig(Config.FOOTER));
        map.put(Config.HEAD, adminService.getConfig(Config.HEAD));
        return map;
    }

    @Override
    public void addFeedback(Feedback feedback) {
        feedbackMapper.insert(feedback);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackMapper.selectAll();
    }

    @Override
    public void okFeedback(Integer id) {
        Feedback feedback = new Feedback();
        feedback.setId(id);
        Feedback feedback1 = feedbackMapper.selectOne(feedback);
        feedback1.setDispose(true);
        feedbackMapper.updateByPrimaryKey(feedback1);
    }
}
