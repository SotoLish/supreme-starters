package club.supreme.framework.model;

import club.supreme.framework.constant.SupremeConstant;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 包括gmt_create、gmt_creator、gmtCrtname字段的表继承的基础实体
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
public class InsertEntity<PK> extends UpdateEntity<PK> implements Serializable {

    public static final String ENTITY_FIELD_CREATED_AT = SupremeConstant.CRUD.ENTITY_FIELD_CREATED_AT;
    public static final String COLUMN_CREATED_AT = SupremeConstant.CRUD.COLUMN_CREATED_AT;

    public static final String ENTITY_FIELD_CREATED_BY = SupremeConstant.CRUD.ENTITY_FIELD_CREATED_BY;
    public static final String COLUMN_CREATED_BY = SupremeConstant.CRUD.COLUMN_CREATED_BY;

    /**
     * serialVersionUID
     **/
    private static final long serialVersionUID = -4603650115161757622L;


    @ApiModelProperty(value = "创建时刻")
    @DateTimeFormat(pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SupremeConstant.Jackson.DATE_TIME_FORMAT , timezone = SupremeConstant.Jackson.TIME_ZONE)
    @TableField(value = InsertEntity.COLUMN_CREATED_AT, fill = FieldFill.INSERT)
    protected LocalDateTime gmtCreate;

    @ApiModelProperty(value = "创建者")
    @TableField(value = InsertEntity.COLUMN_CREATED_BY, fill = FieldFill.INSERT)
    protected PK gmtCreator;
}
