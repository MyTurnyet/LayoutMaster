package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
import dev.paigewatson.layoutmaster.models.rollingstock.AARType;
import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.SheetMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.GS;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        }

        @Test
        public void should_returnAllAARDesignations()
        {
            //assign
            //act
            final List<AARDesignation> allCarTypes = carTypeController.getAARDesignations();
            //assert
            assertThat(allCarTypes.size()).isEqualTo(24);
        }

        @Test
        public void should_returnAllCarTypes()
        {
            //assign
            boxcarType = new AARType(XM, Collections.singletonList(SheetMetal));
            gondolaCarType = new AARType(GS, Collections.singletonList(Parts));
            List<CarType> returnedCarTypes = Arrays.asList(boxcarType, gondolaCarType);
            carTypeServiceFake.setReturnedCarTypeList(returnedCarTypes);

            //act
            final List<CarType> allCarTypes = carTypeController.getAllCarTypes();
            //assert
            assertThat(allCarTypes).isEqualTo(returnedCarTypes);
        }

        @Test
        public void should_returnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(boxcarType);

            //act
            final CarType carTypeByAAR = carTypeController.getCarTypeByAAR(XM);
            //assert
            assertThat(carTypeByAAR).isEqualTo(boxcarType);
        }

//        @Test
//        public void should_notReturnCarTypeByAAR()
//        {
//            //assign
//            carTypeServiceFake.setReturnedCarTypeWithAAR(new NullCarTypeDto());
//
//            //act
//            final CarTypeDto carTypeByAAR = carTypeController.getCarTypeByAAR("XM");
//            //assert
//            assertThat(carTypeByAAR.isNull());
//        }

//        @Test
//        public void should_returnCarTypesT_thatCarry_goods()
//        {
//            //assign
//            final CarTypeDto boxcarTypeDto = new CarTypeDto("XM", Arrays.asList("SheetMetal", "Logs"));
//            final CarTypeDto flatcarTypeDto = new CarTypeDto("FC", Arrays.asList("Logs"));
//            carTypeServiceFake.setReturnedCarTypeList(Arrays.asList(boxcarTypeDto, flatcarTypeDto));
//
//            //act
//            final List<CarTypeDto> carTypesThatCarryLogs = carTypeController.getCarTypesThatCarry("Logs");
//            //assert
//            assertThat(carTypesThatCarryLogs.size()).isEqualTo(2);
//        }

//        @Test
//        public void should_saveCarTypeToRepository()
//        {
//            //assign
//            final CarTypeDto carTypeDto = new CarTypeDto("XM", Collections.singletonList("SheetMetal"));
//
//            //act
//            carTypeController.addNewCarType(carTypeDto);
//
//            //assert
//            assertThat(carTypeServiceFake.savedDtoEntity()).isEqualTo(carTypeDto);
//        }
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
            boxcarType = new AARType(boxCarUUID, XM, Collections.singletonList(SheetMetal));
            gondolaCarType = new AARType(gondolaUUID, GS, Collections.singletonList(Parts));
        }

        @Test
        public void should_returnAllAARDesignations() throws Exception
        {
            when(carTypeService.allAARDesignations()).thenReturn(Arrays.asList(AARDesignation.values()));

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/aar")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[\"XA\",\"XM\",\"XP\",\"XL\",\"XR\",\"XF\",\"FA\",\"FBC\",\"FC\",\"FL\",\"FM\",\"TC\",\"GA\",\"GS\",\"HK\",\"HFA\",\"HT\",\"HTA\",\"TM\",\"TP\",\"RB\",\"RBL\",\"RP\",\"NULL\"]");
        }

        @Test
        public void should_returnAllCarTypes() throws Exception
        {
            List<CarType> returnedCarTypes = Arrays.asList(boxcarType, gondolaCarType);
            when(carTypeService.allCarTypes()).thenReturn(returnedCarTypes);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"null\":false,\"dto\":{\"id\":\"\",\"aarType\":\"XM\",\"carriedGoods\":[\"SheetMetal\"],\"null\":false}},{\"null\":false,\"dto\":{\"id\":\"\",\"aarType\":\"GS\",\"carriedGoods\":[\"Parts\"],\"null\":false}}]");
        }

//        @Test
//        public void should_returnAllCarTypeThatCarry_ExpectedGoods() throws Exception
//        {
//            final UUID uuidGondola = UUID.randomUUID();
//            final UUID uuidBoxcar = UUID.randomUUID();
//            final CarTypeDto boxcarTypeDto = new CarTypeDto(uuidBoxcar, "XM", Arrays.asList("Parts", "Paper"));
//            final CarTypeDto gondolaCarTypeDto = new CarTypeDto(uuidGondola, "GS", Collections.singletonList("Parts"));
//            List<CarTypeDto> returnedCarTypes = Arrays.asList(boxcarTypeDto, gondolaCarTypeDto);
//
//            when(carTypeService.carTypesThatCarryGoodsType(any())).thenReturn(returnedCarTypes);
//
//            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/goods/Paper")
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//            final String contentAsString = result.getResponse().getContentAsString();
//
//            assertThat(contentAsString).isEqualTo("[{\"id\":\"" + uuidBoxcar.toString() + "\",\"aarType\":\"XM\",\"carriedGoods\":[\"Parts\",\"Paper\"],\"null\":false}" +
//                    ",{\"id\":\"" + uuidGondola.toString() + "\",\"aarType\":\"GS\",\"carriedGoods\":[\"Parts\"],\"null\":false}]");
//        }
//
//        @Test
//        public void should_returnCarTypeByAARType() throws Exception
//        {
//            final UUID uuid = UUID.randomUUID();
//            final CarTypeDto carTypeDto = new CarTypeDto(uuid, "XM", Collections.singletonList("SheetMetal"));
//            when(carTypeService.carTypeForAAR("XM")).thenReturn(carTypeDto);
//
//            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/aar/XM")
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//            final String contentAsString = result.getResponse().getContentAsString();
//
//            assertThat(contentAsString).isEqualTo("{\"id\":\"" + uuid.toString() + "\",\"aarType\":\"XM\",\"carriedGoods\":[\"SheetMetal\"],\"null\":false}");
//        }
//
//        @Test
//        public void should_notReturnCarTypeByAARType() throws Exception
//        {
//            when(carTypeService.carTypeForAAR("XM")).thenReturn(new NullCarTypeDto());
//
//            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/types/aar/XM")
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//            final String contentAsString = result.getResponse().getContentAsString();
//
//            assertThat(contentAsString).isEqualTo("{\"id\":\"\",\"aarType\":\"\",\"carriedGoods\":[],\"null\":true}");
//        }
//
//        @Test
//        public void should_addCarTypeToDatabase() throws Exception
//        {
//            final CarTypeDto carTypeDto = new CarTypeDto("XM", Collections.singletonList("SheetMetal"));
//
//
//            //assign
//            final String content = asJsonString(carTypeDto);
//            mockMvc.perform(MockMvcRequestBuilders.post("/models/types")
//                    .content(content)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//
//            verify(carTypeService, times(1)).saveCarTypeToDatabase(any());
//
//        }

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
}