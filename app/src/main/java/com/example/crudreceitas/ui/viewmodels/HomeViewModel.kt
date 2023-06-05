package com.example.crudreceitas.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.repositories.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _recipes = MutableLiveData<State>()
    val recipes: LiveData<State> get() = _recipes

    private var recipeIdCounter = 0

    private var isInitialLoad = true

    fun getAllRecipes() {
        if (!isInitialLoad && _recipes.value is State.Success) return

        viewModelScope.launch {
            _recipes.value = State.Loading

            try {
                val recipes: List<Recipe> = recipeRepository.getAllRecipes()

                delay(1500)

                _recipes.value = if (recipes.isNotEmpty()) {
                    State.Success(recipes)
                } else {
                    State.Empty
                }
            } catch (e: Exception) {
                delay(1000) // Simula um atraso de 1 segundo

                _recipes.value = State.Error(e.message.toString())
            }

            isInitialLoad = false
        }
    }

    fun removeRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                val currentRecipes = _recipes.value

                if (currentRecipes is State.Success) {
                    val updatedRecipes = currentRecipes.data.toMutableList()
                    updatedRecipes.remove(recipe)

                    _recipes.value = State.Loading

                    recipeRepository.removeRecipe(recipe)

                    delay(1000)

                    _recipes.value = if (updatedRecipes.isNotEmpty()) {
                        State.Success(updatedRecipes)
                    } else {
                        State.Empty
                    }
                }
            } catch (e: Exception) {
                delay(1000)
                _recipes.value = State.Error(e.message.toString())
            }
        }
    }

    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                val currentRecipes = _recipes.value

                _recipes.value = State.Loading

                val newRecipe = recipe.copy(id = recipeIdCounter.toString())

                recipeRepository.saveRecipe(newRecipe)



                if (currentRecipes is State.Success) {
                    val updatedRecipes = currentRecipes.data.toMutableList()
                    updatedRecipes.add(newRecipe)
                    delay(1000)
                    _recipes.value = State.Success(updatedRecipes)

                } else {
                    val recipes = _recipes.value?.let {
                        if (it is State.Success) {
                            it.data.toMutableList().apply {
                                add(newRecipe)
                            }
                        } else {
                            mutableListOf(newRecipe)
                        }
                    }
                    delay(1000)
                    _recipes.value = State.Success(recipes ?: emptyList())
                }

                recipeIdCounter++
            } catch (e: Exception) {
                delay(1000)
                _recipes.value = State.Error(e.message.toString())
            }
        }
    }

    sealed interface State {
        object Loading : State
        object Empty : State
        data class Success(val data: List<Recipe>) : State
        data class Error(val message: String) : State
    }
}

