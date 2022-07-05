package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class BasicInformation
{
    private String baseURL;
    private BasicInformation basicInformation;
    private String endPoint;

    private String valueToSearchWith;

    private String parameterToSearchWith;

    private int expectedStatus;
    private ObjectMapper mapper ;

    public BasicInformation()
    {
        mapper= new ObjectMapper();
    }


    public void setEndPoint(String endPoint)
    {
       this.endPoint=endPoint;
    }

    public String getEndPoint()
    {
        return endPoint;
    }

    public BasicInformation readBasicInformation()
    {
        try {
            String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\BasicInfor.JSON";
            basicInformation = mapper.readValue(new File(filePath), BasicInformation.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return basicInformation;
    }

    public int getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(int expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getValueToSearchWith() {
        return valueToSearchWith;
    }

    public void setValueToSearchWith(String valueToSearchWith) {
        this.valueToSearchWith = valueToSearchWith;
    }

    public String getParameterToSearchWith() {
        return parameterToSearchWith;
    }

    public void setParameterToSearchWith(String parameterToSearchWith) {
        this.parameterToSearchWith = parameterToSearchWith;
    }
}