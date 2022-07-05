package data_providers;

import org.testng.annotations.DataProvider;

public class ListOfDataProviders
{
    @DataProvider(name = "drinkType")
    public static Object[][] drinkType()
    {
        return new Object[][] {{"Alcoholic"}, {"Non_Alcoholic"}};
    }

    @DataProvider(name = "listOfFilters")
    public static Object[][] listOfFilters()
    {
        return new Object[][] {{"c"}, {"g"},{"i"}, {"a"}};
    }

}
