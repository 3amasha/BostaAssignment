package bosta.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePickupRequest {

    public String businessLocationId;
    public ContactPerson contactPerson;
    public String scheduledDate;
    public String numberOfParcels;
    public boolean hasBigItems;
    public RepeatedData repeatedData;
    public String creationSrc;

    public CreatePickupRequest(String businessLocationId, ContactPerson contactPerson,
                               String scheduledDate, String numberOfParcels,
                               boolean hasBigItems, String repeatedType, String creationSrc) {
        this.businessLocationId = businessLocationId;
        this.contactPerson = contactPerson;
        this.scheduledDate = scheduledDate;
        this.numberOfParcels = numberOfParcels;
        this.hasBigItems = hasBigItems;
        this.repeatedData = new RepeatedData(repeatedType);
        this.creationSrc = creationSrc;
    }

    // Nested class for contactPerson
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContactPerson {
        public String _id;
        public String name;
        public String email;
        public String phone;

        public ContactPerson(String _id, String name, String email, String phone) {
            this._id = _id;
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }

    // Nested class for repeatedData
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RepeatedData {
        public String repeatedType;
        public List<String> days;

        public RepeatedData(String repeatedType) {
            this.repeatedType = repeatedType;
        }
    }

}
