package com.wegrow.wegrow.demo.controller;

import com.wegrow.wegrow.common.api.CommonPage;
import com.wegrow.wegrow.common.api.CommonResult;
import com.wegrow.wegrow.demo.dto.FollowParam;
import com.wegrow.wegrow.demo.service.FollowService;
import com.wegrow.wegrow.model.Follow;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @ApiOperation(value = "获取全部关注Block")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Follow>> getAllList() {
        return CommonResult.success(followService.listAllRelationship());
    }

    @ApiOperation(value = "创建关系")
    @RequestMapping(value = "/createRelationship", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> createRelationship(@RequestBody @Valid FollowParam followParam, BindingResult bindingResult, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        if (followService.createRelationship(followParam, principal.getName()) > 0) {
            return CommonResult.success("创建成功");
        } else {
            return CommonResult.failed("创建失败, 请检查内容是否正确!");
        }
    }

    @ApiOperation(value = "获取指定用户关注的关系列表")
    @RequestMapping(value = "/listFollowingByPrincipalName", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CommonPage<Follow>> listFollowingByPrincipalName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        List<Follow> followList = followService.listFollowingByPrincipalName(principal.getName(), pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(followList));
    }

    @ApiOperation(value = "获取指定用户被关注的关系列表")
    @RequestMapping(value = "/listFollowedByPrincipalName", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CommonPage<Follow>> listFollowedByPrincipalName(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        List<Follow> followList = followService.listFollowedByPrincipalName(principal.getName(), pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(followList));
    }

    @ApiOperation(value = "根据编号查询关注信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Follow> getItem(@PathVariable("id") Integer id) {
        return CommonResult.success(followService.getFollow(id));
    }

    @ApiOperation(value = "删除用户关注的记录")  // 从表单中获取数据
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable("id") Integer id, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        int count = followService.delete(principal.getName(), id);
        if (count == 1) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "批量更新关注状态")   // 从表单中获取数据
    @RequestMapping(value = "/update/updateBlockStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> updateBlockStatus(@RequestParam("ids") List<Integer> ids,
                                                  @RequestParam("updateTopicStatus") Integer updateTopicStatus, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        int count = followService.updateFollowStatus(principal.getName(), ids, updateTopicStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed("更新失败，请检查输入列表!");
        }
    }
}
