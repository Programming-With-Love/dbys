package com.danbai.ys.controller.restful.v1;

import com.danbai.ys.entity.BaseResult;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * rest 风格 api
 *
 * @author danbai
 * @date 2019-10-29 14:43
 */
@RestController
@RequestMapping("/api/v1")
public class Ys {
    @Autowired
    YsServiceImpl ysService;

    /**
     * 获取所有影视
     *
     * @return BaseResult
     */
    @GetMapping("/ys")
    public BaseResult ys() {
        return ResultUtil.success(ysService.getAll());
    }

    /**
     * 根据id获取影视
     * @param id 影视id
     * @return BaseResult
     */
    @GetMapping("/ys/{id}")
    public BaseResult ysOne(@PathVariable int id) {
        return ResultUtil.success(ysService.selectYsById(id));
    }

    /**
     * 根据关键词搜索影视
     *
     * @param gjc 关键词
     * @return BaseResult
     */
    @GetMapping("/ys/search/{gjc}")
    public BaseResult search(@PathVariable String gjc) {
        return ResultUtil.success(ysService.qcsy(ysService.selectYsByGjc(gjc)));
    }
    @GetMapping("/ys/tv")
    public BaseResult tv(){
        return ResultUtil.success(ysService.getAllTv());
    }
}
