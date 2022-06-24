package com.david_glez.seccion9_proyecto_stores.editModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import com.david_glez.seccion9_proyecto_stores.editModule.model.EditStoreInteractor

class EditStoreViewModel: ViewModel() {

    private val storeSelected = MutableLiveData<StoreEntity>()
    private val showFav = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()

    private val interactor: EditStoreInteractor

    init {
        interactor = EditStoreInteractor()
    }

    fun setStoreSelected(storeEntity: StoreEntity){
        storeSelected.value = storeEntity
    }

    fun getStoreSelected(): LiveData<StoreEntity>{
        return storeSelected
    }

    fun setShowFab(isVisible: Boolean){
        showFav.value = isVisible
    }

    fun getShowFab(): LiveData<Boolean>{
        return showFav
    }

    fun setResult(value: Any){
        result.value = value
    }

    fun getResult(): LiveData<Any>{
        return result
    }

    fun saveStore(storeEntity: StoreEntity){
        interactor.saveStore(storeEntity){ newId ->
            result.value = newId
        }
    }

    fun updateStore(storeEntity: StoreEntity){
        interactor.updateStore(storeEntity){ storeUpdated ->
            result.value = storeUpdated
        }
    }
}