package club.supreme.framework.model;

import club.supreme.framework.constant.SupremeConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 没有主键id的关系表，并且需要创建时间、创建人、修改时间，修改人、状态，租户等字段的可继承{@link SupremeRelationBaseEntity}
 *
 * @author Supreme Lai
 * @date 2021/11/17
 **/
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class SupremeRelationBaseEntity<PK extends Serializable> extends SupremeBaseEntity<PK> {

    /**
     * 覆盖掉行级租户ID
     */
    @TableField(value = SupremeConstant.CRUD.SQL_COLUMN_ID, exist = false)
    private PK id;
}
