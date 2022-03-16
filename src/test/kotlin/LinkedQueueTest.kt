import org.junit.jupiter.api.Test
import ru.tinkof.fintech.kotlin.collections.LinkedQueue
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LinkedQueueTest {

    @Test
    fun queue() {
        val queue1 = LinkedQueue().create()

        val s = 5
        for (i in 1..s) {
            queue1.enqueue(i)
        }

        assertEquals(s, queue1.size())
        for (i in 1 .. s) {
            assertEquals(i, queue1.getHead())
            assertFalse(queue1.isEmpty())
            queue1.dequeue()
            assertEquals(s - i, queue1.size())
        }
        assertTrue(queue1.isEmpty())
    }
}