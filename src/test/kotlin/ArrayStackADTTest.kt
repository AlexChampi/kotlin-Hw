import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.assertThrows
import ru.tinkof.fintech.kotlin.collections.*
import kotlin.test.*

class ArrayStackADTTest {
    @Test
    fun stack() {
        val stack1 = ArrayStackADT().create()
        val s = 5
        for (i in 1..s) {
            stack1.push(i)
        }

        assertEquals(s, stack1.size())
        for (i in s downTo 1) {
            assertEquals(i, stack1.pop())
            if (i > 1) (assertFalse(stack1.isEmpty())) else
                (assertTrue(stack1.isEmpty()))
        }

    }

}
