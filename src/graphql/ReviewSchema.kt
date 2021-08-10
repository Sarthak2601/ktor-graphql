package com.sarthak.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.sarthak.models.*
import com.sarthak.services.DessertService
import com.sarthak.services.ReviewService

fun SchemaBuilder.reviewSchema(reviewService: ReviewService){
    inputType<ReviewInput>{
        description = "The input of the review without identifier"
    }

    type<Review>{
        description = "Review object with attributes text and rating"
    }

    query("review") {
        resolver { reviewId: String ->
            try {
                reviewService.getReview(reviewId)
            }catch (e: Exception){
                null
            }
        }
    }

    mutation("createReview"){
        description = "Create a new review"
        resolver { reviewInput: ReviewInput, dessertId: String, context: Context ->
            try {
                val userId = context.get<User>()?.id ?: error("User not signed in")
                reviewService.createReview(reviewInput, userId, dessertId)
            }catch (e: Exception){
                throw e
            }
        }
    }

    mutation("deleteReview"){
        description = "Delete a review"
        resolver { reviewId: String, context: Context ->
            try {
                val userId = context.get<User>()?.id ?: error("User not signed in")
                reviewService.deleteReview(userId, reviewId)
            }catch (e: Exception){
                null
            }
        }
    }

    mutation("updateReview"){
        description = "Update the existing reviews"
        resolver { reviewId: String, reviewInput: ReviewInput, context: Context ->
            try {
                val userId = context.get<User>()?.id ?: error("User not signed in")
                reviewService.updateReview(reviewId, userId, reviewInput)
            }catch (e: Exception){
                null
            }
        }
    }
}
