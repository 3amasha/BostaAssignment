package bosta.auth;

import bosta.base.APIResources;
import bosta.base.BaseAPI;
import bosta.config.ConfigManager;
import bosta.models.response.GenerateTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

public class TokenManager {
    //Global (shared across all tests)
    private static String cachedToken;

    /**
     * Retrieves the active token, generates it if missing.
     */
    public static synchronized String getInterviewToken() {
        if (cachedToken != null && !cachedToken.isEmpty()) {
            return cachedToken;
        }
        cachedToken = generateNewToken();
        return cachedToken;
    }

    /**
     * Regenerates the token (e.g., after 401 unauthorized)
     */
    public static synchronized String handleUnauthorized() {
        System.out.println("Token expired — regenerating...");
        cachedToken = generateNewToken();
        return cachedToken;
    }

    /**
     * Generates a new interview token via API.
     */
    private static String generateNewToken() {
        System.out.println("Generating new interview token...");

        Response response = io.restassured.RestAssured.given()
                .spec(BaseAPI.getRequestSpec())
        .when()
                .post(APIResources.GENERATE_TOKEN.getResource())
        .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract()
                .response();

        // Try model deserialization
        try {
            GenerateTokenResponse tokenResponse = response.as(GenerateTokenResponse.class);
            if (tokenResponse != null && tokenResponse.token != null) {
                System.out.println("Token generated successfully");
                return tokenResponse.token;
            }
        } catch (Exception e) {
            System.err.println("Token mapping failed, falling back to JSON parsing...");
        }

        // Fallback JSON parsing
        JSONObject json = new JSONObject(response.asString());
        String token = json.optString("token", null);

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token not found in response: " + response.asString());
        }

        System.out.println("Token generated successfully");
        return token;
    }

}
