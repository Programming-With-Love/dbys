package com.danbai.ys.controller.restful.v1;

import com.danbai.ys.entity.*;
import com.danbai.ys.service.UserService;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;


/**
 * rest 风格 api
 *
 * @author danbai
 * @date 2019-10-29 14:43
 */
@RestController
@RequestMapping("/api/v1")
@ApiOperation(value ="影视相关api App用")
public class Ys {
    @Autowired
    YsServiceImpl ysService;
    @Autowired
    UserService userService;

    /**
     * 根据id获取影视
     *
     * @param id 影视id
     * @return BaseResult
     */
    @GetMapping("/ys/{id}")
    @ApiOperation(value ="获取影视根据id")
    public BaseResult ysOne(@PathVariable Integer id) {
        return ResultUtil.success(ysService.selectYsById(id));
    }

    /**
     * 根据关键词搜索影视
     *
     * @param gjc 关键词
     * @return BaseResult
     */
    @GetMapping("/ys/search/{gjc}")
    @ApiOperation(value ="根据关键词搜索影视")
    public BaseResult search(@PathVariable String gjc) {
        if (gjc.length() > 1) {
            return ResultUtil.success(ysService.qcsy(ysService.selectYsByGjc(gjc)));
        }
        return ResultUtil.success(ysService.qcsy(ysService.selectYsByPm(gjc)));
    }

    @GetMapping("/ys/tv")
    @ApiOperation(value ="获取tv列表")
    public BaseResult tv() {
        return ResultUtil.success(ysService.getAllTv());
    }

    @GetMapping("ys/type")
    @ApiOperation(value ="获取类型影视")
    public BaseResult type(String type1, String type2, String region, String year, String sort, @NotNull int page) {
        if (type2.indexOf("'") > -1 | region.indexOf("'") > -1 | year.indexOf("'") > -1) {
            return ResultUtil.error("非法字符");
        }
        return ResultUtil.success(ysService.getByType(type1, type2, region, year, sort, page));
    }
    @PostMapping("/ys/time")
    @ApiOperation(value ="观看时间更新api")
    public void time(VideoTime videoTime, Token token) {
        if(userService.checkToken(token)){
            ysService.addYsTime(videoTime);
        }
    }
    @GetMapping("/ysAndLs")
    @ApiOperation(value ="获取影视的同时获取观看时间")
    public BaseResult getys(Integer id,Token token){
        Map<String, Object> map = new HashMap<>(5);
        Ysb ys = ysService.selectYsById(id);
        map.put("ys", ys);
        if(userService.checkToken(token)){
                HashMap ysLs = ysService.getYsLs(token.getUsername(), id);
                if (ysLs != null) {
                    map.put("gkls", ysLs);
                    VideoTime videoTime = new VideoTime();
                    videoTime.setUsername(token.getUsername());
                    videoTime.setYsid(id);
                    videoTime.setYsjiname((String) ysLs.get("jiname"));
                    map.put("time", ysService.getYsTime(videoTime));
                }
        }
        return ResultUtil.success(map);
    }
}
