/*Developed by Christian Zaccaria (20092351)
 * CA 1 - Rent-A-Home system
 */

package com.example.rentahome;




import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RentAHome {

   private ArrayList<Property> available;
   public ArrayList<Property> getAvailable() {
        return available;
    }

    private HashMap<String,User> users;
    public HashMap<String, User> getUsers() {
        return users;
    }


    public ArrayList<Property> getAvailableByCounty(String county) {
        ArrayList<Property> countyAvailable = new ArrayList<>();
        if(county.equals("County")){
            return available;
        }

        for(Property p: available){
            if(p.getCounty().equals(county)){
                countyAvailable.add(p);
            }
        }
        return countyAvailable;
    }


    public ArrayList<Property> getAvailableByTown(String town) {
        ArrayList<Property> townAvailable = new ArrayList<>();
        if(town.equals("Town")){
            return available;
        }

        for(Property p: available){
            if(p.getTown().equals(town)){
                townAvailable.add(p);
            }
        }
        return townAvailable;
    }

    public ArrayList<Property> getAvailableByCategory(String category) {
        ArrayList<Property> categoryAvailable = new ArrayList<>();
        if(category.equals("Category")){
            return available;
        }

        for(Property p: available){
            if(p.getCategory().equals(category)){
                categoryAvailable.add(p);
            }
        }
        return categoryAvailable;
    }


    public ArrayList<Property> getAvailableByPrice(String price) {
        ArrayList<Property> priceAvailable = new ArrayList<>();
        if(price.equals("Price")){
            return available;
        }

        for(Property p: available){
            if(p.getPrice().equals(price)){
                priceAvailable.add(p);
            }
        }
        return priceAvailable;
    }


    public RentAHome() { available = RentAHomeMain.available;}


    public void updateId() {
        int maxId = 0;
        for(Property p: available){
            if(p.getId()>maxId){
                maxId = p.getId();
            }

        }
        Property.setPropertyId(maxId+1);
    }

    public void updateUserId() {
        int maxId = 0;
        for(Map.Entry<String, User> entry : RentAHomeMain.users.entrySet()){

            if(entry.getValue().getUserId()>maxId){
                maxId = entry.getValue().getUserId();
            }

        }
        User.setAgentId(maxId);
    }



    //Xstream save and load
    @SuppressWarnings("unchecked")
    public void loadProperties() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        xstream.addPermission(AnyTypePermission.ANY);
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("properties.xml"));
        available = (ArrayList<Property>) is.readObject();
        is.close();

        updateId();
    }

    public void saveProperties() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("properties.xml"));
        out.writeObject(available);
        out.close();
    }


    //Xstream save and load
    @SuppressWarnings("unchecked")
    public void loadUsers() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        xstream.addPermission(AnyTypePermission.ANY);
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("users.xml"));
        RentAHomeMain.users = (HashMap<String,User>) is.readObject();
        is.close();

        updateUserId();
    }

    public void saveUsers() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("users.xml"));
        out.writeObject(RentAHomeMain.users);
        out.close();
    }




    public int addProperty(String description, String address, String category, String cnty, String twn,
                           String BER, String eircode, String price, String propertyAgent, String imageName)    {

        Property property = new Property(description,address,category, cnty, twn, BER, eircode, price, propertyAgent, imageName);



        if(available.add(property)){
            try {
                saveProperties();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Property.getPropertyId() - 1;
        }

        return -1;
    }

    //List all agents from the HashMap
    public String listAllAgents(){
        String allAgents = "All Agents:\n";
        for(Map.Entry<String, User> entry : RentAHomeMain.users.entrySet()){
            allAgents += entry.getKey() + " : " + entry.getValue().toString() + "\n";
        }
        return allAgents;
    }

    public String listAllProperties(){
        String allProperties = "All Properties\n";
        for(Property p:RentAHomeMain.getRentAHome().getAvailable()){
            allProperties += "\nID: " + p.getId()+": " + "Description: " + p.getDescription() + "\nPrice range: " + p.getPrice() + "\nProperty Agent: " + p.getPropertyAgent() + "\n";
        }
        return allProperties;
    }






    public String listAllPropertiesSpecific(String county, String town, String category, String price){
        String allProperties ="Available Properties by search:\n";


        for(Property p:available) {
            if (p.getCounty().equals(county) && p.getTown().equals(town)
                    && p.getCategory().equals(category)
                    && p.getPrice().equals(price))
            {

                allProperties += p;

            }
            else {
                allProperties = "Nothing Available";
            }

        }

        return allProperties;
    }

    public Property getPropertyDetailsById(int id) {
        for (Property p : RentAHomeMain.getRentAHome().getAvailable()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean editProperty( int id, String description, String address, String BER, String eircode, String propertyAgent,
                                String cnty, String twn, String category, String price, String imageName) {

        for (int i = 0; i < RentAHomeMain.getRentAHome().getAvailable().size(); i++) {
            Property p = RentAHomeMain.getRentAHome().getAvailable().get(i);
            if (p.getId() == id) {
                p.setDescription(description);
                p.setAddress(address);
                p.setCategory(category);
                p.setCounty(cnty);
                p.setTown(twn);
                p.setBER(BER);
                p.setEircode(eircode);
                p.setPrice(price);
                p.setPropertyAgent(propertyAgent);
                p.setImageName(imageName);

                return true;
            }
        }
        return false;  //No such id found

    }


    public boolean deleteProductByID(int id){
        for(int i=0; i< RentAHomeMain.getRentAHome().getAvailable().size(); i++){
            Property p = RentAHomeMain.getRentAHome().getAvailable().get(i);
            if(p.getId() == id){ //those this product match the id passed to the method?
                RentAHomeMain.getRentAHome().getAvailable().remove(i);

                return true;


            }
        }
        return false;  //No such id found

    }

    public void deleteAgent(String email) {
        RentAHomeMain.users.remove(email);
    }




}
