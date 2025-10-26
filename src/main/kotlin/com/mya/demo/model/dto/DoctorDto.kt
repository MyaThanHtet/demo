package com.mya.demo.model.dto


import jakarta.validation.constraints.NotBlank

data class DoctorRequest(
    @field:NotBlank val name: String,
    @field:NotBlank val degree: String,
    val specialtyIds: List<Long> = emptyList()
)

data class DoctorDetailResponse(
    val id: Long,
    val name: String,
    val degree: String,
    val specialties: List<SpecialtyDto>
)

data class DoctorSummaryDto(
    val id: Long,
    val name: String,
    val degree: String
)