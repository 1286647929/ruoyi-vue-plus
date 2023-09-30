package org.dromara.card.service;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.card.domain.CardUser;
import org.dromara.card.domain.vo.CardUserVo;
import org.dromara.common.core.utils.MapstructUtils;
import org.springframework.stereotype.Service;

/**
 * @author Ay
 * 创建时间 2023-07-16
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class LoginService {
    private final ICardUserService userService;

    /**
     * 登录校验
     * @param userName
     * @param password
     * @return
     */
    public CardUserVo login(String userName, String password) {
        CardUser cardUser = userService.loginByUserName(userName);
        if (BCrypt.checkpw(password, cardUser.getPassword())){
            return MapstructUtils.convert(cardUser,CardUserVo.class);
        }
        return null;
    }
}
