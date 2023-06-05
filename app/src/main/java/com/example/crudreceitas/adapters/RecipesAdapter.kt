package com.example.crudreceitas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudreceitas.databinding.ViewRecipeItemBinding
import com.example.crudreceitas.models.Recipe

class RecipesAdapter(
    private val recipes: List<Recipe>,
    private val onShowClick: (id: String) -> Unit,
    private val onRemoveClick: (recipe: Recipe) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewRecipeItemBinding = ViewRecipeItemBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe: Recipe = recipes[position]
        holder.bind(recipe, onShowClick, onRemoveClick)
    }


    class RecipeViewHolder(
        private val binding: ViewRecipeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            recipe: Recipe,
            onShowClick: (id: String) -> Unit,
            onRemoveClick: (recipe: Recipe) -> Unit
        ) {
            binding.apply {
                itemTitle.text = recipe.name
                itemType.text = "Tipo: ${recipe.type}"
                itemAuthor.text = "Author: ${recipe.author}"
                binding.root.setOnClickListener { onShowClick(recipe.id.toString()) }
                binding.itemDelete.setOnClickListener { onRemoveClick(recipe) }
            }
        }
    }
}