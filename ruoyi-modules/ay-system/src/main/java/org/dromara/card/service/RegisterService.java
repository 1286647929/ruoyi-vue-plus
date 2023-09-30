package org.dromara.card.service;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.card.domain.CardUser;
import org.dromara.card.domain.bo.CardUserBo;
import org.dromara.card.domain.bo.RegisterUserBo;
import org.dromara.common.core.utils.MapstructUtils;
import org.springframework.stereotype.Service;

/**
 * @author Ay
 * 创建时间 2023-07-16
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class RegisterService {

    private final ICardUserService userService;

    public boolean register(RegisterUserBo registerUserBo) {
        CardUserBo cardUserBo = new CardUserBo();
        cardUserBo.setUserName(registerUserBo.getUserName());
        cardUserBo.setPassword(BCrypt.hashpw(registerUserBo.getPassword()));
        return userService.insertByBo(cardUserBo);
    }
}
