package com.cardioguard.evolution.data.remote

import retrofit2.Response
import retrofit2.http.*

// ------------------ RESPUESTAS ------------------

data class LoginResponse(
    val status: String,
    val user_id: Int? = null,
    val message: String? = null
)

data class RegisterResponse(
    val status: String,
    val message: String? = null
)

data class Measurement(
    val id: Int,
    val user_id: Int,
    val bpm: Int,
    val oxigen: Int,
    val temperatura: Int,
    val estado_wifi: Int,
    val estado_sensor: Int,
    val timestamp: String
)

// ------------------ API SERVICE ------------------

interface ApiService {

    // LOGIN sigue usando FormUrlEncoded (tu login actual no usa JSON)
    @FormUrlEncoded
    @POST("api/login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    // REGISTER ahora manda JSON
    @POST("api/register.php")
    suspend fun register(
        @Body data: Map<String, String>
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("api/insert_measurement.php")
    suspend fun insertMeasurement(
        @Field("user_id") userId: Int,
        @Field("bpm") bpm: Int,
        @Field("oxigen") oxigen: Int,
        @Field("temperatura") temperatura: Int,
        @Field("estado_wifi") estadoWifi: Int = 1,
        @Field("estado_sensor") estadoSensor: Int = 1
    ): Response<Map<String, String>>

    @GET("api/get_measurements.php")
    suspend fun getMeasurements(
        @Query("user_id") userId: Int
    ): Response<List<Measurement>>
}