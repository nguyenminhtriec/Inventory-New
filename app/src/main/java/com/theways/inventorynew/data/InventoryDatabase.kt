package com.theways.inventorynew.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun getItemDao(): ItemDao

    companion object {

        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: kotlin.synchronized(lock = this) {
                Room.databaseBuilder(
                    context = context,
                    klass = InventoryDatabase::class.java,
                    name = "inventory_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}