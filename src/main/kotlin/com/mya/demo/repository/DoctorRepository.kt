package com.mya.demo.repository

import com.mya.demo.model.entity.Doctor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DoctorRepository : JpaRepository<Doctor, Long> {
    fun findByName(name: String): Doctor?

    @Query("""
        SELECT d 
        FROM Doctor d 
        JOIN d.specialties s 
        WHERE s.name = :specialtyName
    """)
    fun findBySpecialtyName(specialtyName: String): List<Doctor>

    override fun findAll(pageable: Pageable): Page<Doctor>
}
