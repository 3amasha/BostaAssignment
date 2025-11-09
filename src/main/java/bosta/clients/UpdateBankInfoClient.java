package bosta.clients;

import bosta.auth.TokenManager;
import bosta.base.APIResources;
import bosta.base.BaseAPI;
import bosta.models.request.UpdateBankInfoRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UpdateBankInfoClient {

    public Response updateBankInfoBadRequest(UpdateBankInfoRequest request, String token) {
        // Send request with token
        return given()
                .spec(BaseAPI.getRequestSpec())
                .header("Authorization", token)
                .body(request)
        .when()
                .post(APIResources.UPDATE_BANK_INFO.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }
}
