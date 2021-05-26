package com.sarthak.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.sarthak.models.Dessert
import com.sarthak.models.DessertInput
import com.sarthak.repository.DessertRepository
import java.util.*

fun SchemaBuilder.dessertSchema(){
    val repository = DessertRepository()

    inputType<DessertInput>{
        description = "The input of the dessert without identifier"
    }

    type<Dessert>{
        description = "Dessert object with attributes name, description and imageUrl"
    }

    query("dessert") {
        resolver { dessertId: String ->
            try {
                repository.getById(dessertId)
            }catch (e: Exception){
                null
            }
        }
    }

    query("desserts") {
        resolver { ->
            try {
                repository.getAll()
            }catch (e: Exception){
                emptyList<Dessert>()
            }
        }
    }

    mutation("createDessert"){
        description = "Create a new dessert"
        resolver { dessertInput: DessertInput ->
            try {
                val uid = UUID.randomUUID().toString()
                val dessert = Dessert(uid, dessertInput.name, dessertInput.description, dessertInput.imageUrl)
                repository.add(dessert)
            }catch (e: Exception){
                null
            }
        }
    }

    mutation("deleteDessert"){
        description = "Delete a dessert"
        resolver { dessertId: String ->
            try {
                repository.delete(dessertId)
            }catch (e: Exception){
                null
            }
        }
    }

    mutation("updateDessert"){
        description = "Update the existing desserts"
        resolver { dessertId: String, dessertInput: DessertInput ->
            try {
                val dessert = Dessert(dessertId, dessertInput.name, dessertInput.description, dessertInput.imageUrl)
                repository.update(dessert)
            }catch (e: Exception){
                null
            }
        }
    }
}