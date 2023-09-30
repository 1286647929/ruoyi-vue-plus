package org.dromara.card.service;

import org.dromara.card.domain.bo.CardCarmiBo;
import org.dromara.card.domain.vo.CardCarmiVo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 卡密Service接口
 *
 * @author ay
 * @date 2023-07-20
 */
public interface ICardCarmiService {

    /**
     * 查询卡密
     */
    CardCarmiVo queryById(Long cardId);

    /**
     * 查询卡密列表
     */
    TableDataInfo<CardCarmiVo> queryPageList(CardCarmiBo bo, PageQuery pageQuery);

    /**
     * 查询卡密列表
     */
    List<CardCarmiVo> queryList(CardCarmiBo bo);

    /**
     * 新增卡密
     */
    Boolean insertByBo(CardCarmiBo bo);

    /**
     * 修改卡密
     */
    Boolean updateByBo(CardCarmiBo bo);

    /**
     * 校验并批量删除卡密信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 批量生成卡密
     * @param bo
     * @return
     */
    Boolean insertBatch(CardCarmiBo bo);

    /**
     * 获取单一卡密
     * @param name
     * @return
     */
    CardCarmiVo getOneCarmi(String name);

    /**
     * 根据订单号获取卡密
     * @param tradeNo
     * @return
     */
    CardCarmiVo getCarmiByTradeNo(String tradeNo);
}
