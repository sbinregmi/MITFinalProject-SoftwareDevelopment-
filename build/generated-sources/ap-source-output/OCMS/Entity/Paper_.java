package OCMS.Entity;

import OCMS.Entity.PaperTags;
import OCMS.Entity.Users;
import OCMS.ModelData.Enum.PaperTopic;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-06T14:42:29")
@StaticMetamodel(Paper.class)
public class Paper_ { 

    public static volatile SingularAttribute<Paper, String> paperUrl;
    public static volatile ListAttribute<Paper, PaperTags> paperTags;
    public static volatile SingularAttribute<Paper, Date> createdDate;
    public static volatile SingularAttribute<Paper, String> paperAbstract;
    public static volatile SingularAttribute<Paper, PaperTopic> topic;
    public static volatile SingularAttribute<Paper, String> publisher;
    public static volatile SingularAttribute<Paper, Date> updatedDate;
    public static volatile SingularAttribute<Paper, Users> authorId;
    public static volatile SingularAttribute<Paper, String> paperId;
    public static volatile SingularAttribute<Paper, String> paperTitle;
    public static volatile SingularAttribute<Paper, String> tags;

}