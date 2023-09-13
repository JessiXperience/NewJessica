package org.jessixperience.jessica
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient


object NewJessica: ModInitializer
{
    const val MOD_ID = "NewJessica"
    const val RELEASE = "0.0.2"

    override fun onInitialize() {

        println("$MOD_ID $RELEASE initialized. Let's fun")
    }
}