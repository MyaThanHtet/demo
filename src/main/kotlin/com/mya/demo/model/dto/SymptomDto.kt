package com.mya.demo.model.dto

data class SymptomRequest(val name: String, val description: String)
data class SymptomDto(val id: Long, val name: String, val description: String)