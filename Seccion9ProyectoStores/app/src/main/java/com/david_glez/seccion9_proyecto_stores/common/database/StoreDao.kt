package com.david_glez.seccion9_proyecto_stores.common.database

import androidx.room.*
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity

@Dao
interface StoreDao {
    @Query("SELECT * FROM StoreEntity")
    fun getAllStores(): MutableList<StoreEntity>

    @Query("SELECT * FROM StoreEntity where id = :id")
    fun getStoreById(id: Long): StoreEntity

    @Insert
    fun addStore(storeEntity: StoreEntity): Long

    @Update
    fun updateStore(storeEntity: StoreEntity)

    @Delete
    fun deleteStore(storeEntity: StoreEntity)
}