package com.david_glez.seccion9_proyecto_stores.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoreEntity") // Esto es para indicar que es una entidad y el nombre de la tabla
data class StoreEntity(@PrimaryKey(autoGenerate = true)
                       var id: Long = 0,
                       var name: String,
                       var phone: String,
                       var webSite: String = "",
                       var photoUrl: String,
                       var isFavorite: Boolean = false){

    constructor(): this(name = "", phone = "", photoUrl = "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StoreEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
