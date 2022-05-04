import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(private val threadAmount: Int) : Executor {
    private val taskQueue = LinkedBlockingQueue<Runnable>()
    private val threadsList: MutableList<WorkerThread> = ArrayList()
    private var threadsStatus = true

    init {
        if (threadAmount > THREADS_LIMIT) throw IllegalArgumentException()
        repeat(threadAmount) {
            val thread = WorkerThread()
            threadsList.add(thread)
            thread.start()
        }
    }

    override fun execute(task: Runnable) {
        taskQueue.add(task)
        (threadsList.first() as Object).notify()
    }

    fun shutdown() {
        threadsStatus = false
        threadsList.forEach {
            it.interrupt()
        }
    }

    private inner class WorkerThread : Thread() {
        override fun run() {
            while (threadsStatus == true) {
                synchronized(this) {
                    if (!taskQueue.isEmpty()) {
                        val task = taskQueue.poll()
                        task.run()
                    } else {
                        (this as Object).wait()
                    }
                }

            }
        }
    }
}

private const val THREADS_LIMIT = 10