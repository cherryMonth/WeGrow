package com.wegrow.wegrow.demo.dto;

import io.swagger.annotations.ApiModelProperty;

public class BlockCommentParam {
    @ApiModelProperty(value = "评论所位于的block")
    private Integer blockId;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    @ApiModelProperty(value = "评论是否是根节点")
    private Integer status;

    @ApiModelProperty(value = "评论是否有父节点")
    private Integer parentId;
}
