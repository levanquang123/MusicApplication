package com.example.musicapplication

interface ResultCallback<T> {
    fun onResult(result: T)
}