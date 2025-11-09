package bosta.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePickupResponse {

    public boolean success;
    public String message;
    public PickupData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PickupData {
        public String _id;
        public String type;
        public String puid;
        public String scheduledDate;
        public String scheduledTimeSlot;
        public ContactPerson contactPerson;
        public Business business;
        public String locationType;
        public List<Object> deliveries;
        public String state;
        public Warehouse warehouse;
        public String businessLocationId;
        public List<Object> tickets;
        public List<LogEntry> log;
        public RepeatedData repeatedData;
        public boolean isRepeated;
        public String packageType;
        public List<Object> reschduledPickupsList;
        public boolean isHubAutoAssigned;
        public boolean isHubReassigned;
        public boolean isNewAddressPickupLocation;
        public boolean isNewCityPickupLocation;
        public boolean isNewAreaPickupLocation;
        public int callsNumber;
        public int smsNumber;
        public String creationSrc;
        public Country country;
        public boolean isBusinessFirstPickup;
        public int numberOfParcels;
        public boolean hasBigItems;
        public BusinessActions businessActions;
        public String createdAt;
        public String updatedAt;
        public int __v;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContactPerson {
        public String _id;
        public String name;
        public String phone;
        public String email;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Business {
        public String _id;
        public String name;
        public String phone;
        public Address address;
        public String locationName;
        public boolean newBusiness;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Warehouse {
        public String _id;
        public String name;
        public Address address;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Address {
        public Country country;
        public City city;
        public Zone zone;
        public District district;
        public String firstLine;
        public List<Double> geoLocation;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Country {
        public String _id;
        public String name;
        public String code;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class City {
        public String _id;
        public String name;
        public int sector;
        public String nameAr;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Zone {
        public String _id;
        public String name;
        public String nameAr;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class District {
        public String _id;
        public String name;
        public String nameAr;
        public String fmCode;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LogEntry {
        public String action;
        public String actionType;
        public String reason;
        public TakenBy takenBy;
        public String time;
        public String _id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TakenBy {
        public String userId;
        public String userName;
        public String userRole;
        public String _id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RepeatedData {
        public String repeatedType;
        public List<String> days;
        public String _id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BusinessActions {
        public PickedUpCountConfirmationInfo pickedUpCountConfirmationInfo;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PickedUpCountConfirmationInfo {
        public String status;
        public String takenAt;
    }

}
