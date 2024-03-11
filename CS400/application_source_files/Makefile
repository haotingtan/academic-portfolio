# Author: Haoting Tan

default: run

Location.class: Location.java
	javac Location.java

Road.class: Road.java
	javac Road.java

Path.class: Path.java
	javac Path.java
	
NavigationFrontEnd.class: NavigationFrontEnd.java
	javac NavigationFrontEnd.java

NavigationBackEnd.class: NavigationBackEnd.java
	javac NavigationBackEnd.java

NavigationSystemADT.class: NavigationSystemADT.java
	javac NavigationSystemADT.java

run: Location.class Road.class Path.class NavigationBackEnd.class NavigationFrontEnd.class NavigationSystemADT.class
	java NavigationFrontEnd

test: JUnitTester.class
	java -jar junit5.jar --class-path . --scan-classpath --include-classname=JUnitTester

JUnitTester.class: JUnitTester.java
	javac -cp .:junit5.jar JUnitTester.java -Xlint

clean:
	rm *.class
