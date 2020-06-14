package com.wegrow.wegrow.demo.dto;

import com.wegrow.wegrow.demo.validator.FlagValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/***
 * 用于向客户端传输对象
 */

@ApiModel(value = "Block")

public class BlockDto {
    private Integer id;

    private Integer userId;

    @ApiModelProperty(value = "用户创建的内容名称")
    @NotNull(message="名称不能为空")
    private String title;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "文章状态:是否被封禁")
    private Byte status;

    @ApiModelProperty(value = "所属的专题ID")
    @NotNull(message="专题ID不能为空")
    private Integer topicId;

    @NotNull(message = "内容不能为空")
    @ApiModelProperty(value = "用户创建的内容")
    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getTopicId() {
        return topicId;
    }
     public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
