package com.example.lista6_app.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimalViewModel(aplication: Application): AndroidViewModel(aplication) {

    val readAllData: LiveData<List<Animal>>
    private val repository: AnimalRepository

    init {
        val animalDao = AnimalDatabase.getDatabase(aplication).animalDao()
        repository = AnimalRepository(animalDao)
        readAllData = repository.readAllData
    }

    fun addAnimal(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAnimal(animal)
        }
    }

    fun getFirst(): Animal? {
        return readAllData.value?.get(0)
    }

    fun updateAnimal(animal: Animal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAnimal(animal)
        }
    }

    fun deleteAnimal(animal: Animal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAnimal(animal)
        }
    }

//    fun getAnimalWithId(id: Int): Animal{
//        viewModelScope.launch(Dispatchers.IO) {
//            val ret = repository.getAnimalWithId(id)
//        }
//    }

}