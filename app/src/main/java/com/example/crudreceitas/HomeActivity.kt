package com.example.crudreceitas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudreceitas.adapters.RecipesAdapter
import com.example.crudreceitas.databinding.ActivityHomeBinding
import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.ui.viewmodels.HomeViewModel
import com.example.crudreceitas.ui.viewmodels.HomeViewModel.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModel()

    private val stateObserver = Observer<State> { state ->
        when (state) {
            is State.Loading -> showLoading()
            is State.Empty -> showEmpty()
            is State.Success -> showSuccess(state.data)
            is State.Error -> showError()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.recipes.observe(this, stateObserver)
        viewModel.getAllRecipes()
    }

    private fun showLoading() {
        binding.loading.root.apply { isVisible = true }
        binding.empty.root.apply { isVisible = false }
        binding.error.root.apply { isVisible = false }
        binding.recycler.apply { isVisible = false }
    }

    private fun showEmpty() {
        binding.loading.root.apply { isVisible = false }
        binding.empty.root.apply { isVisible = true }
        binding.error.root.apply { isVisible = false }
        binding.recycler.apply { isVisible = false }
    }

    private fun showSuccess(recipes: List<Recipe>) {
        binding.loading.root.apply { isVisible = false }
        binding.empty.root.apply { isVisible = false }
        binding.error.root.apply { isVisible = false }
        binding.recycler.apply { isVisible = true }

        initRecyclerView(recipes)
    }

    private fun showError() {
        binding.loading.root.apply { isVisible = false }
        binding.empty.root.apply { isVisible = false }
        binding.error.root.apply { isVisible = true }
        binding.recycler.apply { isVisible = false }
        binding.error.errorButton.apply { setOnClickListener { viewModel.getAllRecipes() } }
    }

    private fun initRecyclerView(recipes: List<Recipe>) {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = RecipesAdapter(
            recipes = recipes,
            onShowClick = { id ->
                Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
            },
            onRemoveClick = { recipe ->
                viewModel.removeRecipe(recipe)
            }
        )
    }
}