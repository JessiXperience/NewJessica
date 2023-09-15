package org.jessixperience.jessica.utils.interfaces

interface Util {
    fun Exec()
    fun isActive(): Boolean
    fun SetActive( state: Boolean )
    fun GetTriggerPacket(): String
}