# ProgressCalendar
This app is a simple tracker for daily routines.
It can be used by multiple people on a server as long as every person have it's own table.
The SQL code is in the file "calendar.sql".

# Setup

In order to connect to the database, the "user1" must be changed in the file data.json ( ProgressCalendar/target/classes/data.json ) to the name of the table in the database. Each individual user must have its own table. 

The app connects as default to the localhost. In order to connect to a different host you need to change the variables "user" to the user of the database, "passwd" to the password of the user and "host" must be "jdbc:mysql://" + host_name + "/calendar" in the file ConnectionMannager.java (ProgressCalendar/src/main/java/controller/ConnectionManager.java )

# Usage

Left click on the day to mark a prodictive day, right click to mark a bad day, and middle click to remove the mark. The save button must be pressed so the modifications are sent into the database. by pressing refresh all the modifications that were not saved will be lost.

