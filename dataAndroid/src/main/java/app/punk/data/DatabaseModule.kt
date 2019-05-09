package app.punk.data

import android.content.Context
import android.os.Debug
import androidx.room.Room


class DatabaseModule {

    fun provideDatabase(context: Context): PunkDatabase {
        val builder = Room.databaseBuilder(context, PunkDatabase::class.java, "shows.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }


    fun provideBeerDao(db: PunkDatabase) = db.beerDao()


    fun providePaginatedBeerDao(db: PunkDatabase) = db.paginatedBeerDao()


    fun provideDatabaseTransactionRunner(db: PunkDatabase): DatabaseTransactionRunner = RoomTransactionRunner(db)
}
