package dev.paigewatson.layoutmaster.models.layout

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class LayoutTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        @Test
        fun should_haveListOfRollingsStock() {
            //assign
            val layout = Layout()

            //act

            //assert
            AssertionsForClassTypes.assertThat(layout).isNotNull
        }
    }
}
