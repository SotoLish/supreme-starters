package club.supreme.framework.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 当前用户所属租户
 *
 * @author supreme
 */
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TenantContext implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CAMEL_NAME = "tenantContext";

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "租户名")
    private String tenantName;

}
