# Simple Makefile for the project

default: MyAuction

MyAuction: *.java
	javac MyAuction.java

Driver: *.java
	javac Driver.java

clean:
	$(RM) *.class

.PHONY: default all MyAuction Driver clean
