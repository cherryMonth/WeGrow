package com.wegrow.wegrow.demo.controller;

import com.wegrow.wegrow.common.api.CommonPage;
import com.wegrow.wegrow.common.api.CommonResult;
import com.wegrow.wegrow.demo.dto.BlockParam;
import com.wegrow.wegrow.demo.service.BlockService;
import com.wegrow.wegrow.model.Topic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.wegrow.wegrow.model.Block;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * 用户级别URL
 */

@Controller
@Api(tags = "BlockController")
@RequestMapping("/block")
public class BlockController {
    @Autowired
    private BlockService blockService;

    // 用于用户根据关键词查询系统的文章，只能查看状态为2的文章
    @ApiOperation(value = "根据关键词获取Block")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Block>> getListByKeyWord(@RequestParam(value = "keyword", required = false) String keyword,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<Block> blockList = blockService.listBlock(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(blockList));
    }

    @ApiOperation(value = "根据状态获取用户自己的Block")
    @RequestMapping(value = "/getBlockByStatus", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Block>> getOwnListByStatus(@RequestParam(value = "status") Integer status,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        List<Block> blockList = blockService.listBlockByStatus(principal.getName(), status, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(blockList));
    }

    @ApiOperation(value = "获取指定用户的Block")
    @RequestMapping(value = "/BlocklistByUserId", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Block>> Blocklist(@RequestParam(value = "userId") Integer userId,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<Block> blockList = blockService.listBlockByUserId(userId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(blockList));
    }

    @ApiOperation(value = "根据关键词获取用户自己的Block")
    @RequestMapping(value = "/MyOwnlist", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Block>> getOwnListByKeyWord(@RequestParam(value = "keyword", required = false) String keyword,
                                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        List<Block> blockList = blockService.listBlock(principal.getName(), keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(blockList));
    }

    @ApiOperation(value = "创建内容")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody  // @valid后面必须接着bindingResult才能使用我们自己的返回结果类型
    public CommonResult<Object> create(@RequestBody @Valid BlockParam blockParam, BindingResult bindingResult, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();

        // 插入之后返回主键
        int result = blockService.createBlock(blockParam, username);
        if (result > 0) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed("创建失败, 请检查内容是否正确!");
        }
    }

    @ApiOperation(value = "更新block")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody  // PathVariable 从url中获取
    public CommonResult<Object> update(@PathVariable("id") Integer id,
                                       Principal principal,
                                       @RequestBody @Valid BlockParam blockParam,
                                       BindingResult bindingResult) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        int result = blockService.updateBlock(blockParam, principal.getName(), id);
        if (result > 0) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed("请检查内容信息是否合法!");
        }
    }

    @ApiOperation(value = "删除block")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable("id") Integer id, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        int count = blockService.deleteBlock(principal.getName(), id);
        if (count == 1) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "批量删除Block")  // 从表单中获取数据
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> deleteBatch(@RequestParam("ids") List<Integer> ids, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        int count = blockService.deleteBlock(principal.getName(), ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed("删除失败，请检查输入列表!");
        }
    }

    @ApiOperation(value = "根据编号查询block信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Block> getUserItem(@PathVariable("id") Integer id, Principal principal) {
        Block result = blockService.getBlockByUserPermission(principal.getName(), id);
        if (result == null) {
            return CommonResult.failed("检索失败，请检查输入内容是否存在!");
        }
        return CommonResult.success(result);
    }

    @ApiOperation(value = "根据编号查询block信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Block> getItem(@PathVariable("id") Integer id, Principal principal) {
        Block result = blockService.getBlock(principal.getName(), id);
        if (result == null) {
            return CommonResult.failed("检索失败，请检查输入内容是否存在!");
        }
        return CommonResult.success(result);
    }

    @ApiOperation(value = "批量更新Block状态")   // 从表单中获取数据
    @RequestMapping(value = "/update/updateBlockStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> updateBlockStatus(@RequestParam("ids") List<Integer> ids,
                                                  @RequestParam("updateTopicStatus") Integer updateTopicStatus, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        int count = blockService.updateBlockStatus(principal.getName(), ids, updateTopicStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed("更新失败，请检查输入列表!");
        }
    }
}
