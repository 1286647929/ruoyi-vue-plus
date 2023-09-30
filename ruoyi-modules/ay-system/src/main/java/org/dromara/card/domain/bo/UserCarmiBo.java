package org.dromara.card.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Ay
 * 创建时间 2023-07-24
 */
@Data
public class UserCarmiBo {

    @NotNull
    private Long userId;

    @NotBlank
    private String cardKey;
}
