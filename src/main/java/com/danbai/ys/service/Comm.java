package com.danbai.ys.service;

import com.danbai.ys.entity.Feedback;

import java.util.HashMap;
import java.util.List;

/**
 * @author danbai
 */
public interface Comm {
    /**
     * 获取所有公共内容
     *
     * @return
     */
    HashMap getAllComm();

    /**
     * 添加反馈
     *
     * @param feedback 反馈
     */
    void addFeedback(Feedback feedback);

    /**
     * 获取所有反馈
     *
     * @return
     */
    List<Feedback> getAllFeedback();

    /**
     * 完成反馈处理
     *
     * @param id
     */
    void okFeedback(Integer id);
}
