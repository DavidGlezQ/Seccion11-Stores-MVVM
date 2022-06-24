package com.david_glez.seccion9_proyecto_stores.mainModule.adapters

import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity

interface OnClickListener {
    fun onClick(storeId: Long)
    fun onFavoriteStore(storeEntity: StoreEntity)
    fun onDeleteStore(storeEntity: StoreEntity)
}