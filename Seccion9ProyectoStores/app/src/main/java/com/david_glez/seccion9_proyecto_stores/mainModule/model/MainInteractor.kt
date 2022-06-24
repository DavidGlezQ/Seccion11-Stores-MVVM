package com.david_glez.seccion9_proyecto_stores.mainModule.model

import com.david_glez.seccion9_proyecto_stores.StoreApplication
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteractor {

    /*esta clase tiene como objetivo abstraer la consulta de datos para despues devolverlos a quien
    * lo solicite, Model*/

    interface StoresCallBack {
        fun getStoresCallBack(callback: MutableList<StoreEntity>)
    }

    fun getStoresCallback(stores: StoresCallBack){

    }

    // funcion orden superios
    fun getStores(callback: (MutableList<StoreEntity>) -> Unit){
        doAsync {
            val storesList = StoreApplication.dataBase.storeDao().getAllStores()
            uiThread {
                callback(storesList)
            }
        }
    }
}