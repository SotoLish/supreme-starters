package club.supreme.framework.crud.handler;

import club.supreme.framework.constant.SupremeConstant;
import club.supreme.framework.context.TenantContextHolder;
import club.supreme.framework.props.SupremeProperties;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import javax.annotation.Resource;

/**
 * 行级租户拦截器
 * @author Supreme
 */
public class SupremeTenantLineHandler implements TenantLineHandler {

    @Resource
    private SupremeProperties supremeProperties;


    @Override
    public Expression getTenantId() {
        return new LongValue(TenantContextHolder.getTenantId());
    }

    @Override
    public String getTenantIdColumn() {
        return SupremeConstant.CRUD.COLUMN_TENANT_ID;
    }

    @Override
    public boolean ignoreTable(String tableName) {
        if (SupremeConstant.Tenant.DEFAULT_PRIVILEGED_TENANT_ID.equals(TenantContextHolder.getTenantId())) {
            return true;
        }

        return supremeProperties.getTenant().getIgnoredTables().contains(tableName);
    }

}
