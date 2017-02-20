# StudentSolution

Requirements:
Java 8

The solution has a client-server architecture. This was done in this was as loading and saving the CSV file all time to disk would be expensive in time response. Taking in account that 50000 or more records will be added.

How to use:

Under "dist" directory run:

java -jar Server.jar  //to run server

java -jar Client.jar  //to run client

Commands:

1. Create command, allows to create a student. 

create <name> <gender> <type> 

where:

name: is a string without quotes and without spaces
gender= <male|female>
type= <kindergarden|elementary|highschool|university>

example:

create name=john gender=male type=kindergarden

2. Udates a student.

update <id> <name> <gender> <type>

where:

id: is the id of the student to update
name: is a string without quotes and without spaces
gender: <male|female>
type: <kindergarden|elementary|highschool|university>

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

6. Search by type.

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

-Dependency injection pattern implemented. (As java SE does not provides @Inject).

-I used handful data structures to optimize search reducing search time:
  - search by name
  - search by gender
  - search by type
  - search by gender and type

- Search by name uses an inverted index to find results

- Search by type and gender is implemented by intersecting the results of search by Gender and search by Type.

-Server loads the file at start up.

-All students are managed in memory while server is running. When Client exits from command line it persists all the info in CSV file.
