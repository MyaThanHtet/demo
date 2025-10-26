package com.mya.demo.controller

import com.mya.demo.model.dto.SpecialtyDto
import com.mya.demo.service.SpecialtyRequest
import com.mya.demo.service.SpecialtyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/specialties")
class SpecialtyController(
    private val specialtyService: SpecialtyService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSpecialty(@Valid @RequestBody request: SpecialtyRequest): SpecialtyDto {
        return specialtyService.createSpecialty(request)
    }


    @GetMapping
    fun getAllSpecialties(): List<SpecialtyDto> {
        return specialtyService.getAllSpecialties()
    }
}