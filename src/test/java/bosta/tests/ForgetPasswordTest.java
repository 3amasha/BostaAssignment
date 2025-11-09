package bosta.tests;

import bosta.clients.ForgetPasswordClient;
import bosta.models.request.ForgetPasswordRequest;
import bosta.models.response.ForgetPasswordResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ForgetPasswordTest {

    private ForgetPasswordClient forgetPasswordClient;
    private static String token = "bca27763f5f30353ba0ee3d2ebd8951994f5016e269bbd781798e2884274d631";

    @BeforeClass
    public void setup() {
        forgetPasswordClient = new ForgetPasswordClient();
    }

    @Test(description = "Verify reset link is sent successfully for a valid email")
    public void testForgetPassword_ValidEmail() {
        ForgetPasswordRequest request = new ForgetPasswordRequest("amr.amasha80@gmail.com");

        ForgetPasswordResponse forgetPasswordResponse = forgetPasswordClient.forgetPasswordSuccess(request, token).as(ForgetPasswordResponse.class);

        Assert.assertTrue(forgetPasswordResponse.success, "Expected success to be true");
        Assert.assertEquals(forgetPasswordResponse.message, "Reset link sent to your mail.", "Expected success message");
        Assert.assertNull(forgetPasswordResponse.data, "Expected data to be null");
    }

    @Test(description = "Verify system returns error for empty email")
    public void testForgetPassword_EmptyEmail() {
        ForgetPasswordRequest request = new ForgetPasswordRequest("");

        ForgetPasswordResponse forgetPasswordResponse = forgetPasswordClient.forgetPasswordBadRequest(request, token).as(ForgetPasswordResponse.class);

        Assert.assertFalse(forgetPasswordResponse.success, "Expected success to be false");
        Assert.assertEquals(forgetPasswordResponse.message, "email is not allowed to be empty", "Expected error message for empty email");
    }

    @Test
    public void checkForEmailEnumeration() {
        ForgetPasswordRequest existing = new ForgetPasswordRequest("amira.mosa+991@bosta.co");
        ForgetPasswordRequest notExisting = new ForgetPasswordRequest("noone_1234@example.com");

        Response r1 = forgetPasswordClient.forgetPasswordSuccess(existing, token);
        Response r2 = forgetPasswordClient.forgetPasswordSuccess(notExisting, token);

        Assert.assertEquals(r1.statusCode(), r2.statusCode(), "Status codes should match to prevent email enumeration");
        Assert.assertEquals(r1.asString().contains("success"), r2.asString().contains("success"), "Response bodies should match to prevent email enumeration");
    }

}
