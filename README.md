# Document test API

* Requires [Docker](https://www.docker.com/products/docker-desktop/)

1. Create the docker image:
   `./gradlew bootBuildImage --imageName=renan/europace-challenge`


2. Run the docker image:
   `docker run --rm -p 8081:8081 renan/europace-challenge`


3. Example request:
   1. List found Documents `curl --location --request GET 'http://localhost:8081/v1/documents/'`
        with params filters ex: `?deleted=false&categories=cat_4&type=IMAGE`
        and sorting ex: `?sort=name,DESC`
   2. Count found Documents `curl --location --request GET 'http://localhost:8081/v1/documents/count'`
   with params filters ex: `?deleted=true&categories=cat_4&type=PDF` 
   3. Size of found Documents `curl --location --request GET 'http://localhost:8081/v1/documents/size'`
   with params filters ex: `?deleted=true&categories=cat_4&type=PDF` 
   4. Average Size of found Documents `curl --location --request GET 'http://localhost:8081/v1/documents/average-size'`
      with params filters ex: `?deleted=false&categories=cat_1&type=PDF` 
