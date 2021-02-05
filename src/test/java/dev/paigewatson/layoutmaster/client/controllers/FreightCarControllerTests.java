package dev.paigewatson.layoutmaster.client.controllers;

import dev.paigewatson.layoutmaster.helpers.FreightCarServiceFake;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class FreightCarControllerTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_getAllFreightCars()
        {
            //assign
            final FreightCarServiceFake freightCarServiceFake = new FreightCarServiceFake();
            final FreightCarController freightCarController = new FreightCarController(freightCarServiceFake);

            //act

            //assert

        }
    }
}