package com.aakash.notes.model.data

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

class TIme {
}

fun main() {
    println(LocalDate.now())
    val curr=(System.currentTimeMillis())
    val times= Timestamp(curr)
    println(times)
}