# Booking management service for E-RideRent application
RESTful web service for bookings management.

## Used technologies:
* Java 17
* MySQL
* Spring Boot
* Hibernate
* Spring Data
* JUnit 5 + Mockito
* Maven
* Docker

## Set up a database using Docker for local development
<p>
This part assumes that you have Docker engine already installed and running.
To set up a database, use terminal and navigate to main directory of the project. If opened in IntelliJ, you can use "Terminal"
card there.
</p>
<p>
Run following command:
</p>

````
docker run --name=BookingManagementServiceDB -p 3313:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=1 -e MYSQL_DATABASE=booking_management_service_db -d mysql:8.2
````

## Deploy locally
<p>
To deploy application and database locally using Docker, run following commands in root of the project:

````
mvn clean -DskipTests=true package -f pom.xml
````
````
docker compose up -d
````
</p>