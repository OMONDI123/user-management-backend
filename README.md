# User Guide - Running and Testing User Profile API

## 1. Start the Application

Clone the project using the link shared via the email, then follow the following procedure to get it running.





## Linux/Ubuntu Setup

### Step 1: Clone the Project
git clone <repository-link>
cd user-profile-api
Step 2: Restore the Database
Find the .dmp file called test.dmp in the project root directory. This is the database with dummy data. Restore it using the following commands.

2.1 Switch to postgres user
bash
sudo su postgres
Enter password when asked.

2.2 Access PostgreSQL
bash
psql
2.3 Create user and database
sql
CREATE USER testuser WITH PASSWORD 'testuser123';
CREATE DATABASE test WITH OWNER testuser;
Expected output:

text
CREATE ROLE
CREATE DATABASE
2.4 Exit PostgreSQL
sql
\q
2.5 Exit postgres user
bash
exit
2.6 Restore the database
From the project root directory, run:

bash
psql -U testuser -d test -f test.dmp
Enter password testuser123 when prompted.

Step 3: Start the Project
Option A: Using Maven command line
bash
mvn spring-boot:run
Option B: Using Eclipse IDE
Open Eclipse

File → Import → Existing Maven Projects

Browse to project root directory

Click Finish

Right-click project → Run As → Spring Boot App

Option C: Using IntelliJ IDEA
Open IntelliJ

File → Open

Select project root directory

Wait for dependencies to load

Click Run on UserProfileApplication.java

Step 4: Test the Endpoints
Once the project is running, launch Swagger using this URL:

text
http://localhost:8080/test/swagger-ui/index.html



## Windows Setup

Step 1: Clone the Project
Open Command Prompt or PowerShell:

cmd
git clone <repository-link>
cd user-profile-api
Step 2: Restore the Database
Find the .dmp file called test.dmp in the project root directory. This is the database with dummy data.

2.1 Open Command Prompt as Administrator
Press Windows + X and select "Command Prompt (Admin)" or "Windows Terminal (Admin)".

2.2 Access PostgreSQL
cmd
psql -U postgres
Enter your PostgreSQL admin password when prompted.

2.3 Create user and database
sql
CREATE USER testuser WITH PASSWORD 'testuser123';
CREATE DATABASE test WITH OWNER testuser;
Expected output:

text
CREATE ROLE
CREATE DATABASE
2.4 Exit PostgreSQL
sql
\q
2.5 Restore the database
From the project root directory, run:

cmd
psql -U testuser -d test -f test.dmp
Enter password testuser123 when prompted.

Step 3: Start the Project
Option A: Using Maven command line
cmd
mvn spring-boot:run
Option B: Using Eclipse IDE
Open Eclipse

File → Import → Existing Maven Projects

Browse to project root directory

Click Finish

Right-click project → Run As → Spring Boot App

Option C: Using IntelliJ IDEA
Open IntelliJ

File → Open

Select project root directory

Wait for dependencies to load

Click Run on UserProfileApplication.java

Step 4: Test the Endpoints
Once the project is running, launch Swagger using this URL:

text
http://localhost:8080/test/swagger-ui/index.html
Troubleshooting
Database Connection Issues
Error: Database "test" does not exist

Solution: Create the database again

bash
psql -U postgres -c "CREATE DATABASE test WITH OWNER testuser;"
Error: User "testuser" does not exist

Solution: Create the user again

bash
psql -U postgres -c "CREATE USER testuser WITH PASSWORD 'testuser123';"
Error: Permission denied

Solution: Grant privileges

bash
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE test TO testuser;"
Port Already in Use
Error: Port 8080 already in use

## Windows Setup:

bash
sudo lsof -i :8080
sudo kill -9 <PID>
Windows:

cmd
netstat -ano | findstr :8080
taskkill /PID <PID> /F
Maven Build Issues
Error: Cannot resolve dependencies

bash
mvn clean install -U
Application Fails to Start
Check the application logs for specific error messages. Common causes:

PostgreSQL is not running

Database credentials mismatch

Port 8080 is already occupied

Verification Checklist
Database restored successfully

Application starts without errors

Swagger UI loads in browser

Can view all API endpoints in Swagger

Can execute GET requests from Swagger

Can execute POST/PUT/DELETE requests from Swagger

Stopping the Application
## Windows Setup
Press Ctrl + C in the terminal where the application is running.

## Windows Setup
Press Ctrl + C in the command prompt where the application is running.

Support
If you encounter any issues:

Check that PostgreSQL is running

Verify database credentials match

Ensure you are using Java 21

Check the console logs for error messages