package com.david_glez.seccion9_proyecto_stores.editModule.model

import com.david_glez.seccion9_proyecto_stores.StoreApplication
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreInteractor {

    fun saveStore(storeEntity: StoreEntity, callback: (Long) -> Unit){
        doAsync{
            val newId = StoreApplication.dataBase.storeDao().addStore(storeEntity!!)
            StoreApplication.dataBase.storeDao().deleteStore(storeEntity)
            uiThread {
                callback(newId)
            }
        }
    }

    fun updateStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit){ // retorna entity
        doAsync{
            StoreApplication.dataBase.storeDao().updateStore(storeEntity!!)
            uiThread {
                callback(storeEntity)
            }
        }
    }
}