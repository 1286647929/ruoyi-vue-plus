package org.dromara.card.service;

import org.dromara.card.domain.CardUser;
import org.dromara.card.domain.vo.CardUserVo;
import org.dromara.card.domain.bo.CardUserBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息Service接口
 *
 * @author ay
 * @date 2023-07-16
 */
public interface ICardUserService {

    /**
     * 查询用户信息
     */
    CardUserVo queryById(Long userId);

    /**
     * 查询用户信息列表
     */
    TableDataInfo<CardUserVo> queryPageList(CardUserBo bo, PageQuery pageQuery);

    /**
     * 查询用户信息列表
     */
    List<CardUserVo> queryList(CardUserBo bo);

    /**
     * 新增用户信息
     */
    Boolean insertByBo(CardUserBo bo);

    /**
     * 修改用户信息
     */
    Boolean updateByBo(CardUserBo bo);

    /**
     * 校验并批量删除用户信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    int updateUserStatus(Long userId, String status);

    /**
     * 登录通过用户名查询
     * @param userName
     * @return
     */
    CardUser loginByUserName(String userName);

    /**
     * 给用户续费时长
     * @return
     */
    CardUserVo expireByUserId(Long userId,String cardKey);
}
