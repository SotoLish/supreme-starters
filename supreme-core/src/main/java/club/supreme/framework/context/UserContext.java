package club.supreme.framework.context;

import club.supreme.framework.constant.SupremeConstant;
import club.supreme.framework.enums.ISupremeBaseEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 当前用户上下文对象
 *
 * @author supreme
 */
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserContext implements Serializable {

    private static final long SerialVersionUID = 1L;

    public static final String CAMEL_NAME = "userContext";

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "用户手机号")
    private String userPhoneNo;

    @ApiModelProperty(value = "用户类型")
    private ISupremeBaseEnum<?> userType;

    @ApiModelProperty(value = "用户拥有角色ID", notes = "后台管理使用", example = "[1, 2, 3]")
    private Set<Long> rolesIds;

    @ApiModelProperty(value = "用户拥有角色名称", notes = "后台管理使用", example = "[SuperAdmin, Admin, CEO]")
    private List<String> roles;

    @ApiModelProperty(value = "附加数据")
    private Map<String, Object> extraData;

    @ApiModelProperty(value = "客户端IP地址")
    private String clientIP;

}
