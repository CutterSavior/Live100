package com.lanjii.advice;

import com.lanjii.core.annotation.NotControllerResponseAdvice;
import com.lanjii.core.obj.R;
import com.lanjii.util.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;

/**
 * 返回参数同一包装
 *
 * @author lizheng
 * @date 2024-09-12
 */
@RestControllerAdvice(basePackages = {"com.lanjii"})
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        List<Boolean> judgeResultList = Arrays.asList(
                // :o:判断相应的类型是否为ResultVo类型
                returnType.getParameterType().isAssignableFrom(R.class),
                // :o:判断响应的方法上是否包含 NotControllerResponseAdvice 注解
                returnType.hasMethodAnnotation(NotControllerResponseAdvice.class)
        );

        // 若包含其中一项,则不进行封装
        return !judgeResultList.contains(true);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装
        if (returnType.getGenericParameterType().equals(String.class)) {
            return JsonUtils.objToJson(R.success(body));
        }
        return R.success(body);
    }
}
