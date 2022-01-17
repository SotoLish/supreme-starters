package club.supreme.framework.model;

import club.supreme.framework.constant.SupremeConstant;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Douglas Lai
 * @description 包括id、create_time、created_by字段的表继承的基础实体
 * @projectName base-framework
 * @date 2021/11/17
 **/
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class TenantEntity<PK> extends InsertEntity<PK> implements Serializable {

    private static final long serialVersionUID = -3603650115461757622L;

    public static final String ENTITY_FIELD_TENANT_ID = SupremeConstant.CRUD.ENTITY_FIELD_TENANT_ID;
    public static final String COLUMN_TENANT_ID = SupremeConstant.CRUD.COLUMN_TENANT_ID;

    @ApiModelProperty(value = "行级租户ID", hidden = true)
    @TableField(value = TenantEntity.COLUMN_TENANT_ID, fill = FieldFill.INSERT)
    @JsonIgnore
    private PK gmtTenant;

}
