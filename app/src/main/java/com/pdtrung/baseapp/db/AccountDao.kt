package com.pdtrung.baseapp.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(account: AccountEntity): Long

    @Delete
    fun delete(account: AccountEntity)

    @Query("SELECT * FROM AccountEntity ORDER BY id ASC")
    fun loadAll(): List<AccountEntity>

}