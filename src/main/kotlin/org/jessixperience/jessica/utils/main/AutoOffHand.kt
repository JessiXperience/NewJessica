package org.jessixperience.jessica.utils.main

import net.minecraft.item.*
import org.jessixperience.jessica.NewJessica
import org.jessixperience.jessica.utils.JessicaUtil

class AutoOffHand : JessicaUtil()
{
    private var lowHealth: Int = 10
    private var crtHealth: Int = 5

    init {
        TRIGGER = "net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket"
    }

    private fun onLowHP() {
        val activeItem: ItemStack = player.mainHandStack
        val secondaryItem: ItemStack = player.offHandStack

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

    private fun onCriticalHP() {
        val secondaryItem: ItemStack = player.offHandStack
        if ( secondaryItem.item == Items.TOTEM_OF_UNDYING ) return
        secondaryArmEquip( Items.TOTEM_OF_UNDYING )
    }

    override fun exec(): Boolean {
        if ( !super.exec() ) return false
        if ( NewJessica.DEBUG_MODE ) NewJessica.LOGGER.info( "AutoHand executed" )

        if ( player.health > lowHealth ) return false
        if ( player.health > crtHealth ) {
            this.onLowHP()
            return true
        }
        this.onCriticalHP()
        return true
    }

}