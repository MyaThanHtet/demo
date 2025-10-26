package com.mya.demo.service


import com.mya.demo.model.dto.SpecialtyDto
import com.mya.demo.model.entity.Specialty
import com.mya.demo.repository.SpecialtyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class SpecialtyRequest(val name: String)

@Service
class SpecialtyService(
    private val repository: SpecialtyRepository
) {
    private fun Specialty.toDto() = SpecialtyDto(this.id!!, this.name)

    @Transactional
    fun createSpecialty(request: SpecialtyRequest): SpecialtyDto {
        val newSpecialty = Specialty(name = request.name)
        val savedSpecialty = repository.save(newSpecialty)
        return savedSpecialty.toDto()
    }

    @Transactional(readOnly = true)
    fun getAllSpecialties(): List<SpecialtyDto> {
        return repository.findAll().map { it.toDto() }
    }
}