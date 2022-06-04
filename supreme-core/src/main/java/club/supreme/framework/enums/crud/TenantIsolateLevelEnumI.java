package club.supreme.framework.enums.crud;


import club.supreme.framework.enums.ISupremeBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 多租户隔离级别枚举类
 * @author Supreme
 */
@AllArgsConstructor
@Getter
public enum TenantIsolateLevelEnumI implements ISupremeBaseEnum<Integer> {

    /*
    未实现
    DATASOURCE(4, "数据源级(即每个租户单独一个数据库)");
     */

    LINE(1, "行级(即每张表增加一个'租户ID'字段)");

    private final Integer value;
    private final String label;

}
