package me.codedlife.newjessica;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NewJessica implements ModInitializer {

    private final static String ID = "NewJessica";
    private final static Logger LOGGER = LoggerFactory.getLogger( ID );

    @Override
    public void onInitialize() {
        LOGGER.info( "NewJessica mod initialized" );
    }
}
