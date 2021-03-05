package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake;
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.boxcarType;
import static dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.gondolaType;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.FC;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.GS;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarTypeControllerTests
{

    @Nested
    @Tag("Unit")
    class UnitTests
    {
        private CarTypeController carTypeController;
        private CarTypeServiceFake carTypeServiceFake;
        private CarType boxcarType;
        private CarType gondolaCarType;

        @BeforeEach
        public void setUp()
        {
            carTypeServiceFake = new CarTypeServiceFake();
            carTypeController = new CarTypeController(carTypeServiceFake);
            boxcarType = TestAARTypeCreator.boxcarType();
            gondolaCarType = TestAARTypeCreator.gondolaType();
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            final List<AARDesignation> expectedList = Arrays.asList(XM, FC, GS);
            carTypeServiceFake.setReturnAARTypes(expectedList);
            final List<AARDesignation> aarDesignations = carTypeController.getAARDesignations();
            assertThat(aarDesignations.size()).isEqualTo(3);
            assertThat(aarDesignations).hasSameElementsAs(expectedList);
        }

        @Test
        public void should_returnAllCarTypesInDatabse()
        {
            //assign
            List<CarType> returnedCarTypes = Arrays.asList(boxcarType, gondolaCarType);
            final List<CarType> carTypeDtoList = Arrays.asList(boxcarType, gondolaCarType);
            carTypeServiceFake.setReturnedCarTypeList(returnedCarTypes);

            //act
            final List<CarType> allCarTypes = carTypeController.getAllCarTypes();
            //assert
            IntStream.range(0, allCarTypes.size()).forEachOrdered(i -> assertThat(allCarTypes.get(i).toString()).isEqualTo(carTypeDtoList.get(i).toString()));
        }

        @Test
        public void should_returnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(boxcarType);

            //act
            final CarType carTypeByAAR = carTypeController.getCarTypeByAAR("XM");

            //assert
            assertThat(carTypeByAAR.toString()).isEqualTo(boxcarType.toString());
        }

        @Test
        public void should_notReturnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(new NullCarType());

            //act
            final CarType carTypeByAAR = carTypeController.getCarTypeByAAR("XM");
            //assert
            assertThat(carTypeByAAR.isNull()).isTrue();
        }

        @Test
        public void should_returnCarTypesT_thatCarry_goods()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeList(Arrays.asList(boxcarType, gondolaCarType));

            //act
            final List<CarType> carTypesThatCarryLogs = carTypeController.getCarTypesThatCarry("Logs");
            //assert
            assertThat(carTypesThatCarryLogs.size()).isEqualTo(2);
        }

        @Test
        public void should_saveCarTypeToRepository()
        {
            //assign
            final AARType boxcarType = TestAARTypeCreator.boxcarType();

            //act
            carTypeController.addNewCarType(boxcarType);

            //assert
            assertThat(carTypeServiceFake.savedDtoEntity().toString()).isEqualTo(boxcarType.toString());
        }
    }

    @Nested
    @Tag("Integration")
    @ExtendWith(SpringExtension.class)
    @WebMvcTest(CarTypeController.class)
    public class IntegrationTests
    {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CarTypeService carTypeService;

        private AARType boxcarType;
        private AARType gondolaCarType;
        private UUID boxCarUUID;
        private UUID gondolaUUID;

        @BeforeEach
        public void setupTests()
        {
            boxCarUUID = UUID.randomUUID();
            gondolaUUID = UUID.randomUUID();
            boxcarType = boxcarType(boxCarUUID);
            gondolaCarType = gondolaType(gondolaUUID);
        }

        @Test
        public void should_returnAllAARDesignationsInDatabase() throws Exception
        {
            final List<AARDesignation> expectedList = Arrays.asList(XM, FC, GS);

            when(carTypeService.allAARDesignations()).thenReturn(expectedList);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/aar")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[\"XM\",\"FC\",\"GS\"]");
        }

        @Test
        public void should_returnAllCarTypes() throws Exception
        {
            final List<CarType> aarTypes = Collections.singletonList(gondolaCarType);
            when(carTypeService.allCarTypes()).thenReturn(aarTypes);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"aarDesignation\":\"GS\",\"carriedGoodsList\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Test
        public void should_returnAllCarTypeThatCarry_ExpectedGoods() throws Exception
        {
            List<CarType> returnedCarTypes = Arrays.asList(boxcarType, gondolaCarType);

            when(carTypeService.carTypesThatCarryGoodsType(GoodsType.Logs)).thenReturn(returnedCarTypes);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/goods/Logs")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"" +
                    boxCarUUID.toString() +
                    "\",\"null\":false},{\"aarDesignation\":\"GS\",\"carriedGoodsList\":[\"ScrapMetal\",\"MetalScraps\",\"Logs\",\"Aggregates\"],\"id\":\"" +
                    gondolaUUID.toString() +
                    "\",\"null\":false}]");
        }

        @Test
        public void should_returnCarTypeByAARType() throws Exception
        {
            when(carTypeService.allAARDesignations()).thenReturn(Arrays.asList(AARDesignation.values()));

            when(carTypeService.carTypeForAAR(any())).thenReturn(boxcarType);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("{\"aarDesignation\":\"XM\",\"carriedGoodsList\":[\"Ingredients\",\"Logs\",\"Parts\"],\"id\":\"" +
                    boxCarUUID.toString() +
                    "\",\"null\":false}");
        }

        @Test
        public void should_notReturnCarTypeByAARType() throws Exception
        {
            when(carTypeService.carTypeForAAR(any())).thenReturn(new NullCarType());
            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("{\"aarDesignation\":\"NULL\",\"carriedGoodsList\":[],\"id\":\"\",\"null\":true}");
        }

        @Test
        public void should_addCarTypeToDatabase() throws Exception
        {

            //assign
            final AARType boxcarTypeDto = boxcarType;
            mockMvc.perform(MockMvcRequestBuilders.post("/models/types/add")
                    .content(asJsonString(boxcarTypeDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(carTypeService).saveCarTypeToDatabase(any());

        }

        private String asJsonString(final Object obj)
        {
            try
            {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    @Tag("Functional")
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class FunctionalTests
    {
        private final String collectionName = "AARTypes";

        private final MongoTemplate mongoTemplate;
        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;
        private AARType boxcar;
        private AARType flatcar;

        public FunctionalTests(@Autowired MongoTemplate mongoTemplate)
        {
            this.mongoTemplate = mongoTemplate;
        }

        @BeforeEach
        public void setup()
        {
            mongoTemplate.remove(new Query(), collectionName);
            insertCarTypesForTesting();
        }

        @Test
        public void should_returnAllCarTypes()
        {
            final AARType[] aarTypesArray = this.restTemplate.getForObject("http://localhost:" + port + "/models/types", AARType[].class);
            assertThat(aarTypesArray.length).isEqualTo(3);
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            final AARDesignation[] returnedAARList = this.restTemplate.getForObject("http://localhost:" + port + "/models/aar", AARDesignation[].class);
            final List<AARDesignation> expectedAarDesignationList = Arrays.asList(XM, FC, GS);
            assertThat(returnedAARList).hasSameElementsAs(expectedAarDesignationList);
        }


        @Test
        public void should_returnAllCarTypeThatCarry_ExpectedGoods()
        {
            final AARType[] contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/types/goods/Parts", AARType[].class);

            assertThat(contentAsString.length).isEqualTo(2);
            assertThat(contentAsString[0]).isEqualTo(boxcar);
            assertThat(contentAsString[1]).isEqualTo(flatcar);
        }

        @Test
        public void should_returnCarTypeByAARType()
        {
            final AARType contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/types/aar/XM", AARType.class);

            assertThat(contentAsString).isEqualTo(boxcar);
        }

        @Test
        public void should_notReturnCarTypeByAARType()
        {
            final NullCarType contentAsString = this.restTemplate.getForObject("http://localhost:" + port + "/models/types/aar/QR", NullCarType.class);

            assertThat(contentAsString.isNull()).isTrue();
        }

        private void insertCarTypesForTesting()
        {
            final UUID boxcarUUID = UUID.randomUUID();
            boxcar = TestAARTypeCreator.boxcarType(boxcarUUID);
            AARType gondola = TestAARTypeCreator.gondolaType();
            flatcar = TestAARTypeCreator.flatcarType();
            final List<AARType> aarTypes = Arrays.asList(boxcar, gondola, flatcar);
            for (AARType aarType : aarTypes)
            {
                mongoTemplate.insert(aarType, collectionName);
            }
        }

    }
}
