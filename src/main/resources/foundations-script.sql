drop view if exists myview;
drop table if exists ERS_REIMBURSEMENTS;
drop table if exists ERS_USERS;
drop table if exists ERS_REIMBURSEMENT_TYPES;
drop table if exists ERS_REIMBURSEMENT_STATUSES;
drop table if exists ERS_USER_ROLES;

CREATE TABLE ERS_USER_ROLES
(
	ROLE_ID VARCHAR,
	ROLE VARCHAR UNIQUE,
	
	CONSTRAINT ROLE_ID_PK
	PRIMARY KEY (ROLE_ID)
);

CREATE TABLE ERS_REIMBURSEMENT_STATUSES
( 
	STATUS_ID VARCHAR,
	STATUS VARCHAR UNIQUE,
	
	CONSTRAINT STATUS_ID_PK
	PRIMARY KEY (STATUS_ID)
);

CREATE TABLE ERS_REIMBURSEMENT_TYPES
(
	TYPE_ID VARCHAR,
	TYPE VARCHAR UNIQUE,
	
	CONSTRAINT TYPE_ID_PK
	PRIMARY KEY (TYPE_ID)
);

CREATE TABLE ERS_USERS
(
	USER_ID VARCHAR,
	USERNAME VARCHAR UNIQUE NOT NULL,
	EMAIL VARCHAR UNIQUE NOT NULL,
	PASSWORD VARCHAR NOT NULL,
	GIVEN_NAME VARCHAR NOT NULL,
	SURNAME VARCHAR NOT NULL,
	IS_ACTIVE BOOLEAN,
	ROLE_ID VARCHAR,
	
	FOREIGN KEY(ROLE_ID)
	REFERENCES ERS_USER_ROLES(ROLE_ID),
	
	constraint USER_ID_PK
	primary key (USER_ID)
);


CREATE TABLE ERS_REIMBURSEMENTS
(	
	REIMB_ID VARCHAR,
	AMOUNT NUMERIC(6, 2) NOT NULL,
	SUBMITTED TIMESTAMP NOT NULL,
	RESOLVED TIMESTAMP,
	DESCRIPTION VARCHAR NOT NULL,
	RECEIPT BYTEA,
	PAYMENT_ID VARCHAR,
	AUTHOR_ID VARCHAR NOT NULL,
	RESOLVER_ID VARCHAR,
	STATUS_ID VARCHAR NOT NULL,
	TYPE_ID VARCHAR not null,

	FOREIGN KEY (AUTHOR_ID)
	REFERENCES ERS_USERS (USER_ID),
	
	FOREIGN KEY (RESOLVER_ID)
	REFERENCES ERS_USERS (USER_ID),
	
	FOREIGN KEY (STATUS_ID)
	REFERENCES ERS_REIMBURSEMENT_STATUSES (STATUS_ID),
	
	FOREIGN KEY (TYPE_ID)
	REFERENCES ERS_REIMBURSEMENT_TYPES (TYPE_ID)
);


insert into ers_user_roles values ('1', 'ADMIN');
insert into ers_user_roles values ('2', 'USER');
insert into ers_user_roles values ('3', 'MANAGER');

insert into ers_reimbursement_statuses values ('1', 'PENDING');
insert into ers_reimbursement_statuses values ('2', 'APPROVED');
insert into ers_reimbursement_statuses values ('3', 'DENIED');

insert into ers_reimbursement_types values ('1', 'LODGING');
insert into ers_reimbursement_types values ('2', 'TRAVEL');
insert into ers_reimbursement_types values ('3', 'FOOD');
insert into ers_reimbursement_types values ('4', 'Other');


insert into ERS_USERS values ('1' , 'iamwolverine', 'wolverine@gmail.com', 'p4$$Word', 'Hugh', 'Jackman', true, '1');
update ers_users set password = '$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC' where username = 'iamwolverine';

insert into ERS_USERS values ('2', 'jimothyson', 'jimbo533@gmail.com', '$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC',
'Jimmy', 'Timothyson', true, '2');

insert into ERS_USERS values ('3' , 'thefriendlyghost', 'TheRealCasper@gmail.com', 
'$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC', 'Casper', 'Ghost', true, '3');

insert into ERS_USERS values ('4' , 'deleteable', 'SomeRealEmail@gmail.com', 
'$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC', 'Gregoreo', 'Sundae', true, '3');

insert into ERS_USERS values ('5' , 'MegaMind', 'Brainiac879@gmail.com', 
'$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC', 'Brain', 'MegaHead', true, '2');

insert into ERS_USERS values ('6' , 'LivinLikeLarry', 'BubbleBuddy@gmail.com', 
'$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC', 'Larry', 'Lobster', true, '1');

insert into ERS_USERS values ('7' , 'TheHero213', 'Zero0000@gmail.com', 
'$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC', 'Zero', 'Smith', false, '2');







insert into ERS_REIMBURSEMENTS  values ('Caspars new haunted house!!!!', '654.22', '2022-03-04 04:33:43.000', 
null, 'A very noticeable scam', null, null, '3', null, '1', '2');

insert into ERS_REIMBURSEMENTS values ('Larrys gym membership' , '744.00', '2022-03-04 04:33:43.000',
null, 'Walked a dog in a park', null, null, '6', null, '1', '2');

insert into ERS_REIMBURSEMENTS values ('!!!VERYNOTICEABLEID!!!' , '123.45', '2022-03-04 04:33:43.000', 
null, 'The weekend is here!', null, null, '2', null, '1', '1');

insert into ERS_REIMBURSEMENTS values ('KKVERYNOTICEABLEIDKK' , '987.40', '2022-03-04 04:33:43.000', 
null, 'Worked hard, ate a muffin', null, null, '2', null, '1', '2');

insert into ERS_REIMBURSEMENTS values ('VERYNOTICEABLEID' , '654.25', '2022-03-04 04:33:43.000', 
null, 'I have not slept since Saturday reimbursement', null, null, '2', null, '1', '3');

insert into ERS_REIMBURSEMENTS values ('Enthuware' , '8.99', '2022-03-04 04:33:43.000', 
null, '1st Certification of the year', null, null, '2', null, '1', '4');

insert into ERS_REIMBURSEMENTS values ('Weekend Shenanigans' , '42.54', '2022-03-04 04:33:43.000', 
'2022-03-04 04:33:43.000', 'Adopted a pet duck', null, null, '2', '3', '3', '3');

insert into ERS_REIMBURSEMENTS values ('Relocation Fees' , '500.00', '2022-03-04 04:33:43.000', 
'2022-03-04 04:33:43.000', 'Relocation Reimbursement', null, null, '2', '3', '2', '4');



select * from ers_users eu ;
select * from ers_user_roles;
select * from ers_reimbursement_types ert ;
select * from ers_reimbursement_statuses ers;
select * from ers_reimbursements er ;

select * from 
ers_reimbursements er 
JOIN ers_reimbursement_statuses ers
            ON er.status_id  = er.status_id 
JOIN ers_reimbursement_types ert
            ON ert.type_id  = er.type_id;  
 SELECT rmb.REIMB_ID, rmb.AMOUNT, rmb.SUBMITTED, rmb.RESOLVED, 
 rmb.DESCRIPTION, rmb.RECEIPT, rmb.PAYMENT_ID, rmb.AUTHOR_ID, rmb.RESOLVER_ID, 
 rmb.STATUS_ID, rmb.TYPE_ID, rmbs.STATUS, rmbt.TYPE FROM ERS_REIMBURSEMENTS rmb 
 JOIN ERS_REIMBURSEMENT_STATUSES rmbs ON rmb.STATUS_ID = rmbs.STATUS_ID 
 JOIN ERS_REIMBURSEMENT_TYPES rmbt ON rmb.TYPE_ID = rmbt.TYPE_ID;
           
--delete from ers_users where user_id = '474dfa4e-0eaf-4684-acc6-2146bcc91b20';
--update ERS_USERS set given_name = 'Guy', surname = 'Dood', email = 'myguydood3@gmail.com', username = 'HandsomeDevil3', password = 'p4SSWORD', is_active = 'FALSE', role_id = '2' where user_id = '95fc4077-6c36-4bdb-b775-6bec44d03c4b';
--delete from ers_users where username = 'Hugh';
--delete from ers_user_roles where role_id = '1';


SELECT US.USER_ID, US.USERNAME, US.EMAIL, US.PASSWORD, US.GIVEN_NAME, 
			US.SURNAME, US.IS_ACTIVE, US.ROLE_ID, URS.ROLE
            FROM ERS_USERS US
            JOIN ERS_USER_ROLES URS
            ON US.ROLE_ID = URS.ROLE_ID;      