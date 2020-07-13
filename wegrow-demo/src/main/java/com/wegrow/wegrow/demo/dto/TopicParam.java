package com.wegrow.wegrow.demo.dto;

import com.wegrow.wegrow.demo.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TopicParam {

    @ApiModelProperty(value = "专题名字", required = true)
    @NotEmpty(message = "名称不能为空")
    private String topicName;

    @ApiModelProperty(value = "专题简介")
    private String topicInfo;

    @ApiModelProperty(value = "专题介绍图片HASH", required = true)
    @NotEmpty(message = "专题logo不能为空")
    private String avatarHash;
}
