package club.supreme.framework.web.jackson;

import club.supreme.framework.enums.ISupremeBaseEnum;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * 继承了BaseEnum接口的枚举值，将会统一按照以下格式序列化
 * {
 * "value": "foo",
 * "valueLabel": "bar"
 * }
 *
 * @author Zhu JW
 * @author Uncarbon
 **/
public class EnumModule extends SimpleModule {
    public EnumModule() {
        super("jacksonEnumTypeModule", Version.unknownVersion());
        this.setDeserializers(new CustomDeserializers());
        this.addSerializer(new EnumSerializer());
    }

    private static class CustomDeserializers extends SimpleDeserializers {
        private CustomDeserializers() {

        }

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
            // ISupremeBaseEnum<?>，调用此序列化方法，否则使用 jackson 默认的序列化方法
            return ISupremeBaseEnum.class.isAssignableFrom(type) ?
                    new EnumDeserializer(type) :
                    super.findEnumDeserializer(type, config, beanDesc);
        }

        private static class EnumDeserializer<E extends ISupremeBaseEnum<?>> extends StdScalarDeserializer<E> {
            private final Class<E> enumType;

            private EnumDeserializer(Class<E> clazz) {
                super(clazz);
                this.enumType = clazz;
            }

            @Override
            public E deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                // 前台如果传递只传value
                if (parser.getCurrentToken().isNumeric()) {
                    return ISupremeBaseEnum.of(this.enumType, parser.getIntValue()).orElseThrow(() -> new IllegalArgumentException("非法输入值"));
                } else if (StrUtil.isNotBlank(parser.getText())) {
                    return ISupremeBaseEnum.of(this.enumType, parser.getText()).orElseThrow(() -> new IllegalArgumentException("非法输入值"));
                } else {
                    throw new IllegalArgumentException("枚举值类型错误");
                }
            }
        }
    }

    @SuppressWarnings({"rawtypes"})
    private static class EnumSerializer extends StdSerializer<ISupremeBaseEnum> {

        private EnumSerializer() {
            super(ISupremeBaseEnum.class);
        }

        @Override
        public void serialize(ISupremeBaseEnum data, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeObject(data.getValue());
            jsonGenerator.writeFieldName(jsonGenerator.getOutputContext().getCurrentName() + "Label");
            jsonGenerator.writeString(data.getLabel());
        }
    }

}
