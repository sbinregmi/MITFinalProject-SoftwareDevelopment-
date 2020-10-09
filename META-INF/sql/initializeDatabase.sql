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
           ,[PASSWORD],[PHONENUMBER],[AFFILIATION],[ROLE],[TIMEZONE],[UPDATEDDATE],[USERNAME])
     VALUES
           ('Melbourne 1','Australia','2020-02-02','sabin@gmail.com','Sabin',1,1,1,'Regmi','sabin123','98545555','MIT From CQU',
            'Author','GMT+10',null,'sbinregmi'),
           ('ABc address','Australia','2020-02-02','shorif@gmail.com','Md',1,1,1,'Shorif','shorif123','98545555','MIT From CQU',
            'Participant','GMT+10',null,'shorif'),
			('ABc address','Australia','2020-02-02','participant@gmail.com','Md',1,1,1,'Shorif','participant123','98545555','MIT From CQU',
            'Participant','GMT+10',null,'participant'),
           ('ABc address','Australia','2020-02-02','prashant@gmail.com','Prashant',1,1,1,'Gaurav','prashant123','98545555','MIT From CQU',
            'Organizer','GMT+10',null,'prashant'),
           ('ABc address','Australia','2020-02-02','admin@gmail.com','Admin',1,1,1,'Admin','admin123','98545555','MIT From CQU',
            'Admin','GMT+10',null,'admin'),
			('ABc address','Australia','2020-02-02','author@gmail.com','Admin',1,1,1,'Author','author123','98545555','MIT From CQU',
            'Author','GMT+10',null,'author')
			
GO


INSERT INTO [dbo].[PAPER]
           ([CREATEDDATE],[PAPERABSTRACT],[PAPERTITLE],[PAPERURL],[PUBLISHER],[UPDATEDDATE]
           ,[AUTHORID_ID])
     VALUES
           ('2020-02-02', 'This is abstract','TItle 1','','Paper publisher',null,1),
           ('2020-02-02', 'This is abstract 2','TItle 2','','Paper publisher 2',null,1),
           ('2020-02-02', 'This is abstract 3','TItle 3','','Paper publisher 3',null,1)
GO

INSERT INTO [dbo].[TAGS]
			([DESCRIPTION],[NAME],[URL])
     VALUES
           ('This is tag description','AI',''),
           ('This is tag description','Block Chain',''),
           ('This is tag description','IoT',''),
           ('This is tag description','Python Programming','')
GO
INSERT INTO [dbo].[USERTAGS]
           ([USERID_ID],[USERTAGID_ID])
     VALUES
           (1,1),
		   (1,2),
		   (2,2),
		   (2,4),
		   (3,2),
		   (4,2),
		   (4,4)
GO

INSERT INTO [dbo].[PAPERTAGS]
           ([PAPERID_PAPERID],[TAGID_ID])
     VALUES
           (1,2),
		   (1,4),
		   (2,3),
		   (3,1)
GO
INSERT INTO [dbo].[SESSION]
           ([CREATEDDATE]
		   ,[IMAGEURL]
		   ,[DESCRIPTION]
           ,[SESSIONDATETIME]
           ,[SESSIONNAME]
           ,[TIMEZONE]
           ,[UPDATEDDATE])
     VALUES
           ('2020-09-08'
           ,''
           ,'This is description'
           ,'2020-10-20 23:05:48.907'
           ,'Session 1'
           ,'GMT+10'
           ,null),

		   ('2020-09-08'
           ,''
           ,'This is description'
           ,'2020-10-20 23:05:48.907'
           ,'Session 2'
           ,'GMT+10'
           ,null),
		    ('2020-09-08'
           ,''
           ,'This is description'
           ,'2020-08-20 23:05:48.907'
           ,'Session 3'
           ,'GMT+10'
           ,null)
GO
INSERT INTO [dbo].[SESSIONPAPER]
           (STATUS
           ,PAPERID_PAPERID
           ,SESSIONID_SESSIONID)
		   VALUES('ACCEPTED',
		   2,
		   1)
GO
INSERT INTO [dbo].[SESSIONPARTICIPANT]
           (STATUS
           ,PARTICIPANTID_ID
           ,SESSIONID_SESSIONID)
		   VALUES('ACCEPTED',
		   2,
		   1)
GO
DBCC CHECKIDENT (PAPERTAGS, reseed,6)
GO
DBCC CHECKIDENT (PAPER, reseed,4)
GO
DBCC CHECKIDENT (USERS, reseed,5)
GO
DBCC CHECKIDENT (TAGS, reseed,5)
GO
DBCC CHECKIDENT (TAGS, reseed,9)
GO
DBCC CHECKIDENT (SESSION, reseed,5)
GO
DBCC CHECKIDENT (SESSIONPAPER, reseed,5)
SELECT * FROM SESSION