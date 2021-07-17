package com.sarthak.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.sarthak.models.User
import com.sarthak.models.UserInput
import com.sarthak.services.AuthService

fun SchemaBuilder.authSchema(authService: AuthService){
    inputType<UserInput>{
        description = "The input of the user without identifier"
    }

    type<User>{
        User::hashPass.ignore()
        description = "User object with attributes like id and email"
    }

    mutation("signIn"){
        description = "Authenticating an existing user"
        resolver { userInput: UserInput ->
            try {
                authService.signIn(userInput)
            }catch (e: Exception){
                null
            }
        }
    }

    mutation("signUp"){
        description = "Authenticating a new user"
        resolver { userInput: UserInput ->
            try {
                authService.signUp(userInput)
            }catch (e: Exception){
                null
            }
        }
    }
}