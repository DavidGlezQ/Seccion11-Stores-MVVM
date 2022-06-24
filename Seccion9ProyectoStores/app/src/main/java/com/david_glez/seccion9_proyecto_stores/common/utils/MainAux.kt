package com.david_glez.seccion9_proyecto_stores.common.utils

import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity

interface MainAux {
    fun hideFab(isVisible: Boolean = false)
    fun addStore(storeEntity: StoreEntity)
    fun updateStore(storeEntity: StoreEntity)
}