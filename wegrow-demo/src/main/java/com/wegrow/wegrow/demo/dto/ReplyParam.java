package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;

public class ReplyParam {
    @ApiModelProperty(value = "回复所位于的评论的ID")
    private Integer commentId;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    @ApiModelProperty(value = "评论是否被删除")
    private Integer status;

    @ApiModelProperty(value = "评论的用户")
    private Integer userId;
}
