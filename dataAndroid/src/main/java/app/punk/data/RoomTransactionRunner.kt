package app.punk.data

import java.util.concurrent.Callable

class RoomTransactionRunner(private val db: PunkDatabase) : DatabaseTransactionRunner {
    override operator fun <T> invoke(run: () -> T): T = db.runInTransaction(Callable<T> { run() })
}