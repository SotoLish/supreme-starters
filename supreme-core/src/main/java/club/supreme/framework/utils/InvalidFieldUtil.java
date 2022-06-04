package club.supreme.framework.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数绑定验证
 *
 * @author Zhu JW
 * @author Supreme
 **/
@UtilityClass
public class InvalidFieldUtil {

    public static final String MESSAGE_FORMAT = "【{}】{}";
    /**
     * 参数绑定验证
     * @return 快速返回首个无效表单项
     */
    public static InvalidField getInvalidField(BindingResult bindingResult) {
        if (bindingResult == null) {
            return null;
        }

        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError == null) {
            return null;
        }

        return InvalidField.builder()
                .fieldName(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }

    /**
     * 参数绑定验证
     * @return 快速返回首个无效表单项
     */
    public static String getInvalidFieldStr(BindingResult bindingResult){
        InvalidField invalidField = getInvalidField(bindingResult);
        return StrUtil.format(MESSAGE_FORMAT, invalidField.getFieldName(), invalidField.getMessage());
    }

    /**
     * 参数绑定验证
     * @return 全量返回无效表单项
     */
    public static List<InvalidField> listInvalidField(BindingResult bindingResult) {
        List<InvalidField> invalidFieldList = new ArrayList<>();
        if (bindingResult == null || CollUtil.isEmpty(bindingResult.getFieldErrors())) {
            // 返回空List
            return invalidFieldList;
        }

        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            InvalidField invalidField = new InvalidField();
            invalidField.setFieldName(fieldError.getField());
            invalidField.setMessage(fieldError.getDefaultMessage());
            invalidFieldList.add(invalidField);
        }

        return invalidFieldList;
    }

    /**
     * 参数绑定验证
     * @return 全量返回无效表单项
     */
    public static List<String> listInvalidFieldStr(BindingResult bindingResult) {
        List<String> invalidFieldStrList = new ArrayList<>();
        if (bindingResult == null || CollUtil.isEmpty(bindingResult.getFieldErrors())) {
            // 返回空List
            return invalidFieldStrList;
        }

        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            invalidFieldStrList.add(StrUtil.format(MESSAGE_FORMAT,
                    fieldError.getField(),
                    fieldError.getDefaultMessage()));
        }

        return invalidFieldStrList;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class InvalidField {

        @ApiModelProperty(value = "字段名")
        private String fieldName;

        @ApiModelProperty(value = "错误原因")
        private String message;

    }

}
