package org.dromara.card.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.card.domain.bo.CardUserBo;
import org.dromara.card.domain.bo.RegisterUserBo;
import org.dromara.card.domain.vo.CardUserVo;
import org.dromara.card.service.LoginService;
import org.dromara.card.service.RegisterService;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ay
 * 创建时间 2023-07-16
 */
@Slf4j
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardAuthController extends BaseController {

    private final LoginService loginService;

    private final RegisterService registerService;

    @PostMapping("/login")
    public R<CardUserVo> login(@RequestBody CardUserBo card){
        CardUserVo userVo = loginService.login(card.getUserName(), card.getPassword());
        if (userVo != null){
            return R.ok("登录成功",userVo);
        }else {
            return R.fail("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public R<Void> register(@Validated(AddGroup.class) @RequestBody RegisterUserBo registerUserBo){
        return toAjax(registerService.register(registerUserBo));
    }

}
