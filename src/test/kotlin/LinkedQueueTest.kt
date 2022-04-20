import org.junit.jupiter.api.Test
import ru.tinkoff.fintech.kotlin.collections.LinkedQueue
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LinkedQueueTest {

    @Test
    fun queueEnqueue() {
        val queue = LinkedQueue<Int>()
        val s = 5

        for (i in 1..s) {
            queue.offer(i)
        }

        assertEquals(s, queue.size)
        assertFalse(queue.isEmpty())
    }

    @Test
    fun queueDequeue() {
        val queue = LinkedQueue<Int>()
        val s = 5

        for (i in 1..s) {
            queue.offer(i)
        }

        for (i in 1..s) {
            assertEquals(i, queue.peek())
            queue.remove()
        }
        assertEquals(0, queue.size)
        assertTrue(queue.isEmpty())
    }
}