package com.example.crudreceitas.models

data class Recipe(
    val type: String,
    val name: String,
    val author: String,
    val id: String? = null,
    val ingredients: String,
)