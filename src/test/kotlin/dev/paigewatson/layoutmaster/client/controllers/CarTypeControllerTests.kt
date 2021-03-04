package dev.paigewatson.layoutmaster.client.controllers

import com.ninjasquad.springmockk.MockkBean
import dev.paigewatson.layoutmaster.client.services.CarTypeService
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.AARType
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType
import io.mockk.every
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*
import java.util.stream.IntStream

class CarTypeControllerTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        private var carTypeServiceFake: CarTypeServiceFake = CarTypeServiceFake()
        private val carTypeController: CarTypeController = CarTypeController(carTypeServiceFake)
        private var boxcarType: CarType = TestAARTypeCreator.boxcarType()
        private var gondolaCarType: CarType = TestAARTypeCreator.gondolaType()


        @Test
        fun should_returnAllAARDesignations() {
            val expectedList = listOf(AARDesignation.XM, AARDesignation.FC, AARDesignation.GS)
            carTypeServiceFake.setReturnAARTypes(expectedList)
            val aarDesignations = carTypeController.getAARDesignations()
            Assertions.assertThat(aarDesignations.size).isEqualTo(3)
            Assertions.assertThat(aarDesignations).hasSameElementsAs(expectedList)
        }

        @Test
        fun should_returnAllCarTypesInDatabse() {
            //assign
            val returnedCarTypes = listOf(boxcarType, gondolaCarType)
            val carTypeDtoList = listOf(boxcarType, gondolaCarType)
            carTypeServiceFake.setReturnedCarTypeList(returnedCarTypes)

            //act
            val allCarTypes = carTypeController.allCarTypes()
            //assert
            IntStream.range(0, allCarTypes.size).forEachOrdered { i: Int ->
                Assertions.assertThat(
                    allCarTypes[i].toString()
                ).isEqualTo(carTypeDtoList[i].toString())
            }
        }

        @Test
        fun should_returnCarTypeByAAR() {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(boxcarType)

            //act
            val carTypeByAAR = carTypeController.getCarTypeByAAR("XM")

            //assert
            Assertions.assertThat(carTypeByAAR.toString()).isEqualTo(boxcarType.toString())
        }

        @Test
        fun should_notReturnCarTypeByAAR() {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(NullCarType())

            //act
            val carTypeByAAR = carTypeController.getCarTypeByAAR("XM")
            //assert
            Assertions.assertThat(carTypeByAAR.isNull).isTrue
        }

        @Test
        fun should_returnCarTypesT_thatCarry_goods() {
            //assign
            carTypeServiceFake.setReturnedCarTypeList(listOfNotNull(boxcarType, gondolaCarType))

            //act
            val carTypesThatCarryLogs = carTypeController.getCarTypesThatCarry("Logs")
            //assert
            Assertions.assertThat(carTypesThatCarryLogs.size).isEqualTo(2)
        }

        @Test
        fun should_saveCarTypeToRepository() {
            //assign
            val boxcarType: CarType = TestAARTypeCreator.boxcarType()

            //act
            carTypeController.addNewCarType(boxcarType)

            //assert
            Assertions.assertThat(carTypeServiceFake.savedDtoEntity().toString()).isEqualTo(boxcarType.toString())
        }
    }

    @Nested
    @Tag("Integration")
    @ExtendWith(SpringExtension::class)
    @WebMvcTest(
        CarTypeController::class
    )
    inner class IntegrationTests(
        @Autowired private val mockMvc: MockMvc
    ) {

        @MockkBean
        private lateinit var carTypeService: CarTypeService

        private var boxCarUUID: UUID = UUID.randomUUID()
        private var gondolaUUID: UUID = UUID.randomUUID()
        private var boxcarType: AARType = TestAARTypeCreator.boxcarType(boxCarUUID)
        private var gondolaCarType: AARType = TestAARTypeCreator.gondolaType(gondolaUUID)

        @Test
        @Throws(Exception::class)
        fun should_returnAllAARDesignationsInDatabase() {
            val expectedList = listOf(AARDesignation.XM, AARDesignation.FC, AARDesignation.GS)
            every { carTypeService.allAARDesignations() }.returns(expectedList)
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/models/aar")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
            val contentAsString = result.response.contentAsString
            Assertions.assertThat(contentAsString).isEqualTo("[\"XM\",\"FC\",\"GS\"]")
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllCarTypes() {
            val aarTypes: List<AARType> = listOf(gondolaCarType)
            every { carTypeService.allCarTypes() }.returns(aarTypes)
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/models/types")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
            val contentAsString = result.response.contentAsString
            Assertions.assertThat(contentAsString).isEqualTo(
                "[{\"aarDesignation\":\"GS\",\"carriedGoodsList\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"id\":\"" +
                        gondolaUUID.toString() +
                        "\",\"null\":false}]"
            )
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllCarTypeThatCarry_ExpectedGoods() {
            val returnedCarTypes: List<AARType> = listOfNotNull(boxcarType, gondolaCarType)
            every { carTypeService.carTypesThatCarryGoodsType(GoodsType.Logs) }.returns(returnedCarTypes)
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/models/types/goods/Logs")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
            val contentAsString = result.response.contentAsString
            Assertions.assertThat(contentAsString).isEqualTo(
                "[{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"" +
                        boxCarUUID.toString() +
                        "\",\"null\":false},{\"aarDesignation\":\"GS\",\"carriedGoodsList\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"id\":\"" +
                        gondolaUUID.toString() +
                        "\",\"null\":false}]"
            )
        }

        @Test
        @Throws(Exception::class)
        fun should_returnCarTypeByAARType() {
            every { carTypeService.allAARDesignations() }.returns(listOf(*AARDesignation.values()))
            every { carTypeService.carTypeForAAR(any()) }.returns(boxcarType)
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/models/types/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
            val contentAsString = result.response.contentAsString
            Assertions.assertThat(contentAsString).isEqualTo(
                "{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"" +
                        boxCarUUID.toString() +
                        "\",\"null\":false}"
            )
        }

        @Test
        @Throws(Exception::class)
        fun should_notReturnCarTypeByAARType() {
            every { carTypeService.carTypeForAAR(any()) }.returns(NullCarType())

            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/models/types/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
            val contentAsString = result.response.contentAsString
            Assertions.assertThat(contentAsString)
                .isEqualTo("{\"aarDesignation\":\"NULL\",\"carriedGoodsList\":[],\"id\":\"\",\"null\":true}")
        }

        //        @Test
        //        public void should_addCarTypeToDatabase() throws Exception
        //        {
        //
        //            //assign
        //            final CarType boxcarTypeDto = boxcarType;
        //            mockMvc.perform(MockMvcRequestBuilders.post("/models/types/add")
        //                    .content(asJsonString(boxcarTypeDto))
        //                    .contentType(MediaType.APPLICATION_JSON)
        //                    .accept(MediaType.APPLICATION_JSON))
        //                    .andExpect(status().isOk());
        //
        //            verify(carTypeService, times(1)).saveCarTypeToDatabase(any());
        //
        //        }
//        private fun asJsonString(obj: Any): String {
//            return try {
//                ObjectMapper().writeValueAsString(obj)
//            } catch (e: Exception) {
//                throw RuntimeException(e)
//            }
//        }
    }

    @Nested
    @Tag("Data")
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    inner class FunctionalTests {
        @Autowired
        private lateinit var mongoTemplate: MongoTemplate

        private val collectionName = "AARTypes"

        @LocalServerPort
        private val port = 0

        @Autowired
        private lateinit var restTemplate: TestRestTemplate

        private var boxcar: AARType = TestAARTypeCreator.boxcarType(boxcarUUID)
        private var gondola: AARType = TestAARTypeCreator.gondolaType()
        private var flatcar: AARType = TestAARTypeCreator.flatcarType()

        @BeforeEach
        fun setup() {
            mongoTemplate.remove(Query(), collectionName)
            insertCarTypesForTesting()
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllCarTypes() {
            val aarTypesArray =
                restTemplate.getForObject("http://localhost:$port/models/types", Array<AARType>::class.java)
            Assertions.assertThat(aarTypesArray.size).isEqualTo(3)
        }

        @Test
        fun should_returnAllAARDesignations() {
            val returnedAARList =
                restTemplate.getForObject("http://localhost:$port/models/aar", Array<AARDesignation>::class.java)
            val expectedAarDesignationList = listOf(AARDesignation.XM, AARDesignation.FC, AARDesignation.GS)
            Assertions.assertThat(returnedAARList).hasSameElementsAs(expectedAarDesignationList)
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllCarTypeThatCarry_ExpectedGoods() {
            val contentAsString = restTemplate.getForObject(
                "http://localhost:$port/models/types/goods/Parts",
                Array<AARType>::class.java
            )
            Assertions.assertThat(contentAsString.size).isEqualTo(2)
            Assertions.assertThat(contentAsString[0]).isEqualTo(boxcar)
            Assertions.assertThat(contentAsString[1]).isEqualTo(flatcar)
        }

        @Test
        @Throws(Exception::class)
        fun should_returnCarTypeByAARType() {
            val contentAsString =
                restTemplate.getForObject("http://localhost:$port/models/types/aar/XM", AARType::class.java)
            Assertions.assertThat(contentAsString).isEqualTo(boxcar)
        }

        @Test
        @Throws(Exception::class)
        fun should_notReturnCarTypeByAARType() {
            val contentAsString =
                restTemplate.getForObject("http://localhost:$port/models/types/aar/QR", NullCarType::class.java)
            Assertions.assertThat(contentAsString.isNull).isTrue
        }

        private val boxcarUUID: UUID
            get() {
                return UUID.randomUUID()
            }

        private fun insertCarTypesForTesting() {
            val aarTypes: List<AARType> = listOfNotNull(boxcar, gondola, flatcar)
            for (aarType in aarTypes) {
                mongoTemplate.insert(aarType, collectionName)
            }
        }
    }
}

