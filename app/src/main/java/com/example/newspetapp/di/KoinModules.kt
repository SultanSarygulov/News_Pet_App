package com.example.newspetapp.di

import com.example.newspetapp.data.module.Api
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.data.repository.AuthRepository
import com.example.newspetapp.presentation._auth.code.CodeViewModel
import com.example.newspetapp.presentation._auth.login.LoginViewModel
import com.example.newspetapp.presentation._auth.new_password.NewPasswordViewModel
import com.example.newspetapp.presentation._auth.register.RegisterViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



    val viewModelModule = module{

        viewModel { LoginViewModel(authRepository = get()) }
        viewModel { RegisterViewModel(authRepository = get()) }
        viewModel { CodeViewModel(authRepository = get()) }
        viewModel { NewPasswordViewModel(authRepository = get()) }
    }

    val retrofitModule = module{

        single { CustomPreferences(androidContext()) }
        single { getOkHttpInstance() }
        single{ getRetrofit(okHttpClient = get())}
        single { getApi(retrofit = get()) }
        factory { AuthRepository(api = get()) }
    }

    fun getApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    fun getOkHttpInstance(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()
    }
