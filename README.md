# GoEuro Java Developer Test

This is my solution to the **Java Developer Test** for applicants to **GoEuro**, for details about the proposed problem go to [GoEuro dev-test](https://github.com/goeuro/dev-test).

In the following sections I briefly explain some characteristics I would like to highlight about both the development process and the resulting solution itself.

## Architecture
Due to the nature of the problem, I designed the solution based on a *pipeline architecture* whose main components are depicted in the following block diagram.

![Pipeline architecture](/pipeline.png)

By implementing the *strategy pattern*, the modules at the different levels of this architecture are loosely coupled in accordance with the *dependency inversion principle* so that it is easy to implement reading from and writing to different types of sources (HTTP services, databases, flat files, etc.) and with different data formats (JSON, CSV, XML, etc.).

The solution implements *pagination* when reading from a source. This was done for performance reasons whenever data can be partitioned, so that a page of data is read first and then processed and loaded while the next page is already being read.

As to adhere to the statement of:

> keep things simple but well engineered

the solution is implemented with only one extraction and one loading thread at a time, however, thanks to the *single responsibility principle* I highly emphasized during the development, the solution could be easily extended to manage concurrent extraction from multiple sources and even to multiple destinations at a time by modifying only the `etl.ETL` class.

## Development process
If it is true that I envisioned the main architecture of the solution after analyzing the requirements, the design and implementation of more specific details where decided through a process of Test-Driven Development (which for lack of expertise in the subject I refuse to call BDD) as explained below:

1. Define the next task to implement.
2. Define a class to achieve it and assign the task as its only responsibility.
3. Define the functionality the class needs to provide to perform such task.
4. Create the main test cases for every functionality.
5. Code the class to pass those tests.
6. If more methods or internal steps are created while coding, write test cases for them.
7. After detecting a code smell or creating multiple related classes, review and refactor if deemed convenient.

8. Test, test, test and test again. Because the request states: to implement the solution 

> as you would do for production code

  I coded automatic tests when possible and manually tested everywhere because that's the minimum requirement I need to fulfill to allow myself to put code into production. An alluring example of this (from my point of view) is the test case `etl.ExtractorTest.testConnectionErrorHandling()` where manual actions need to be carried out along with running it. If it is not something I would do in a *Continuos Deployment* environment, I consider it adecuate for this particular case and I think it shows how much emphasis I put in making sure the code works. 

## Implementation
The main algorithm behind the solution consists in parsing the information extracted from any kind of source into `Map` collections representing objects, whose attributes are '.'-separated by levels of nesting. 
For example, the JSON object:
```
{
 fullName: "Potsdam, Germany",
 geo_position: {
 	latitude: 52.39886,
 	longitude: 13.06566
 }
}
``` 
Would be parsed into the Map:
```
[
 "fullName":"Potsdam, Germany",
 "geo_position.latitude":52.39886,
 "geo_position.longitude":13.06566
]
```
Once the data is represented in this format it can then be transformed internally and finally re-parsed to the destination's data format.

Throughout the development process most of the architectural decisions were taken in accordance with the SOLID principles, design patterns and personal expertise. An overview of the final class diagram is showed below.

![Class diagram](/classes.png)

### Configuration File
The program is executed as requested by passing the city name to query as a parameter. Furthermore, a configuration file with the name `dev-test.conf` may be created to change some behaviours as it's self-explained in its structure:
```
#API endpoint to query
API_URL=http://api.goeuro.com/api/v2/position/suggest/en/

#The wanted object's attributes 
#The delimiter to separate the objects's nested hierarchies
attributes_wanted=_id;name;type;geo_position.latitude;geo_position.longitude
attributes_delimiter=.

#The number of times to attempt to connect to the service 
#The delay between re-connections in case of failure
connection_attempts=3
reconnection_delay=1

#The delimiter between columns of the generated CSV
#The path to the file where to store the results
csv_delimiter=,
csv_path=GoEuroTest.csv
```

## Final Notes
The following are decisions I deliberately took which in a real formal setting I would have talked with the team to agree upon first:

- Throughout the code I implemented different techniques and strategies to illustrate how I am acquainted with them, such as iteration and recursion, different implementations of threading in Java (CompletableFutures, Runnables and Callables), thread synchronization (by intrinsic lock which turned out to be enough), streams, lambdas, method references, anonymous classes, etc. I would have adhere to the team's coding standards to choose among them.
- Given the size and nature of the application I decided to use state-of-the-art `List<Map>`'s to handle the data, in a more formal long-term setting I would have probably used a wrapper class upon agreement with the team.
- In this particular scenario where the application extracts data only from one source in one data format, the solution I developed can actually be left behind in performance tests, in a real setting I would have evaluated the priority and trade-offs between performance and maintainability and extensibility.

## Dependencies
To build the solution the following dependencies are required:

- OkHTTP 3.4.1
- Okio 1.10.0
- [JSON-java 20160810](https://github.com/stleary/JSON-java)
