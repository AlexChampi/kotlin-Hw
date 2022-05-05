import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(private val threadAmount: Int) : Executor {
    private val taskQueue = LinkedBlockingQueue<Runnable>()
    private val threadsList: MutableList<WorkerThread> = ArrayList()

    init {
        if (threadAmount > THREADS_LIMIT) throw IllegalArgumentException("Max threads number $THREADS_LIMIT. Input amount $threadAmount")
        repeat(threadAmount) {
            val thread = WorkerThread()
            threadsList.add(thread)
            thread.start()
        }
    }

    override fun execute(task: Runnable) {
        taskQueue.add(task)
        try {
            val thread = threadsList.first { it.isWaiting == true }
            synchronized(thread) {
                (thread as Object).notify()
            }
        } catch (e: Exception) {
            println("All threads are busy")
        }
    }


    fun shutdown() {
        threadsList.forEach {
            it.interrupt()
        }
    }

    private inner class WorkerThread : Thread() {
        var isWaiting = true
            private set

        override fun run() {
            while (true) {
                synchronized(this) {
                    val task = taskQueue.poll()
                    if (task != null) {
                        task.run()
                        isWaiting = false
                    } else {
                        (this as Object).wait()
                        isWaiting = false
                    }
                }
            }
        }
    }
}

private const val THREADS_LIMIT = 10