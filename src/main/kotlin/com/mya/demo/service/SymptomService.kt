package com.mya.demo.service

import com.mya.demo.model.dto.SymptomDto
import com.mya.demo.model.dto.SymptomRequest
import com.mya.demo.model.entity.Symptom
import com.mya.demo.repository.SymptomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SymptomService(
    private val repository: SymptomRepository
) {
    private fun Symptom.toDto() = SymptomDto(this.id!!, this.name, this.description)

    @Transactional
    fun createSymptom(request: SymptomRequest): SymptomDto {
        val newSymptom = Symptom(name = request.name, description = request.description)
        val savedSymptom = repository.save(newSymptom)
        return savedSymptom.toDto()
    }

    @Transactional(readOnly = true)
    fun getAllSymptoms(): List<SymptomDto> {
        return repository.findAll().map { it.toDto() }
    }
}