package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.models.rollingstock.CarTypeDesignation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarTypeControllerTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_returnAllCarTypes()
        {
            //assign
            final CarTypeController carTypeController = new CarTypeController();

            //act
            final List<CarTypeDesignation> allCarTypes = carTypeController.getAllCarTypes();
            //assert
            assertThat(allCarTypes.size()).isEqualTo(23);
        }
    }
}