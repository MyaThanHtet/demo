package com.mya.demo.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table


@Entity
@Table(name = "doctors")
class Doctor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var degree: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "doctor_specialty",
        joinColumns = [JoinColumn(name = "doctor_id")],
        inverseJoinColumns = [JoinColumn(name = "specialty_id")]
    )
    var specialties: MutableSet<Specialty> = mutableSetOf()
) {
    constructor() : this(null, "", "")
}