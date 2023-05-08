# Cat Image Manager
Following are the requirements for this service

### Requirements
#### Your API MUST support the following operations:
* Upload a cat pic.
* Delete a cat pic.
* Update a previously uploaded cat pic (not just metadata) in place.
* Fetch a particular cat image file by its ID.
* Fetch a list of the uploaded cat pics.
#### Additionally, you MUST:
* Correctly use HTTP response codes, including error handling.
* Provide documentation for your API's behavior.
* Provide instructions for us to get your API up and running.
* Write a basic suite of tests for your code.

### Suggestions
If you want to show off a little bit, do one or more of the following:
* Dockerize your application, it's okay if your pictures aren't persisted outside of the
container runtime.
* Setup authentication/authorization
# Getting Started

Building the jar artifact

``./mvnw clean package``

Running test cases

``./mvnw clean test``

Running application in local environment (without docker)

``java -jar target/cat-image-manager-0.0.1-SNAPSHOT.jar``

Running application in local environment (with docker)

Building the docker image

``docker build -t cat-image-manager .``

Running the docker image

``docker run -d -p 8081:8081 cat-manager-manager:latest``

[Swagger Link](http://localhost:8081/swagger-ui/)

Example CURL commands

* Fetching all images
* ``
curl --location --request GET 'http://localhost:8081/image-manager/v1/images'
``
* Fetching particular image
* ``
curl --location --request GET 'http://localhost:8081/image-manager/v1/images/1'
``
* Uploading an image
* ``curl --location --request POST 'http://localhost:8081/image-manager/v1/images' \
  --form 'name=" abc1239y9887"' \
  --form 'file=@"/Users/netomi/Downloads/swimlanes-2fc094d3a5388ba7556558bbc663f0b1.png"' \
  --form 'description="Very good image. But not bad"'``
* Updating an existing image
* ``curl --location --request PUT 'http://localhost:8081/image-manager/v1/images/123' \
  --form 'name=" abc1239y98872455"' \
  --form 'file=@"/Users/netomi/Downloads/swimlanes-2fc094d3a5388ba7556558bbc663f0b1.png"' \
  --form 'description="Very good image. But not bad"'``
* Delete an existing image



