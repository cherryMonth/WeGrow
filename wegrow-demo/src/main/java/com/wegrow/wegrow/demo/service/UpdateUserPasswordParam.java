package com.wegrow.wegrow.demo.service;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UpdateUserPasswordParam {
    @ApiModelProperty(value = "邮箱", required = true)
    @Email(message = "邮箱不能为空")
    private String email;
    @ApiModelProperty(value = "旧密码", required = true)
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
