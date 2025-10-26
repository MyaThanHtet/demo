package com.mya.demo.service


import com.mya.demo.exception.ResourceNotFoundException
import com.mya.demo.model.dto.*
import com.mya.demo.model.entity.Medication
import com.mya.demo.model.entity.Patient
import com.mya.demo.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PatientService(
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository,
    private val symptomRepository: SymptomRepository,
    private val medicationRepository: MedicationRepository
) {

    private fun Patient.toDetailResponse(medications: List<Medication>): PatientDetailResponse {
        return PatientDetailResponse(
            id = this.id!!,
            name = this.name,
            age = this.age,
            primaryDoctor = this.primaryDoctor?.let { DoctorSummaryDto(it.id!!, it.name, it.degree) },
            symptoms = this.symptoms.map { SymptomDto(it.id!!, it.name, it.description) },
            medications = medications.map { MedicationDto(it.id!!, it.name, it.dosage) }
        )
    }

    @Transactional
    fun createPatient(request: PatientRequest): PatientSummaryDto {
        val primaryDoctor = doctorRepository.findById(request.primaryDoctorId)
            .orElseThrow { ResourceNotFoundException("Primary Doctor with ID ${request.primaryDoctorId} not found") }

        val symptoms = symptomRepository.findAllById(request.symptomIds)
        if (symptoms.size != request.symptomIds.size) {
            val foundIds = symptoms.map { it.id }.toSet()
            val missingIds = request.symptomIds.filter { it !in foundIds }
            throw ResourceNotFoundException("Symptoms not found for IDs: $missingIds")
        }

        val newPatient = Patient(
            name = request.name,
            age = request.age,
            primaryDoctor = primaryDoctor,
            symptoms = symptoms.toMutableSet()
        )

        val savedPatient = patientRepository.save(newPatient)

        return PatientSummaryDto(savedPatient.id!!, savedPatient.name, savedPatient.age)
    }

    @Transactional(readOnly = true)
    fun getPatientDetailsByName(name: String): PatientDetailResponse {
        val patient = patientRepository.findByName(name)
            ?: throw ResourceNotFoundException("Patient with name '$name' not found")

        val medications = medicationRepository.findByPatient(patient)

        return patient.toDetailResponse(medications)
    }

    @Transactional(readOnly = true)
    fun getDoctorByPatientName(patientName: String): DoctorDetailResponse {
        val patient = patientRepository.findByName(patientName)
            ?: throw ResourceNotFoundException("Patient with name '$patientName' not found")

        val doctor = patient.primaryDoctor
            ?: throw ResourceNotFoundException("Patient '$patientName' has no primary doctor assigned")

        return DoctorDetailResponse(
            id = doctor.id!!,
            name = doctor.name,
            degree = doctor.degree,
            specialties = doctor.specialties.map { SpecialtyDto(it.id!!, it.name) }
        )
    }
}