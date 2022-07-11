package club.supreme.framework.model;

import club.supreme.framework.constant.SupremeConstant;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务系统的实体类的基类
 *
 * @author Supreme Lai
 * @date 2019/05/05
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
public abstract class SupremeBaseEntity<PK extends Serializable> implements Serializable {

    /**
     * serialVersionUID
     **/
    private static final long serialVersionUID = -4603650115461757622L;

    /**
     * 主键
     *
     * @author Supreme Lai
     * @date 2020/8/10 9:18
     **/
    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = Update.class)
    @TableId(value = SupremeConstant.CRUD.SQL_COLUMN_ID, type = IdType.ASSIGN_ID)
    protected PK id;

    /**
     * 行级租户ID
     */
    @ApiModelProperty(value = "行级租户ID")
    @TableField(value = SupremeConstant.CRUD.COLUMN_TENANT_ID, fill = FieldFill.INSERT)
    private PK tenantId;

    /**
     * 创建时刻
     */
    @ApiModelProperty(value = "创建时刻")
    @DateTimeFormat(pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT ,
            timezone = SupremeConstant.Jackson.TIME_ZONE)
    @TableField(value = SupremeConstant.CRUD.COLUMN_CREATED_AT, fill = FieldFill.INSERT)
    protected LocalDateTime createdAt;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @TableField(value = SupremeConstant.CRUD.COLUMN_CREATED_BY, fill = FieldFill.INSERT)
    protected String createdBy;

    /**
     * 更新时刻
     */
    @ApiModelProperty(value = "更新时刻")
    @DateTimeFormat(pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT,
            timezone = SupremeConstant.Jackson.TIME_ZONE)
    @TableField(value = SupremeConstant.CRUD.COLUMN_UPDATED_AT, fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updatedAt;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    @TableField(value = SupremeConstant.CRUD.COLUMN_UPDATED_BY, fill = FieldFill.INSERT_UPDATE)
    protected String updatedBy;

    /**
     * 逻辑删除标识
     */
    @ApiModelProperty(value = "逻辑删除标识")
    @JsonIgnore
    @TableField(value = SupremeConstant.CRUD.COLUMN_DEL_FLAG)
    private Integer delFlag;

    /**
     * 乐观锁
     * 需自行加@Version注解才有效
     */
    @ApiModelProperty(value = "乐观锁", notes = "需再次复制本字段，并自行加 @Version 注解才有效")
    @TableField(value = SupremeConstant.CRUD.COLUMN_REVISION)
    private Long revision;

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
