package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class UserInfoUpdateParam {

    @ApiModelProperty(value = "用户昵称")
    @NotBlank
    private String username;

    @ApiModelProperty(value = "用户简介")
    private String aboutMe;

    @ApiModelProperty(value = "用户头像HASH")
    @NotBlank
    private String avatarHash;
}
