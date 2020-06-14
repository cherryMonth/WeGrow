package com.wegrow.wegrow.demo.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 类似于Flask的Form，用于表单验证
 */
@Getter
@Setter
public class UserParam {

    @ApiModelProperty(value = "用户邮箱")
    @Email(message = "邮箱格式不合法")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    @NotEmpty(message = "用户昵称不能为空")
    private String username;

    @ApiModelProperty(value = "用户简介")
    private String aboutMe;

    @ApiModelProperty(value = "用户头像HASH")
    private String avatarHash;
}
