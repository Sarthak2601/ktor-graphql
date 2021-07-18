package com.sarthak

import com.apurebase.kgraphql.GraphQL
import com.sarthak.di.mainModule
import com.sarthak.graphql.authSchema
import com.sarthak.graphql.dessertSchema
import com.sarthak.graphql.profileSchema
import com.sarthak.graphql.reviewSchema
import com.sarthak.services.AuthService
import com.sarthak.services.DessertService
import com.sarthak.services.ProfileService
import com.sarthak.services.ReviewService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin { modules(mainModule) }

    install(GraphQL) {
        val desertService = DessertService()
        val reviewService = ReviewService()
        val authService = AuthService()
        val profileService = ProfileService()
        playground = true
        context { call ->
            authService.verifyToken(call)?.let { +it }
            +log
            +call
        }
        schema {
            dessertSchema(desertService)
            reviewSchema(reviewService)
            authSchema(authService)
            profileSchema(profileService)
        }
    }
}

