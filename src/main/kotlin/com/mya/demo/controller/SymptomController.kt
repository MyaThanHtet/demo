package com.mya.demo.controller


import com.mya.demo.model.dto.SymptomDto
import com.mya.demo.model.dto.SymptomRequest
import com.mya.demo.service.SymptomService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/symptoms")
class SymptomController(
    private val symptomService: SymptomService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSymptom(@Valid @RequestBody request: SymptomRequest): SymptomDto {
        return symptomService.createSymptom(request)
    }

    @GetMapping
    fun getAllSymptoms(): List<SymptomDto> {
        return symptomService.getAllSymptoms()
    }
}