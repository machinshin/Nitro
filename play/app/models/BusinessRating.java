package models;
import java.util.*;

import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import org.codehaus.jackson.node.ObjectNode;

@Entity
public class BusinessRating extends Model {

    @Constraints.Required
    @Id
    public String businessId;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public Float avgRating;

    @Constraints.Required
    public Integer numReviews;

    @Constraints.Required
    public Float latitude;

    @Constraints.Required
    public Float longitude;

    @Formats.DateTime(pattern="MM/dd/yyyy")
    public Date timestamp = new Date();

    public BusinessRating( String businessId, String name, Float avgRating,
            Integer numReviews, Date Timestamp, Float latitude, Float longitude ) {

        this.businessId = businessId;
        this.name       = name;
        this.avgRating  = avgRating;
        this.numReviews = numReviews;
        this.timestamp  = timestamp;
        this.latitude   = latitude;
        this.longitude  = longitude;
    }

    public static Finder<String, BusinessRating> find =
        new Finder<String, BusinessRating>(String.class, BusinessRating.class);

    public void toObjectNode(ObjectNode node){
        node.put("businessId", businessId);
        node.put("name", name);
        node.put("avgRating", avgRating);
        node.put("numReviews", numReviews);
        node.put("latitude", latitude);
        node.put("longitude", longitude);
        node.put("timestamp", String.format("%1$tm/%1$te/%1$tY", timestamp));
    }
}
