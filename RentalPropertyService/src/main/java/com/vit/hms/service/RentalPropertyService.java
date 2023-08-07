package com.vit.hms.service;

import java.util.*;
import com.mongodb.client.*;
import com.vit.hms.util.*;
import com.vit.hms.bean.RentalPropertyBean;
import com.vit.hms.dao.RentalPropertyDAO;
import org.bson.Document;
import org.bson.types.ObjectId;

public class RentalPropertyService {
    public static void main(String[] args){
        MongoClient mongoClient  = DBUtil.getDBConnection();
        MongoDatabase db = mongoClient.getDatabase("RentalProperty");
        MongoCollection<Document> rental = db.getCollection("RENTAL_TBL");
/*
        RentalPropertyBean b = new RentalPropertyBean();
        b.setRentalAmount(15000);
        b.setNoOfBedRooms(2);
        b.setLocation("HSR");
        b.setCity("Bengaluru");
        RentalPropertyService rps = new RentalPropertyService();
        System.out.println(rps.addRentalProperty(b));
 */
        FindIterable<Document> iterable = rental.find();
        MongoCursor<Document> cursor = iterable.iterator();
        System.out.println("rental: ");
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
        String result=new RentalPropertyService().fetchRentalProperty(15000,25000,2,"AnnaNagar","Chennai");
        System.out.println(result);
    }
    public String addRentalProperty(RentalPropertyBean bean){
        if (bean.getCity() == null || bean.getLocation() == null) {
            return "NULL VALUES IN INPUT";
        }
        if (bean.getCity().length() == 0 || bean.getLocation().length() == 0 || bean.getNoOfBedRooms() == 0
                || bean.getRentalAmount() == 0) {
            return "INVALID INPUT";
        }
        try {
            validateCity(bean.getCity());
        } catch (InvalidCityException e) {
            return "INVALID CITY";
        }
        RentalPropertyDAO dao = new RentalPropertyDAO();
        int count = dao.createRentalProperty(bean);
        if(count>0){
            MongoCollection<Document> rental = DBUtil.getDBConnection().getDatabase("RentalProperty").getCollection("RENTAL_TBL");
            rental.insertOne(new Document("_id", new ObjectId()).append("PROPERTYID", bean.getPropertyId())
                    .append("RENTALAMOUNT", bean.getRentalAmount())
                    .append("NOOFBEDROOMS", bean.getNoOfBedRooms())
                    .append("LOCATION", bean.getLocation())
                    .append("CITY", bean.getCity()));
        }
        return (count > 0) ? "SUCCESS" : "FAILURE";
    }
    public List<RentalPropertyBean> getPropertyByCriteria(int minRentalAmount,int maxRentalAmount,int noOfBedRooms,String location,String city){
        RentalPropertyBean bean = new RentalPropertyBean();
        bean.setRentalAmount(minRentalAmount);
        bean.setNoOfBedRooms(noOfBedRooms);
        bean.setLocation(location);
        bean.setCity(city);
        RentalPropertyDAO dao = new RentalPropertyDAO();
        List<RentalPropertyBean> propertyList = dao.findPropertyByCriteria(minRentalAmount, maxRentalAmount, bean);
        return propertyList;
    }
    public String fetchRentalProperty(int minRentalAmount,int maxRentalAmount,int noOfBedRooms,String location,String city){
        if (minRentalAmount == 0 || maxRentalAmount == 0 || maxRentalAmount < minRentalAmount
                || noOfBedRooms <= 0 || location == null || city == null
                || location.length() == 0 || city.length() == 0) {
            return "INVALID VALUES";
        }
        try {
            validateCity(city);
        } catch (InvalidCityException e) {
            return "INVALID CITY";
        }
        List<RentalPropertyBean> propertyList = getPropertyByCriteria(minRentalAmount, maxRentalAmount, noOfBedRooms, location, city);

        if (propertyList.isEmpty()) {
            return "NO MATCHING RECORDS";
        } else {
            return "RECORDS AVAILABLE:" + propertyList.size();
        }
    }
    public void validateCity(String city) throws InvalidCityException{
        if (!"Chennai".equalsIgnoreCase(city) && !"Bengaluru".equalsIgnoreCase(city)) {
            throw new InvalidCityException("Invalid City Name");
        }
    }
}