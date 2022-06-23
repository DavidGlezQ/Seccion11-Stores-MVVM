package com.david_glez.seccion9_proyecto_stores

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.david_glez.seccion9_proyecto_stores.Database.StoreDataBase

class StoreApplication : Application() {

    //Singleton
    companion object{
        lateinit var dataBase: StoreDataBase
    }

    override fun onCreate() {
        super.onCreate()

        val MIGRATION_1_2 = object: Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE StoreEntity ADD COLUMN photoUrl TEXT NOT NULL DEFAULT ''")
            }

        }

        dataBase = Room.databaseBuilder(this,
            StoreDataBase::class.java, "StoreDataBase")
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}