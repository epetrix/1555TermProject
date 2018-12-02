# Simple Makefile for the project

PROJ = MyAuction

default: all
all: $(PROJ)

$(PROJ): *.java
	javac $(PROJ).java

clean:
	$(RM) *.class

.PHONY: default all $(PROJ) clean
