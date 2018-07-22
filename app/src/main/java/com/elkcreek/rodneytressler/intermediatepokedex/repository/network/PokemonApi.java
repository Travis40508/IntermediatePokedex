package com.elkcreek.rodneytressler.intermediatepokedex.repository.network;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {

    @GET("{name}")
    Observable<Pokemon> getPokemon(@Path("name") String pokemonName);


    @Entity
    class Pokemon {

        @PrimaryKey(autoGenerate = true)
        int id;

        @SerializedName("name")
        @Expose private String pokemonName;

        @SerializedName("weight")
        @Expose private int pokemonWeight;

        @SerializedName("height")
        @Expose private int pokemonHeight;

        @Ignore
        @SerializedName("sprites")
        @Expose private Sprites pokemonSprites;

        private String spriteUrl;

        public String getPokemonName() {
            return pokemonName;
        }

        public int getPokemonWeight() {
            return pokemonWeight;
        }

        public int getPokemonHeight() {
            return pokemonHeight;
        }


        public Sprites getPokemonSprites() {
            return pokemonSprites;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPokemonName(String pokemonName) {
            this.pokemonName = pokemonName;
        }

        public void setPokemonWeight(int pokemonWeight) {
            this.pokemonWeight = pokemonWeight;
        }

        public void setPokemonHeight(int pokemonHeight) {
            this.pokemonHeight = pokemonHeight;
        }

        public void setPokemonSprites(Sprites pokemonSprites) {
            this.pokemonSprites = pokemonSprites;
        }

        public String getSpriteUrl() {
            return spriteUrl;
        }

        public void setSpriteUrl(String spriteUrl) {
            this.spriteUrl = spriteUrl;
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
