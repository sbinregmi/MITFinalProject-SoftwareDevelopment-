/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  SabinRegmi
 * Created: Sep 10, 2020
 */

Use [OCMSDB]
GO
INSERT INTO [dbo].[USERS]
           ([ADDRESS],[COUNTRY],[CREATEDDATE],[EMAIL],[FIRSTNAME],[ISACTIVE],[ISAPPROVED],[ISPUBLIC],[LASTNAME]
           ,[PASSWORD],[PHONENUMBER],[QUALIFICATION],[ROLE],[TIMEZONE],[UPDATEDDATE],[USERNAME])
     VALUES
           ("Melbourne","Australia",2020-02-02,"sabin@gmail.com",1,1,1,"Regmi","sbinregmi1","98545555","MIT From CQU",
            "Author","GMT+10",null,"sbinregmi");
GO