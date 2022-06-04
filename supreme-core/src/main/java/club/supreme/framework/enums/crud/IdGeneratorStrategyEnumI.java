package club.supreme.framework.enums.crud;


import club.supreme.framework.enums.ISupremeBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 主键ID生成器策略枚举类
 * @author Supreme
 */
@AllArgsConstructor
@Getter
public enum IdGeneratorStrategyEnumI implements ISupremeBaseEnum<Integer> {

    /**
     * Twitter雪花算法
     */
    SNOWFLAKE(1, "SNOWFLAKE");

    private final Integer value;
    private final String label;

}
