package app.nileza.droidmemo.manager;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by NileZia on 10/11/2017 AD.
 */

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
       // PreferanceEx preferanceEx = new PreferanceEx();

        Request newRequest = chain.request().newBuilder()
                .header("content-type", "application/json")
                .addHeader("x-access-token", "")
                .build();
        return chain.proceed(newRequest);
    }
}
