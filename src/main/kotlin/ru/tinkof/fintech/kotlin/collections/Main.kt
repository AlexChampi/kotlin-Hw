import ru.tinkof.fintech.kotlin.collections.ArrayStack
import ru.tinkof.fintech.kotlin.collections.LinkedQueue

fun dumpStack(stackADT: ArrayStack<String>) {
    while (!stackADT.isEmpty()) {
        println("${stackADT.size}  ${stackADT.pop()}")
    }
}

fun dumpQueue(l: LinkedQueue<String>) {
    while (!l.isEmpty()) {
        println("${l.size} ${l.getTop()}")
        l.dequeue()
    }
}

fun main() {
    val a = ArrayStack<String>()
    val b = ArrayStack<String>()
    for (i in 1..5) {
        a.push("s_1_" + i)
        b.push("s_2_" + i)

    }
    dumpStack(a)
    dumpStack(b)

    val l1 = LinkedQueue<String>()
    val l2 = LinkedQueue<String>()
    for (i in 1..5) {
        l1.enqueue("q_1_" + i)
        l2.enqueue("q_2_" + i)

    }
    dumpQueue(l1)
    dumpQueue(l2)


}


