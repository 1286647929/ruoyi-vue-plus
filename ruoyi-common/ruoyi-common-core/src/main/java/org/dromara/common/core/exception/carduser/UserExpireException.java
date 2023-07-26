package org.dromara.common.core.exception.carduser;

import lombok.Data;

import java.io.Serial;

/**
 * @author Ay
 * 创建时间 2023-07-26
 */
@Data
public class UserExpireException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;

    public UserExpireException() {}

    public UserExpireException(String message) {
        this.message = message;
    }
}
