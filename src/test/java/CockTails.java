import utilities.Reporting;
import data_providers.ListOfDataProviders;
import models.BasicInformation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automation.TheCockTailDB;

public class CockTails
{
    private BasicInformation basicInformation;
    private TheCockTailDB cockTailDB;
    @BeforeMethod
    public void initializeData()
    {
        basicInformation = new BasicInformation().readBasicInformation();
        cockTailDB = new TheCockTailDB();
    }

    @Test
    public void searchIngredientByName()
    {
        Reporting.reportName=new Throwable().getStackTrace()[0].getMethodName();
        basicInformation.setParameterToSearchWith("i");
        basicInformation.setValueToSearchWith("vodka");
        cockTailDB.validate_IngredientsSearchedByName(basicInformation);
    }

    @Test
    public void searchCocktailsByName()
    {
        Reporting.reportName=new Throwable().getStackTrace()[0].getMethodName();
        String cocktailName="margarita";
        basicInformation.setParameterToSearchWith("s");
        basicInformation.setValueToSearchWith(cocktailName.toLowerCase());
        cockTailDB.validate_CocktailsSearchByName(basicInformation);
    }

    @Test(dataProvider = "drinkType", dataProviderClass = ListOfDataProviders.class)
    public void filterByAlcoholic(String typeOfDrink)
    {
        Reporting.reportName=new Throwable().getStackTrace()[0].getMethodName();
        basicInformation.setEndPoint("/filter.php");
        basicInformation.setParameterToSearchWith("a");
        basicInformation.setValueToSearchWith(typeOfDrink);
        cockTailDB.filterByAlcoholic(basicInformation);
    }

    @Test(dataProvider = "listOfFilters", dataProviderClass = ListOfDataProviders.class)
    public void  filterAlistBy_Categories_Glasses_ngredients_Alcoholic(String filters)
    {
        Reporting.reportName=new Throwable().getStackTrace()[0].getMethodName();
        basicInformation.setEndPoint("/list.php");
        basicInformation.setParameterToSearchWith(filters);
        basicInformation.setValueToSearchWith("list");
        cockTailDB.filterAlistBy_Categories_Glasses_ngredients_Alcoholic(basicInformation);
    }

}
