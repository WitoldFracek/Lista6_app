package com.example.lista6_app.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lista6_app.ListAdapter

val CAT = ListAdapter.CAT
val DOG = ListAdapter.DOG

@Entity(tableName = "animalTable")
data class Animal (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val breed: String,
    val species: Int,
    val red: Int,
    val green: Int,
    val blue: Int,
    val gender: Char,
    val behaviour: Float,
    val age: Int
    )