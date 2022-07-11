package club.supreme.framework.exception;


import club.supreme.framework.exception.code.BaseExceptionCode;
import club.supreme.framework.exception.code.ExceptionCode;

/**
 * 403  禁止访问
 *
 * @author supreme
 * @version 1.0
 */
public class ForbiddenException extends BaseUncheckedException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException(int code, String message) {
        super(code, message);
    }
    public static ForbiddenException wrap(BaseExceptionCode ex) {
        return new ForbiddenException(ex.getCode(), ex.getMsg());
    }

    public static ForbiddenException wrap(String msg) {
        return new ForbiddenException(ExceptionCode.FORBIDDEN.getCode(), msg);
    }

    @Override
    public String toString() {
        return "ForbiddenException [message=" + getMessage() + ", code=" + getCode() + "]";
    }

}
