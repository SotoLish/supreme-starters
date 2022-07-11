package club.supreme.framework.web.controller;

import club.supreme.framework.crud.service.SupremeBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

/**
 * 简单的实现了BaseController，为了获取注入 Service 和 实体类型
 * <p>
 * 基类该类后，没有任何方法。
 * 可以让业务Controller继承 SuperSimpleController 后，按需实现 *Controller 接口
 *
 * @param <S>      Service
 * @param <Entity> 实体
 * @author supreme
 * @date 2020年03月07日22:08:27
 */
public abstract class SuperSimpleController<S extends SupremeBaseService<Entity>, Entity> implements BaseController<Entity> {

    /**
     * 实体类
     */
    Class<Entity> entityClass = null;
    /**
     * 基础服务
     */
    @Autowired
    protected S baseService;

    /**
     * 得到实体类
     *
     * @return {@link Class}<{@link Entity}>
     */
    @Override
    public Class<Entity> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class<Entity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return this.entityClass;
    }

    /**
     * 获得基本服务
     *
     * @return {@link SupremeBaseService}<{@link Entity}>
     */
    @Override
    public SupremeBaseService<Entity> getBaseService() {
        return baseService;
    }
}
