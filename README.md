Excercise Project Overview & Requirements

Modeling a system to manage students that will be used by high schools, elementary schools,
kindergardens, etc.
Create the business objects to manage the students in the system:
- Store the students in the system
- Create new students
- Delete a specific student
- Search for students in ways that make sense for the clients
  o By name, sorted alphabetically
  o By student type (kinder, elementary, high, university) sorting by date, most recent to least recent.
  o By gender and type (female elementary) sorting by date, most recent to least recent.

No DB nor GUI for this project.

If this were a real application, the search request would originate from a web page. The student
management classes would be invoked to perform the search (perhaps even as a standalone web
service) and the results returned to a web page. To simplify this example, the request will come from
the command line, and the results will be returned to the console.

The sample may only utilize 10 students, however, it should be able to efficiently handle 50,000 or more. And hundreds of simultaneous requests that you would expect to find in a web based application; i.e., students being simultaneously searched
for, created, and updated.

To simulate a web based operating environment, the command line should be used to:
- Read students at system startup from a CSV input file as well as reading the search request from the command line
Ex: java studentSolution input.csv name=leia
Ex: java studentSolution input.csv type=kinder
Ex: java studentSolution input.csv type=elementary gender=female
- Use the create student methods to create each student from the CSV file in the business
objects.
- Echo the results of the requested search operation to the console (system.out)The CSV File
Given an unordered CSV file with EOL (\n) terminated lines of the format:
Kinder,Leia,F,20151231145934
High,Luke,M,20130129080903
Where each line has the format:
Student Type, Student Name, Gender, Timestamp of last update in the system
The “Timestamp” format is: “<year><month><day><hour><minute><second>”, e.g.:
The representation for December 31st, 2013 14:59:34 is 20131231145934.

# StudentSolution

Requirements:
Java 7

The solution has a client-server architecture. 
Searching feature test over 50000 records, response time 25ms. TODO: Add stress test to verify this.

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
