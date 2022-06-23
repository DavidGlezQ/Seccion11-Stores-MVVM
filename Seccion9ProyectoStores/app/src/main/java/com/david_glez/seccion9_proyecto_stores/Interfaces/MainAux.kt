package com.david_glez.seccion9_proyecto_stores.Interfaces

import com.david_glez.seccion9_proyecto_stores.Entities.StoreEntity

interface MainAux {
    fun hideFab(isVisible: Boolean = false)
    fun addStore(storeEntity: StoreEntity)
    fun updateStore(storeEntity: StoreEntity)
}