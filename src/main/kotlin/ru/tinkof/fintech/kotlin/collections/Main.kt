import ru.tinkof.fintech.kotlin.collections.ArrayStackADT
import ru.tinkof.fintech.kotlin.collections.LinkedQueue

fun dumpStack(stackADT: ArrayStackADT) {
    while (!stackADT.isEmpty()) {
        println("${stackADT.size()}  ${stackADT.pop()}")
    }
}

fun dumpQueue(l: LinkedQueue) {
    while (!l.isEmpty()) {
        println("${l.size()} ${l.getHead()}")
        l.dequeue()
    }
}

fun main() {
    val a = ArrayStackADT().create()
    val b = ArrayStackADT().create()
    for (i in 1..5) {
        a.push("s_1_" + i)
        b.push("s_2_" + i)

    }
    dumpStack(a)
    dumpStack(b)

    val l1 = LinkedQueue().create()
    val l2 = LinkedQueue().create()
    for (i in 1..5) {
        l1.enqueue("q_1_" + i)
        l2.enqueue("q_2_" + i)

    }
    dumpQueue(l1)
    dumpQueue(l2)


}


