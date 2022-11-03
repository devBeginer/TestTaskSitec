package com.devbeginner.testtasksitec.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import org.koin.android.ext.koin.androidContext
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module

/*private const val BASEURL = "https://dev.sitec24.ru/UKA_TRADE/hs/MobileClient/"

val netModule = module {
    *//*single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(androidContext()))
            .addInterceptor(HttpLoggingInterceptor(
                object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("OkHttp", message)
                    }
                }
            ).apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()
    }*//*



    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }


    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}*/
