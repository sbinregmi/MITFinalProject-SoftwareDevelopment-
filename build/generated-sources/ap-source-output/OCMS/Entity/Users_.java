package OCMS.Entity;

import OCMS.Entity.Paper;
import OCMS.Entity.SessionParticipant;
import OCMS.Entity.UserTags;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-06T14:42:29")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile SingularAttribute<Users, String> role;
    public static volatile SingularAttribute<Users, String> address;
    public static volatile SingularAttribute<Users, String> timeZone;
    public static volatile SingularAttribute<Users, Date> dateOfBirth;
    public static volatile ListAttribute<Users, Paper> paperList;
    public static volatile SingularAttribute<Users, Date> updatedDate;
    public static volatile SingularAttribute<Users, Boolean> isActive;
    public static volatile SingularAttribute<Users, String> userName;
    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, String> qualification;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> phoneNumber;
    public static volatile SingularAttribute<Users, Date> createdDate;
    public static volatile SingularAttribute<Users, Boolean> isPublic;
    public static volatile ListAttribute<Users, UserTags> userTags;
    public static volatile SingularAttribute<Users, Long> id;
    public static volatile SingularAttribute<Users, Boolean> isApproved;
    public static volatile ListAttribute<Users, SessionParticipant> sessionParticipantList;
    public static volatile SingularAttribute<Users, String> email;

}