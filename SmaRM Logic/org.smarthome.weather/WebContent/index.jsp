<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css" type="text/css"></link>
<title>SmaRM Weather Services</title>

<script>
function gweather(url)
{
	var location = document.forms["gweatherform"]["location"].value;
	var format = document.forms["gweatherform"]["format"].value;
	
	var servicecall = url + "/" + location + "." + format;
	
    window.open(servicecall, 'popup', 'width=320,height=240,resizeable=no');
    document.login.setAttribute('target', 'popup');
    document.login.setAttribute('onsubmit', '');
    document.login.submit();
};

function cfWunderground(url)
{
	var date = document.forms["wundergroundform"]["yyyymmdd"].value;
	var hour = document.forms["wundergroundform"]["hour"].value;
	var country = document.forms["wundergroundform"]["country"].value;
	var state = document.forms["wundergroundform"]["state"].value;
	var city = document.forms["wundergroundform"]["location"].value;
	var format = document.forms["wundergroundform"]["format"].value;
	var reqType = document.forms["wundergroundform"]["reqtype"].value;
	
	var servicecall = url + "/" + reqType + "/" + date + "/" + hour + "/" + country + "/" + state + "/" + city + "." + format;
	
    window.open(servicecall, 'popup', 'width=320,height=240,resizeable=no');
    document.login.setAttribute('target', 'popup');
    document.login.setAttribute('onsubmit', '');
    document.login.submit();
};
</script>
</head>
<body>
	<% String hour = new SimpleDateFormat("HH").format(new Date()); %>
	<% Calendar calendar = Calendar.getInstance(); 
	   calendar.add(Calendar.DATE, -1);
	   String yesterday = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	%>
	<form name="wundergroundform" method="GET" onsubmit="cfWunderground('../org.smarthome.weather/rest'); return false;">
		<fieldset>
			<legend>Wunderground Weather API (history)</legend>
			
			<label for="yyyymmdd">Date (yyyyMMdd)</label>
			<input name="yyyymmdd" value="<%= yesterday %>" style="width:20em" />
			<br/>
			<label for="hour">Hour (0-23)</label>
			<input name="hour" value="<%= hour %>" style="width:20em" />
			<br/>
			<label for="country">Country</label>
			<input name="country" value="US" style="width:20em" />
			<br/>
			<label for="state">State</label>
			<input name="state" value="WA" style="width:20em" />
			<br/>
			<label for="location">City</label>
			<input name="location" value="Pullman" style="width:20em" />
			<br/>	
			<label for="format">Response Format</label>
			<select name="format" style="width:20em">
		    <option value="xml">application/xml</option>
			<option value="json">application/json</option>
			</select>
			<br/>
			<label for="reqtype">Data Source</label>
			<select name="reqtype" style="width:20em">
		    <option value="history">historical</option>
			<option value="forecast">prediction</option>
			</select>
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>
	
	<form name="gweatherform" method="GET" onsubmit="gweather('../org.smarthome.weather/rest/gweather'); return false;">
		<fieldset>
			<legend>gWeather (current conditions)</legend>
			
			<label for="location">Location</label>
			<input name="location" value="Pullman" style="width:20em" />
			<br/>
			<label for="format">Response Format</label>
			<select name="format" style="width:20em" >
		    <option value="xml">application/xml</option>
			<option value="json">application/json</option>
			</select>
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>
</body>
</html>