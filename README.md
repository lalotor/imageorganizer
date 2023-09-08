# imageorganizer

WIP - Version 0.2

# Define local Java version (17)

jenv local 17.0.4.1

# Run app

/mvnw spring-boot:run
./mvnw -s /Users/elktorre/.m2/settings.plain.xml spring-boot:run -Dspring-boot.run.arguments="--dir=$HOME/Documents/temp/images --year=2023"

./mvnw package
./mvnw -s /Users/elktorre/.m2/settings.plain.xml package
java -jar target/imageorganizer-0.0.1-SNAPSHOT.jar --dir=$HOME/Documents/temp/images --year=2023 