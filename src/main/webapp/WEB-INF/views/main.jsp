<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Create Solr Object</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

form {
	max-width: 400px;
	margin: 0 auto;
	padding: 20px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
}

input, textarea {
	width: 100%;
	padding: 8px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

button {
	background-color: #4CAF50;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

button:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<h1>Create Solr Object</h1>
	<a href="/uilist">/UI List</a>
	<form action="/create" method="post">
		<label for="name">Name:</label> <input type="text" id="name"
			name="name" placeholder="Enter object name" required> <label
			for="content">Content:</label>
		<textarea id="content" name="content" rows="6"
			placeholder="Enter object content" required></textarea>
		<br>
		<button type="submit">Create Object</button>
	</form>
</body>
</html>