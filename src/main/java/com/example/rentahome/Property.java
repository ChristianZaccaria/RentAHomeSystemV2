/*Developed by Christian Zaccaria (20092351)
 * CA 1 - Rent-A-Home system
 */

package com.example.rentahome;

public class Property {
    private static int propertyId = 0001;

    private int id;
    private String description;
    private String address;
    private String category;
    private String county;
    private String town;
    private String BER;
    private String eircode;
    private String price;
    private String propertyAgent;
    private String imageName;


    public Property(String description, String address, String category, String cnty, String twn,
                    String BER, String eircode, String price, String propertyAgent, String imageName) {
        super();
        this.id = propertyId++;
        this.description = description;
        this.address = address;
        this.category = category;
        county = cnty;
        town = twn;
        this.BER = BER;
        this.eircode = eircode;
        this.price = price;
        this.propertyAgent = propertyAgent;
        this.imageName = imageName;
    }

    public static int getPropertyId() {
        return propertyId;
    }

    public static void setPropertyId(int propertyId) {
        Property.propertyId = propertyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getBER() {
        return BER;
    }

    public void setBER(String BER) {
        this.BER = BER;
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPropertyAgent() {
        return propertyAgent;
    }

    public void setPropertyAgent(String propertyAgent) {
        this.propertyAgent = propertyAgent;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "\nProperty{" +
                "Id=" + id +
                ", Description='" + description + '\'' +
                ", Address='" + address + '\'' +
                ", Category='" + category + '\'' +
                ", County='" + county + '\'' +
                ", Town='" + town + '\'' +
                ", BER='" + BER + '\'' +
                ", Eircode='" + eircode + '\'' +
                ", Price= €" + price +
                ", Property Agent='" + propertyAgent + '\'' +
                '}';
    }
}






