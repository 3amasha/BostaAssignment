package bosta.clients;

import bosta.base.APIResources;
import bosta.base.BaseAPI;
import bosta.models.request.CreatePickupRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreatePickupClient {


    public Response createPickupSuccess(CreatePickupRequest request, String token) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .header("Authorization", token)
                .body(request)
        .when()
                .post(APIResources.CREATE_PICKUP.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response createPickupBadRequest(CreatePickupRequest request, String token) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .header("Authorization", token)
                .body(request)
        .when()
                .post(APIResources.CREATE_PICKUP.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response createPickupUnauthorized(CreatePickupRequest request, String token) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .header("Authorization", token)
                .body(request)
        .when()
                .post(APIResources.CREATE_PICKUP.getResource())
        .then()
                .spec(BaseAPI.unauthorized401())
                .extract()
                .response();
    }

}
