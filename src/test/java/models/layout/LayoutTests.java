package models.layout;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LayoutTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveListOfRollingsStock()
        {
            //assign

            final Layout layout = new Layout();

            //act

            //assert
            assertThat(layout).isNotNull();
        }
    }
}