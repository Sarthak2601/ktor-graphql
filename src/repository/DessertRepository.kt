package com.sarthak.repository

import com.sarthak.data.desserts
import com.sarthak.models.Dessert

class DessertRepository: RepositoryInterface<Dessert> {

    override fun getById(id: String): Dessert {
        return try {
            desserts.find {
                it.id == id
            }?: throw Exception("Couldn't find dessert with id = $id")
        }catch (e: Exception){
            throw Exception("Can't find dessert")
        }
    }

    override fun getAll(): List<Dessert> {
        return desserts
    }

    override fun delete(id: String): Boolean {
        return try {
            val dessert = desserts.find {
                it.id == id
            }?: throw Exception("Couldn't find dessert with id = $id")
            desserts.remove(dessert)
            true
        }catch (e: Throwable){
            throw Exception("Couldn't find dessert")
        }
    }

    override fun add(entry: Dessert): Dessert {
        desserts.add(entry)
        return entry
    }

    override fun update(entry: Dessert): Dessert {
        return try {
            val dessert = desserts.find {
                it.id == entry.id
            }?.apply {
                name = entry.name
                description = entry.description
                imageUrl = entry.imageUrl
            } ?: throw Exception("Couldn't find dessert with id = ${entry.id}")
            entry
        }catch (e: Throwable){
            throw Exception("Couldn't find dessert")
        }
    }
}
