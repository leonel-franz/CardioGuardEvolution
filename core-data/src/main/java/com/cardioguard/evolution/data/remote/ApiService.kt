package com.cardioguard.evolution.data.remote

import com.cardioguard.evolution.models.auth.LoginRequest
import com.cardioguard.evolution.models.auth.LoginResponse
import com.cardioguard.evolution.models.auth.RegisterRequest
import com.cardioguard.evolution.models.auth.BasicResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.FieldMap
import retrofit2.http.Field

interface ApiService {

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register.php")
    suspend fun register(@Body request: RegisterRequest): BasicResponse

    // Si tu backend usa x-www-form-urlencoded en vez de JSON, cambia a:
    // @FormUrlEncoded
    // @POST("login.php")
    // suspend fun loginForm(@Field("email") email: String, @Field("password") password: String): LoginResponse
}
