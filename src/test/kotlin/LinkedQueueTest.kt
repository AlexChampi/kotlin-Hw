import org.junit.jupiter.api.Test
import ru.tinkoff.fintech.kotlin.collections.LinkedQueue
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LinkedQueueTest {

    @Test
    fun queueEnqueue() {
        val queue1 = LinkedQueue<Int>()
        val s = 5

        for (i in 1..s) {
            queue1.offer(i)
        }

        assertEquals(s, queue1.size)
        assertFalse(queue1.isEmpty())
    }

    @Test
    fun queueDequeue() {
        val queue2 = LinkedQueue<Int>()
        val s = 5

        for (i in 1..s) {
            queue2.offer(i)
        }

        for (i in 1..s) {
            assertEquals(i, queue2.peek())
            queue2.remove()
        }
        assertEquals(0, queue2.size)
        assertTrue(queue2.isEmpty())
    }
}