package com.elkcreek.rodneytressler.intermediatepokedex.repository.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;

@Database(version = 1, entities = PokemonApi.Pokemon.class, exportSchema = false)
public abstract class PokemonDatabase extends RoomDatabase {

    abstract public PokemonDao pokemonDao();
}
