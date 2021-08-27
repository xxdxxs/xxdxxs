package com.xxdxxs.web;

import com.xxdxxs.annotate.ResultDefine;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * 处理web返回结果
 * @author xxdxxs
 */
public class WebReturnHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        // 如果有我们自定义的 ResultDefine 注解 就用我们这个Handler 来处理
        boolean isHas = methodParameter.getMethodAnnotation(ResultDefine.class) != null;
        return isHas;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        modelAndViewContainer.setRequestHandled(true);
        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annotations = methodParameter.getMethodAnnotations();
        Arrays.stream(annotations).forEach(annotation -> {
            if (annotation.equals(ResultDefine.class)){

            }
        });

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        /* String json = jsonSerializer.toJson(returnValue);
        response.getWriter().write(json);*/
    }
}
