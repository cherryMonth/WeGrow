package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReplyParam {
    @ApiModelProperty(value = "回复所位于的评论的ID")
    @NotNull(message = "被评论的ID不能为空")
    private Integer commentId;

    @ApiModelProperty(value = "回复的内容")
    @NotBlank(message = "回复内容不能为空")
    private String content;

    @ApiModelProperty(value = "被评论的用户")
    @NotNull(message = "被评论的用户不能为空")
    private Integer userId;
}
