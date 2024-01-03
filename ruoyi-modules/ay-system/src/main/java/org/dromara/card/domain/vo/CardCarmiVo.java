package org.dromara.card.domain.vo;

import org.dromara.card.domain.CardCarmi;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 卡密视图对象 card_carmi
 *
 * @author ay
 * @date 2023-07-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CardCarmi.class)
public class CardCarmiVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 卡密id
     */
    @ExcelProperty(value = "卡密id")
    private Long cardId;

    /**
     * 商户订单号
     */
    @ExcelProperty(value = "商户订单号")
    private String outTradeNo;

    /**
     * 卡密
     */
    @ExcelProperty(value = "卡密")
    private String cardKey;

    /**
     * 卡密类型
     */
    @ExcelProperty(value = "卡密类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "card_carmi_type")
    private String cardType;

    /**
     * 状态（0未使用 1已使用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "card_carmi_status")
    private String status;

    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
