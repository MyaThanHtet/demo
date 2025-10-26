package com.mya.demo.model.dto


import jakarta.validation.constraints.NotBlank

data class PatientRequest(
    @field:NotBlank val name: String,
    val age: Int,
    val primaryDoctorId: Long,
    val symptomIds: List<Long> = emptyList()
)

data class PatientDetailResponse(
    val id: Long,
    val name: String,
    val age: Int,
    val primaryDoctor: DoctorSummaryDto?,
    val symptoms: List<SymptomDto>,
    val medications: List<MedicationDto>
)

data class PatientSummaryDto(
    val id: Long,
    val name: String,
    val age: Int
)