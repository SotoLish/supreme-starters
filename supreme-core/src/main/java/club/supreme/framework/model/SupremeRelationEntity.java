package club.supreme.framework.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 没有主键id的关系表，并且需要创建时间、创建人、修改时间，修改人、状态，租户等字段的可继承{@link SupremeRelationEntity}
 * @author Douglas Lai
 * @date 2021/11/17
 **/
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class SupremeRelationEntity<PK> extends TenantEntity<PK> implements Serializable {

    private static final long serialVersionUID = -2603650115461757622L;

}
