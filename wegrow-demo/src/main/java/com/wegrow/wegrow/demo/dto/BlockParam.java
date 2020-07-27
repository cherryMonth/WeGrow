package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlockParam {
    @ApiModelProperty(value = "用户创建的内容名称")
    private String title;

    @ApiModelProperty(value = "所属的专题ID")
    private Integer topicId;

    @ApiModelProperty(value = "内容的初始状态")
    private Integer status;

    @ApiModelProperty(value = "用户创建的内容")
    private String blockContent;

    @ApiModelProperty(value = "文章图像")
    private String blockImage;
}
