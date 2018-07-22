package com.elkcreek.rodneytressler.intermediatepokedex.di.components;


import com.elkcreek.rodneytressler.intermediatepokedex.di.PokedexApplication;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.ApplicationModule;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.NetworkModule;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.ViewModule;


import dagger.Component;

@Component(modules = {ApplicationModule.class, ViewModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(PokedexApplication pokedexApplication);
}
