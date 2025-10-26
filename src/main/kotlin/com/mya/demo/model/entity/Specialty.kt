package com.mya.demo.model.entity


import jakarta.persistence.*

@Entity
@Table(name = "specialties")
class Specialty(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String
) {
    constructor() : this(null, "")
}