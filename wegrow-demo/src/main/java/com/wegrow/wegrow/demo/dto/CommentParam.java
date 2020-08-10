package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;

public class CommentParam {
    @ApiModelProperty(value = "评论所位于的目标表")
    private Integer targetType;

    @ApiModelProperty(value = "评论所位于的目标表的ID")
    private Integer targetId;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    @ApiModelProperty(value = "评论是否被删除")
    private Integer status;
}
