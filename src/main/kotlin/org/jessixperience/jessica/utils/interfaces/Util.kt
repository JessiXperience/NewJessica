package org.jessixperience.jessica.utils.interfaces

interface Util {
    fun Init()
    fun Exec(): Boolean
    fun isActive(): Boolean
    fun SetActive( state: Boolean )
    fun GetTriggerPacket(): String
}