package com.sarthak.services

import com.mongodb.client.MongoClient
import com.sarthak.models.Profile
import com.sarthak.repository.DessertRepository
import com.sarthak.repository.UserRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileService: KoinComponent {
    private val client: MongoClient by inject()
    private val userRepository = UserRepository(client)
    private val dessertRepository = DessertRepository(client)

    fun getProfile(userId: String): Profile {
        val user = userRepository.getById(userId)
        val desserts = dessertRepository.getDessertsByUserId(userId)
        return Profile(user, desserts)
    }
}