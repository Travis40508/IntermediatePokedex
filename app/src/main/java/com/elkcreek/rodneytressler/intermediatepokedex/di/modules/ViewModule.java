package com.elkcreek.rodneytressler.intermediatepokedex.di.modules;


import com.elkcreek.rodneytressler.intermediatepokedex.ui.PokedexView.PokedexFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ViewModule {

    @ContributesAndroidInjector
    abstract PokedexFragment contributesPokedexFragmentInjector();
}
