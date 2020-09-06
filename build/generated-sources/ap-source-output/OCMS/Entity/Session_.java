package OCMS.Entity;

import OCMS.Entity.SessionParticipant;
import java.util.Date;
import java.util.TimeZone;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-06T14:42:28")
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
    public static volatile SingularAttribute<Session, Date> sessionDateTime;
    public static volatile SingularAttribute<Session, Boolean> isSeatAvailable;
    public static volatile ListAttribute<Session, SessionParticipant> sessionIds;

}