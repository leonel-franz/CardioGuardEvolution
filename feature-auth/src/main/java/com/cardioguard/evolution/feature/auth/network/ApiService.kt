package com.cardioguard.evolution.feature.auth.network

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class LoginResponse(val status: String, val user_id: String? = null, val message: String? = null)
data class RegisterResponse(val status: String, val message: String? = null)

interface ApiService {
    @FormUrlEncoded
    @POST("api/login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("api/register.php")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): Response<RegisterResponse>
}
