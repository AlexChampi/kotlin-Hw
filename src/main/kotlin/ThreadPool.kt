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
        val thread = threadsList.first { it.isWaiting == true }
        if (thread != null) {
            synchronized(thread) {
                (thread as Object).notify()
//            thread.isBusy = true
            }
        }

    }


    fun shutdown() {
        threadsList.forEach {
            it.interrupt()
        }
    }

    private inner class WorkerThread : Thread() {
        var isWaiting = true
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