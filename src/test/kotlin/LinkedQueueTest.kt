import org.junit.jupiter.api.Test
import ru.tinkof.fintech.kotlin.collections.LinkedQueue
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import io.mockk.verify
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify

class LinkedQueueTest {

    @Test
    fun queueEnqueue() {
        val queue1 = LinkedQueue<String>()
        val s = 5

        for (i in 1..s) {
            queue1.enqueue(i)
        }

        assertEquals(s, queue1.size)
        assertFalse(queue1.isEmpty())
    }

    @Test
    fun queueDequeue() {
        val queue2 = LinkedQueue<String>()
        val s = 5

        for (i in 1..s) {
            queue2.enqueue(i)
        }

        for (i in 1..s) {
            assertEquals(i, queue2.getTop())
            queue2.dequeue()
        }
        assertEquals(0, queue2.size)
        assertTrue(queue2.isEmpty())
    }
}