package org.jessixperience.jessica.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.item.*
import org.jessixperience.jessica.utils.interfaces.Util

class AutoOffHand : Util {
    private var lowHealth: Int = 4
    private var minHealth: Int = 7

    private var swords: Array<Item> = arrayOf(
        Items.WOODEN_SWORD,
        Items.STONE_SWORD,
        Items.GOLDEN_SWORD,
        Items.IRON_SWORD,
        Items.DIAMOND_SWORD,
        Items.NETHERITE_SWORD
    )
    private var pickaxes: Array<Item> = arrayOf(
        Items.WOODEN_PICKAXE,
        Items.STONE_PICKAXE,
        Items.GOLDEN_PICKAXE,
        Items.IRON_PICKAXE,
        Items.DIAMOND_PICKAXE,
        Items.NETHERITE_PICKAXE
    )

    fun onLowHP() {
        val mc = MinecraftClient.getInstance()
        val activeItem: ItemStack = mc.player!!.activeItem

        println( activeItem.name.content.toString() )
        println( activeItem.name.toString() )
        println( activeItem.name.asOrderedText().toString() )

        if ( swords.contains( activeItem.item ) ) {
            println( "Sword" );
            if ( mc.player!!.inventory.contains( ItemStack( Items.GOLDEN_APPLE ) ) ) return

        }

        if ( pickaxes.contains( activeItem.item ) ) {
            println( "Pickaxe" );
            if ( mc.player!!.inventory.contains( ItemStack( Items.CHORUS_FRUIT ) ) ) return

        }

    }

    fun onMinHP() {
        println( "Test c" );
    }

    override fun isActive(): Boolean {
        return true;
    }

    override fun exec() {
        if ( MinecraftClient.getInstance().player == null ) return
        val playerHealth: Float = MinecraftClient.getInstance().player!!.health

        if ( playerHealth > lowHealth ) return;

        if ( playerHealth < minHealth ) {
            this.onMinHP()
            return
        }

        this.onLowHP()
    }


}