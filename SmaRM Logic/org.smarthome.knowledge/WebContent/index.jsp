<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css" type="text/css"></link>
<title>SmaRM Knowledge Services</title>
</head>
<body>
	<form action="../org.smarthome.knowledge/rest/verbalize" method="POST">
		<fieldset>
			<legend>OWL Verbalizer</legend>
			
			<label for="xml">Ontology (OWL2 XML)</label>
			<br/>
			<textarea name="xml" rows="20" cols="60"></textarea>
			<br/>
			<br/>
			<label for="outputFormat">Output Format</label>
			<select name="outputFormat">
			<option value="ace">ACE</option>
			<option value="html">HTML</option>
			<option value="csv">CSV</option>
			</select>
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>
	
	<form action="../org.smarthome.knowledge/rest/ape" method="POST">
		<fieldset>
			<legend>ACE Parser Engine (APE)</legend>

			<label for="lexion">ACE Lexicon</label>
			<br/>
			<textarea name="lexion" rows="4" cols="60"></textarea>
			<br/>
			<label for="text">Ontology (ACE)</label>
			<br/>
			<textarea name="text" rows="20" cols="60"></textarea>
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>
	
	<form action="../org.smarthome.knowledge/rest/acerules" method="POST">
		<fieldset>
			<legend>AceRules Engine (AceRules)</legend>

			<label for="program">Program</label>
			<br/>
			<textarea name="program" rows="20" cols="60"></textarea>
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>
	
	<form action="../org.smarthome.knowledge/rest/race" method="POST">
		<fieldset>
			<legend>ACE Reasoning Engine (APE)</legend>
			
			<label for="axioms">ACE Axioms</label>
			<br/>
			<textarea name="axioms" rows="20" cols="60"></textarea>
			<br/>
			<label for="query">ACE Query</label>
			<br/>
			<textarea name="query" rows="10" cols="60"></textarea>
			<br/>
			<input type="submit" value="Submit" />
		</fieldset>
	</form>

</body>
</html>