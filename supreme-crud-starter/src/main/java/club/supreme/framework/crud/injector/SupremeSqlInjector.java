package club.supreme.framework.crud.injector;

import club.supreme.framework.crud.injector.method.UpdateAllById;
import club.supreme.framework.model.SupremeBaseEntity;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 自定义sql 注入器
 *
 * @author zuihou
 * @date 2020年02月19日15:39:49
 */
public class SupremeSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);

        //增加自定义方法
        methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
        methodList.add(new UpdateAllById(field -> !ArrayUtil.containsAny(new String[]{
                SupremeBaseEntity.COLUMN_CREATED_AT, SupremeBaseEntity.COLUMN_CREATED_BY
        }, field.getColumn())));
        return methodList;
    }
}
