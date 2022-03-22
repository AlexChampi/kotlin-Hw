import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.tinkoff.fintech.hw5.*

class AutomobileServiceTests {
    val auto1 = Automobile("Форд", "Raptor", "SUV", 5_000_000, 15, 2010)
    val auto2 = Automobile("Бентли", "Continental", "Coupe", 20_000_000, 7, 2016)
    val auto3 = Automobile("БМВ", "M5", "Coupe", 7_000_000, 9, 2019)
    val auto4 = Automobile("Лада", "XRay", "CUV", 1_200_000, 10, 2020)
    val auto5 = Automobile("Мини", "Countryman", "Hatchback", 3_000_000, 6, 2018)


    @Test
    fun internationFormat() {
        val list = listOf(auto1, auto2, auto3, auto4, auto5)

        val result = AutomobileService().internationalFormatSort(list)

        val expectedResult = listOf(
            Convertor().convert(auto4), Convertor().convert(auto5), Convertor().convert(auto1),
            Convertor().convert(auto3), Convertor().convert(auto2)
        )
        assertEquals(expectedResult, result)

    }

    @Test
    fun grouping() {
        val list = listOf(auto1, auto2, auto3, auto4, auto5)

        val result = AutomobileService().bodyGrouping(list)

        val expectedResult = mapOf(
            "SUV" to listOf(auto1),
            "Coupe" to listOf(auto2, auto3),
            "CUV" to listOf(auto4),
            "Hatchback" to listOf(auto5)
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun filtration() {
        val list = listOf(auto1, auto2, auto3, auto4, auto5)

        val result = AutomobileService().yearFiltration(list, 2018)

        val expectedResult = listOf(
            "Мини Countryman was assembled in 2018",
            "БМВ M5 was assembled in 2019",
            "Лада XRay was assembled in 2020"
        )
        assertEquals(expectedResult, result)
    }
}