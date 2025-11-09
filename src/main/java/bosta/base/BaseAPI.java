package bosta.base;

import bosta.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.lessThan;

public class BaseAPI {

    // Thread-safe RequestSpecification
    private static final ThreadLocal<RequestSpecification> requestSpecThread = new ThreadLocal<>();

    // Common ResponseSpecifications
    private static final ThreadLocal<ResponseSpecification> okSpec = new ThreadLocal<>();
    private static final ThreadLocal<ResponseSpecification> createdSpec = new ThreadLocal<>();
    private static final ThreadLocal<ResponseSpecification> badRequestSpec = new ThreadLocal<>();
    private static final ThreadLocal<ResponseSpecification> unauthorizedSpec = new ThreadLocal<>();
    private static final ThreadLocal<ResponseSpecification> forbiddenSpec = new ThreadLocal<>();
    private static final ThreadLocal<ResponseSpecification> notFoundSpec = new ThreadLocal<>();
    private static final ThreadLocal<ResponseSpecification> serverErrorSpec = new ThreadLocal<>();

    /**
     * Builds or returns the cached thread-safe RequestSpecification.
     */
    public static RequestSpecification getRequestSpec() {
        if (requestSpecThread.get() == null) {

            RequestSpecBuilder builder = new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.getBaseUrl())
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .setRelaxedHTTPSValidation()
                    .setConfig(
                            RestAssuredConfig.config()
                                    .httpClient(HttpClientConfig.httpClientConfig()
                                            .setParam("http.connection.timeout", ConfigManager.getConnectionTimeout())
                                            .setParam("http.socket.timeout", ConfigManager.getReadTimeout())
                                    )
                    );

            // Enable detailed request logging if configured
            if (ConfigManager.isRequestLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            requestSpecThread.set(builder.build());
        }

        return requestSpecThread.get();
    }

    /**
     * Returns a RequestSpecification with custom headers.
     */
    public static RequestSpecification withHeaders(Map<String, String> headers) {
        return with()
                .spec(getRequestSpec())
                .headers(headers);
    }

    /**
     * Returns a RequestSpecification with custom path parameters.
     */
    public static RequestSpecification withPathParams(Map<String, String> pathParams) {
        return with()
                .spec(getRequestSpec())
                .pathParams(pathParams);
    }

    /**
     * RequestSpecification with Authorization header.
     * Useful for secured endpoints.
     */
    public static RequestSpecification withAuthorization(String token) {
        return with()
                .spec(getRequestSpec())
                .header("Authorization", token);
    }

    // ---------------------------------------------------------------
    // Response Specifications — cached per thread for isolation
    // ---------------------------------------------------------------

    public static ResponseSpecification ok200() {
        if (okSpec.get() == null) okSpec.set(buildResponseSpec(200));
        return okSpec.get();
    }

    public static ResponseSpecification created201() {
        if (createdSpec.get() == null) createdSpec.set(buildResponseSpec(201));
        return createdSpec.get();
    }

    public static ResponseSpecification badRequest400() {
        if (badRequestSpec.get() == null) badRequestSpec.set(buildResponseSpec(400));
        return badRequestSpec.get();
    }

    public static ResponseSpecification unauthorized401() {
        if (unauthorizedSpec.get() == null) unauthorizedSpec.set(buildResponseSpec(401));
        return unauthorizedSpec.get();
    }

    public static ResponseSpecification forbidden403() {
        if (forbiddenSpec.get() == null) forbiddenSpec.set(buildResponseSpec(403));
        return forbiddenSpec.get();
    }

    public static ResponseSpecification notFound404() {
        if (notFoundSpec.get() == null) notFoundSpec.set(buildResponseSpec(404));
        return notFoundSpec.get();
    }

    public static ResponseSpecification serverError500() {
        if (serverErrorSpec.get() == null) serverErrorSpec.set(buildResponseSpec(500));
        return serverErrorSpec.get();
    }

    /**
     * Builds a reusable ResponseSpecification for a given status code.
     */
    private static ResponseSpecification buildResponseSpec(int statusCode) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(ConfigManager.getMaxResponseTimeout()));

        if (ConfigManager.isResponseLoggingEnabled()) {
            builder.log(LogDetail.ALL);
        }

        return builder.build();
    }

    /**
     * Resets cached specs — useful between test suites or threads.
     */
    public static void resetSpecs() {
        requestSpecThread.remove();
        okSpec.remove();
        createdSpec.remove();
        badRequestSpec.remove();
        unauthorizedSpec.remove();
        forbiddenSpec.remove();
        notFoundSpec.remove();
        serverErrorSpec.remove();
    }


}
