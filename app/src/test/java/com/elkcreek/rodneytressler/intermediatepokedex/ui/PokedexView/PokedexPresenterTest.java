package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseService;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class PokedexPresenterTest {

    private PokedexPresenter presenter;
    @Mock
    private PokemonService pokemonService;

    @Mock
    private PokemonDatabaseService pokemonDatabaseService;

    @Mock
    private PokedexView pokedexView;

    private PokemonApi.Pokemon pokemon;
    private String pokemonName;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = spy(new PokedexPresenter(pokemonService, pokemonDatabaseService));
        presenter.setView(pokedexView);
        presenter.subscribe();

        pokemonName = "Bulbasaur";
        pokemon = new PokemonApi.Pokemon();
        pokemon.setPokemonSprites(new PokemonApi.Sprites());
        pokemon.setSpriteUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");

        doReturn(Single.just(pokemon)).when(pokemonDatabaseService).findPokemonByName(eq(pokemonName));
        doReturn(Observable.just(pokemon)).when(pokemonService).getPokemon(eq(pokemonName));
    }

    @After
    public void tearDown() throws Exception{
        presenter.unsubscribe();
    }

    @Test
    public void verifyOnButtonTurnsPokedexScreenOn() {
        presenter.onButtonClicked();
        verify(pokedexView).turnOnPokedexScreen();
    }

    @Test
    public void verifyOnButtonTurnsOnStatsScreen() {
        presenter.onButtonClicked();
        verify(pokedexView).turnOnStatsScreen();
    }

    @Test
    public void verifyOnButtonTurnsOnSearchScreen() {
        presenter.onButtonClicked();
        verify(pokedexView).turnOnPokemonSearchScreen();
    }

    @Test
    public void verifySearchButtonShowsProgressBar() {
        presenter.searchClicked(pokemonName);
        verify(pokedexView).showProgressBar();
    }

    @Test
    public void verifySearchButtonClearsEditTest() {
        presenter.searchClicked(pokemonName);
        verify(pokedexView).clearPokemonInput();
    }

    @Test
    public void verifySpriteShownWhenDataseContainsPokemon() {
        presenter.findPokemon(pokemonName);
        verify(pokedexView).showPokemonSprite(eq(pokemon.getSpriteUrl()));
    }

    @Test
    public void verifyHideProgressBarOnSuccessfulDatabasePokemonReturn() {
        presenter.findPokemon(pokemonName);
        verify(pokedexView).hideProgressBar();
    }

    @Test
    public void verifyShowPokemonNameOnSuccessfulPokemonDatabaseReturn() {
        presenter.findPokemon(pokemonName);
        verify(pokedexView).showPokemonName(eq(pokemon.getPokemonName()));
    }

    @Test
    public void verifyShowWeightOnSuccessfulPokemonDatabaseReturn() {
        presenter.findPokemon(pokemonName);
        verify(pokedexView).showPokemonWeight(eq("Weight - " + pokemon.getPokemonWeight()));
    }

    @Test
    public void verifyShowHeightOnSuccessfulPokemonDatabaseReturn() {
        presenter.findPokemon(pokemonName);
        verify(pokedexView).showPokemonHeight(eq("Height - " + pokemon.getPokemonWeight()));
    }

    @Test
    public void verifySpriteShownWhenNetworkCallSuccessful() {
        presenter.findPokemon(pokemonName);
        verify(pokedexView).showPokemonSprite(eq(pokemon.getSpriteUrl()));
    }

}