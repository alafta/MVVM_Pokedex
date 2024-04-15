package com.example.newpokedexapp.di

import android.app.Application
import android.content.Context
import com.example.newpokedexapp.data.remote.PokeApi
import com.example.newpokedexapp.repository.PokemonRepository
import com.example.newpokedexapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: com.example.newpokedexapp.data.remote.PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): com.example.newpokedexapp.data.remote.PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(com.example.newpokedexapp.data.remote.PokeApi::class.java)
    }
}