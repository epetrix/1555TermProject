# CS 1555 Term Project
Design and implement an electronic auctioning system.

## Contributors
- Kyle Amoroso [@kamoroso94](https://github.com/kamoroso94)
- Alex Kim [@adk67](https://github.com/adk67)
- Eden Petri [@epetrix](https://github.com/epetrix)

## How to Run
First, make sure to `source` the correct environment file for your shell.  For
example, if you're running this with `bash` on the class3 server, you would use
this command.
```
$ source ~panos/1555/bash.env.class3
```
Next, from the project root directory, you will need to create the databases,
triggers, etc.  Log into `sqlplus` and run these commands, then `exit`.
```
SQL> @schema
SQL> @insert
SQL> @trigger
SQL> exit
```
Finally, you `make` the project and run it like so.
```
$ make MyAuction
$ java MyAuction
```
To run the Driver program, do the following:
```
$ make Driver
java Driver
```
The program will prompt you to log in to the database with your `sqlplus` user
credentials. The default administrator account for the auction site is `admin`
with the password `root`.
