package org.dromara.card.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.card.domain.CardCarmi;
import org.dromara.card.domain.bo.CardCarmiBo;
import org.dromara.card.domain.vo.CardCarmiVo;
import org.dromara.card.mapper.CardCarmiMapper;
import org.dromara.card.service.ICardCarmiService;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 卡密Service业务层处理
 *
 * @author ay
 * @date 2023-07-20
 */
@RequiredArgsConstructor
@Service
public class CardCarmiServiceImpl implements ICardCarmiService {

    private final CardCarmiMapper baseMapper;

    /**
     * 查询卡密
     */
    @Override
    public CardCarmiVo queryById(Long cardId){
        return baseMapper.selectVoById(cardId);
    }

    /**
     * 查询卡密列表
     */
    @Override
    public TableDataInfo<CardCarmiVo> queryPageList(CardCarmiBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CardCarmi> lqw = buildQueryWrapper(bo);
        Page<CardCarmiVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询卡密列表
     */
    @Override
    public List<CardCarmiVo> queryList(CardCarmiBo bo) {
        LambdaQueryWrapper<CardCarmi> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CardCarmi> buildQueryWrapper(CardCarmiBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CardCarmi> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCardKey()), CardCarmi::getCardKey, bo.getCardKey());
        lqw.eq(StringUtils.isNotBlank(bo.getCardType()), CardCarmi::getCardType, bo.getCardType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), CardCarmi::getStatus, bo.getStatus());
        lqw.orderByDesc(CardCarmi::getCreateTime);
        return lqw;
    }

    /**
     * 新增卡密
     */
    @Override
    public Boolean insertByBo(CardCarmiBo bo) {
        CardCarmi add = MapstructUtils.convert(bo, CardCarmi.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCardId(add.getCardId());
        }
        return flag;
    }

    /**
     * 修改卡密
     */
    @Override
    public Boolean updateByBo(CardCarmiBo bo) {
        CardCarmi update = MapstructUtils.convert(bo, CardCarmi.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CardCarmi entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除卡密
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean insertBatch(CardCarmiBo bo) {
        Integer cardNum = bo.getCardNum();
        ArrayList<CardCarmi> list = new ArrayList<>();
        for (int i = 0; i < cardNum; i++) {
            CardCarmi cardCarmi = new CardCarmi();
            cardCarmi.setCardType(bo.getCardType());
            cardCarmi.setStatus(bo.getStatus());
            cardCarmi.setRemark(bo.getRemark());
            if (StringUtils.isNotEmpty(bo.getCardPre())){
                cardCarmi.setCardKey(bo.getCardPre()+"-"+IdUtil.simpleUUID());
            }else {
                cardCarmi.setCardKey(IdUtil.simpleUUID());
            }
            list.add(cardCarmi);
        }
        return baseMapper.insertBatch(list);
    }

    @Override
    public CardCarmiVo getOneCarmi(String name) {
        String cardType = "";
        if (name.equals("月卡")){
            cardType = "30";
        }
        if (name.equals("季卡")){
            cardType = "120";
        }
        if (name.equals("年卡")){
            cardType = "365";
        }
        if (cardType != "" && StringUtils.isNotEmpty(cardType)){
            return baseMapper.getCarmiOne(cardType);
        }else {
            throw new ServiceException("请正确填写卡密类型");
        }
    }

    @Override
    public CardCarmiVo getCarmiByTradeNo(String tradeNo) {
        LambdaQueryWrapper<CardCarmi> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StringUtils.isNotEmpty(tradeNo),CardCarmi::getOutTradeNo,tradeNo);
        return baseMapper.selectVoOne(queryWrapper);
    }
}
