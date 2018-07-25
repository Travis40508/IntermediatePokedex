package com.elkcreek.rodneytressler.intermediatepokedex.ui.MainActivityView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    private MainPresenter presenter;
    @Mock MainView view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = spy(new MainPresenter());
        presenter.setView(view);
        presenter.subscribe();
    }

    @Test
    public void verifyPokedexFragmentSet() {
        verify(view).setPokedexFragment();
    }

}