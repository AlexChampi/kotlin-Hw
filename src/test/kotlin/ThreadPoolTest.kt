import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.lang.Thread.sleep

class ThreadPoolTest : FeatureSpec() {

    private val task = mockk<Runnable>()

    override fun beforeEach(testCase: TestCase) {
        every { task.run() } answers { sleep(5) }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("success") {
            scenario("success") {
                val poolSize = 5
                val taskAmount = 13
                val threadPool = ThreadPool(poolSize)

                repeat(taskAmount) {
                    threadPool.execute(task)
                }

                verify(exactly = taskAmount) { task.run() }
            }

        }
        feature("fail") {
            scenario("thread amount is over") {
                shouldThrow<IllegalArgumentException> { ThreadPool(15) }
            }
        }
    }
}