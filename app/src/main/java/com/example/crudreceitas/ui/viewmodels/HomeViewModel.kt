package com.example.crudreceitas.ui.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.repositories.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _recipes = MutableLiveData<State>()
    val recipes: LiveData<State> get() = _recipes

    fun getAllRecipes() {
        viewModelScope.launch {
            _recipes.value = State.Loading

            try {
                val recipes: List<Recipe> = recipeRepository.getAllRecipes()

                Handler().postDelayed({
                    if (recipes.isNotEmpty()) {
                        _recipes.value = State.Success(recipes)
                    } else {
                        _recipes.value = State.Empty
                    }
                }, 3000)
            } catch (e: Exception) {
                Handler().postDelayed({
                    _recipes.value = State.Error(e.message.toString())
                }, 3000)
            }
        }
    }
}

sealed interface State {
    object Loading : State
    object Empty : State
    data class Success(val data: List<Recipe>) : State
    data class Error(val message: String) : State
}