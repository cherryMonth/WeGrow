package com.wegrow.wegrow.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {
    private Integer id;

    private Integer userId;

    @ApiModelProperty(value = "用户创建的内容名称")
    private String title;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "文章状态:是否可用")
    private Integer status;

    @ApiModelProperty(value = "所属的专题ID")
    private Integer topicId;

    @ApiModelProperty(value = "用户创建的内容")
    private String blockContent;

    private String blockImage;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getBlockContent() {
        return blockContent;
    }

    public void setBlockContent(String blockContent) {
        this.blockContent = blockContent;
    }

    public String getBlockImage() {
        return blockImage;
    }

    public void setBlockImage(String blockImage) {
        this.blockImage = blockImage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", title=").append(title);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", topicId=").append(topicId);
        sb.append(", blockContent=").append(blockContent);
        sb.append(", blockImage=").append(blockImage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}