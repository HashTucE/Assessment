# Assessment

<p align="center">
  <img src=https://user-images.githubusercontent.com/95872501/224155098-59ee106a-10cd-4189-a830-e957db28003c.png>
</p>

Assessment is a Microservice as a REST API that communicates with [Patient](https://github.com/HashTucE/Patient.git) and [Note](https://github.com/HashTucE/Note.git) to retrieve data to assess the risk for a patient of contracting type 2 diabetes.

Please go to [Mediscreen](https://github.com/HashTucE/Mediscreen.git) to learn more about this project.

# Prerequisites
- Java 19
- Maven 4.0.0
- Spring Boot 3.0.2
- MySQL 8.0.29
- Docker 4.16.2

# How to upload the Assessment image to your Docker Registry

1. Clone `Assessment` to your local machine.
2. Using a prompt, once located to the root of `Assessment`, execute this command to compile, test, package and install the project :
  ```
  mvn clean install
  ```
3. Still at the same location (to point to the `Dockerfile`) build the image of `Assessment` using :
  ```
  docker build -t jar .
  (Make sure that the jar name inside the Dockerfile is identical to the jar name located to the target folder !)
  ```
  
4. You can then execute this command to link your local image with a repository of `Docker Registry` :
  ```
  docker tag [local_image_name]:latest [your_docker_username]/[distant_repository_name]:latest
  (Make sure to be connected to your account on Docker Desktop !)
  ```
5. And finally execute this command to upload your local image to your `Docker Registry` :
  ```
  docker push [your_docker_username]/[distant_repository_name]:latest
  ```
6. If you want to build [Mediscreen](https://github.com/HashTucE/Mediscreen.git) with the image you just created, make sure to modify his docker-compose.yml of replacing the `image path` inside `assessment.ms` by your own :
  ```
  [your_docker_username]/[distant_repository_name]:latest
  ```
