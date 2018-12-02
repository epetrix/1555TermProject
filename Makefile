# Simple Makefile for the project

default: all
all: MyAuction

MyAuction: *.java
	javac MyAuction.java

clean:
	$(RM) *.class

.PHONY: default all MyAuction clean
