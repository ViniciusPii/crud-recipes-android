package com.example.crudreceitas.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudreceitas.infra.SingleEventLiveData
import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.repositories.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _recipes = MutableLiveData<State>()
    val recipes: LiveData<State> get() = _recipes

    val action = SingleEventLiveData<Action>()

    fun getAllRecipes() {
        viewModelScope.launch {
            _recipes.value = State.Loading

            try {
                val recipes: List<Recipe> = recipeRepository.getAllRecipes()

                delay(100)
                _recipes.value = if (recipes.isNotEmpty()) {
                    State.Success(recipes)
                } else {
                    State.Empty
                }
            } catch (e: Exception) {
                delay(100)
                _recipes.value = State.Error(e.message.toString())
            }
        }
    }

    fun removeRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                _recipes.value = State.Loading
                recipeRepository.removeRecipe(recipe)
                action.value = Action.Delete
            } catch (e: Exception) {
                delay(100)
                _recipes.value = State.Error(e.message.toString())
            }
        }
    }

    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                _recipes.value = State.Loading
                recipeRepository.saveRecipe(recipe)
            } catch (e: Exception) {
                delay(100)
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

    sealed interface Action {
        object Delete : Action
    }
}

