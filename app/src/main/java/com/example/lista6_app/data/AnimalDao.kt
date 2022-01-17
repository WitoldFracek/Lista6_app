package com.example.lista6_app.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAnimal(animal: Animal)

    @Query("SELECT * FROM animalTable ORDER BY id ASC")
    fun readAllData(): LiveData<List<Animal>>

    @Query("SELECT * FROM animalTable WHERE id = :id")
    fun getAnimalWithId(id: Int): Animal

    @Update
    fun updateAnimal(animal: Animal)

    @Delete
    fun deleteAnimal(animal: Animal)
}