package com.sarthak.repository

import com.mongodb.client.MongoCollection
import com.sarthak.models.Model
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

interface RepositoryInterface<T> {
    var collection: MongoCollection<T>

    fun getById(id: String): T {
        return try {
            collection.findOne(Model::id eq id)?: throw Exception("Can't find")
        }catch (e: Exception){
            throw e
        }
    }
    fun getAll(): List<T>{
        return try {
            collection.find().asIterable().map { it } ?:throw Exception("Can't find")
        }catch (e: Exception){
            throw e
        }
    }
    fun delete(id:String): Boolean {
        return try {
            collection.deleteOne(Model::id eq id)?: throw Exception("Can't delete")
            true
        }catch (e: Exception){
            throw e
        }
    }
    fun add(entry: T): T {
        return try {
            collection.insertOne(entry)?: throw Exception("Can't add")
            entry
        }catch (e: Exception){
            throw e
        }
    }
    fun update(entry: Model): T{
        return try {
            collection.updateOne(Model::id eq entry.id, entry)
            collection.findOne(Model::id eq entry.id)?: throw Exception("Can't update")
        }catch (e: Exception){
            throw e
        }
    }
}
