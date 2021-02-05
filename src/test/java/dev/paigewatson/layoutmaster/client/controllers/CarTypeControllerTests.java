package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.client.services.CarTypeService;
import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.data.models.NullCarTypeDto;
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
            assertThat(allCarTypes.size()).isEqualTo(23);
        }

        @Test
        public void should_returnAllCarTypes()
        {
            //assign
            final CarTypeDto carTypeDto = new CarTypeDto("XM", Collections.singletonList("SheetMetal"));
            List<CarTypeDto> returnedCarTypes = Collections.singletonList(carTypeDto);
            carTypeServiceFake.setReturnedCarTypeDTOs(returnedCarTypes);

            //act
            final List<CarTypeDto> allCarTypes = carTypeController.getAllCarTypes();
            //assert
            assertThat(allCarTypes).isEqualTo(returnedCarTypes);
        }

        @Test
        public void should_returnCarTypeByAAR()
        {
            //assign
            final CarTypeDto carTypeDto = new CarTypeDto("XM", Collections.singletonList("SheetMetal"));
            carTypeServiceFake.setReturnedCarTypeWithAAR(carTypeDto);

            //act
            final CarTypeDto carTypeByAAR = carTypeController.getCarTypeByAAR("XM");
            //assert
            assertThat(carTypeByAAR.toString()).isEqualTo(carTypeDto.toString());
        }

        @Test
        public void should_notReturnCarTypeByAAR()
        {
            //assign
            carTypeServiceFake.setReturnedCarTypeWithAAR(new NullCarTypeDto());

            //act
            final CarTypeDto carTypeByAAR = carTypeController.getCarTypeByAAR("XM");
            //assert
            assertThat(carTypeByAAR.isNull());
        }

        @Test
        public void should_saveCarTypeToRepository()
        {
            //assign
            final CarTypeDto carTypeDto = new CarTypeDto("XM", Collections.singletonList("SheetMetal"));


            //act
            carTypeController.addNewCarType(carTypeDto);

            //assert

            assertThat(carTypeServiceFake.savedDtoEntity()).isEqualTo(carTypeDto);
        }
//        @Test
//        public void should_saveCarTypeToRepository()
//        {
//            //assign
//            final CarTypeDto carTypeDto = new CarTypeDto("", "XM", Collections.singletonList("SheetMetal"));
//
//
//            //act
//            carTypeController.addNewCarType(carTypeDto);
//
//            //assert
//
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

        @Test
        public void should_returnAllAARDesignations() throws Exception
        {
            when(carTypeService.allAARDesignations()).thenReturn(Arrays.asList(AARDesignation.values()));

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/aar")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[\"XA\",\"XM\",\"XP\",\"XL\",\"XR\",\"XF\",\"FA\",\"FBC\",\"FC\",\"FL\",\"FM\",\"TC\",\"GA\",\"GS\",\"HK\",\"HFA\",\"HT\",\"HTA\",\"TM\",\"TP\",\"RB\",\"RBL\",\"RP\"]");
        }

        @Test
        public void should_returnAllCarTypes() throws Exception
        {
            final UUID uuid = UUID.randomUUID();
            final CarTypeDto carTypeDto = new CarTypeDto(uuid, "XM", Collections.singletonList("SheetMetal"));
            List<CarTypeDto> returnedCarTypes = Collections.singletonList(carTypeDto);
            when(carTypeService.allCarTypes()).thenReturn(returnedCarTypes);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/cartypes")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[{\"id\":\"" + uuid.toString() + "\",\"aarType\":\"XM\",\"carriedGoods\":[\"SheetMetal\"],\"null\":false}]");
        }

        @Test
        public void should_returnCarTypeByAARType() throws Exception
        {
            final UUID uuid = UUID.randomUUID();
            final CarTypeDto carTypeDto = new CarTypeDto(uuid, "XM", Collections.singletonList("SheetMetal"));
            when(carTypeService.carTypeForAAR("XM")).thenReturn(carTypeDto);

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/cartypes/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("{\"id\":\"" + uuid.toString() + "\",\"aarType\":\"XM\",\"carriedGoods\":[\"SheetMetal\"],\"null\":false}");
        }

        @Test
        public void should_notReturnCarTypeByAARType() throws Exception
        {
            when(carTypeService.carTypeForAAR("XM")).thenReturn(new NullCarTypeDto());

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/cartypes/aar/XM")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("{\"id\":\"\",\"aarType\":\"\",\"carriedGoods\":[],\"null\":true}");
        }

        @Test
        public void should_addCarTypeToDatabase() throws Exception
        {
            final CarTypeDto carTypeDto = new CarTypeDto("XM", Collections.singletonList("SheetMetal"));


            //assign
            final String content = asJsonString(carTypeDto);
            mockMvc.perform(MockMvcRequestBuilders.post("/models/cartypes")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(carTypeService, times(1)).saveCarTypeToDatabase(any());

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
}