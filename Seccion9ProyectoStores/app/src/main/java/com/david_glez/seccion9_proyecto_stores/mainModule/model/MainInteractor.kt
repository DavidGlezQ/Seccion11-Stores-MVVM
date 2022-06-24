package com.david_glez.seccion9_proyecto_stores.mainModule.model

import com.david_glez.seccion9_proyecto_stores.StoreApplication
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteractor { //Model

    /* esta clase tiene como objetivo abstraer la consulta de datos para despues devolverlos a quien
    * lo solicite, Model */

    // funcion orden superios
    fun getStores(callback: (MutableList<StoreEntity>) -> Unit){
        doAsync {
            val storesList = StoreApplication.dataBase.storeDao().getAllStores()
            uiThread {
                callback(storesList)
            }
        }
    }

    fun deleteStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit){
        doAsync{
            StoreApplication.dataBase.storeDao().deleteStore(storeEntity)
            uiThread {
                callback(storeEntity)
            }
        }
    }

    fun updateStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit){
        doAsync {
            StoreApplication.dataBase.storeDao().updateStore(storeEntity)
            uiThread {
                callback(storeEntity)
            }
        }
    }
}