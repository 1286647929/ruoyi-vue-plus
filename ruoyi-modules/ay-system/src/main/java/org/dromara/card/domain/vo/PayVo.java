package org.dromara.card.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.card.domain.CardUser;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ay
 * 创建时间 2023-07-29
 */
@Data
@AutoMapper(target = CardUser.class)
public class PayVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 支付类型
     */
    private String type;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 金额
     */
    private String money;

    /**
     * 业务扩展参数
     */
    private String param;
}
