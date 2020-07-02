package com.danbai.ys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@ApiModel(value = "反馈信息实体类", description = "记录app反馈信息的类")
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "类型")
    private Integer type;//1bug反馈   2求片
    @ApiModelProperty(value = "时间")
    private Date time;
    @ApiModelProperty(value = "处理")
    private Boolean dispose;
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return dispose
     */
    public Boolean getDispose() {
        return dispose;
    }

    /**
     * @param dispose
     */
    public void setDispose(Boolean dispose) {
        this.dispose = dispose;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}