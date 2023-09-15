package org.jessixperience.jessica.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.util.Hand
import org.jessixperience.jessica.utils.interfaces.Util

open class JessicaUtil : Util
{
    protected var TRIGGER = ""
    protected lateinit var mc: MinecraftClient
    protected lateinit var player: ClientPlayerEntity

    private var isActive: Boolean = true
    private var initialized: Boolean = false

    override fun isActive(): Boolean {
        return isActive;
    }

    override fun setActive( state: Boolean ) {
        isActive = state
    }

    override fun getTriggerPacket(): String {
        return TRIGGER
    }

    private fun getFromInventory(searchedItem: Item): Int {
        for ( itemIndex in 0 until player.inventory.size() ) {
            val currentStack = player.inventory.getStack(itemIndex).item
            if ( currentStack != searchedItem ) continue
            return itemIndex
        }
        return -1
    }

    protected fun secondaryArmEquip( item: Item): Boolean {
        val itemIndex = getFromInventory( item )
        if ( itemIndex == -1 ) return false

        val itemStack = player.inventory.removeStack( itemIndex )
        val armItemStack = player.inventory.removeStack( PlayerInventory.OFF_HAND_SLOT )

        player.inventory.insertStack( itemIndex, armItemStack )
        player.setStackInHand( Hand.OFF_HAND, itemStack )

        return true
    }

    override fun init() {
        mc = MinecraftClient.getInstance()
        player = mc.player!!
        this.initialized = true
    }

    override fun exec(): Boolean {
        if ( !initialized ) return false;
        return isActive && mc.player != null
    }
}