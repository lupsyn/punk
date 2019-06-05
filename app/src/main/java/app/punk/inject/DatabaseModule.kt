package app.punk.inject

import android.os.Debug
import androidx.room.Room
import app.punk.data.DatabaseTransactionRunner
import app.punk.data.PunkDatabase
import app.punk.data.RoomTransactionRunner


object DatabaseModule {

    private fun initDatabase(): PunkDatabase {
        val builder = Room.databaseBuilder(AppModule.applicationContext, PunkDatabase::class.java, "shows.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    private val db: PunkDatabase by lazy { initDatabase() }

    val beerDao by lazy { db.beerDao() }

    val paginatedBeerDao by lazy { db.paginatedBeerDao() }

    val databaseTransactionRunner by lazy { RoomTransactionRunner(db) }
}
