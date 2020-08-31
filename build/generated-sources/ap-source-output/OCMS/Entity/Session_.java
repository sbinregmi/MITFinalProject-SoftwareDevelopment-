package OCMS.Entity;

import OCMS.Entity.SessionParticipant;
import java.sql.Date;
import java.sql.Time;
import java.util.TimeZone;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-31T19:32:05")
@StaticMetamodel(Session.class)
public class Session_ { 

    public static volatile SingularAttribute<Session, String> country;
    public static volatile SingularAttribute<Session, Date> createdDate;
    public static volatile SingularAttribute<Session, String> sessionName;
    public static volatile SingularAttribute<Session, String> organization;
    public static volatile SingularAttribute<Session, Integer> maximumParticipant;
    public static volatile SingularAttribute<Session, TimeZone> timeZone;
    public static volatile SingularAttribute<Session, Long> sessionId;
    public static volatile SingularAttribute<Session, Date> updatedDate;
    public static volatile SingularAttribute<Session, Time> sessionDateTime;
    public static volatile SingularAttribute<Session, Boolean> isSeatAvailable;
    public static volatile SingularAttribute<Session, String> tags;
    public static volatile ListAttribute<Session, SessionParticipant> sessionIds;

}