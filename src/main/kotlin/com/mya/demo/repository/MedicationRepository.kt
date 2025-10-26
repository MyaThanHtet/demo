package com.mya.demo.repository

import com.mya.demo.model.entity.Medication
import com.mya.demo.model.entity.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface MedicationRepository : JpaRepository<Medication, Long> {
    fun findByPatient(patient: Patient): List<Medication>
}