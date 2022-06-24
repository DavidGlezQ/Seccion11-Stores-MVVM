package com.david_glez.seccion9_proyecto_stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.david_glez.seccion9_proyecto_stores.StoreApplication
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainViewModel: ViewModel() { // Aqui se alamcenan todas las tiendas de nuestro modelo
    private var stores: MutableLiveData<List<StoreEntity>>

    init {
        stores = MutableLiveData()
        loadStores()
    }

    fun getStores(): LiveData<List<StoreEntity>>{
        return stores
    }

    private fun loadStores(){
        doAsync {
            val storesList = StoreApplication.dataBase.storeDao().getAllStores()
            uiThread {
                stores.value = storesList
            }
        }
    }
}