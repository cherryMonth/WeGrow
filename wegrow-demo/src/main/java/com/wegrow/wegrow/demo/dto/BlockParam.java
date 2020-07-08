package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class BlockParam {
    @ApiModelProperty(value = "用户创建的内容名称")
    @NotEmpty(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "所属的专题ID")
    @NotNull(message = "专题不能为空")
    private Integer topicId;

    @ApiModelProperty(value = "内容的初始状态")
    private Integer status;

    @ApiModelProperty(value = "用户创建的内容")
    @NotEmpty(message = "内容不能为空")
    private String blockContent;
}
