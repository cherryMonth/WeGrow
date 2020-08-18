package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdateUserPasswordParam {
    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
