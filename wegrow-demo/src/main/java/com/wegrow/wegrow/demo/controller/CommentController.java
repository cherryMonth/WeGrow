package com.wegrow.wegrow.demo.controller;

import com.wegrow.wegrow.common.api.CommonResult;
import com.wegrow.wegrow.demo.dto.CommentParam;
import com.wegrow.wegrow.demo.dto.ReplyParam;
import com.wegrow.wegrow.demo.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@Api(tags = "CommentController")
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "创建评论内容")
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    @ResponseBody  // @valid后面必须接着bindingResult才能使用我们自己的返回结果类型
    public CommonResult<Object> create(@RequestBody @Valid CommentParam commentParam, BindingResult bindingResult, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();

        long result = commentService.createComment(username, commentParam);
        if (result > 0) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed("创建失败, 请检查内容是否正确!");
        }
    }

    @ApiOperation(value = "创建评论内容")
    @RequestMapping(value = "/createReplyComment", method = RequestMethod.POST)
    @ResponseBody  // @valid后面必须接着bindingResult才能使用我们自己的返回结果类型
    public CommonResult<Object> createReplyComment(@RequestBody @Valid ReplyParam replyParam, BindingResult bindingResult, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();

        int result = commentService.createReplyComment(username, replyParam);
        if (result > 0) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed("创建失败, 请检查内容是否正确!");
        }
    }


    @ApiOperation(value = "获取评论")
    @RequestMapping(value = "/getCommentList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> getCommentList(@RequestParam(value = "targetType") String targetType,
                                               @RequestParam(value = "targetId") Integer targetId,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }

        List<Object> result = commentService.getCommentList(targetType, targetId, pageNum, pageSize);
        long pagePaginationNum = commentService.getCountNum(targetType, targetId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("commentList", result);
        hashMap.put("pagePaginationNum", (pagePaginationNum + pageSize - 1) / pageSize);
        return CommonResult.success(hashMap);
    }
}
