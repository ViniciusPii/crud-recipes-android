package com.example.crudreceitas.repositories.impl

import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.repositories.RecipeRepository

class RecipeRepositoryImpl : RecipeRepository {
    private val recipes = mutableListOf<Recipe>()
    override suspend fun getAllRecipes(): List<Recipe> {

        try {
            for (i in 1..3) {
                recipes.add(
                    Recipe(
                        type = "Salgado",
                        name = "Torta de Frango",
                        author = "Vini",
                        id = i.toString(),
                        ingredients = "Frango, Farinha e outros",
                    )
                )
            }
            return recipes
            throw Exception("Forçando uma exceção")
        } catch (e: Exception) {
            throw Exception("Error")
        }
    }

    override suspend fun removeRecipe(recipe: Recipe) {
        recipes.remove(recipe)
    }
}