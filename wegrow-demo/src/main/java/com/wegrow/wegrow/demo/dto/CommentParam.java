package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentParam {
    @ApiModelProperty(value = "评论所位于的目标表")
    @NotBlank(message = "目标类型不能为空")
    private String targetType;

    @ApiModelProperty(value = "评论所位于的目标表的ID")
    @NotNull(message = "目标ID不能为空")
    private Integer targetId;

    @ApiModelProperty(value = "评论的内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty(value = "返回添加的评论所位于的页数")
    private Integer pageSize;
}
