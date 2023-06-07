package com.example.crudreceitas.di

import android.app.Application
import com.example.crudreceitas.repositories.RecipeRepository
import com.example.crudreceitas.repositories.impl.RecipeRepositoryImpl
import com.example.crudreceitas.ui.viewmodels.FormViewModel
import com.example.crudreceitas.ui.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }

    private val appModule = module {
        single<RecipeRepository> { RecipeRepositoryImpl() }

        viewModel { HomeViewModel(recipeRepository = get()) }
        viewModel { FormViewModel(recipeRepository = get()) }
    }

}