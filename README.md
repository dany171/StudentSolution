# StudentSolution

Requirements:
Java 8

The solution has a client-server architecture. This was done as loading and saving all time to disk would be expensive in time response. Taking in account that
50000 users could be using this project.

How to use:
-Import this to Eclipse or another JAVA IDE
-Run Server.java 
-Run Client.java 
-Use commands to manage students

Commands:

1. Create command, allows to create a student. 
create <name> <gender> <type> 

where:
name: is a string without quotes and without spaces
gender= <male|female>
type= <kindergarden|elementary|highschool|university>

example
create name=john gender=male type=kindergarden

2. Udates a student
update <id> <name> <gender> <type>

where:
id: is the id of the student to update
name: is a string without quotes and without spaces
gender= <male|female>
type= <kindergarden|elementary|highschool|university>

example
update id=1 name=bob gender=male type=elementary

3. Deletes a student
delete <id>

where:
id: is the id of the student to delete

4. Search by name
search <name>

where:
name: is a string without quotes and without spaces containing the name of a student

example:
search name=john

5. Search by gender
search <gender>

where:
gender: <male|female>

example:
search gender=male

6. Search by type
search <type>

where:
type: <kindergarden|elementary|highschool|university>

example:
search type=elementary

7. Search by gender and type
search <gender> <type>

where:
gender: <male|female>
type: <kindergarden|elementary|highschool|university>

example:
search gender=female type=kindergarden

8. Exits the app persisting all the data in CSV file. For now the file is by default input.csv
exit

example:
exit

Technical details

-Dependency injection pattern implemented. (As jaga SE does not provides @Inject).

-I used handful data structures to optimize search reducing the complexity to 1 in below cases:
  - search by name
  - search by gender
  - search by type

- Search by type and gender is implemented y intersecting the results of search by Gender and search by Type. I think it is good enough

-Server loads the file at start up.

-All students are managed in memory while server is running. When Server exits from command line it persists all the info in CSV file.

-Strategy pattern was designed for BasicStudentSearchService, it is pending for implementation.

-Timestamp format pending.















