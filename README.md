# covid19tracker



## web app url: https://covid19ta.herokuapp.com/

### helth endpoint: https://covid19ta.herokuapp.com/health
### register endpoint: https://covid19ta.herokuapp.com/register?username=onlineUser&hasCovid=true&latitude=5553.2323&longitude=553.3434
###https://covid19ta.herokuapp.com/sighting?username=test&latitude=232323.2323&longitude=34343.2323
###to run the app you need to set this enviroment variables:

DB_HOST=

DB_USER=

DB_PASSWORD=

DB_NAME=

DB_PORT=

DB_TABLE=

____
- What is Observability? What is Monitoring? What is the difference?

Monitoring tells us whether the system works.

Observability lets us ask why it’s not working.

Monitoring an app will get information about the system and let us know in the event of a failure.
Observability is a quality of the app or technology you are using that grants an easy way of seeing what and where it broke.

- answer the questions and try to explain the difference using your own CovidTracker App!

Monitoring will tell me if the tracker is working or not. (Ex: Problem in Trecker setup ).
Observability tell us about the details of the failure, what it is, where it is, and how it happened.(Ex: The registration data is incomplete, an error in getting the geographical location, an error in creating the user, an error in the request).
