package com.mya.demo.controller

import com.mya.demo.model.dto.ApiResponse
import com.mya.demo.model.dto.DoctorDetailResponse
import com.mya.demo.model.dto.DoctorRequest
import com.mya.demo.model.dto.DoctorSummaryDto
import com.mya.demo.model.dto.PatientSummaryDto
import com.mya.demo.service.DoctorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/api/v1/doctors")
class DoctorController(
    private val doctorService: DoctorService
) {

    @GetMapping
    fun getAllDoctors(pageable: Pageable): Page<DoctorSummaryDto> {
        val doctorPage = doctorService.getAllDoctors(pageable)
        return doctorPage
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createDoctor(
        @Valid @RequestBody request: DoctorRequest
    ): ApiResponse<DoctorSummaryDto> {
        val response = doctorService.createDoctor(request)
        return ApiResponse(
            status = true,
            message = "New Doctor added successfully.",
            data = response
        )
    }

    @GetMapping("/{id}")
    fun getDoctorDetails(
        @PathVariable id: Long
    ): ApiResponse<DoctorDetailResponse> {
        val response = doctorService.getDoctorById(id)
        return ApiResponse(
            status = true,
            message = "Get Doctor details by Id.",
            data = response
        )
    }

    @GetMapping("/search")
    fun getDoctorsBySpecialty(
        @RequestParam specialty: String
    ): ApiResponse<List<DoctorSummaryDto>> {
        val response = doctorService.getDoctorsBySpecialtyName(specialty)

        return ApiResponse(
            status = true,
            message = "Search doctor by specialty.",
            data = response
        )
    }

    @GetMapping("/patient")
    fun getPatientByDoctorName(
        @RequestParam name: String
    ): ApiResponse<List<PatientSummaryDto>> {

        val response = doctorService.getPatientByDoctorName(name)

        return ApiResponse(
            status = true,
            message = "Search patient by doctor name.",
            data = response
        )
    }
}