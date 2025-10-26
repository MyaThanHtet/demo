package com.mya.demo.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "symptoms")
class Symptom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var description: String
) {
    constructor() : this(null, "", "")
}