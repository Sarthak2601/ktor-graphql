package com.sarthak.models

data class Dessert(
    override val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
) : Model

data class DessertInput(
    val name: String,
    val description: String,
    val imageUrl: String
)
