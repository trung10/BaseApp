package com.pdtrung.baseapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [AccountEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE TootEntity2 (uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, text TEXT, urls TEXT, contentWarning TEXT);")
                database.execSQL("INSERT INTO TootEntity2 SELECT * FROM TootEntity;")
                database.execSQL("DROP TABLE TootEntity;")
                database.execSQL("ALTER TABLE TootEntity2 RENAME TO TootEntity;")
            }
        }
    }
}