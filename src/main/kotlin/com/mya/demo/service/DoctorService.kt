package com.mya.demo.service

import com.mya.demo.exception.ResourceNotFoundException
import com.mya.demo.model.dto.*
import com.mya.demo.model.entity.Doctor
import com.mya.demo.repository.DoctorRepository
import com.mya.demo.repository.PatientRepository
import com.mya.demo.repository.SpecialtyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DoctorService(
    private val doctorRepository: DoctorRepository,
    private val specialtyRepository: SpecialtyRepository,
    private val patientRepository: PatientRepository
) {
    private fun Doctor.toDetailResponse(): DoctorDetailResponse {
        return DoctorDetailResponse(
            id = this.id!!,
            name = this.name,
            degree = this.degree,
            specialties = this.specialties.map { SpecialtyDto(it.id!!, it.name) }
        )
    }

    @Transactional(readOnly = true)
    fun getAllDoctors(pageable: Pageable): Page<DoctorSummaryDto> {

        val doctorPage: Page<Doctor> = doctorRepository.findAll(pageable)
        return doctorPage.map { doctor ->
            DoctorSummaryDto(doctor.id!!, doctor.name, doctor.degree)
        }
    }

    @Transactional
    fun createDoctor(request: DoctorRequest): DoctorSummaryDto {
        val specialties = specialtyRepository.findAllById(request.specialtyIds).toMutableSet()

        val newDoctor = Doctor(
            name = request.name,
            degree = request.degree,
            specialties = specialties
        )

        val savedDoctor = doctorRepository.save(newDoctor)
        return DoctorSummaryDto(savedDoctor.id!!, savedDoctor.name, savedDoctor.degree)
    }

    @Transactional(readOnly = true)
    fun getDoctorById(id: Long): DoctorDetailResponse {
        val doctor = doctorRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Doctor not found with id: $id")
        }
        return doctor.toDetailResponse()
    }

    @Transactional(readOnly = true)
    fun getDoctorsBySpecialtyName(specialtyName: String): List<DoctorSummaryDto> {
        val specialty = specialtyRepository.findByName(specialtyName)
            ?: throw ResourceNotFoundException("Specialty '$specialtyName' not found")

        val allDoctors = doctorRepository.findAll()

        return allDoctors
            .filter { doctor -> doctor.specialties.any { it.name.equals(specialtyName, ignoreCase = true) } }
            .map { DoctorSummaryDto(it.id!!, it.name, it.degree) }
    }

    @Transactional(readOnly = true)
    fun getDoctorsBySpecialtyName2(specialtyName: String): List<DoctorSummaryDto> {

        val doctors = doctorRepository.findBySpecialtyName(specialtyName)

        return doctors.map { DoctorSummaryDto(it.id!!, it.name, it.degree) }
    }


    @Transactional(readOnly = true)
    fun getPatientByDoctorName(doctorName: String): List<PatientSummaryDto> {
        val doctor = doctorRepository.findByName(doctorName)
            ?: throw ResourceNotFoundException("Doctor '$doctorName' not found")
        val patients = patientRepository.findAll()

        return patients
            .filter { patient -> patient.primaryDoctor.let { it!!.name.equals(doctorName, ignoreCase = true) } }
            .map { PatientSummaryDto(it.id!!, it.name, it.age) }
    }
}

