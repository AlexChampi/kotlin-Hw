import org.junit.jupiter.api.Test;
import ru.tinkoff.fintech.kotlin.collections.ArrayStack
import kotlin.test.*

class ArrayStackTest {
    @Test
    fun stackPush() {
        val stack = ArrayStack<Int>()
        val s = 5

        for (i in 1..s) {
            stack.push(i)
        }

        assertEquals(s, stack.size)
        assertFalse(stack.isEmpty())
    }

    @Test
    fun stackPop() {
        val stack = ArrayStack<Int>()
        val s = 5

        for (i in 1..s) {
            stack.push(i)
        }

        for (i in s downTo 1) {
            assertEquals(i, stack.pop())
        }
        assertTrue(stack.isEmpty())
    }

}
