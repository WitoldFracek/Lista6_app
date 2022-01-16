package com.example.lista6_app.data

import androidx.lifecycle.LiveData

class AnimalRepository(private val animalDao: AnimalDao) {

    val readAllData: LiveData<List<Animal>> = animalDao.readAllData()

    suspend fun addAnimal(animal: Animal) {
        animalDao.addAnimal(animal)
    }

}