package com.mya.demo.model.entity


import jakarta.persistence.*

@Entity
@Table(name = "medications")
class Medication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var dosage: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    var patient: Patient? = null
) {
    constructor() : this(null, "", "")
}