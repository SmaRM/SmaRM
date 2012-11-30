<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css" type="text/css"></link>
<title>SmaRM Data Services</title>
</head>
<body>
	<% String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	   String time = new SimpleDateFormat("hh:mm:ss").format(new Date()); %>
	<form action="../org.smarthome.data/rest/resources/newsensordata" method="POST">
		<fieldset>
			<legend>Add Resource</legend>
			
			<label for="date">Date</label>
			<input name="date" value="<%= date %>" style="width:20em" />
			<br/>
			<label for="time">Time</label>
			<input name="time" value="<%= time %>" style="width:20em" />
			<br/>
			<label for="location">Location</label>
			<input name="location" value="OutsideDoor" style="width:20em" />
			<br/>
			<label for="sensorid">Sensor ID</label>
			<input name="sensorid" value="FrontDoor" style="width:20em" />
			<br/>
			<label for="sensorvalue">Sensor Value</label>
			<input name="sensorvalue" value="OPEN" style="width:20em" />
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>

	<% String datetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()); %>
	<form action="../org.smarthome.data/rest/resources" method="POST">
		<fieldset>
			<legend>Add Resource</legend>
			
			<label for="userId">UserID</label>
			<input name="userId" style="width:20em" />
			<br/>
			<label for="date">Date</label>
			<input name="date" value="<%= datetime %>" style="width:20em" />
			<br/>
			<label for="location">Location</label>
			<input name="location" style="width:20em" />
			<br/>
			<label for="activity">Activity</label>
			<input name="activity" style="width:20em" />
			<br/>
			<label for="duration">Duration [min]</label>
			<input name="duration" style="width:20em" />
			<br/>
			<label for="energyConsumption">Energy Cons. [kWh]</label>
			<input name="energyConsumption" style="width:20em" />
			<br/>
			<label for="waterConsumption">Water Cons. [m&sup3;]</label>
			<input name="waterConsumption" style="width:20em" />
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>
</body>
</html>