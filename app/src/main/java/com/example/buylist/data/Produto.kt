package com.example.buylist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Produto(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val preco: Double,
    var quantidade: Int = 1

) {

}