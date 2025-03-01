# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.5/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

# Deploy
### Deploy to Google Cloud App Engine

- Config *(If Google Cloud not login)*
```shell
gcloud auth login
```
```shell
gcloud config set project nguyenducthinh-springboot
```
- Deploy

Gradle -> Tasks -> app engine -> appengineDeploy