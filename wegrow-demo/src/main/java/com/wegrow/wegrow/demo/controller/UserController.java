package com.wegrow.wegrow.demo.controller;

import com.wegrow.wegrow.common.api.CommonPage;
import com.wegrow.wegrow.common.api.CommonResult;
import com.wegrow.wegrow.demo.dto.UserInfoUpdateParam;
import com.wegrow.wegrow.demo.dto.UserLoginParam;
import com.wegrow.wegrow.demo.service.UserService;
import com.wegrow.wegrow.demo.dto.UpdateUserPasswordParam;
import com.wegrow.wegrow.demo.dto.UserParam;
import com.wegrow.wegrow.model.Comment;
import com.wegrow.wegrow.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "UserController")
@RequestMapping("/user")
public class UserController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserService adminService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<User> register(@RequestBody @Valid UserParam userParam, BindingResult result) {
        User user = adminService.register(userParam);
        if (user == null) {
            return CommonResult.failed("创建用户失败，请检查邮箱或者用户名是否合法!");
        }
        return CommonResult.success(user);
    }

    @ApiOperation(value = "用户更新")
    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> updateInfo(@RequestBody @Valid UserInfoUpdateParam userInfoUpdateParam, BindingResult result, Principal principal) {
        String token = adminService.update(principal.getName(), userInfoUpdateParam);
        if (token == null) {
            return CommonResult.failed("更新用户失败，请检查用户信息或者用户名是否合法!");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> login(@RequestBody @Valid UserLoginParam userLoginParam, BindingResult result) {
        String token = adminService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")  // 刷新token的时间
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "根据Id获取用户的名称")
    @RequestMapping(value = "/userName/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> getUserName(@PathVariable("id") Integer id) {
        return CommonResult.success(adminService.getItem(id).getUsername());
    }

    @ApiOperation(value = "根据Id获取用户的头像")
    @RequestMapping(value = "/userAvatar/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> getUserAvatar(@PathVariable("id") Integer id) {
        return CommonResult.success(adminService.getItem(id).getAvatarHash());
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> getAdminInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        User user = adminService.getUserByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("UserName", user.getUsername());
        data.put("RolesList", adminService.getRolesList(user.getId()));
        data.put("PermissionList", adminService.getPermissionList(user.getId()));
        data.put("Email", user.getEmail());
        data.put("AvatarHash", user.getAvatarHash());
        data.put("AboutMe", user.getAboutMe());
        data.put("UserId", user.getId());
        return CommonResult.success(data);
    }

    @ApiOperation(value = "获取给定用户的统计信息")
    @RequestMapping(value = "/summaryInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> getAdminInfo(@PathVariable("id") Integer id) {
        return CommonResult.success(adminService.getSummaryUserInfo(id));
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody  // 由于我们只对token进行管理，所以把用户的登陆和登出交给前端进行管理，我们只负责管理token的生命周期
    public CommonResult<Object> logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或邮箱分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<User>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<User> adminList = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> updatePassword(@RequestBody @Valid UpdateUserPasswordParam updatePasswordParam, Principal principal) {
        if (null == principal) {
            return CommonResult.unauthorized(null);
        }
        int status = adminService.updatePassword(principal.getName(), updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }
}
