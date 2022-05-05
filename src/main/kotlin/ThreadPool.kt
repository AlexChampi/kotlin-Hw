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

        synchronized(this)
        {
            val thread = threadsList.first()
            (thread as Object).notify()
        }
    }


    fun shutdown() {
        threadsList.forEach {
            it.interrupt()
        }
    }

    private inner class WorkerThread : Thread() {
        override fun run() {
            while (true) {
                synchronized(this) {
                    val task = taskQueue.poll()
                    if (task != null) {
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