package com.example.a97569.dangerouschemicals.mvp.model.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 *******************************************************************************
 * @FileName:  JsonConverterFactory
 * @Package com.yidaoyun.newenergyvehicleandroid.http
 * @Description:  网络请求/解析时，实体bean与JsonObject转换工具类
 * @author: lpz
 * @date:   2018/10/8  14:07
 * @version V1.0
 *******************************************************************************
 */
public class JsonConverterFactory extends Converter.Factory {

    public static JsonConverterFactory create() {
        return new JsonConverterFactory();
    }

    /**
     *
     * @param type:http请求时请求体类型
     * @param parameterAnnotations：接口注释
     * @param retrofit：请求实体
     * @return :请求体由JSONObject转换为RequestBody
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new JsonRequestBodyConverter();
    }

    /**
     *
     * @param type:http响应时时请求体类型
     * @param annotations：接口注释
     * @param retrofit：响应实体
     * @return Converter<JSONObject>:响应体由ResponseBody转换为JSONObject
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new JsonResponseBodyConverter();
    }

    //这是请求时处理
    public static class JsonRequestBodyConverter implements Converter<JSONObject, RequestBody> {

        public RequestBody convert(JSONObject value) throws IOException {
            return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), value.toString());
        }
    }

    //这是请求后处理，如果后台不做限制我们这里就不进行转换处理
    public static class JsonResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {

        @Override
        public ResponseBody convert(ResponseBody value) throws IOException {
            return value;
        }
    }
}
