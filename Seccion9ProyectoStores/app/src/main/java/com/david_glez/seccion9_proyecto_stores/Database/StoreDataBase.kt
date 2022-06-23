package com.david_glez.seccion9_proyecto_stores.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.david_glez.seccion9_proyecto_stores.Daos.StoreDao
import com.david_glez.seccion9_proyecto_stores.Entities.StoreEntity

@Database(entities = arrayOf(StoreEntity::class), version = 2)
abstract class StoreDataBase : RoomDatabase(){
    abstract fun storeDao(): StoreDao
}