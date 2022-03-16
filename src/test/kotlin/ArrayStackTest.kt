import org.junit.jupiter.api.Test;
import ru.tinkof.fintech.kotlin.collections.*
import kotlin.test.*

class ArrayStackTest {
    @Test
    fun stackPush() {
        val stack1 = ArrayStack<Int>()
        val s = 5

        for (i in 1..s) {
            stack1.push(i)
        }

        assertEquals(s, stack1.size)
        assertFalse(stack1.isEmpty())
    }

    @Test
    fun stackPop() {
        val stack2 = ArrayStack<Int>()
        val s = 5

        for (i in 1..s) {
            stack2.push(i)
        }

        for (i in s downTo 1) {
            assertEquals(i, stack2.pop())
        }
        assertTrue(stack2.isEmpty())
    }

}
