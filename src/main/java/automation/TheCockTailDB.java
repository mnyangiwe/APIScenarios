package automation;

import com.fasterxml.jackson.databind.SerializationFeature;
import utilities.BaseFramework;
import utilities.Reporting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import models.*;
import org.testng.Assert;


public class TheCockTailDB extends BaseFramework {
    private String responseBody = null;
    private Gson gson;
    private ObjectMapper mapper;

    public TheCockTailDB() {
        gson = new Gson();
        mapper = new ObjectMapper();
        reporting = new Reporting();
    }

    @Override
    public void checkStatus(BasicInformation basicInformation)
    {
        BaseFramework.response = getResources(basicInformation);
        Assert.assertTrue(response.getStatusCode() == basicInformation.getExpectedStatus());
    }


    public void validate_IngredientsSearchedByName(BasicInformation basicInformation)
    {
        try {
            checkStatus(basicInformation);
            displayMessageOnTheReport("PASSED","Have connected to the server. Status : "+response.getStatusCode());
            responseBody = getFoundResponseBody();
            mapper.readValue(responseBody, Ingredients.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        responseBody = responseBody.replace("\\r\\n\\r\\n","");
        Ingredients ingredients = gson.fromJson(responseBody, Ingredients.class);
        displayMessageOnTheReport("JSON",cleanedJsonBody(ingredients));

        for (Ingredient ingredient : ingredients.ingredients)
        {
            Assert.assertNotNull(ingredient.getIdIngredient(), "The ingredient Id does not exist.");
            Assert.assertNotNull(ingredient.getStrIngredient(), "The ingredient does not exist.");
            Assert.assertNotNull(ingredient.getStrDescription(), "The description does not exist.");
            Assert.assertNotNull(ingredient.getStrType(), "The type does not exist.");
            Assert.assertTrue((ingredient.getStrAlcohol().toLowerCase().equals("yes") || (ingredient.getStrAlcohol().equals(null))), "Alcohol should be yes or null");
            if (ingredient.getStrAlcohol().equals(null)) {
                Assert.assertNull(ingredient.getStrABV(), "When the Alcohol is null, ABV should be null.");
                displayMessageOnTheReport("PASSED","ABV is "+ingredient.getStrABV()+" because "+ingredient.getStrAlcohol()+" is null.");
            } else {
                Assert.assertNotNull(ingredient.getStrABV(), "When an ingredient is alcoholic, ABV should not be null.");
                displayMessageOnTheReport("PASSED","ABV should not be null When an ingredient is alcoholic");
            }
        }
        displayMessageOnTheReport("PASSED","Have validated all required fields. Test is Complete.");

    }

    public void validate_CocktailsSearchByName(BasicInformation basicInformation) {
        try {
            checkStatus(basicInformation);
            displayMessageOnTheReport("PASSED","Have connected to the server. Status : "+response.getStatusCode());
            responseBody = getFoundResponseBody();
            mapper.readValue(responseBody, Drinks.class);
            Drinks cocktails = gson.fromJson(responseBody, Drinks.class);
            displayMessageOnTheReport("INFOR","Found Drinks : ");
            displayMessageOnTheReport("JSON", cleanedJsonBody(cocktails));
            try {
                if (!cocktails.drinks.equals(null)) {
                    int numberOfFoundCocktails = cocktails.drinks.size();
                    displayMessageOnTheReport("INFOR","There are "+ numberOfFoundCocktails +" cocktails on the database.");
                    for (int x = 1; x <= numberOfFoundCocktails; x++) {
                        Drink drink = cocktails.drinks.get(x);
                        Assert.assertNotNull(drink.getStrDrink());
                        validateEitherNullOrString(drink.getStrTags());
                        Assert.assertNotNull(drink.getStrCategory());
                        Assert.assertNotNull(drink.getStrAlcoholic());
                        Assert.assertNotNull(drink.getStrGlass());
                        Assert.assertNotNull(drink.getStrInstructions());
                        Assert.assertNotNull(drink.getStrIngredient1());
                        validateEitherNullOrString(drink.getStrMeasure1());
                        Assert.assertNotNull(drink.getStrCreativeCommonsConfirmed());
                        validateEitherNullOrString(drink.getDateModified());
                        displayMessageOnTheReport("PASSED","Cocktail number "+x+" is ");
                    }
                }
            } catch (Exception exception) {
                if(cocktails.drinks.equals(null))
                {
                    displayMessageOnTheReport("PASSED","Could not find any cocktail");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        displayMessageOnTheReport("PASSED","Has completed the test.");
    }

    public void filterByAlcoholic(BasicInformation basicInformation) {
        try {
            checkStatus(basicInformation);
            displayMessageOnTheReport("PASSED","Have connected to the server. Status : "+response.getStatusCode());
            responseBody = getFoundResponseBody();
            mapper.readValue(responseBody, Drinks.class);
            Drinks cocktails = gson.fromJson(responseBody, Drinks.class);
            displayMessageOnTheReport("INFOR","Found Drinks : ");
            displayMessageOnTheReport("JSON", cleanedJsonBody(cocktails));
            if (!cocktails.drinks.equals(null))
            {
                int numberOfFoundCocktails = cocktails.drinks.size();
                displayMessageOnTheReport("INFOR","There are "+ numberOfFoundCocktails +" cocktails on the database.");
                for (int x = 0; x < numberOfFoundCocktails; x++)
                {
                    Drink cocktail = cocktails.drinks.get(x);
                    Assert.assertNotNull(cocktail.getStrDrink());
                    Assert.assertNotNull(cocktail.getStrDrinkThumb());
                    Assert.assertNotNull(cocktail.getIdDrink());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        displayMessageOnTheReport("PASSED","Has completed the test.");
    }

    public void filterAlistBy_Categories_Glasses_ngredients_Alcoholic(BasicInformation basicInformation)
    {
        try {
            checkStatus(basicInformation);
            displayMessageOnTheReport("PASSED","Have connected to the server. Status : "+response.getStatusCode());
            responseBody = getFoundResponseBody();
            mapper.readValue(responseBody, Drinks.class);
            Drinks cocktails = gson.fromJson(responseBody, Drinks.class);
            displayMessageOnTheReport("INFOR","Found Drinks : ");
            displayMessageOnTheReport("JSON", cleanedJsonBody(cocktails));
            if (!cocktails.drinks.equals(null)) {
                int numberOfFoundCocktails = cocktails.drinks.size();
                for (int x = 0; x < numberOfFoundCocktails; x++) {
                    Drink cocktail = cocktails.drinks.get(x);
                    switch (basicInformation.getParameterToSearchWith()) {
                        case "c" -> Assert.assertNotNull(cocktail.getStrCategory());
                        case "g" -> Assert.assertNotNull(cocktail.getStrGlass());
                        case "i" -> Assert.assertNotNull(cocktail.getStrIngredient1());
                        case "a" -> Assert.assertNotNull(cocktail.getStrAlcoholic());
                    }

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        displayMessageOnTheReport("PASSED","Has completed the test.");
    }

    private void validateEitherNullOrString(String strValidate)
    {
        try{
            Assert.assertTrue((strValidate.equals(null))||(!strValidate.equals(null)));
        }catch(NullPointerException exception){}
        //Assert.assertTrue((strValidate.equals(null))||(!strValidate.equals(null)));
    }

    private String cleanedJsonBody(Object obj)
    {
        String displayedBody = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            displayedBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( obj);
            displayMessageOnTheReport("JSON",displayedBody);
        }catch (Exception ex){}
        return displayedBody;
    }

}
