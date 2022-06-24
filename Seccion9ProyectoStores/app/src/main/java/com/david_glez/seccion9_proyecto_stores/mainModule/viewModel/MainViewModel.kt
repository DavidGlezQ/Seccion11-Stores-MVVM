package com.david_glez.seccion9_proyecto_stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.david_glez.seccion9_proyecto_stores.StoreApplication
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import com.david_glez.seccion9_proyecto_stores.mainModule.model.MainInteractor
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainViewModel: ViewModel() { // Aqui se alamcenan todas las tiendas de nuestro modelo

    // esta clase se comunica con la vista y por getStores puede devolver el resultado
    /*tambien tiene acceso al modelo, en este caso el interactor (MainInteractor) ViewModel*/
    private var interactor: MainInteractor
    private var storeList: MutableList<StoreEntity>

    init {
        storeList = mutableListOf()
        interactor = MainInteractor()
    }

    private val stores: MutableLiveData<List<StoreEntity>> by lazy{
        MutableLiveData<List<StoreEntity>>().also {
            loadStores()
        }
    }

    fun getStores(): LiveData<List<StoreEntity>>{
        return stores
    }

    private fun loadStores(){
        // llamada funcion de orden superior
        interactor.getStores {
            stores.value = it
            storeList = it
        }
    }

    fun deleteStore(storeEntity: StoreEntity){
        interactor.deleteStore(storeEntity) {
            val index = storeList.indexOf(storeEntity)
            if (index != -1){
                storeList.removeAt(index)
                stores.value = storeList
            }
        }
    }

    fun updateStore(storeEntity: StoreEntity){
        storeEntity.isFavorite = !storeEntity.isFavorite
        interactor.updateStore(storeEntity) {
            val index = storeList.indexOf(storeEntity)
            if (index != -1){
                storeList.set(index, storeEntity)
                stores.value = storeList
            }
        }
    }
}