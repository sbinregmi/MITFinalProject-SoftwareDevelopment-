package OCMS.Entity;

import OCMS.Entity.Paper;
import OCMS.Entity.Session;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-06T14:42:29")
@StaticMetamodel(SessionPaper.class)
public class SessionPaper_ { 

    public static volatile SingularAttribute<SessionPaper, Long> id;
    public static volatile SingularAttribute<SessionPaper, Session> sessionId;
    public static volatile SingularAttribute<SessionPaper, Paper> paperId;

}