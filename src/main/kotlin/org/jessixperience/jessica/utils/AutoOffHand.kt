package org.jessixperience.jessica.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.item.*
import net.minecraft.util.Hand
import org.jessixperience.jessica.NewJessica
import org.jessixperience.jessica.utils.interfaces.Util

class AutoOffHand : Util
{
    private val TRIGGER: String = "net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket"
    private var lowHealth: Int = 10
    private var critHealth: Int = 5
    private var isActive: Boolean = true

    private fun getFromInventory( searchedItem: Item ): Int {
        var itemIndex: Int = -1;
        var player = MinecraftClient.getInstance().player!!

        for ( itemIndex in 0 until player.inventory.size() ) {
            val currentStack = player.inventory.getStack(itemIndex).item
            if ( currentStack != searchedItem ) continue
            return itemIndex
        }

        return itemIndex
    }

    private fun secondaryArmEquip( item: Item ): Boolean {
        val mc = MinecraftClient.getInstance();
        val player = mc.player!!

        val itemIndex = getFromInventory( item )
        if ( itemIndex == -1 ) return false

        val itemStack = player.inventory.removeStack( itemIndex )
        val armItemStack = player.inventory.removeStack( 40 )

        player.inventory.setStack( itemIndex, armItemStack )
        player.setStackInHand( Hand.OFF_HAND, itemStack )

        return true
    }

    fun onLowHP() {
        NewJessica.LOGGER.info( "Low HP" )

        val mc = MinecraftClient.getInstance()

        val activeItem: ItemStack = mc.player!!.mainHandStack
        val secondaryItem: ItemStack = mc.player!!.offHandStack

        if ( activeItem.item is SwordItem ) {
            if (secondaryItem.item == Items.GOLDEN_APPLE ||
                secondaryItem.item == Items.ENCHANTED_GOLDEN_APPLE ) return

            val success = secondaryArmEquip( Items.ENCHANTED_GOLDEN_APPLE )
            if ( !success ) secondaryArmEquip( Items.GOLDEN_APPLE )
        }

        if ( activeItem.item is PickaxeItem ) {
            if ( secondaryItem.item == Items.CHORUS_FRUIT ) return
            secondaryArmEquip( Items.CHORUS_FRUIT )
        }
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
        return isActive;
    }

    override fun GetTriggerPacket(): String {
        return TRIGGER
    }

    override fun Exec() {
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