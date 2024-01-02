FROM openjdk:17-jdk-alpine@sha256:a996cdcc040704ec6badaf5fecf1e144c096e00231a29188596c784bcf858d05
RUN apk update && apk upgrade && apk add bash
WORKDIR /home/ERideRent_Booking_Management
ADD target/E-RideRent-Booking-Management.jar /home/ERideRent_Booking_Management/
CMD java -jar /home/ERideRent_Booking_Management/E-RideRent-Booking-Management.jar