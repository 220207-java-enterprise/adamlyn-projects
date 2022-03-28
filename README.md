# Expense Reimbursement System

## Project Description

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used

* Java JDBC with PostgreSQL - version 42.3.3
* Javax Servlet API - version 4.0.1
* Jackson Core - version 4.0.0
* JSON Web Token - version 0.9.1
* Bcrypt - version 4.13.2
* Apache Log4j - version 2.17.2

## Features

* Anyone can register as a User or Manager
* Users can create reimbursements and edit pending reimbursements
* Admins can create reimbursements and edit pending reimbursements, edit, enable and disable User and Manager accounts, and view all accounts in the database
* Managers can create reimbursements, approve and deny other pending reimbursements, and edit pending reimbursements

To-do list:
* Implement Unit testing for both User Service and Reimbursement Service
* Integrate with PRISM service

## Getting Started
   
In gitbash: git clone https://github.com/220207-java-enterprise/adamlyn-projects


## Usage

> Insert Dummy Data into your database, preferably with an admin, example:
insert into ERS_USERS values ('1' , 'iamwolverine', 'wolverine@gmail.com', 'p4$$Word', 'Hugh', 'Jackman', true, '1');
update ers_users set password = '$2a$10$E1sBPVK2fsJifOY2OHHovesyrmrZQunzCn00m5VELk/6n8FtodqHC' where username = 'iamwolverine';

> To run tomcat: In Gitbash, navigate to the project, and type: sh $Catalina_Home/bin/catalina.sh run
> To run the project, in Intellij, type: mvn tomcat7:run 
> To interact with the project, use Postman


## Contributors

> Adam Lyn

## License

This project uses the following license: [<license_name>](<link>).
