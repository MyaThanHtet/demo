package com.mya.demo.repository

import com.mya.demo.model.entity.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByName(name: String): Patient?
}
