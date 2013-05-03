package models;
import java.util.*;

import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.node.ObjectNode;

@Entity
public class YelpReview extends Model {

    @Constraints.Required
    @Id
    public Integer reviewId;
    @JsonIgnore
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="business_id")
    public BusinessRating businessId;
    @Constraints.Required
    public Float rating;

    public String excerpt;
    @Constraints.Required
    public String user_name;
    @Constraints.Required
    @JsonIgnore
    public String user_id;
    @Constraints.Required
    public Date timeCreated;

    public YelpReview( Integer reviewId, Float rating, String excerpt,
            String user_name, String user_id, Date timeCreated,
            BusinessRating businessId ){
        this.reviewId  = reviewId;
        this.rating    = rating;
        this.excerpt   = excerpt;
        this.user_name = user_name;
        this.user_id   = user_id;
        this.timeCreated= timeCreated;
        this.businessId = businessId;
    }

    public static Finder<String, YelpReview > find =
        new Finder<String, YelpReview> (String.class, YelpReview.class);

    public static List<YelpReview> findReviewsForBiz(String businessId){
        return find.where().eq("business_id", businessId).findList();
    }

    public void toObjectNode(ObjectNode node){
        node.put("reviewId", reviewId);
        node.put("rating", rating);
        node.put("user_name", user_name );
        node.put("time_created", String.format("%1$tm/%1$te/%1$tY", timeCreated));
        node.put("excerpt", excerpt);
    }

}
