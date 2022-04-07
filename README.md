## Authentication Server
The application stores user credentials in its database and provides authentication for the 'Training_center' application. The authentication process is described in the 'Training_center' 'README.md' file.

### Prerequisites
The following needs to be installed before building the project:
* Java 17
* PostgreSQL 14.2:
  * A database with the following configuration needs to be created:
    * username: `postgres`
    * password: `123`
    * database name: `authentication`
  * If you'd like to specify your own database configuration, you need to modify `application.yaml`

The application will be available at http://localhost:8080/