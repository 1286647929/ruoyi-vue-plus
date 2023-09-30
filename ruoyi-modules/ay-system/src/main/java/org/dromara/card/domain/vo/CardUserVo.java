package org.dromara.card.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.card.domain.CardUser;
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
 * 用户信息视图对象 card_user
 *
 * @author ay
 * @date 2023-07-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CardUser.class)
public class CardUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userName;

//    @ExcelProperty(value = "密码")
//    private String password;

    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "card_user_type")
    private String userType;

    /**
     * 过期时间
     */
    @ExcelProperty(value = "过期时间")
    private Date expireTime;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 最后登录IP
     */
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    private Date loginDate;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
