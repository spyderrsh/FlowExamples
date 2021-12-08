package util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object ThreadUtil {
    private val poolId = AtomicInteger(0)
    fun getThreadFactoryWithName(name: String) : ThreadFactory{
        return object : ThreadFactory{
            private val mPoolId = poolId.incrementAndGet()
            override fun newThread(r: Runnable): Thread = Thread(r).apply {
                setName("$name-pool-$mPoolId")
            }
        }
    }
    fun createDispatcherWithName(name: String): CoroutineDispatcher = Executors.newSingleThreadExecutor(
        getThreadFactoryWithName(name)
    ).asCoroutineDispatcher()
}