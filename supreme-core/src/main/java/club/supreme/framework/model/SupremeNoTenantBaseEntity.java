package club.supreme.framework.model;

import club.supreme.framework.constant.SupremeConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 没有租户的表
 * {@link SupremeNoTenantBaseEntity}
 *
 * @author supreme
 * @date 2021/11/17
 **/
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class SupremeNoTenantBaseEntity<PK extends Serializable> extends SupremeBaseEntity<PK> {

    /**
     * 覆盖掉行级租户ID
     */
    @TableField(value = SupremeConstant.CRUD.COLUMN_TENANT_ID, exist = false)
    private PK tenant;
}
