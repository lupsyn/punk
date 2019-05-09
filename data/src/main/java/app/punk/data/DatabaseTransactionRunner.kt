package app.punk.data

interface DatabaseTransactionRunner {
    operator fun <T> invoke(run: () -> T): T
}