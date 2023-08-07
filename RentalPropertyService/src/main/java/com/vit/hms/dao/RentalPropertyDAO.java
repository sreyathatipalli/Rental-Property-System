package com.vit.hms.dao;
import java.util.stream.Collectors;
import java.util.*;
import com.mongodb.client.*;
import com.vit.hms.bean.RentalPropertyBean;
import com.vit.hms.util.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class RentalPropertyDAO {
    private final List<RentalPropertyBean> rentalProperties;

    public RentalPropertyDAO() {
        this.rentalProperties = new ArrayList<>();
    }

    public String generatePropertyID(String city) {
        String cityCode = city.substring(0, 3).toUpperCase();
        int randNum = new Random().nextInt(9000) + 1000;
        return cityCode + randNum;
    }

    public int createRentalProperty(RentalPropertyBean bean) {
        String propertyId = generatePropertyID(bean.getCity());
        bean.setPropertyId(propertyId);
        rentalProperties.add(bean);
        return 1;
    }

    public List<RentalPropertyBean> findPropertyByCriteria(int minRentalAmount, int maxRentalAmount, RentalPropertyBean bean) {
        MongoCollection<Document> rental = DBUtil.getDBConnection().getDatabase("RentalProperty").getCollection("RENTAL_TBL");

        List<Document> f = rental.find(and(
                gte("RENTALAMOUNT", minRentalAmount),
                lte("RENTALAMOUNT", maxRentalAmount),
                eq("NOOFBEDROOMS", bean.getNoOfBedRooms()),
                eq("LOCATION", bean.getLocation()),
                eq("CITY", bean.getCity())
        )).into(new ArrayList<>());
        return f.stream().map(doc -> {
            RentalPropertyBean b = new RentalPropertyBean();
            b.setCity(doc.getString("CITY"));
            b.setLocation(doc.getString("LOCATION"));
            b.setNoOfBedRooms(doc.getInteger("NOOFBEDROOMS"));
            b.setPropertyId(doc.getString("PROPERTYID"));
            b.setRentalAmount(doc.getInteger("RENTALAMOUNT"));
            return b;
        }).collect(Collectors.toList());
    }
}