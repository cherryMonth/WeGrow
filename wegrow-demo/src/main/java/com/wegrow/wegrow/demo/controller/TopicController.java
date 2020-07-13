package com.wegrow.wegrow.demo.controller;

import com.wegrow.wegrow.common.api.CommonPage;
import com.wegrow.wegrow.common.api.CommonResult;
import com.wegrow.wegrow.demo.dto.TopicParam;
import com.wegrow.wegrow.demo.service.TopicService;
import com.wegrow.wegrow.model.Topic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * block的主题controller
 * 用于用户自己创建的分类专栏
 */
@Controller
@Api(tags = "TopicController")
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @ApiOperation(value = "创建专题")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody  // @valid后面必须接着bindingResult才能使用我们自己的返回结果类型
    public CommonResult<Object> create(@RequestBody @Valid TopicParam topicParam, BindingResult bindingResult, Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        int count = topicService.createTopic(principal.getName(), topicParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else if (count == -1) {
            return CommonResult.validateFailed(String.format("%s已经存在，无法重复创建!", topicParam.getTopicName()));
        } else {
            return CommonResult.failed("创建失败, 请检查专题信息是否正确!");
        }
    }

    @ApiOperation(value = "更新专题消息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> update(@PathVariable("id") Integer id,
                                       @RequestBody @Valid TopicParam topicParam,
                                       BindingResult result, Principal principal) {
        int count = topicService.updateTopic(principal.getName(), id, topicParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else if (count == -1) {
            return CommonResult.validateFailed(String.format("%s已经存在，无法重复创建!", topicParam.getTopicName()));
        } else {
            return CommonResult.failed("请检查专题信息是否合法!");
        }
    }

    @ApiOperation(value = "删除专区")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable("id") Integer id, Principal principal) {
        int count = topicService.deleteTopic(principal.getName(), id);
        if (count > 0) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "根据专区名称分页获取用户自己的专区列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Topic>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   Principal principal) {
        List<Topic> topicList = topicService.listTopic(principal.getName(), keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(topicList));
    }

    @ApiOperation(value = "根据编号查询专区信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Topic> getItem(@PathVariable("id") Integer id, Principal principal) {
        return CommonResult.success(topicService.getTopic(principal.getName(), id));
    }

    @ApiOperation(value = "查询用户的专题数")
    @RequestMapping(value = "/getTopicLength", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Integer> getTopicLength(Principal principal) {
        return CommonResult.success(topicService.getAllTopicLength(principal.getName()));
    }

    @ApiOperation(value = "批量删除专区")  // 从表单中获取数据
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> deleteBatch(@RequestParam("ids") List<Integer> ids, Principal principal) {
        int count = topicService.deleteTopic(principal.getName(), ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed("删除失败，请检查输入列表!");
        }
    }
}
