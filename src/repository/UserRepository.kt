package com.sarthak.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.sarthak.models.Dessert
import com.sarthak.models.User
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class UserRepository(client: MongoClient): RepositoryInterface<User> {
    override lateinit var collection: MongoCollection<User>
    init {
        val database = client.getDatabase("backend")
        collection = database.getCollection<User>("User")
    }

    fun getUserByEmail(email: String? = null): User?{
        return try {
            collection.findOne(User::email eq email)
        }catch (t: Throwable){
            throw Exception("No user found")
        }
    }
}