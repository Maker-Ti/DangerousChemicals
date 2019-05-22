package com.example.a97569.dangerouschemicals.mvp.model.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Maker on 2018/12/14.
 */

public interface ApiWarning {
    @Headers("Cache-Control: public, max-age=0")
    @GET("dcam/user/getUserByUnameAndPwd?")//登录
    Observable<ResponseBody> getLogin(@Query("username") String username,
                                      @Query("password") String password
                                              );
}
