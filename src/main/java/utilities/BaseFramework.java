package utilities;
import io.restassured.response.Response;
import models.BasicInformation;
import static io.restassured.RestAssured.given;

public abstract class BaseFramework
{
    public static Response response;
    protected Reporting reporting = null;

    public abstract void checkStatus(BasicInformation basicInformation);

    protected Response getResources(BasicInformation basicInformation)
    {
        response = given()
                .baseUri(basicInformation.getBaseURL())
                .param(basicInformation.getParameterToSearchWith(),basicInformation.getValueToSearchWith())
                .get(basicInformation.getEndPoint());
        return response;
    }

    protected String getFoundResponseBody()
    {
        return response.getBody().asString();
    }

    protected void displayMessageOnTheReport(String testState,String msg)
    {reporting.displayMessage(testState,msg);}


}
