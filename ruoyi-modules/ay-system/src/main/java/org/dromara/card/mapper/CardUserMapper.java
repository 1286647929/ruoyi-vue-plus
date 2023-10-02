package org.dromara.card.mapper;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.dromara.card.domain.CardUser;
import org.dromara.card.domain.vo.CardUserVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 用户信息Mapper接口
 *
 * @author ay
 * @date 2023-07-16
 */
public interface CardUserMapper extends BaseMapperPlus<CardUser, CardUserVo> {
    int updateById(@Param(Constants.ENTITY) CardUser user);
}
