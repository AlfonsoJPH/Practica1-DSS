all: build run
build:
	mvn clean install
run:
	java -jar target/practica1-0.0.1-SNAPSHOT.jar
doxygen:
	doxygen Doxyfile
doc:
	firefox html/index.html &
