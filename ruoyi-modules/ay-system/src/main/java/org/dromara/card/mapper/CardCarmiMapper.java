package org.dromara.card.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.card.domain.CardCarmi;
import org.dromara.card.domain.vo.CardCarmiVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 卡密Mapper接口
 *
 * @author ay
 * @date 2023-07-20
 */
public interface CardCarmiMapper extends BaseMapperPlus<CardCarmi, CardCarmiVo> {
    int updateStatus(@Param("cardKey") String cardKey);

    CardCarmiVo getCarmiOne(@Param("cardType") String cardType);
}
