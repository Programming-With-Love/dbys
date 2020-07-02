package com.danbai.ys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 观看历史类
 *
 * @author danbai
 * @date 2019/10/13
 */
@ApiModel(value = "反馈信息实体类", description = "记录app反馈信息的类")
public class Gkls implements Serializable {

    private static final long serialVersionUID = 1L;
    //最多显示30条历史记录
    /**
     * 最大历史记录
     */
    public static final int MAX = 30;
    @ApiModelProperty(value = "影视图片地址")
    public String ysimg;
    @ApiModelProperty(value = "影视片名")
    public String pm;
    @ApiModelProperty(value = "影视集名")
    public String ji;
    @ApiModelProperty(value = "观看时长")
    public String time;
    @ApiModelProperty(value = "观看的时间")
    public Date gktime;
    @ApiModelProperty(value = "id")
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYsimg() {
        return ysimg;
    }

    public void setYsimg(String ysimg) {
        this.ysimg = ysimg;
    }


    public String getPm() {
        return pm;
    }

    public Date getGktime() {
        return gktime;
    }

    public void setGktime(Date gktime) {
        this.gktime = gktime;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getJi() {
        return ji;
    }

    public void setJi(String ji) {
        this.ji = ji;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
