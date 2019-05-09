package app.punk.data.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import app.punk.data.entities.BeerEntity

interface BeerEntityDao<in E : BeerEntity> {
    @Insert
    fun insert(entity: E): Long

    @Insert
    fun insertAll(vararg entity: E)

    @Insert
    fun insertAll(entities: List<E>)

    @Update
    fun update(entity: E)

    @Delete
    fun delete(entity: E): Int
}