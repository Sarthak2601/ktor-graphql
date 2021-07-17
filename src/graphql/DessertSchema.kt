package com.sarthak.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.sarthak.models.Dessert
import com.sarthak.models.DessertInput
import com.sarthak.services.DessertService

fun SchemaBuilder.dessertSchema(dessertService: DessertService){
    inputType<DessertInput>{
        description = "The input of the dessert without identifier"
    }

    type<Dessert>{
        description = "Dessert object with attributes name, description and imageUrl"
    }

    query("dessert") {
        resolver { dessertId: String ->
            try {
                dessertService.getDessert(dessertId)
            }catch (e: Exception){
                null
            }
        }
    }

    query("desserts"){
        resolver{ page: Int?, size: Int? ->
            try {
                dessertService.getDessertsPage(page?: 0, size?: 10 )
            }catch (e: Exception){
                null
            }
        }
    }

    mutation("createDessert"){
        description = "Create a new dessert"
        resolver { dessertInput: DessertInput ->
            try {
                val userId = "abc"
                dessertService.createDessert(dessertInput, userId)
            }catch (e: Exception){
                throw e
            }
        }
    }

    mutation("deleteDessert"){
        description = "Delete a dessert"
        resolver { dessertId: String ->
            try {
                val userId = "abc"
                dessertService.deleteDessert(userId, dessertId)
            }catch (e: Exception){
                null
            }
        }
    }

    mutation("updateDessert"){
        description = "Update the existing desserts"
        resolver { dessertId: String, dessertInput: DessertInput ->
            try {
                val userId = "abc"
                dessertService.updateDessert(dessertId, userId, dessertInput)
            }catch (e: Exception){
                null
            }
        }
    }
}