package com.lessugly.mrmoika.model;


import org.json.JSONException;
import org.json.JSONObject;

public class Carwash {
    private Integer id;
    private String phoneNumber;
    private String carwashName;
    private Double latitude;
    private Double longitude;
    private String address;
    private Integer numBoxes;
    private String workingMode;
    private String description;

    public Carwash(String phoneNumber, String carwashName, Double latitude, Double longitude, String address) {
        this.phoneNumber = phoneNumber;
        this.carwashName = carwashName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public JSONObject getCarwashAsJSON(){
        JSONObject carwash = new JSONObject();
        try {
            carwash.put("id", id)
                    .put("phoneNumber", phoneNumber)
                    .put("carwashName", carwashName)
                    .put("latitude", latitude)
                    .put("longitude", longitude)
                    .put("address", address)
                    .put("numBoxes", numBoxes)
                    .put("workingMode", workingMode)
                    .put("description", description);
        } catch (JSONException e){

        }
        return carwash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarwashName() {
        return carwashName;
    }

    public void setCarwashName(String carwashName) {
        this.carwashName = carwashName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumBoxes() {
        return numBoxes;
    }

    public void setNumBoxes(Integer numBoxes) {
        this.numBoxes = numBoxes;
    }

    public String getWorkingMode() {
        return workingMode;
    }

    public void setWorkingMode(String workingMode) {
        this.workingMode = workingMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carwash carwash = (Carwash) o;

        if (id != null ? !id.equals(carwash.id) : carwash.id != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(carwash.phoneNumber) : carwash.phoneNumber != null) return false;
        if (carwashName != null ? !carwashName.equals(carwash.carwashName) : carwash.carwashName != null) return false;
        if (latitude != null ? !latitude.equals(carwash.latitude) : carwash.latitude != null) return false;
        if (longitude != null ? !longitude.equals(carwash.longitude) : carwash.longitude != null) return false;
        if (address != null ? !address.equals(carwash.address) : carwash.address != null) return false;
        if (numBoxes != null ? !numBoxes.equals(carwash.numBoxes) : carwash.numBoxes != null) return false;
        if (workingMode != null ? !workingMode.equals(carwash.workingMode) : carwash.workingMode != null) return false;
        if (description != null ? !description.equals(carwash.description) : carwash.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (carwashName != null ? carwashName.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (numBoxes != null ? numBoxes.hashCode() : 0);
        result = 31 * result + (workingMode != null ? workingMode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Carwash{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carwashName='" + carwashName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", numBoxes=" + numBoxes +
                ", workingMode='" + workingMode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
