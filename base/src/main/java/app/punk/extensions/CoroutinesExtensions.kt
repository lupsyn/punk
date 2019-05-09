package app.punk.extensions

import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun <A, B> Collection<A>.parallelForEach(
    parallelism: Int = 5,
    block: suspend (A) -> B
): Unit = coroutineScope {
    val producer = produce {
        forEach { send(it) }
        close()
    }
    repeat(parallelism) {
        launch {
            for (item in producer) {
                block(item)
            }
        }
    }
}