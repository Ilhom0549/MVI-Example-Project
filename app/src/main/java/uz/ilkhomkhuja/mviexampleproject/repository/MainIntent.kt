package uz.ilkhomkhuja.mviexampleproject.repository

sealed class MainIntent {
    object FetchUser : MainIntent()
}