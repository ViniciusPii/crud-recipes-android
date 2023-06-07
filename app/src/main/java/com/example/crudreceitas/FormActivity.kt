package com.example.crudreceitas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.crudreceitas.databinding.ActivityFormBinding
import com.example.crudreceitas.models.Recipe
import com.example.crudreceitas.ui.viewmodels.FormViewModel
import com.example.crudreceitas.ui.viewmodels.FormViewModel.Action
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class FormActivity : AppCompatActivity() {
    private val binding: ActivityFormBinding by lazy {
        ActivityFormBinding.inflate(layoutInflater)
    }

    private val viewModel: FormViewModel by viewModel()

    private val actionObserver = Observer<Action?> { action ->
        when (action) {
            is Action.Save -> {
                setResult(1)
                finish()
            }

            else -> Unit
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewModel.action.observe(this, actionObserver)
        setupListeners()
    }

    private fun setupListeners() {
        binding.formButton.setOnClickListener {

            val recipe = Recipe(
                id = UUID.randomUUID().toString(),
                type = "Salgado",
                name = "Teste do Vini",
                author = "Vini",
                ingredients = "",
            )

            viewModel.saveRecipe(recipe)
        }
    }
}