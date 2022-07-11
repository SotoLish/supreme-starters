package club.supreme.framework.crud.service.impl;

import club.supreme.framework.exception.BizException;
import club.supreme.framework.crud.mapper.SupremeBaseMapper;
import club.supreme.framework.crud.service.SupremeBaseService;
import club.supreme.framework.model.response.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;

import static club.supreme.framework.exception.code.ExceptionCode.SERVICE_MAPPER_ERROR;

/**
 * 不含缓存的Service实现
 * <p>
 * <p>
 * 2，removeById：重写 ServiceImpl 类的方法，删除db
 * 3，removeByIds：重写 ServiceImpl 类的方法，删除db
 * 4，updateAllById： 新增的方法： 修改数据（所有字段）
 * 5，updateById：重写 ServiceImpl 类的方法，修改db后
 *
 * @param <MAPPER> Mapper
 * @param <ENTITY> 实体
 * @author supreme
 * @date 2020年02月27日18:15:17
 */
@NoArgsConstructor
public class SupremeBaseServiceImpl<MAPPER extends SupremeBaseMapper<ENTITY>, ENTITY>
        extends ServiceImpl<MAPPER, ENTITY> implements SupremeBaseService<ENTITY> {
    private Class<ENTITY> entityClass = null;

    public SupremeBaseMapper getSuperMapper() {
        if (baseMapper instanceof SupremeBaseMapper) {
            return baseMapper;
        }
        throw BizException.wrap(SERVICE_MAPPER_ERROR);
    }

    @Override
    public Class<ENTITY> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return this.entityClass;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ENTITY model) {
        R<Boolean> result = handlerSave(model);
        if (result.getDefExec()) {
            return super.save(model);
        }
        return result.getData();
    }

    /**
     * 处理新增相关处理
     *
     * @param model 实体
     * @return 是否成功
     */
    protected R<Boolean> handlerSave(ENTITY model) {
        return R.successDef();
    }

    /**
     * 处理修改相关处理
     *
     * @param model 实体
     * @return 是否成功
     */
    protected R<Boolean> handlerUpdateAllById(ENTITY model) {
        return R.successDef();
    }

    /**
     * 处理修改相关处理
     *
     * @param model 实体
     * @return 是否成功
     */
    protected R<Boolean> handlerUpdateById(ENTITY model) {
        return R.successDef();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAllById(ENTITY model) {
        R<Boolean> result = handlerUpdateAllById(model);
        if (result.getDefExec()) {
            return SqlHelper.retBool(getSuperMapper().updateAllById(model));
        }
        return result.getData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(ENTITY model) {
        R<Boolean> result = handlerUpdateById(model);
        if (result.getDefExec()) {
            return super.updateById(model);
        }
        return result.getData();
    }
}
