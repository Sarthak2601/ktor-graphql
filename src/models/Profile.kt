package com.sarthak.models

data class Profile(val user: User, val desserts: List<Dessert> = emptyList())
