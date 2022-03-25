package uz.ilkhomkhuja.mviexampleproject.network

import retrofit2.http.GET
import uz.ilkhomkhuja.mviexampleproject.models.User

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}