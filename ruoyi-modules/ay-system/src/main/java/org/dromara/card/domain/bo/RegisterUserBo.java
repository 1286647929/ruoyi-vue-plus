package org.dromara.card.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.card.domain.CardUser;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * @author Ay
 * 创建时间 2023-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CardUser.class, reverseConvertGenerate = false)
public class RegisterUserBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空", groups = { AddGroup.class, EditGroup.class })
    @Min(message = "账户长度不能低于2个字符",value = 2)
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = { AddGroup.class, EditGroup.class })
    @Min(message = "密码长度不能低于5个字符",value = 5)
    private String password;
}
