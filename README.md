Sample maven project for spring boot applications using java and kotlin languages.
It follows hexagonal architecture principles (https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749)
and uses different testing techniques, especially for the data layer classes.
It uses some basic concepts of functional programming (like monads) using arrow kotlin library.
It contains also a Jenkinsfile to allow continuous integration with jenkins, and 
a Dockerfile that can be used after the build stage to build a docker image.
