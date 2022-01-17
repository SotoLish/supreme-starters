package club.supreme.framework.model;

import club.supreme.framework.constant.SupremeConstant;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 包括gmtModified、gmtModifiedby、gmtMfyname字段的表继承的基础实体
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
public class UpdateEntity<PK> extends SupremeEntity<PK> implements Serializable {

    private static final long serialVersionUID = 4159873634279173683L;

    public static final String COLUMN_UPDATED_AT = SupremeConstant.CRUD.COLUMN_UPDATED_AT;
    public static final String ENTITY_FIELD_UPDATED_AT = SupremeConstant.CRUD.ENTITY_FIELD_UPDATED_AT;

    public static final String COLUMN_UPDATED_BY = SupremeConstant.CRUD.COLUMN_UPDATED_BY;
    public static final String ENTITY_FIELD_UPDATED_BY = SupremeConstant.CRUD.ENTITY_FIELD_UPDATED_BY;

    public static final String COLUMN_STATUS = SupremeConstant.CRUD.COLUMN_STATUS;
    public static final String ENTITY_FIELD_STATUS = SupremeConstant.CRUD.ENTITY_FIELD_STATUS;

    public static final String COLUMN_REVISION = "revision";
    public static final String ENTITY_FIELD_REVISION = COLUMN_REVISION;

    /**
     * 更新时刻
     */
    @ApiModelProperty(value = "更新时刻")
    @DateTimeFormat(pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT,
            timezone = SupremeConstant.Jackson.TIME_ZONE)
    @TableField(value = UpdateEntity.COLUMN_UPDATED_AT, fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime gmtModified;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    @TableField(value = UpdateEntity.COLUMN_UPDATED_BY, fill = FieldFill.INSERT_UPDATE)
    protected PK gmtModifiedby;

    /**
     * 逻辑删除标识
     */
    @ApiModelProperty(value = "逻辑删除标识")
    @TableField(value = UpdateEntity.COLUMN_STATUS)
    @JsonIgnore
    private Integer gmtStatus;


    /**
     * 乐观锁
     */
    @Version
    @ApiModelProperty(value="乐观锁")
    @NotNull(groups = SupremeBaseEntity.Update.class)
    private Long revision;
}
