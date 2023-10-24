package org.dromara.card.service;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.card.domain.CardUser;
import org.dromara.card.domain.vo.CardUserVo;
import org.dromara.card.mapper.CardUserMapper;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.ServletUtils;
import org.dromara.common.core.utils.StringUtils;
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

    private final CardUserMapper userMapper;
    /**
     * 登录校验
     * @param userName
     * @param password
     * @return
     */
    public CardUserVo login(String userName, String password,String machineId) {
        CardUser cardUser = userService.loginByUserName(userName);
        if (BCrypt.checkpw(password, cardUser.getPassword())){
            if (StringUtils.isNotEmpty(cardUser.getMachineId()) && !StringUtils.equals(cardUser.getMachineId(), machineId)){
                throw new ServiceException("当前账户已绑定其他机器，请更换回绑定设备登录或重新解绑！您的机器码为:" + machineId);
            }
            if (StringUtils.isEmpty(cardUser.getMachineId())){
                cardUser.setMachineId(machineId);
                userMapper.updateById(cardUser);
            }
            return MapstructUtils.convert(cardUser,CardUserVo.class);
        }
        return null;
    }

    public void recordLoginInfo(Long userId) {
        CardUser cardUser = new CardUser();
        cardUser.setUserId(userId);
        cardUser.setLoginIp(ServletUtils.getClientIP());
        cardUser.setLoginDate(DateUtils.getNowDate());
        userMapper.updateById(cardUser);
    }
}
