package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 类似于Flask的Form，用于表单验证
 */
@Getter
@Setter
public class UserParam {

    @ApiModelProperty(value = "用户邮箱", required = true)
    @NotBlank
    @Email(message = "邮箱格式不合法")
    private String email;

    @ApiModelProperty(value = "用户昵称", required = true)
    @NotBlank(message = "用户昵称不能为空")
    private String username;

    @ApiModelProperty(value = "用户简介")
    private String aboutMe;

    @ApiModelProperty(value = "用户头像HASH", required = true)
    @NotBlank
    private String avatarHash;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty
    @Size(min=6, max = 20, message = "密码长度应该在6和20之间")
    private String password;
}
