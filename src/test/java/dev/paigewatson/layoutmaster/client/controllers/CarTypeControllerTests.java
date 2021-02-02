package dev.paigewatson.layoutmaster.client.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.paigewatson.layoutmaster.data.CarTypeRepository;
import dev.paigewatson.layoutmaster.helpers.CarTypeServiceFake;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation;
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

import java.util.ArrayList;
import java.util.List;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarType boxCarType = new CarType(XM, carriedGoodsList);
            ArrayList<CarType> returnedCarTypes = new ArrayList<>();
            returnedCarTypes.add(boxCarType);
            carTypeServiceFake.setReturnedCarTypes(returnedCarTypes);

            //act
            final List<CarType> allCarTypes = (List<CarType>) carTypeController.getAllCarTypes();
            //assert
            assertThat(allCarTypes).isEqualTo(returnedCarTypes);
        }
//
//        @Test
//        public void should_saveCarTypeToRepository()
//        {
//            //assign
//            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
//            carriedGoodsList.add(Ingredients);
//            final CarType carType = new CarType(FC, carriedGoodsList);
//
//            //act
//            carTypeController.addNewCarType(carType);
//
//            //assert
//            assertThat(repositoryFake.savedEntity().isOfType(FC)).isTrue();
//            assertThat(repositoryFake.savedEntity().canCarry(Ingredients)).isTrue();
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
        private CarTypeRepository repository;

        @Test
        public void should_returnAllAARDesignations() throws Exception
        {
            //assign
            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/models/aar")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            final String contentAsString = result.getResponse().getContentAsString();

            assertThat(contentAsString).isEqualTo("[\"XA\",\"XM\",\"XP\",\"XL\",\"XR\",\"XF\",\"FA\",\"FBC\",\"FC\",\"FL\",\"FM\",\"TC\",\"GA\",\"GS\",\"HK\",\"HFA\",\"HT\",\"HTA\",\"TM\",\"TP\",\"RB\",\"RBL\",\"RP\"]");
        }

        @Test
        public void should_addCarTypeToDatabase() throws Exception
        {
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);
            final CarType carType = new CarType(XM, carriedGoodsList);


            //assign
            final String content = asJsonString(carType);
            mockMvc.perform(MockMvcRequestBuilders.post("/models/cartypes")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(repository, times(1)).save(any());

        }

        public String asJsonString(final Object obj)
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