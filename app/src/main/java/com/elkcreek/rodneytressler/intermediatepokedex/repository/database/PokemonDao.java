package com.elkcreek.rodneytressler.intermediatepokedex.repository.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PokemonDao {

    @Insert(onConflict = REPLACE)
    void insertPokemon(PokemonApi.Pokemon pokemon);

    @Query("SELECT * FROM pokemon WHERE pokemonName LIKE :pokemonName")
    Single<PokemonApi.Pokemon> findPokemonByName(String pokemonName);

    @Query("SELECT * FROM pokemon")
    List<PokemonApi.Pokemon> getAllSavedPokemon();
}
