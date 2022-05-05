import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

enum class ThreadStatus {
    STOPPED, RUNNING, WAITING
}

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
            val thread = threadsList.first { it.status == ThreadStatus.WAITING }
            synchronized(thread) {
                (thread as Object).notify()
            }
        } catch (e: Exception) {
            println("All threads are busy")
        }
    }


    fun shutdown() {
        var isAllThreadsStopped = false
        while (!isAllThreadsStopped) {
            isAllThreadsStopped = true
            threadsList.forEach {
                if (it.status == ThreadStatus.RUNNING) {
                    isAllThreadsStopped = false
                } else if (it.status == ThreadStatus.WAITING) {
                    it.m_interrupt()
                }
            }
        }
    }

    private inner class WorkerThread : Thread() {
        var status = ThreadStatus.WAITING
            private set

        fun m_interrupt() {
            this.interrupt()
            this.status = ThreadStatus.STOPPED
        }

        override fun run() {
            while (true) {
                synchronized(this) {
                    val task = taskQueue.poll()
                    if (task != null) {
                        task.run()
                        status = ThreadStatus.RUNNING
                    } else {
                        (this as Object).wait()
                        status = ThreadStatus.WAITING
                    }
                }
            }
        }
    }
}

private const val THREADS_LIMIT = 10