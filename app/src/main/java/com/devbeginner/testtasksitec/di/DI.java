package com.devbeginner.testtasksitec.di;

import android.os.Build;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class DI {
    private static final String BASEURL = "https://dev.sitec24.ru/UKA_TRADE/hs/MobileClient/";


    public DI() {
    }

    private static final DI INSTANCE = new DI();
    private static final Retrofit RETROFIT_INSTANCE = provideRetrofit(provideGson(), provideHttpClient());

    public static DI getINSTANCE() {
        return INSTANCE;
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClientBuilder() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //if (Build.VERSION.SDK_INT < 29) {
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            //}
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            //OkHttpClient okHttpClient = builder.build();
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static OkHttpClient provideHttpClient() {
        return getUnsafeOkHttpClientBuilder()
                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();

                        // Настраиваем запросы
                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                //.header("Authorization", "basic " +
                                //        Arrays.toString(Base64.encode("http:http".getBytes(), Base64.NO_WRAP)))
                                .header("Authorization", Credentials.basic("http", "http"))
                                //.method(original.method(), original.body())
                                .build();

                        Response response = chain.proceed(request);

                        return response;
                    }
                })
                .build();

    }

    private static Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                //.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }


    private static Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }

    public static Retrofit getRetrofitInstance() {
        return RETROFIT_INSTANCE;
    }
}
