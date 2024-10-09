all: build run
build:
	mvn clean install
run:
	cp /D1/Projects/OPhabs/target/original*.jar /D1/.minecraft/plugins/OPhabs.jar
doxygen:
	doxygen Doxyfile
doc:
	firefox html/index.html &
