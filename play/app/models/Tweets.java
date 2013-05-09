package models;
import java.util.*;

import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.node.ObjectNode;

@Entity
public class Tweets extends Model {

    @Constraints.Required
    @Id
    public Integer tweetsId;

    @JsonIgnore
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="business_id")
    public BusinessRating businessId;

    @Constraints.Required
    public Integer numTweets;

    @Constraints.Required
    public Integer followerCount;

    @Formats.DateTime(pattern="MM/dd/yyyy")
    public Date timestamp = new Date();

    public Tweets( Integer numTweets, Date timestamp, Integer followerCount, BusinessRating businessId) {

        this.businessId    = businessId;
        this.numTweets     = numTweets;
        this.timestamp     = timestamp;
        this.followerCount = followerCount;
    }

    public static Finder<String, Tweets> find =
        new Finder<String, Tweets>(String.class, Tweets.class);

    public static List<Tweets> findTweetsForBiz(String businessId){
        return find.where().eq("business_id", businessId).findList();
    }

    public void toObjectNode(ObjectNode node){
        node.put("tweetsId", tweetsId);
        node.put("numTweets", numTweets);
        node.put("followerCount", followerCount);
        node.put("timestamp", String.format("%1$tm/%1$te/%1$tY", timestamp));
    }
}
