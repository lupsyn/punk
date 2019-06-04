package app.punk.inject

import android.os.Debug
import androidx.room.Room
import app.punk.data.DatabaseTransactionRunner
import app.punk.data.PunkDatabase
import app.punk.data.RoomTransactionRunner


object DatabaseModule {
    private val db: PunkDatabase by lazy { initDatabase() }

    private fun initDatabase(): PunkDatabase {
        val builder = Room.databaseBuilder(AppModule.provideApplicationContext(), PunkDatabase::class.java, "shows.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    fun provideBeerDao() = db.beerDao()

    fun providePaginatedBeerDao() = db.paginatedBeerDao()

    fun provideDatabaseTransactionRunner(): DatabaseTransactionRunner = RoomTransactionRunner(db)
}
