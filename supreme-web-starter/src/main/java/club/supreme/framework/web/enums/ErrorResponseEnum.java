package club.supreme.framework.web.enums;


import club.supreme.framework.enums.ISupremeBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorResponseEnum implements ISupremeBaseEnum<Integer> {

    ILLEGAL_ENUM_VALUE(400, "非法枚举值"),
    CONTAINS_ILLEGAL_CHARACTER(400, "包含非法字符"),

    ;

    private final Integer value;
    private final String label;
}
