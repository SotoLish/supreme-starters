package club.supreme.framework.enums;

import club.supreme.framework.exception.BizException;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 基础枚举类
 *
 * @author Zhu JW
 * @author Uncarbon
 **/
public interface ISupremeBaseEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 枚举选项的值,通常由字母或者数字组成,并且在同一个枚举中值唯一;对应数据库中的值通常也为此值
     *
     * @return 枚举的值
     */
//    T getValue();

    /**
     * 枚举选项的文本，通常为中文
     *
     * @return 枚举的文本
     */
    String getLabel();

    /**
     * 枚举选项的编码，通常为枚举值本身
     *
     * @return 枚举值本身
     */
    default String getCode() {
        return toString();
    }

    /**
     * 断言不为空
     * 注意：枚举选项的值value需要为Integer类型
     * @param object 需要判断的对象
     */
    default void assertNotNull(@Nullable Object object) {
        if (ObjectUtil.isNull(object)) {
            if (this.getValue() instanceof Integer) {
                throw new BizException((Integer) this.getValue(), this.getLabel());
            }

            throw new UnsupportedOperationException("枚举选项的值value需要为Integer类型");
        }
    }

    /**
     * 对比是否和value相等,对比地址,值,value转为string忽略大小写对比,text忽略大小写对比
     *
     * @param v value
     * @return 是否相等
     */
    @SuppressWarnings("all")
    default boolean eq(Object v) {
        if (v == null) {
            return false;
        }
        if (v instanceof Object[]) {
            v = Arrays.asList(v);
        }
        return this == v
                || getValue() == v
                || getValue().equals(v)
                || String.valueOf(getValue()).equalsIgnoreCase(String.valueOf(v))
                || getLabel().equalsIgnoreCase(String.valueOf(v)
        );
    }

    /**
     * 从指定的枚举类中查找想要的枚举,并返回一个{@link Optional},如果未找到,则返回一个{@link Optional#empty()}
     *
     * @param type      实现了{@link ISupremeBaseEnum}的枚举类
     * @param predicate 判断逻辑
     * @param <T>       枚举类型
     * @return 查找到的结果
     */
    static <T extends Enum<?> & ISupremeBaseEnum<?>> Optional<T> find(Class<T> type, Predicate<T> predicate) {
        if (type.isEnum()) {
            for (T each : type.getEnumConstants()) {
                if (predicate.test(each)) {
                    return Optional.of(each);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 根据枚举的{@link ISupremeBaseEnum#getValue()}来查找.
     *
     * @see this#find(Class, Predicate)
     */
    static <T extends Enum<?> & ISupremeBaseEnum<?>> Optional<T> findByValue(Class<T> type, Object value) {
        return find(type, e -> e.getValue() == value || e.getValue().equals(value) || String.valueOf(e.getValue()).equalsIgnoreCase(String.valueOf(value)));
    }

    /**
     * 根据枚举的{@link ISupremeBaseEnum#getLabel()} 来查找.
     *
     * @see this#find(Class, Predicate)
     */
    static <T extends Enum<?> & ISupremeBaseEnum<?>> Optional<T> findByLabel(Class<T> type, String text) {
        return find(type, e -> e.getLabel().equalsIgnoreCase(text));
    }

    /**
     * 根据枚举的{@link ISupremeBaseEnum#getValue()},{@link ISupremeBaseEnum#getLabel()} ()}来查找.
     *
     * @see this#find(Class, Predicate)
     */
    static <T extends Enum<?> & ISupremeBaseEnum<?>> Optional<T> find(Class<T> type, Object target) {
        return find(type, v -> v.eq(target));
    }


    static <E extends ISupremeBaseEnum<?>> Optional<E> of(Class<E> type, Object value) {
        if (type.isEnum()) {
            for (E enumConstant : type.getEnumConstants()) {
                Predicate<E> predicate = e -> e.getValue() == value || e.getValue().equals(value) || String.valueOf(e.getValue()).equalsIgnoreCase(String.valueOf(value));
                if (predicate.test(enumConstant)) {
                    return Optional.of(enumConstant);
                }
            }
        }
        return Optional.empty();
    }
}
