package com.sarthak.services

import com.mongodb.client.MongoClient
import com.sarthak.models.*
import com.sarthak.repository.DessertRepository
import com.sarthak.repository.ReviewRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class ReviewService: KoinComponent {
    private val client: MongoClient by inject()
    private val repository = ReviewRepository(client)

    fun getReview(id: String): Review {
        return repository.getById(id)
    }

    fun createReview(reviewInput: ReviewInput, userId: String, dessertId: String): Review {
        val uid = UUID.randomUUID()
        val review = Review(uid.toString(), userId, dessertId, reviewInput.text, reviewInput.rating)
        return repository.add(review)
    }

    fun updateReview(reviewId: String, userId: String, reviewInput: ReviewInput): Review {
        val review = repository.getById(reviewId)
        if (review.userId == userId){
            val update = Review(reviewId, userId, review.dessertId, reviewInput.text, reviewInput.rating)
            return repository.update(update)
        }
        error("Can't update review")
    }

    fun deleteReview(userId: String, reviewId: String): Boolean{
        val review = repository.getById(reviewId)
        if (review.userId == userId){
            return repository.delete(reviewId)
        }
        error("Can't delete review")
    }
}