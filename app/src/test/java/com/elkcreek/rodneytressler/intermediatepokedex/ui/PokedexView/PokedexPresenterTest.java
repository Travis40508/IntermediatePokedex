package com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView;

import com.elkcreek.rodneytressler.intermediatepokedex.repository.database.PokemonDatabaseService;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonApi;
import com.elkcreek.rodneytressler.intermediatepokedex.repository.network.PokemonService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import io.reactivex.Observable;
import io.reactivex.Single;
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
        when(pokemonService.getPokemon(pokemonName)).thenReturn(Observable.just(pokemon));
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
        // throw error when return from pokemon database service for this test only
        doReturn(Single.error(NoSuchElementException::new)).when(pokemonDatabaseService).findPokemonByName(eq(pokemonName));
        presenter.findPokemon(pokemonName);

        //API Call made
        verify(pokemonService).getPokemon(eq(pokemonName));

        //Pokemon retrieved and inserted into database
        verify(pokemonDatabaseService).insertPokemon(eq(pokemon));

        //Pokemon info displayed on view
        verify(pokedexView).showPokemonSprite(eq(pokemon.getSpriteUrl()));
        verify(pokedexView).hideProgressBar();
        verify(pokedexView).showPokemonWeight("Weight - " + pokemon.getPokemonWeight());
        verify(pokedexView).showPokemonHeight("Height - " + pokemon.getPokemonHeight());
        verify(pokedexView).showPokemonName(pokemon.getPokemonName());
    }

}