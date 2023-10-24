package org.dromara.card.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.card.domain.CardCarmi;
import org.dromara.card.domain.CardUser;
import org.dromara.card.domain.bo.CardUserBo;
import org.dromara.card.domain.vo.CardUserVo;
import org.dromara.card.mapper.CardCarmiMapper;
import org.dromara.card.mapper.CardUserMapper;
import org.dromara.card.service.ICardUserService;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户信息Service业务层处理
 *
 * @author ay
 * @date 2023-07-16
 */
@RequiredArgsConstructor
@Service
public class CardUserServiceImpl implements ICardUserService {

    private final CardUserMapper baseMapper;

    private final CardCarmiMapper cardCarmiMapper;

    /**
     * 查询用户信息
     */
    @Override
    public CardUserVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 查询用户信息列表
     */
    @Override
    public TableDataInfo<CardUserVo> queryPageList(CardUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CardUser> lqw = buildQueryWrapper(bo);
        Page<CardUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户信息列表
     */
    @Override
    public List<CardUserVo> queryList(CardUserBo bo) {
        LambdaQueryWrapper<CardUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CardUser> buildQueryWrapper(CardUserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CardUser> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), CardUser::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getUserType()), CardUser::getUserType, bo.getUserType());
        lqw.between(params.get("beginExpireTime") != null && params.get("endExpireTime") != null,
            CardUser::getExpireTime ,params.get("beginExpireTime"), params.get("endExpireTime"));
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), CardUser::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getLoginIp()), CardUser::getLoginIp, bo.getLoginIp());
        return lqw;
    }

    /**
     * 新增用户信息
     */
    @Override
    public Boolean insertByBo(CardUserBo bo) {
        CardUser add = MapstructUtils.convert(bo, CardUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改用户信息
     */
    @Override
    public Boolean updateByBo(CardUserBo bo) {
        CardUser update = MapstructUtils.convert(bo, CardUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CardUser entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 修改状态
     * @param userId
     * @param status
     * @return
     */
    @Override
    public int updateUserStatus(Long userId, String status) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<CardUser>()
                .set(CardUser::getStatus, status)
                .eq(CardUser::getUserId, userId));
    }

    @Override
    public CardUser loginByUserName(String userName) {
        LambdaQueryWrapper<CardUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
            .eq(CardUser::getUserName, userName);

        CardUser cardUser = baseMapper.selectOne(queryWrapper);
        if(cardUser != null) {
            if (!cardUser.getStatus().equals("0")){
                throw new ServiceException("用户已被停用！");
            }
            return cardUser;
        }else {
            throw new ServiceException("用户名错误！");
        }
    }

    @Override
    public CardUserVo queryByUserName(String userName) {
        LambdaQueryWrapper<CardUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
            .eq(CardUser::getUserName, userName);

        CardUserVo cardUserVo = baseMapper.selectVoOne(queryWrapper);
        if(cardUserVo != null) {
            return cardUserVo;
        }else {
            throw new ServiceException("用户名错误！");
        }
    }

    @Override
    public CardUserVo expireByUserName(String userName,String cardKey) {
        LambdaQueryWrapper<CardCarmi> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CardCarmi::getCardKey,cardKey);
        CardCarmi cardCarmi = cardCarmiMapper.selectOne(queryWrapper);
        if (cardCarmi == null) {
            throw new ServiceException("卡密不存在！");
        }
        String status = cardCarmi.getStatus();
        if (status.equals("1")){
            throw new ServiceException("卡密已被使用！");
        }
        String cardType = cardCarmi.getCardType();

        CardUserVo cardUserVo = queryByUserName(userName);
        Date expireTime = cardUserVo.getExpireTime();
        if (expireTime == null){
            expireTime = DateUtil.date();
        }
        DateTime dateTime = DateUtil.offsetDay(expireTime, Convert.toInt(cardType));

        LambdaUpdateWrapper<CardUser> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(CardUser::getExpireTime, dateTime)
            .eq(CardUser::getUserName,userName);
        int update = baseMapper.update(null, updateWrapper);
        if (update>0){
//            cardCarmiMapper.updateStatus(cardKey);
            cardCarmiMapper.update(null,new LambdaUpdateWrapper<CardCarmi>()
                .set(CardCarmi::getStatus,1)
                .set(CardCarmi::getRemark,"使用者："+userName)
                .eq(CardCarmi::getCardKey,cardKey));
            return baseMapper.selectVoById(cardUserVo.getUserId());
        }else {
            throw new ServiceException("续费失败！请重试");
        }
    }

    /**
     * 重置用户密码
     * @param userId    用户id
     * @param password  用户密码
     * @return
     */
    @Override
    public int resetUserPwd(Long userId, String password) {
        return baseMapper.update(null,new LambdaUpdateWrapper<CardUser>()
            .set(CardUser::getPassword,password)
            .eq(CardUser::getUserId,userId));
    }
}
