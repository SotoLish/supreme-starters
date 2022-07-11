package club.supreme.framework.satoken.interceptor;

import club.supreme.framework.context.TenantContext;
import club.supreme.framework.context.TenantContextHolder;
import club.supreme.framework.context.UserContext;
import club.supreme.framework.context.UserContextHolder;
import club.supreme.framework.satoken.util.IPUtil;
import cn.dev33.satoken.stp.StpUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 从请求头解析用户信息，并赋值到上下文
 *
 * @author supreme
 */
@Slf4j
public class DefaultSaTokenParseInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (handler instanceof ResourceHttpRequestHandler) {
            // 直接放行静态资源
            return true;
        }

        // SA-Token 会自动从请求头中解析 token，所以这里可以直接拿到对应 session，从而取出业务字段
        if (StpUtil.isLogin()) {
            UserContext userContext = (UserContext) StpUtil.getSession().get(UserContext.CAMEL_NAME);

            // 获取用户公网IP
            userContext.setClientIP(IPUtil.getClientIPAddress(request));
            UserContextHolder.setUserContext(userContext);

            log.debug("[SA-Token] 从请求头解析出用户上下文 >> {}", userContext);

            // 赋值租户上下文
            TenantContext tenantContext = (TenantContext) StpUtil.getSession().get(TenantContext.CAMEL_NAME);
            TenantContextHolder.setTenantContext(tenantContext);

        } else {
            UserContextHolder.setUserContext(null);
            TenantContextHolder.setTenantContext(null);
        }

        return true;
    }
}
