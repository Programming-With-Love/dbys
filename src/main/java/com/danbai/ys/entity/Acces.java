package com.danbai.ys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统计类
 *
 * @author danbai
 * @date 2019-10-18 12:30
 */
@ApiModel(value = "统计类", description = "统计访问ip的记录")
public class Acces {
    @ApiModelProperty(value = "日期")
    private String name;
    @ApiModelProperty(value = "ip数")
    private int count;

    public Acces(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
