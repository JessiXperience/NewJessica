package org.jessixperience.jessica.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.item.*
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket
import net.minecraft.text.Text
import net.minecraft.util.Hand
import org.jessixperience.jessica.NewJessica
import org.jessixperience.jessica.utils.interfaces.Util

class AutoOffHand : Util
{
    private var lowHealth: Int = 7
    private var critHealth: Int = 4

    private fun getFromInventory(searchedItem: Item ): Int {
        var itemIndex: Int = -1;
        var player = MinecraftClient.getInstance().player!!

        for ( itemIndex in 0 until player.inventory.size() ) {
            val currentStack = player.inventory.getStack(itemIndex).item
            if ( currentStack != searchedItem ) continue
            return itemIndex
        }

        return itemIndex
    }

    fun onLowHP() {
        NewJessica.LOGGER.info( "Low HP" )

        val mc = MinecraftClient.getInstance()

        val activeItem: ItemStack = mc.player!!.mainHandStack
        val secondaryItem: ItemStack = mc.player!!.offHandStack

        if ( activeItem.item is SwordItem ) {
            if (secondaryItem.item == Items.GOLDEN_APPLE ||
                secondaryItem.item == Items.ENCHANTED_GOLDEN_APPLE ) return
            onSword( mc.player!! )
        }

        if ( secondaryItem.item is PickaxeItem ) {
            if ( secondaryItem.item == Items.CHORUS_FRUIT ) return
            onPickaxe( mc.player!! )
        }
    }

    private fun onSword( player: ClientPlayerEntity ) {
        NewJessica.LOGGER.info( "Sword detected" )

        var appleIndex = getFromInventory( Items.ENCHANTED_GOLDEN_APPLE )
        if ( appleIndex == -1 ) appleIndex = getFromInventory( Items.GOLDEN_APPLE )
        if ( appleIndex == -1 ) return
        println( "Golden apple found: $appleIndex" )

        val appleStack = player.inventory.removeStack( appleIndex )
        player.setStackInHand( Hand.OFF_HAND, appleStack )
    }

    private fun onPickaxe( player: ClientPlayerEntity ) {
        NewJessica.LOGGER.info( "Pickaxe detected" )

        val chorusIndex = getFromInventory( Items.POPPED_CHORUS_FRUIT )
        if ( chorusIndex == -1 ) return
        println( "Chorus found: $chorusIndex" )

        val chorusStack = player.inventory.removeStack( chorusIndex )
        player.setStackInHand( Hand.OFF_HAND, chorusStack )
    }

    fun onCriticalHP() {
        NewJessica.LOGGER.info( "Critical HP" )

        val player = MinecraftClient.getInstance().player!!
        val totemIndex = getFromInventory( Items.TOTEM_OF_UNDYING )
        if ( totemIndex == -1 ) return
        println( "Totem found $totemIndex" )

        val totemStack = player.inventory.removeStack( totemIndex )
        player.setStackInHand( Hand.OFF_HAND, totemStack )
    }

    override fun isActive(): Boolean {
        return true;
    }

    override fun exec() {
        if ( MinecraftClient.getInstance().player == null ) return
        NewJessica.LOGGER.info( "Damage taken" )
        val playerHealth: Float = MinecraftClient.getInstance().player!!.health

        if ( playerHealth > lowHealth ) return;

        if ( playerHealth > critHealth ) {
            this.onLowHP()
            return
        }

        this.onCriticalHP()
    }


}