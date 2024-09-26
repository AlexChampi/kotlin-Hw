import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.tinkoff.fintech.hw5.*

class AutomobileServiceTests {
    var dictionary = Dictionary(DICTIONARY_MAP)
    val convertor = Convertor(dictionary, rate)
    val automobileService = AutomobileService(convertor)


    @Test
    fun internationFormat() {

        val result = automobileService.internationalFormatSort(autos)

        val expectedResult = listOf(
            auto4.copy(model = DICTIONARY_MAP.getValue(auto4.model), price = auto4.price / rate),
            auto5.copy(model = DICTIONARY_MAP.getValue(auto5.model), price = auto5.price / rate),
            auto1.copy(model = DICTIONARY_MAP.getValue(auto1.model), price = auto1.price / rate),
            auto3.copy(model = DICTIONARY_MAP.getValue(auto3.model), price = auto3.price / rate),
            auto2.copy(model = DICTIONARY_MAP.getValue(auto2.model), price = auto2.price / rate)
        )
        assertEquals(expectedResult, result)

    }

    @Test
    fun grouping() {


        val result = automobileService.bodyGrouping(autos)

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


        val result = automobileService.yearFiltration(autos, 2018)

        val expectedResult = listOf(
            "Мини Countryman was assembled in 2018",
            "БМВ M5 was assembled in 2019",
            "Лада XRay was assembled in 2020"
        )
        assertEquals(expectedResult, result)
    }

    val auto1 = Automobile("Форд", "Raptor", "SUV", 5_000_000, 15, 2010)
    val auto2 = Automobile("Бентли", "Continental", "Coupe", 20_000_000, 7, 2016)
    val auto3 = Automobile("БМВ", "M5", "Coupe", 7_000_000, 9, 2019)
    val auto4 = Automobile("Лада", "XRay", "CUV", 1_200_000, 10, 2020)
    val auto5 = Automobile("Мини", "Countryman", "Hatchback", 3_000_000, 6, 2018)
    val autos = listOf(auto1, auto2, auto3, auto4, auto5)

}

private const val rate = 110
private val DICTIONARY_MAP = mapOf(
    "Форд" to "Ford",
    "Бентли" to "Bentley",
    "БМВ" to "BMW",
    "Лада" to "Lada",
    "Мини" to "Mini"
)
