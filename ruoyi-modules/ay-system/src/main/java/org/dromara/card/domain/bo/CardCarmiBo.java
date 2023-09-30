package org.dromara.card.domain.bo;

import org.dromara.card.domain.CardCarmi;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 卡密业务对象 card_carmi
 *
 * @author ay
 * @date 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CardCarmi.class, reverseConvertGenerate = false)
public class CardCarmiBo extends BaseEntity {

    /**
     * 卡密id
     */
    @NotNull(message = "卡密id不能为空", groups = { EditGroup.class })
    private Long cardId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 卡密
     */
//    @NotBlank(message = "卡密不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cardKey;

    /**
     * 卡密数量
     */
    @NotNull(message = "卡密数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer cardNum;

    /**
     * 卡密前缀
     */
    private String cardPre;

    /**
     * 卡密类型
     */
    @NotBlank(message = "卡密类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cardType;

    /**
     * 状态（0未使用 1已使用）
     */
    @NotBlank(message = "状态（0未使用 1已使用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 备注
     */
    private String remark;


}
