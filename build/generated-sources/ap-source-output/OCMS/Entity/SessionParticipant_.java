package OCMS.Entity;

import OCMS.Entity.Session;
import OCMS.Entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-08-31T19:32:05")
@StaticMetamodel(SessionParticipant.class)
public class SessionParticipant_ { 

    public static volatile SingularAttribute<SessionParticipant, Users> participantId;
    public static volatile SingularAttribute<SessionParticipant, Long> id;
    public static volatile SingularAttribute<SessionParticipant, Session> sessionId;

}