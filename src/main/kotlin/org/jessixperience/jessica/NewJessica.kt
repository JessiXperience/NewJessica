package org.jessixperience.jessica
import net.fabricmc.api.ModInitializer
import org.jessixperience.jessica.utils.main.AutoOffHand
import org.jessixperience.jessica.utils.interfaces.Util
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object NewJessica: ModInitializer
{
    const val MOD_ID = "NewJessica"
    const val RELEASE = "0.0.2"
    const val DEBUG_MODE = false

    val LOGGER: Logger = LoggerFactory.getLogger( MOD_ID )
    val utils: List<Util> = listOf(
        AutoOffHand()
    )

    fun getLogger(): Logger {
        return LOGGER;
    }

    override fun onInitialize() {
        println("$MOD_ID $RELEASE initialized. Let's fun")
    }
}