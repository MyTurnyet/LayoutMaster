package dev.paigewatson.layoutmaster.client.controllers

import dev.paigewatson.layoutmaster.client.services.CarTypeService
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.AARType
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
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
        private lateinit var carTypeController: CarTypeController
        private var carTypeServiceFake: CarTypeServiceFake = CarTypeServiceFake()
        private var boxcarType: CarType = TestAARTypeCreator.boxcarType()
        private var gondolaCarType: CarType = TestAARTypeCreator.gondolaType()

        @BeforeEach
        fun setUp() {
            carTypeController = CarTypeController(carTypeServiceFake)
        }

        @Test
        fun should_returnAllAARDesignations() {
            val expectedList = listOf(AARDesignation.XM, AARDesignation.FC, AARDesignation.GS)
            carTypeServiceFake.setReturnAARTypes(expectedList)
            val aarDesignations = carTypeController.aARDesignations
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
            val allCarTypes = carTypeController.allCarTypes
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
    inner class IntegrationTests {
        @Autowired
        private val mockMvc: MockMvc? = null

        @MockBean
        private val carTypeService: CarTypeService? = null
        private var boxcarType: AARType? = null
        private var gondolaCarType: AARType? = null
        private var boxCarUUID: UUID? = null
        private var gondolaUUID: UUID? = null

        @BeforeEach
        fun setupTests() {
            boxCarUUID = UUID.randomUUID()
            gondolaUUID = UUID.randomUUID()
            boxcarType = TestAARTypeCreator.boxcarType(boxCarUUID)
            gondolaCarType = TestAARTypeCreator.gondolaType(gondolaUUID)
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllAARDesignationsInDatabase() {
            val expectedList = listOf(AARDesignation.XM, AARDesignation.FC, AARDesignation.GS)
            Mockito.`when`(carTypeService!!.allAARDesignations()).thenReturn(expectedList)
            val result = mockMvc!!.perform(
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
            val aarTypes: ArrayList<AARType> = listOfNotNull(gondolaCarType) as ArrayList<AARType>
            Mockito.`when`(carTypeService!!.allCarTypes()).thenReturn(aarTypes)
            val result = mockMvc!!.perform(
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
            Mockito.`when`(carTypeService!!.carTypesThatCarryGoodsType(GoodsType.Logs)).thenReturn(returnedCarTypes)
            val result = mockMvc!!.perform(
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
            Mockito.`when`(carTypeService!!.allAARDesignations()).thenReturn(listOf(*AARDesignation.values()))
            Mockito.`when`(carTypeService.carTypeForAAR(ArgumentMatchers.any())).thenReturn(boxcarType)
            val result = mockMvc!!.perform(
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
            Mockito.`when`(carTypeService!!.carTypeForAAR(ArgumentMatchers.any())).thenReturn(NullCarType())
            val result = mockMvc!!.perform(
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
    inner class FunctionalTests(@param:Autowired private val mongoTemplate: MongoTemplate) {
        private val collectionName = "AARTypes"

        @LocalServerPort
        private val port = 0

        @Autowired
        private val restTemplate: TestRestTemplate? = null
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
                restTemplate!!.getForObject("http://localhost:$port/models/types", Array<AARType>::class.java)
            Assertions.assertThat(aarTypesArray.size).isEqualTo(3)
        }

        @Test
        fun should_returnAllAARDesignations() {
            val returnedAARList =
                restTemplate!!.getForObject("http://localhost:$port/models/aar", Array<AARDesignation>::class.java)
            val expectedAarDesignationList = listOf(AARDesignation.XM, AARDesignation.FC, AARDesignation.GS)
            Assertions.assertThat(returnedAARList).hasSameElementsAs(expectedAarDesignationList)
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllCarTypeThatCarry_ExpectedGoods() {
            val contentAsString = restTemplate!!.getForObject(
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
                restTemplate!!.getForObject("http://localhost:$port/models/types/aar/XM", AARType::class.java)
            Assertions.assertThat(contentAsString).isEqualTo(boxcar)
        }

        @Test
        @Throws(Exception::class)
        fun should_notReturnCarTypeByAARType() {
            val contentAsString =
                restTemplate!!.getForObject("http://localhost:$port/models/types/aar/QR", NullCarType::class.java)
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

