package dev.paigewatson.layoutmaster.client.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dev.paigewatson.layoutmaster.client.services.FreightCarService
import dev.paigewatson.layoutmaster.helpers.FreightCarServiceFake
import dev.paigewatson.layoutmaster.helpers.TestFreightCarCreator
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.util.*

class FreightCarControllerTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        private var boxcarOne: RollingStock = TestFreightCarCreator.boxcar("PNWR", 2341)
        private var boxcarTwo: RollingStock = TestFreightCarCreator.boxcar("BCR", 2342)
        private var boxcarThree: RollingStock = TestFreightCarCreator.boxcar("PNWR", 2335)
        private var gondolaOne: RollingStock = TestFreightCarCreator.flatcar("ATSF", 1232)
        private var flatCarOne: RollingStock = TestFreightCarCreator.gondola("BNSF", 1234)
        private var freightCarServiceFake: FreightCarServiceFake = FreightCarServiceFake()
        private lateinit var freightCarController: FreightCarController

        @BeforeEach
        fun setupInventory() {
            freightCarController = FreightCarController(freightCarServiceFake)
        }

        @Test
        fun should_saveFreightCar_toDatabase() {
            //assign
            val gondolaTypeUUID = UUID.randomUUID()
            val gondolaToSaveUUID = UUID.randomUUID()
            val gondolaToSave = TestFreightCarCreator.gondola(gondolaToSaveUUID, "FOO", 2345, gondolaTypeUUID)

            //act
            val returnedFreightCarDto = freightCarController.saveFreightCarToDatabase(gondolaToSave)

            //assert
            val savedFreightCar = freightCarServiceFake.savedFreightCar
            AssertionsForClassTypes.assertThat(returnedFreightCarDto.toString()).isEqualTo(savedFreightCar.toString())
        }

        @Test
        fun should_getAllFreightCars() {
            //assign
            freightCarServiceFake.setReturnedFreightCars(
                listOfNotNull(
                    boxcarOne, boxcarTwo, boxcarThree,
                    flatCarOne, gondolaOne
                )
            )

            //act
            val freightCarDtoList = freightCarController.allFreightCars
            //assert
            AssertionsForClassTypes.assertThat(freightCarDtoList.size).isEqualTo(5)
        }

        @Test
        fun should_getAllFreightCars_ofAARType() {
            //assign
            freightCarServiceFake.setReturnedFreightCars(
                listOfNotNull(
                    boxcarOne, boxcarTwo, boxcarThree
                )
            )

            //act
            val freightCarDtoList = freightCarController.getAllFreightCarsOfAARType(AARDesignation.XM)
            //assert
            AssertionsForClassTypes.assertThat(freightCarDtoList.size).isEqualTo(3)
        }

        @Test
        fun should_getAllFreightCars_withRoadName() {
            //assign
            freightCarServiceFake.setReturnedFreightCars(
                listOfNotNull(
                    boxcarOne, boxcarTwo, boxcarThree,
                    flatCarOne
                )
            )

            //act
            val freightCarDtoList = freightCarController.getAllFreightCarsWithRoadName("BCR")
            //assert
            AssertionsForClassTypes.assertThat(freightCarDtoList.size).isEqualTo(4)
        }
    }

    @Nested
    @Tag("Integration")
    @ExtendWith(SpringExtension::class)
    @WebMvcTest(
        FreightCarController::class
    )
    inner class IntegrationTests(
        @Autowired private val mockMvc: MockMvc
    ) {

        @MockkBean
        private lateinit var freightCarService: FreightCarService

        private var boxcarTypeUUID: UUID = UUID.randomUUID()
        private var boxcarOneUUID: UUID = UUID.randomUUID()
        private var boxcarTwoUUID: UUID = UUID.randomUUID()
        private var flatcarTypeUUID: UUID = UUID.randomUUID()
        private var flatcarUUID: UUID = UUID.randomUUID()
        private var gondolaTypeUUID: UUID = UUID.randomUUID()
        private var gondolaUUID: UUID = UUID.randomUUID()
        private var boxcarOne: RollingStock = TestFreightCarCreator.boxcar(boxcarOneUUID, "PNWR", 2341, boxcarTypeUUID)
        private var boxcarTwo: RollingStock = TestFreightCarCreator.boxcar(boxcarTwoUUID, "BCR", 2342, boxcarTypeUUID)
        private var gondolaOne: RollingStock = TestFreightCarCreator.gondola(gondolaUUID, "BNSF", 1234, gondolaTypeUUID)
        private var flatCarOne: RollingStock = TestFreightCarCreator.flatcar(flatcarUUID, "ATSF", 1232, flatcarTypeUUID)
        private var flatCarTwo: RollingStock = TestFreightCarCreator.flatcar("PNWR", 1266)

        @BeforeEach
        fun setupTests() {
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllFreightCars() {
            every { freightCarService.allFreightCars() }.returns(
                listOf(
                    boxcarOne, gondolaOne, flatCarOne
                )
            )

            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/freightcars")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$[0].id").value(boxcarOneUUID.toString()))
                .andExpect(jsonPath("$[0].roadName").value("PNWR"))
                .andExpect(jsonPath("$[0].roadNumber").value(2341))
                .andExpect(jsonPath("$[1].id").value(gondolaUUID.toString()))
                .andExpect(jsonPath("$[1].roadName").value("BNSF"))
                .andExpect(jsonPath("$[1].roadNumber").value(1234))
                .andExpect(jsonPath("$[2].id").value(flatcarUUID.toString()))
                .andExpect(jsonPath("$[2].roadName").value("ATSF"))
                .andExpect(jsonPath("$[2].roadNumber").value(1232))
                .andReturn()
        }

        @Test
        @Throws(Exception::class)
        fun should_addFreightCarToDatabase() {
            every { freightCarService.saveFreightCarToDatabase(any()) }.returns(boxcarOne)
            val content = asJsonString(boxcarOne)
            mockMvc.perform(
                MockMvcRequestBuilders.post("/inventory/freightcars/add")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
            verify { freightCarService.saveFreightCarToDatabase(any()) }
        }

        private fun asJsonString(obj: Any?): String {
            return try {
                ObjectMapper().writeValueAsString(obj)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllFreightCarsByAARType() {
            every { freightCarService.allFreightCarsByAARType(any()) }.returns(
                listOf(
                    boxcarOne, boxcarTwo
                )
            )
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/freightcars/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(boxcarOneUUID.toString()))
                .andExpect(jsonPath("$[0].roadName").value("PNWR"))
                .andExpect(jsonPath("$[0].roadNumber").value(2341))
                .andExpect(jsonPath("$[1].id").value(boxcarTwoUUID.toString()))
                .andExpect(jsonPath("$[1].roadName").value("BCR"))
                .andExpect(jsonPath("$[1].roadNumber").value(2342))
                .andExpect(jsonPath("$[*].carType.aarDesignation").value(listOfNotNull("XM", "XM")))
                .andReturn()
        }

        @Test
        @Throws(Exception::class)
        fun should_returnAllFreightCarsWithRoadName_PNWR() {
            every { freightCarService.allFreightCarsByRoadName(any()) }.returns(
                listOf(
                    boxcarOne, flatCarTwo
                )
            )
            val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/freightcars/PNWR")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(boxcarOneUUID.toString()))
                .andExpect(jsonPath("$[0].roadName").value("PNWR"))
                .andExpect(jsonPath("$[0].roadNumber").value(2341))
                .andExpect(jsonPath("$[1].roadName").value("PNWR"))
                .andExpect(jsonPath("$[1].roadNumber").value(1266))
                .andReturn()
        }
    }
}
