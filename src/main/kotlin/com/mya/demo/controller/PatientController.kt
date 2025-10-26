package com.mya.demo.controller


import com.mya.demo.model.dto.ApiResponse
import com.mya.demo.model.dto.PatientDetailResponse
import com.mya.demo.model.dto.PatientRequest
import com.mya.demo.model.dto.PatientSummaryDto
import com.mya.demo.service.PatientService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/v1/patients")
class PatientController(
    private val patientService: PatientService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPatient(
        @Valid @RequestBody request: PatientRequest
    ): ApiResponse<PatientSummaryDto> {

        val createdPatientDto = patientService.createPatient(request)

        return ApiResponse(
            status = true,
            message = "New patient added successfully.",
            data = createdPatientDto
        )
    }

    @GetMapping("/details")
    fun getPatientDetailsByName(
        @RequestParam name: String
    ): ApiResponse<PatientDetailResponse> {
        val patientDetailResponse = patientService.getPatientDetailsByName(name)

        return ApiResponse(
            status = true,
            message = "Get Patient details by name.",
            data = patientDetailResponse
        )
    }

    @GetMapping("/doctor/{patientName}")
    fun getPatientDoctorDetails(
        @PathVariable patientName: String
    ): ApiResponse<PatientDetailResponse> {

        val response = patientService.getDoctorByPatientName(patientName) as PatientDetailResponse
        return ApiResponse(
            status = true,
            message = "Get Doctor details by patient name",
            data = response
        )
    }
}