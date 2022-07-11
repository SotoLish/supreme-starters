package club.supreme.framework.web.jackson;

import club.supreme.framework.enums.ISupremeBaseEnum;
import club.supreme.framework.exception.BizException;
import club.supreme.framework.web.enums.ErrorResponseEnum;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 枚举转换
 *
 * @author supreme
 **/
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumConverterFactory implements ConverterFactory<String, ISupremeBaseEnum> {
    private final Map<Class, Converter> converterCache = new WeakHashMap<>();

    @Override
    public <T extends ISupremeBaseEnum> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return converterCache.computeIfAbsent(targetType,
                k -> converterCache.put(k, new EnumConverter(k))
        );
    }

    protected static class EnumConverter<T extends ISupremeBaseEnum<T> & Serializable> implements Converter<Object, T> {

        private final Class<T> enumType;

        public EnumConverter(@NonNull Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(@NonNull Object value) {
            return ISupremeBaseEnum.of(this.enumType, value).orElseThrow(() -> new BizException(ErrorResponseEnum.ILLEGAL_ENUM_VALUE));
        }
    }
}
