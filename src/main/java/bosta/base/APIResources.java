package bosta.base;

public enum APIResources {

    GENERATE_TOKEN("api/v2/users/generate-token-for-interview-task"),
    CREATE_PICKUP("api/v2/pickups"),
    UPDATE_BANK_INFO("/api/v2/businesses/add-bank-info"),
    FORGET_PASSWORD("api/v2/users/forget-password");

    private final String resource;
    APIResources(String resource) {
        this.resource = resource;
    }

    /** Returns the raw endpoint string as defined in Swagger. */
    public String getResource() {
        return resource;
    }
}
