package com.sarthak.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.sarthak.models.User
import com.sarthak.services.ProfileService

fun SchemaBuilder.profileSchema(profileService: ProfileService){

    query("getProfile"){
        resolver{ context: Context ->
            try {
                val userId = context.get<User>()?.id ?: error("User not signed in")
                profileService.getProfile(userId)
            }catch (e: Exception){
                null
            }
        }
    }
}