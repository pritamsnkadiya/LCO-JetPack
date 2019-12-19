package com.example.lcogetpack.di.api;

import android.util.Log;

import com.example.lcogetpack.data.utils.AppConstants;
import com.example.lcogetpack.data.utils.Method;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lcogetpack.di.init.ApplicationAppContext.getAppContext;

@Module
public class ApiClient {
    private String urlPath;
    public static String TAG = ApiClient.class.getName();

    public ApiClient(String urlPath) {
        this.urlPath = urlPath;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Gson gson) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlPath)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            String token;
            AppConstants.TOKEN = Method.getPreferences(getAppContext(), "Authorization");
            Request original = chain.request();
            if (getAppContext() != null && !AppConstants.TOKEN.equalsIgnoreCase("")) {
                token = Method.getPreferences(getAppContext(), "Authorization");
                // // token = "Bearer" + " " + Method.getPreferences(AppConstants.CONTEXT, "Authorization");
            } else {
                token = "";
            }
            Request request = original.newBuilder()
                    .header("Authorization", token)
                    .method(original.method(), original.body())
                    .build();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request);
            }

            try {
                Log.d(TAG, "request" + "\n" + requestLog);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());
            String bodyString = response.body().string();
            Log.d(TAG, "response" + "\n" + responseLog + "\n" + bodyString);


            try {
                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), bodyString))
                        .build();
            } catch (Exception e) {
                Log.d(TAG, e.getMessage() + "---- XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }
            return null;
        }

        public static String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }


}
