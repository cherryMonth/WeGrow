package com.wegrow.wegrow.demo.controller;

import com.wegrow.wegrow.common.api.CommonResult;
import com.wegrow.wegrow.demo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wegrow.wegrow.model.Block;

import java.util.List;

@Api(tags = "DemoController")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @ApiOperation(value = "获取全部用户")
    @RequestMapping(value = "/block/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Block>> getUserList(){
        return CommonResult.success(demoService.listAllBlock());
    }

}
