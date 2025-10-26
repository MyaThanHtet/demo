package com.mya.demo.repository

import com.mya.demo.model.entity.Symptom
import org.springframework.data.jpa.repository.JpaRepository

interface SymptomRepository : JpaRepository<Symptom, Long>
