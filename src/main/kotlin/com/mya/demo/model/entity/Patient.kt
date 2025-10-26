package com.mya.demo.model.entity


import jakarta.persistence.*

@Entity
@Table(name = "patients")
class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var age: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    var primaryDoctor: Doctor? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "patient_symptom",
        joinColumns = [JoinColumn(name = "patient_id")],
        inverseJoinColumns = [JoinColumn(name = "symptom_id")]
    )
    var symptoms: MutableSet<Symptom> = mutableSetOf()
) {
    constructor() : this(null, "", 0)
}