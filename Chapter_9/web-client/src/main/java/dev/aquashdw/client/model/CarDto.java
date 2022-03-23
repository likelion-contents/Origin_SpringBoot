package dev.aquashdw.client.model;

import java.util.ArrayList;
import java.util.List;

public class CarDto {
    private Long id;
    private String uid;
    private String vin;
    private String makeAndModel;
    private String color;
    private String transmission;
    private String driveType;
    private String fuelType;
    private String carType;
    private List<String> carOptions;
    private List<String> specs;
    private Long doors;
    private Long mileage;
    private Long kilometrage;
    private String licensePlate;

    public CarDto() {
        this.carOptions = new ArrayList<>();
        this.specs = new ArrayList<>();
    }

    public CarDto(Long id, String uid, String vin, String makeAndModel, String color, String transmission, String driveType, String fuelType, String carType, List<String> carOptions, List<String> specs, Long doors, Long mileage, Long kilometrage, String licensePlate) {
        this.id = id;
        this.uid = uid;
        this.vin = vin;
        this.makeAndModel = makeAndModel;
        this.color = color;
        this.transmission = transmission;
        this.driveType = driveType;
        this.fuelType = fuelType;
        this.carType = carType;
        this.carOptions = carOptions;
        this.specs = specs;
        this.doors = doors;
        this.mileage = mileage;
        this.kilometrage = kilometrage;
        this.licensePlate = licensePlate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public void setMakeAndModel(String makeAndModel) {
        this.makeAndModel = makeAndModel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public List<String> getCarOptions() {
        return carOptions;
    }

    public void setCarOptions(List<String> carOptions) {
        this.carOptions = carOptions;
    }

    public List<String> getSpecs() {
        return specs;
    }

    public void setSpecs(List<String> specs) {
        this.specs = specs;
    }

    public Long getDoors() {
        return doors;
    }

    public void setDoors(Long doors) {
        this.doors = doors;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public Long getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(Long kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String toString() {
        return "CarData{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", vin='" + vin + '\'' +
                ", makeAndModel='" + makeAndModel + '\'' +
                ", color='" + color + '\'' +
                ", transmission='" + transmission + '\'' +
                ", driveType='" + driveType + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", carType='" + carType + '\'' +
                ", carOptions=" + carOptions +
                ", carSpecs=" + specs +
                ", doors=" + doors +
                ", mileage=" + mileage +
                ", kilometrage=" + kilometrage +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
}
