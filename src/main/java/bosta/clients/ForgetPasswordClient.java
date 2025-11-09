package bosta.clients;

import bosta.base.APIResources;
import bosta.base.BaseAPI;
import bosta.models.request.ForgetPasswordRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ForgetPasswordClient {

    public Response forgetPasswordSuccess(ForgetPasswordRequest request, String token) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .header("Authorization", token)
                .body(request)
        .when()
                .post(APIResources.FORGET_PASSWORD.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response forgetPasswordBadRequest(ForgetPasswordRequest request, String token) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .header("Authorization", "Bearer " + token)
                .body(request)
        .when()
                .post(APIResources.FORGET_PASSWORD.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

}
