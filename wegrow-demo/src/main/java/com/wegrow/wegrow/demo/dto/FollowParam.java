package com.wegrow.wegrow.demo.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FollowParam {
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "关注者不能为空")
    private Integer userId;

    @ApiModelProperty(value = "用户关注的人的ID")
    @NotNull(message = "被关注者不能为空")
    private Integer followedUserId;
}
