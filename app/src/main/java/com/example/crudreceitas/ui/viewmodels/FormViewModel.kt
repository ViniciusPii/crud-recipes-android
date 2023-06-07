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

class FormViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    val action = SingleEventLiveData<Action>()

    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                _state.value = State.Loading
                recipeRepository.saveRecipe(recipe)
                action.value = Action.Save
            } catch (e: Exception) {
                delay(100)
                _state.value = State.Error(e.message.toString())
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Error(val message: String) : State
    }

    sealed interface Action {
        object Save : Action
    }
}


