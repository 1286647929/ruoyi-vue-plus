package org.dromara.card.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.card.domain.CardUser;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.util.Date;

/**
 * 用户信息业务对象 card_user
 *
 * @author ay
 * @date 2023-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CardUser.class, reverseConvertGenerate = false)
public class CardUserBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 用户类型
     */
    @NotBlank(message = "用户类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userType;

    /**
     * 密码
     */
    private String password;

    /**
     * 过期时间
     */
    @NotNull(message = "过期时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date expireTime;

    /**
     * 帐号状态（0正常 1停用）
     */
    @NotBlank(message = "帐号状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 机器码
     */
    private String machineId;
}
