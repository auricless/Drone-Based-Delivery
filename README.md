# Drone-based-Delivery

## Description

A sample test project for Drone-based-Delivery.

“The Drone” is a new technology company which is working in the area of drone-based
delivery for urgent package delivery in locations with difficult access. The company has a fleet
of 10 drones. A drone is capable of delivering small load (payload) additionally to its navigation
and cameras.

For this use case, the load is medications.

## Requirements

- Java 8 or higher
- Maven

## Build Instructions

Build the project

```mvn clean install```

Run the application:

```mvn spring-boot:run```

Or run the JAR file:

```java -jar target/Drone-Based-Delivery-0.0.1-SNAPSHOT```

## Test Instructions

Run the tests

```mvn test```

## Documentation API (Postman Collection)

1. Download Postman: If you haven’t already, download and install Postman.

2. Import the Collection:
- Open Postman.
- Click on the "Import" button in the top left corner.
- Choose the Postman Collection file ```Drone-Based-Delivery\Drone API.postman_collection``` and click "Open".

3. Explore the Collection:
- Once imported, you’ll see the collection in the left sidebar.
- Click on the collection name to view and run the various API requests.

4. Run Requests:
- Select any request from the collection to see details and execute it.
- Adjust request parameters or bodies as needed to test different scenarios.
