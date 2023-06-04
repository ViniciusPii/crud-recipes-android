package com.example.crudreceitas.repositories.impl

import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.repositories.RecipeRepository

class RecipeRepositoryImpl : RecipeRepository {
    override suspend fun getAllRecipes(): List<Recipe> {

        try {
            val recipes = mutableListOf<Recipe>()


//            for (i in 1..10) {
//                recipes.add(
//                    Recipe(
//                        "Vini Sales",
//                        i.toString(),
//                        "2 xícaras de trigo. 3 colheres de açucar. 2 colheres de manteiga.",
//                        "Bolo de Cenoura",
//                        "Doce"
//                    )
//                )
//            }


            return recipes

            throw Exception("Forçando uma exceção")
        } catch (e: Exception) {
            throw Exception("Error")
        }
    }
}