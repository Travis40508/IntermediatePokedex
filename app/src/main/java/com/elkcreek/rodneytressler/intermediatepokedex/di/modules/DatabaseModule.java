package com.elkcreek.rodneytressler.intermediatepokedex.di.modules;


import android.arch.persistence.room.Room;
import android.content.Context;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabase;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseService;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static final String POKEMON_DATABASE = "pokemon_database";

    @Provides
    @Singleton
    PokemonDatabase providesPokemonDatabase(Context context) {
        PokemonDatabase database = Room.databaseBuilder(context, PokemonDatabase.class, POKEMON_DATABASE)
                .build();

        return database;
    }

    @Provides
    PokemonDatabaseService providesPokemonDatabaseService(PokemonDatabase pokemonDatabase) {
        return new PokemonDatabaseServiceImpl(pokemonDatabase);
    }
}
