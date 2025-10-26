package com.mya.demo.model.dto

data class ApiResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T? = null
)