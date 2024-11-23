<div align="center">
    <h1>DietiDeals</h1>
    <div>
    <img alt="Static Badge" src="https://img.shields.io/badge/Java-17-red">
    <img alt="Static Badge" src="https://img.shields.io/badge/SpringBoot-3.2.2-green">
    <img alt="Static Badge" src="https://img.shields.io/badge/Kotlin-1.9-darkblue">
    <img alt="Static Badge" src="https://img.shields.io/badge/Android-14-brightgreen">
    <img alt="Static Badge" src="https://img.shields.io/badge/JetpackCompose-green">
    </div>
    <img src="/Documents/Logo%20DietiDeals.png" width="25%" alt="DietiDeals">
</div>

DietiDeals is an online auction management platform. The system consists of a performant and reliable mobile application through which users can access the system's functionalities in an intuitive, quick, and enjoyable way.

# Features
- Advanced system for creating auctions of different types
- Possibility to bid on auctions
- Secure authentication system with JWT
- Encrypted traffic with HTTPs
- Access with OAuth2
- Custom notification system for auction winner, participant and creator
- Possibility to upload images for your auctions
- Possibility to customize your profile with a description
- Badge to show to other users if you are a verified insertionist

# Dependencies
This is a list of technologies used for the development of the project, necessary for the correct functioning of the software produced.
## BackEnd
- [Java](https://docs.oracle.com/en/java/javase/17/) [17]
- [SpringBoot](https://docs.spring.io/spring-boot/index.html) [3.2.2]
- [SpringSecurity](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/springsecurity.pdf) [3.2.2]
- [SpringBoot Data JPA](https://docs.spring.io/spring-boot/docs/3.2.4/reference/pdf/spring-boot-reference.pdf) [3.2.2]
- [PosgreSQL Database](https://www.postgresql.org/files/documentation/pdf/14/postgresql-14-A4.pdf) [14.0]
- [Lombok](https://projectlombok.org/) [1.18.30]
- [ModelMapper](https://modelmapper.org/) [3.2.0]
- [AWS Java SDK S3](https://docs.aws.amazon.com/s3/) [1.12.772]
- [AWS Java SDK SNS](https://docs.aws.amazon.com/sns/) [1.12.773]
- [JsonWebToken](https://javadoc.io/doc/io.jsonwebtoken/jjwt-api/latest/index.html) 
- [Jackson](https://javadoc.io/doc/com.fasterxml.jackson.core/jackson-core/latest/index.html)
- [JUnit](https://junit.org/junit5/docs/current/user-guide/) [5.11.3]
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) [5.14.2]
- [Maven](https://maven.apache.org/guides/index.html) [4.0]
- [Docker](https://docs.docker.com/) [24.0.5]

## FrontEnd
- [Kotlin](https://kotlinlang.org/docs/home.html) [1.9]
- [Android 14.0 SDK](https://developer.android.com/develop) [34]
- [Android Studio](https://developer.android.com/studio) [Iguana 2023]
- [Jetpack Compose](https://developer.android.com/develop/ui/compose/documentation)
- Coil
- [Retrofit](https://square.github.io/retrofit/)

# Getting Started

Obviously the Local Installation will lack the image loading and reading functionality, and the platform user notification functionality, furthermore it is not possible to use a mobile device that connects to the endpoint of the application launched locally, unless the port where the application connects locally is made reachable on the internet [The instructions for performing this operation will not be provided in this guide and can be found in other sources if necessary].

1. This is a common step. Clone the repository:
    ```sh
    git clone https://github.com/bbwark/DietiDeals24_15.git
    ```

## Local Installation

1. Install and setup PostgreSQL. Guide available [here](https://www.postgresql.org/docs/current/tutorial-start.html).

1. Navigate to the backend directory:
    ```sh
    cd DietiDeals24_15/back/DietiDeals24_25
    ```

1. Before starting the application you need to generate an SSL certificate (or get one from a trusted CA like Let's Encrypt, if you want to use the application in a production context).
Run the following command to generate it:

    ```sh
    keytool -genkey -alias myapp -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 365
    ```

1. Move the generated certificate into the resources folder:

    ```sh
    mv keystore.p12 src/main/resources
    ```

1. Create the application-secret.properties file

    ```sh
    nano application-secret.properties
    ```

1. Fill in the file with the following fields:

    ```
    server.ssl.key-store=classpath:keystore.p12
    server.ssl.key-store-password= #Password created when SSL was generated
    server.ssl.key-store-type=PKCS12
    server.ssl.key-alias= #Alias used when SSL was generated

    spring.datasource.username= #Username inserted when setup PostgreSQL
    spring.datasource.password= #Password inserted when setup PostgreSQL
    #It's possible to use also the standard credentials which are both 'postgres'
    ```

    Then save the file

1. Move the application-secret.properties file into the resource folder:

    ```sh
    mv application-secret.properties src/main/resources
    ```

1. Install the project:
    ```sh
    mvn clean install
    ```

1. Start the application:
    ```sh
    mvn spring-boot:run
    ```

The application is now reachable at port https://localhost:8181, using the exposed endpoints and the correct self-signed certificate.

## Installation on AWS EC2 Instance
Precise guidance for AWS Console will not be provided as the service is continually being updated and the interface may change over time.

1. Perform steps from 2 to 7 of the local installation guide. 

1. Create an account on the AWS Console, then configure the EC2 instance to your liking. It is important that the instance is accessible, so make sure to make SSH requests accessible only from your IP address, while all other requests need global access to work properly.

1. After creating the EC2 instance create an S3 bucket, access to this bucket can be set as you like but it is important that the URLs can be visible to your users, since Coil (in Front-End) will take care of showing the images by loading them from the S3 URL.  
As a minimum security suggestion, the upload to the bucket must be accessible only from the EC2 instance.

1. The use of the SNS service is necessary for the correct functioning of the notification system.  
At the time of the development of the project it was necessary to obtain permissions from the FireBase console by registering the Android Studio project and then configuring a Topic on SNS.  
Since these services are constantly evolving, it is recommended to do research outside of this guide to complete this step.

1. Once SNS and S3 are configured, you need to create a new IAM role for the EC2 instance that gives the correct permissions to interact with SNS and S3. At the time of the project development, the AmazonS3FullAccess and AmazonSNSFullAccess permissions were provided for development purposes.

1. While still in the `DietiDeals24_15/back/DietiDeals24_25` folder you need to create the Dockerfile:

    ```sh
    nano Dockerfile
    ```

1. The Dockerfile will need to be filled with the following settings:
    ```sh
    FROM openjdk:17-jdk-alpine
    COPY target/*.jar app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```
    The use of the Alpine distribution was chosen due to the lightness of Alpine

1. Return to parent folder (`cd ..`)

1. Compress the folder into a zip file

1. Send the compressed folder to your EC2 instance:

    ```sh
    scp -i path/to/your/private/key/for/EC2/access DietiDeals24_25.zip EC2_distribution@XXX.XXX.XXX.XXX:/path/in/the/EC2Instance
    ```

    Instead of `EC2_distribution` there must be the distribution of the chosen EC2 instance, while instead of `XXX.XXX.XXX.XXX` there must be the public IP address of the instance.

1. Open an SSH Session with your EC2 Instance

1. Navigate to the path where you sent the compressed folder

1. Check your docker version, otherwise you have to install it.

1. After installing docker, you need to create a new network under which multiple containers can communicate, because we will be using the PostgreSQL database in a separate container than the DietiDeals one as a good practice:

    ```sh
    docker network create dietidealsnetwork
    ```

1. Let's download a pre-configured container image with PostgreSQL:

    ```sh
    docker pull postgres:14
    ```
    We are using version 14 as defined in the dependencies, although newer versions may probably work fine as well.

1. Let's run the docker container under the network we created previously:

    ```sh
    docker run --network=dietidealsnetwork --name dietideals_ps_database -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -p 5432:5432 -d postgres
    ```
    Instead of `POSTGRES_USER` and `POSTGRES_PASSWORD` you can insert the username and password you entered in the file created in step 6 of *Local Installation*

1. Enter the container's psql console to create the database that will be used by DietiDeals:

    ```sh
    docker exec -it dietideals_ps_database psql -U postgres
    ```

1. Enter the following command:

    ```sql
    CREATE DATABASE dietidealsdatabase;
    ```
    Then you can quit the psql console with `\q`

1. Starting from the parent folder of DietiDeals24_25.zip, unzip it and then navigate:

    ```sh
    cd DietiDeals24_25
    ```

1. From here, you need to make a small change to the application.properties file, so run the command:

    ```sh
    nano src/main/resources/application.properties
    ```

    Then uncomment the line 7 and comment the line 6. This is because on that line is defined the path to where the database is located, the standard setting is local with the standard PostgreSQL settings, but in this case it is in another docker container.

1. Run the following build command:

    ```sh
    mvn clean install
    ```

    If the tests are blocking you can try to skip them, the application may still mostly work correctly:
    ```sh
    mvn clean install -DskipTests
    ```

1. Now everything is ready, we need to create the docker image to run:

    ```sh
    sudo docker build -t dietideals:latest .
    ```

1. Now go back to the parent folder with `cd ..` and then run the following command:

    ```sh
    nohup docker run --network=dietidealsnetwork --name=dietideals -p8181:8181 dietideals:latest &
    ```

    Alternatively, if you are interested in other more specific Docker features, you can use the following command:

    ```sh
    docker run --network=dietidealsnetwork --name=dietideals -p8181:8181 -d dietideals:latest
    ```

    This will run the container in detached mode instead of simply as a background process.

1. The application setup and its deployment on an EC2 instance is complete, the next step is the installation of the application frontend through Android Studio, this can be done in various ways, also the Android Studio tool, like other tools used, is continuously updated and the interfaces may change over time, it is recommended a personal research to perform the installation of a project in Android Studio on your device or the use through the IDE simulator.

In the documentation folder inside the repository there is a mock data generator in Python that can be used on your database to verify that it works correctly, it also has a small user guide attached.

# Core Endpoints
### Authentication
The most important endpoints in the application are those for authentication such as the following:
```http
POST /auth/registerUser
POST /auth/loginUser
```
### Auction Interaction
Followed by those for creating an auction, placing a bid, and getting the auction:
```http
POST /auctions            # Create a new auction
GET  /auctions/{id}       # Retrieve auction details
POST /bids                # Place a bid on an auction
```
### Note
For a comprehensive overview of all available endpoints and their specifications, please refer to the detailed API documentation in the Documentation folder of this repository.  
All endpoints require proper authentication through JWT tokens, except for registration and login.

# Authors
Both authors of the project contributed to the construction of the application on the FrontEnd and BackEnd sides in an almost equally distributed manner.
- Simone Cioffi
- Bruno De Vivo
