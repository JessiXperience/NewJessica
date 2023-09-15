package org.jessixperience.jessica
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object NewJessica: ModInitializer
{
    const val MOD_ID = "NewJessica"
    const val RELEASE = "0.0.2"

    val LOGGER: Logger = LoggerFactory.getLogger( MOD_ID )

    fun getLogger(): Logger {
        return LOGGER;
    }

    override fun onInitialize() {

        println("$MOD_ID $RELEASE initialized. Let's fun")
    }
}