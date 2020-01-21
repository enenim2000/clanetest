# Spring Boot News API
REST APIs implemented using Spring Boot for news article

## How to Run

* Build the project by running `mvn clean package` inside clane project
* Once successfully built, run the service by using the following command:
```
java -jar  clane/target/clanetest.jar

## REST APIs Endpoints
### Account sign-up resource
```
POST /accounts/sign-up
Accept: application/json
Content-Type: application/json

{
  "name": "Eno Asukwo",
  "email":"eno2000@gmail.com",
  "bio":"He is a developer",
  "password":"Password@123",
  "confirm_password":"Password@123"
}

```


### Account sign-in resource
```
POST /accounts/sign-in
Accept: application/json
Content-Type: application/json

{
	"username":"eno2000@gmail.com",
	"password":"Password@123"
}


### Retrieve a list of Authors
```
Get /authors
Accept: application/json
Content-Type: application/json

```

### Submit an Article Resource
```
POST /articles
Accept: application/json
Content-Type: application/json

{
  "title": "Beauty and the Beast",
  "content":"The true love story thats transcend"
}


### Retrieve list of Articles
```
GET /articles
Accept: application/json
Content-Type: application/json



### Update an Article  Resource
```
PUT /articles/{id}
Accept: application/json
Content-Type: application/json

{
  "title": "River runs dry",
  "content":"The beautiful sight of waterfall"
}



### Delete an Article  Resource
```
DELETE /articles/{id}
Accept: application/json
Content-Type: application/json
```
