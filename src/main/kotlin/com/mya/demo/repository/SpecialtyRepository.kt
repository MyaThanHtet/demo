package com.mya.demo.repository

import com.mya.demo.model.entity.Specialty
import org.springframework.data.jpa.repository.JpaRepository

interface SpecialtyRepository : JpaRepository<Specialty, Long> {
    fun findByName(name: String): Specialty?
}