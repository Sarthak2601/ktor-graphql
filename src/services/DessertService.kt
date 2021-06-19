package com.sarthak.services

import com.mongodb.client.MongoClient
import com.sarthak.models.Dessert
import com.sarthak.models.DessertInput
import com.sarthak.repository.DessertRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class DessertService: KoinComponent {
    private val client: MongoClient by inject()
    private val repository = DessertRepository(client)

    fun getDessert(id: String): Dessert{
        return repository.getById(id)
    }

    fun createDessert(dessertInput: DessertInput, userId: String): Dessert {
        val uid = UUID.randomUUID()
        val dessert = Dessert(uid.toString(), userId, dessertInput.name, dessertInput.description, dessertInput.imageUrl)
        return repository.add(dessert)
    }

    fun updateDessert(dessertId: String, userId: String, dessertInput: DessertInput): Dessert{
        val dessert = repository.getById(dessertId)
        if (dessert.userId == userId){
            val update = Dessert(dessertId, userId, dessertInput.name, dessertInput.description, dessertInput.imageUrl)
            repository.update(update)
        }
        error("Can't update dessert")
    }

    fun deleteDessert(userId: String, dessertId: String): Boolean{
        val dessert = repository.getById(dessertId)
        if (dessert.userId == userId){
            return repository.delete(dessertId)
        }
        error("Can't delete dessert")
    }
}