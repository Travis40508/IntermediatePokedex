package com.elkcreek.rodneytressler.intermediatepokedex.repository.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("{name}")
    Observable<Pokemon> getPokemon(@Path("name") String pokemonName);

    class Pokemon {

        @SerializedName("name")
        @Expose private String pokemonName;

        @SerializedName("weight")
        @Expose private int pokemonWeight;

        @SerializedName("height")
        @Expose private int pokemonHeight;

        @SerializedName("sprites")
        @Expose private Sprites pokemonSprites;

        public String getPokemonName() {
            return pokemonName;
        }

        public int getPokemonWeight() {
            return pokemonWeight;
        }

        public int getPokemonHeight() {
            return pokemonHeight;
        }
    }

    class Sprites {

        @SerializedName("front_default")
        @Expose private String pokemonFrontSprite;

        public String getPokemonFrontSprite() {
            return pokemonFrontSprite;
        }
    }
}
