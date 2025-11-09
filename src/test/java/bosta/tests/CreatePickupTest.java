package bosta.tests;

import bosta.clients.CreatePickupClient;
import bosta.models.request.CreatePickupRequest;
import bosta.models.response.CreatePickupResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreatePickupTest {

    private CreatePickupClient createPickupClient;
    private static String token = "bca27763f5f30353ba0ee3d2ebd8951994f5016e269bbd781798e2884274d631";
    private static String emptyToken = "";

    @BeforeClass
    public void setup() {
        createPickupClient = new CreatePickupClient();
    }

    @Test(description = "Verify that a pickup can be created successfully with valid request data")
    public void testCreatePickup_Success() {
        // Prepare contact person details
        CreatePickupRequest.ContactPerson contactPerson =
                new CreatePickupRequest.ContactPerson(
                        "_sCFBrHGi",
                        "Amr Amasha",
                        "amr.amasha80@gmail.com",
                        "+201011814172"
                );

        // Create request payload
        CreatePickupRequest request = new CreatePickupRequest(
                "MFqXsoFhxO",             // businessLocationId
                contactPerson,            // contact person info
                "2025-11-15",             // scheduled date
                "3",                      // number of parcels
                false,                    // has big items
                "One Time",               // repeated type
                "Web"                     // creation source
        );

        // Send request using client and capture response and deserialize
        CreatePickupResponse createPickupResponse = createPickupClient.createPickupSuccess(request, token).as(CreatePickupResponse.class);

        // Assert key fields
        Assert.assertTrue(createPickupResponse.success, "Response message does not confirm successful creation");
    }

    @Test
    public void testMissingAuth() {
        // Prepare contact person details
        CreatePickupRequest.ContactPerson contactPerson =
                new CreatePickupRequest.ContactPerson(
                        "_sCFBrHGi",
                        "Amr Amasha",
                        "amr.amasha80@gmail.com",
                        "+201011814172"
                );

        // Create request payload
        CreatePickupRequest request = new CreatePickupRequest(
                "MFqXsoFhxO",             // businessLocationId
                contactPerson,            // contact person info
                "2025-11-15",             // scheduled date
                "3",                      // number of parcels
                false,                    // has big items
                "One Time",               // repeated type
                "Web"                     // creation source
        );

        // Send request using client and capture response and deserialize
        CreatePickupResponse createPickupResponse = createPickupClient.createPickupUnauthorized(request, emptyToken).as(CreatePickupResponse.class);

        // Assert key fields
        Assert.assertFalse(createPickupResponse.success, "Response message should indicate failure due to invalid auth");
    }



}
