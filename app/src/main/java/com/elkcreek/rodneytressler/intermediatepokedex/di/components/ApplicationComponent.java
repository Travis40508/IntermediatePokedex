package com.elkcreek.rodneytressler.intermediatepokedex.di.components;


import com.elkcreek.rodneytressler.intermediatepokedex.di.PokedexApplication;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.ApplicationModule;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.DatabaseModule;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.NetworkModule;
import com.elkcreek.rodneytressler.intermediatepokedex.di.modules.ViewModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ViewModule.class, NetworkModule.class, DatabaseModule.class})
public interface ApplicationComponent {
    void inject(PokedexApplication pokedexApplication);
}
