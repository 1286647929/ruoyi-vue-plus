package org.dromara.card.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.card.domain.CardUser;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Ay
 * 创建时间 2023-08-05
 */
@Data
@AutoMapper(target = CardUser.class)
public class PayStatusVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String trade_no;

    private String payurl;
}
