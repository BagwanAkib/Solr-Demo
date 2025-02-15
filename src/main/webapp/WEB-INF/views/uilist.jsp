<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>${info}</div>
	<div>Query: ${q}</div>
	<a href="/">/Create page</a>
	<br>
	<div>
		<ul>
			<li>Use exact search with double quotes (e.g., "abc").</li>
			<li>Use <code>+</code> for 'AND' operation (e.g., "abc" +
				"def").
			</li>
			<li>Use <code>-</code> for 'OR' operation (e.g., "abc" - "def").
			</li>
			<li>Do not use <code>*</code> in your search queries.
			</li>
		</ul>
	</div>
	<br>

	<form action="/uilist" method="get">
		<label for="query">Enter Search Query:</label> <input type="text"
			id="q" name="q" value="${q}" placeholder="Search..." required>
		<button type="submit">Search</button>
	</form>
	<br>
	<c:if test="${list == null || list.isEmpty()}">
		<div>No search results.</div>
	</c:if>
	<c:forEach var="item" items="${list}">
		<li><div>
				<a href="/delete?id=${item.getId()}"
					onclick="return confirm('Are you sure you want to delete this item?');">
					Delete >> </a> ${item.getSearchObject()}
			</div></li>
	</c:forEach>
</body>
</html>