## set API key in terminal 
set WEATHER_KEY=f19a62a715ef93c8e8198fc9695bf9bf

https://openweathermap.org/current

mvn package -Dmaven.test.skip=true

java -jar target/day17-workshop-0.0.1-SNAPSHOT.jar 

https://hub.docker.com/_/openjdk/tags


## build image
docker build -t leqing92/day17-workshop:0.0.1 .

##run image
docker - d - p local_port:container_port -e OP 

docker run -d -p 8080:8080 -e WEATHER_KEY=f19a62a715ef93c8e8198fc9695bf9bf leqing92/day17-workshop:0.0.1 .