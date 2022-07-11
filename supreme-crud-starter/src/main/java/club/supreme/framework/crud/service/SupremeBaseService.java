package club.supreme.framework.crud.service;

import club.supreme.framework.crud.mapper.SupremeBaseMapper;
import club.supreme.framework.exception.BizException;
import club.supreme.framework.exception.code.ExceptionCode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import java.util.List;

/**
 * 最高基础服务
 * 基于MP的 IService 新增了2个方法： saveBatchSomeColumn、updateAllById
 * 其中：
 * 1，updateAllById 执行后，会清除缓存
 * 2，saveBatchSomeColumn 批量插入
 *
 * @param <T> 实体
 * @author supreme
 * @date 2020年03月03日20:49:03
 */
@SuppressWarnings("ALL")
public interface SupremeBaseService<ENTITY> extends IService<ENTITY> {
    /**
     * 获取实体的类型
     *
     * @return
     */
    @Override
    Class<ENTITY> getEntityClass();

    /**
     * 批量保存数据
     * <p>
     * 注意：该方法仅仅测试过mysql
     *
     * @param entityList
     * @return
     */
    default boolean saveBatchSomeColumn(List<ENTITY> entityList) {
        if (entityList.isEmpty()) {
            return true;
        }
        if (entityList.size() > 5000) {
            throw BizException.wrap(ExceptionCode.TOO_MUCH_DATA_ERROR);
        }
        return SqlHelper.retBool(((SupremeBaseMapper) getBaseMapper()).insertBatchSomeColumn(entityList));
    }

    /**
     * 根据id修改 entity 的所有字段
     *
     * @param entity
     * @return
     */
    boolean updateAllById(ENTITY entity);
}
