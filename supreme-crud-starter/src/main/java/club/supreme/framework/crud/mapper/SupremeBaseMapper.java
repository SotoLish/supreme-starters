package club.supreme.framework.crud.mapper;

import club.supreme.framework.model.SupremeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupremeBaseMapper<ENTITY extends SupremeEntity<?>> extends BaseMapper<ENTITY> {
    /**
     * 以下定义的 4个 method 其中 3 个是内置的选装件
     */
    int insertBatchSomeColumn(List<ENTITY> entityList);

    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) ENTITY entity);

    int deleteByIdWithFill(ENTITY entity);

    /**
     * 全量修改所有字段
     *
     * @param entity 实体
     * @return 修改数量
     */
    int updateAllById(@Param(Constants.ENTITY) ENTITY entity);

}
