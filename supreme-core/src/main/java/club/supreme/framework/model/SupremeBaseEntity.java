package club.supreme.framework.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.io.Serializable;

/**
 * 包括id、gmt_create、gmt_creator、字段的表继承的基础实体
 * 业务系统的实体类的基类
 *
 * @author Douglas Lai
 * @date 2019/05/05
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SupremeBaseEntity<PK> extends TenantEntity<PK> implements Serializable {

    public static final String PK_ID = "id";

    /**
     * serialVersionUID
     **/
    private static final long serialVersionUID = -4603650115461757622L;

    /**
     * 主键
     *
     * @author Douglas Lai
     * @date 2020/8/10 9:18
     **/
    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = Update.class)
    @TableId(value = SupremeBaseEntity.PK_ID, type = IdType.ASSIGN_ID)
    protected PK id;


    /**
     * 保存和缺省验证组
     */
    public interface Insert extends Default {

    }

    /**
     * 更新和缺省验证组
     */
    public interface Update extends Default {

    }
}
