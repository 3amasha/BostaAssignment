package bosta.tests;

import bosta.auth.TokenManager;
import bosta.base.BaseAPI;
import bosta.base.BaseTest;
import bosta.clients.UpdateBankInfoClient;
import bosta.models.request.UpdateBankInfoRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateBankInfoTest extends BaseTest {

    private UpdateBankInfoClient bankInfoClient;
    private static String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InVydmIxOWNKT0E4M044eHY2d0lzUCIsInJvbGVzIjpbIkJVU0lORVNTX0FETUlOIl0sImJ1c2luZXNzQWRtaW5JbmZvIjp7ImJ1c2luZXNzSWQiOiI2U2xuRmpodjVPRWFESWFBZWVjdGciLCJidXNpbmVzc05hbWUiOiJ0ZXN0IGNvbXBhbnkgbmFtZSJ9LCJlbWFpbCI6ImFtaXJhLm1vc2ErOTkxQGJvc3RhLmNvIiwicGhvbmUiOiIrMjAxMDU1NTkyODI5IiwiY291bnRyeSI6eyJfaWQiOiI2MGU0NDgyYzdjYjdkNGJjNDg0OWM0ZDUiLCJuYW1lIjoiRWd5cHQiLCJuYW1lQXIiOiLZhdi12LEiLCJjb2RlIjoiRUcifSwidG9rZW5UeXBlIjoiQUNDRVNTIiwidG9rZW5WZXJzaW9uIjoiVjIiLCJzZXNzaW9uSWQiOiIwMUs5SkM4TjQzTTVDRVQyRjEwQlZCRTQzRCIsImlhdCI6MTc2MjYzOTIyOCwiZXhwIjoxNzYzODQ4ODI4fQ.WTwacpzr_riHMOp6nndQ4-tlPTKIHSX1tBBdxV6hXyA";

    @BeforeClass
    public void setUp() {
       // token = TokenManager.getInterviewToken(); //comment to use the old token(static)
        bankInfoClient = new UpdateBankInfoClient();
    }



    @Test(description = "Verify updating bank info with invalid OTP")
    public void testUpdateBankInfo_InvalidOtp() {
        //Build request body using POJO
        UpdateBankInfoRequest request = new UpdateBankInfoRequest(
                "Test Name",
                "NBG",
                "123",
                "EG123456789012345678901299999",
                "123" //the otp is invalid or expired
        );
        //send the request using the client
        Response response = bankInfoClient.updateBankInfoBadRequest(request, token);
        //Assertions
        Assert.assertEquals(response.statusCode(), 400, "Expected status code 400");
        Assert.assertTrue(response.asString().contains("success"), "Response should indicate success");
    }

    @Test
    public void sqlInjectionShouldNotSucceed() {
        // injection payload examples
        String sqli = "test' OR '1'='1'; -- ";
        String maliciousIBAN = "EG123' OR '1'='1";

        UpdateBankInfoRequest req = new UpdateBankInfoRequest(
                sqli,
                "NBG Bank",
                "123456",
                maliciousIBAN,
                "123"
        );

        Response response = bankInfoClient.updateBankInfoBadRequest(req, token);
        Assert.assertEquals(response.statusCode(), 400, "Expected status code 400 for SQL injection attempt");
        Assert.assertTrue(response.asString().contains("success"), "Response should indicate success field");
    }

    @Test
    public void quickOtpAttemptsShouldTriggerRateLimitOrLockout() {
        UpdateBankInfoRequest req = new UpdateBankInfoRequest(
                "Test User",
                "NBG Bank",
                "123",
                "EG123456789012345678901299999",
                "" // will set OTP in loop
        );

        int attempts = 5; // small number
        int errors = 0;
        for (int i = 0; i < attempts; i++) {
            req.paymentInfoOtp = String.format("%03d", i); // "000", "001", ...
            Response res = bankInfoClient.updateBankInfoBadRequest(req, token);
            if (res.statusCode() == 429 || res.asString().toLowerCase().contains("blocked")
                    || res.asString().toLowerCase().contains("too many")) {
                errors++;
                break; // found rate limit / lock
            }
        }
        Assert.assertTrue(errors > 0, "Should observe rate-limiting or lockout after few failed attempts");
    }
}
