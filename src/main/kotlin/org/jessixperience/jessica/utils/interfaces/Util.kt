package org.jessixperience.jessica.utils.interfaces

interface Util
{
    fun init()
    fun exec(): Boolean
    fun isActive(): Boolean
    fun setActive(state: Boolean )
    fun getTriggerPacket(): String
}