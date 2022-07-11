package club.supreme.framework.crud.page;

import club.supreme.framework.crud.conditions.Wraps;
import club.supreme.framework.utils.DateUtils;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.Map;

/**
 * 分页工具类
 *
 * @author supreme
 * @date 2020/11/26 10:25 上午
 */
public class PageUtil {
    private PageUtil() {
    }

    /**
     * 重置时间区间参数
     *
     * @param params 分页参数
     */
    public static <T> void timeRange(PageParams<T> params) {
        if (params == null) {
            return;
        }
        Map<String, Object> extra = params.getExtra();
        if (MapUtil.isEmpty(extra)) {
            return;
        }
        for (Map.Entry<String, Object> field : extra.entrySet()) {
            String key = field.getKey();
            Object value = field.getValue();
            if (ObjectUtil.isEmpty(value)) {
                continue;
            }
            if (key.endsWith(Wraps.ST)) {
                extra.put(key, DateUtils.getStartTime(value.toString()));
            } else if (key.endsWith(Wraps.ED)) {
                extra.put(key, DateUtils.getEndTime(value.toString()));
            }
        }
    }
}
