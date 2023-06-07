package com.example.crudreceitas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudreceitas.adapters.RecipesAdapter
import com.example.crudreceitas.databinding.ActivityHomeBinding
import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.ui.viewmodels.HomeViewModel
import com.example.crudreceitas.ui.viewmodels.HomeViewModel.State
import com.example.crudreceitas.ui.viewmodels.HomeViewModel.Action
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var adapter: RecipesAdapter

    private val stateObserver = Observer<State> { state ->
        when (state) {
            is State.Loading -> showLoading()
            is State.Empty -> showEmpty()
            is State.Success -> showSuccess(state.data)
            is State.Error -> showError()
        }
    }
    private val actionObserver = Observer<Action?> { action ->
        when (action) {
            is Action.Delete -> viewModel.getAllRecipes()
            else -> Unit
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.recipes.observe(this, stateObserver)
        viewModel.action.observe(this, actionObserver)
        viewModel.getAllRecipes()

        setupListeners()
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
        adapter = RecipesAdapter(recipes = recipes, onShowClick = { id ->
            Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
        }, onRemoveClick = { recipe ->
            viewModel.removeRecipe(recipe)
        })

        binding.recycler.adapter = adapter
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)

            launcher.launch(intent)
        }

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == 1) {
                viewModel.getAllRecipes()
            }
        }
}