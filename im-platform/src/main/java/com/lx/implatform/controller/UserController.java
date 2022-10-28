package com.lx.implatform.controller;


import com.lx.common.result.Result;
import com.lx.common.result.ResultUtils;
import com.lx.common.util.BeanUtils;
import com.lx.implatform.entity.User;
import com.lx.implatform.service.IUserService;
import com.lx.implatform.session.SessionContext;
import com.lx.implatform.session.UserSession;
import com.lx.implatform.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping("/online")
    @ApiOperation(value = "判断用户是否在线",notes="返回在线的用户id集合")
    public Result checkOnline(@NotEmpty @RequestParam("userIds") String userIds){
        List<Long> onlineIds = userService.checkOnline(userIds);
        return ResultUtils.success(onlineIds);
    }

    @GetMapping("/self")
    @ApiOperation(value = "获取当前用户信息",notes="获取当前用户信息")
    public Result findSelfInfo(){
        UserSession session = SessionContext.getSession();
        User user = userService.getById(session.getId());
        UserVO userVO = BeanUtils.copyProperties(user,UserVO.class);
        return ResultUtils.success(userVO);
    }


    @GetMapping("/find/{id}")
    @ApiOperation(value = "查找用户",notes="根据id查找用户")
    public Result findByIde(@NotEmpty @PathVariable("id") long id){
        User user = userService.getById(id);
        UserVO userVO = BeanUtils.copyProperties(user,UserVO.class);
        return ResultUtils.success(userVO);
    }


    @GetMapping("/findByNickName")
    @ApiOperation(value = "查找用户",notes="根据昵称查找用户")
    public Result findByNickName(@NotEmpty(message = "用户昵称不可为空") @RequestParam("nickName") String nickName){
           return ResultUtils.success( userService.findUserByNickName(nickName));
    }
}

