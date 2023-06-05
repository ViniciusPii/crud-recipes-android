package com.example.crudreceitas.repositories.impl

import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.repositories.RecipeRepository

class RecipeRepositoryImpl : RecipeRepository {
    private val recipes = mutableListOf<Recipe>()
    override suspend fun getAllRecipes(): List<Recipe> = recipes

    override suspend fun removeRecipe(recipe: Recipe) {
        recipes.remove(recipe)
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

}