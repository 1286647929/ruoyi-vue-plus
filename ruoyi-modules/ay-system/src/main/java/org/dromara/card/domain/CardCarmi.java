package org.dromara.card.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 卡密对象 card_carmi
 *
 * @author ay
 * @date 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("card_carmi")
public class CardCarmi extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 卡密id
     */
    @TableId(value = "card_id")
    private Long cardId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 卡密
     */
    private String cardKey;

    /**
     * 卡密类型
     */
    private String cardType;

    /**
     * 状态（0未使用 1已使用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;


}
