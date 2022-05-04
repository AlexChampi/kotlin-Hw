import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(private val threadAmount: Int) : Executor {
    private val taskQueue = LinkedBlockingQueue<Runnable>()
    private val threadsList: MutableList<WorkerThread> = ArrayList()

    init {
        if (threadAmount > THREADS_LIMIT) throw IllegalArgumentException("QWWWWWWWWWWWWWWWWWWWW")
        repeat(threadAmount) {
            val thread = WorkerThread()
            threadsList.add(thread)
            thread.start()
        }
    }

    override fun execute(task: Runnable) {
        taskQueue.add(task)
        threadsList.forEach {
            synchronized(it) {
                if (it.isRunnig == true) {
                    (it as Object).notify()
                }
            }
        }
    }

    fun shutdown() {
        threadsList.forEach {
            it.interrupt()
        }
    }

    private inner class WorkerThread : Thread() {
        var isRunnig = true
        override fun run() {
            while (isRunnig) {
                synchronized(this) {
                    if (!taskQueue.isEmpty()) {
                        val task = taskQueue.poll()
                        task.run()
                    } else {
                        try {
                            (this as Object).wait()
                        } catch (e: Exception) {
                            isRunnig = false
                        }
                    }
                }

            }
        }
    }
}

private const val THREADS_LIMIT = 10