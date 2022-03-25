package uz.ilkhomkhuja.mviexampleproject.repository

import uz.ilkhomkhuja.mviexampleproject.network.ApiService

class MainRepository(private var apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
}