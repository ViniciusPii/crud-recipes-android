package com.example.crudreceitas.repositories

import com.example.crudreceitas.models.Recipe

interface RecipeRepository {
    suspend fun getAllRecipes(): List<Recipe>
}