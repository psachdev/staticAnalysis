<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<title>Static Analysis</title>
<link rel="stylesheet" type="text/css" href="style.css">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
</head>
<body>
</head>

<body>
	<%@ page import="beans.AppInfoBean"%>
	<div class="row">
		<div class="span10">
			<h1>
				<a href="<c:url value="/"></c:url>">Static Analysis</a>
			</h1>
		</div>
	</div>
	<div class="container-fluid">
		<div class="span4">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">

					<li class="active"><a
						href="<c:url value="showStatistics.do?category=1"></c:url>">Percentage
							apps per category</a></li>
					<li class="active">
					<li class="nav-header">Top 20 Permissions</li>
					<li class="active"><a
						href="<c:url value="showStatistics.do?category=2"></c:url>">Group
							by overall apps</a></li>
					<li class="active"><a
						href="<c:url value="showStatistics.do?category=3"></c:url>">Group
							by category</a></li>

					<li class="nav-header">Top 20 External Packages</li>
					<li class="active"><a
						href="<c:url value="showStatistics.do?category=4"></c:url>">Group
							by overall apps</a></li>
					<li class="active"><a
						href="<c:url value="showStatistics.do?category=5"></c:url>">Group
							by category</a></li>
				</ul>
			</div>
			<br>

			<c:if test="${fn:length(appCategoryList) gt 0}">
				<div class="well sidebar-nav" id="categories">
					<ul class="nav nav-list">
						<c:forEach var="x" items="${appCategoryList}">
							<li class="active">
							<c:url value="showStatistics.do" var="theUrl">
							<c:param name="category" value="${category}" />
							<c:param name="selectedAppCategory" value="${x.category}" />  
							</c:url>
							<a href="${theUrl}"> ${x.category}</a></li>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</div>
		<div class="span8">
			<c:if test="${errMsg}">
				<h1>${errMsg}</h1>
			</c:if>
			<table class="table table-hover">
				<tbody>
					<c:choose>
						<c:when test="${category == 1}">
							<tr>
								<td><h3>Category</h3></td>
								<td><h3>Percentage Apps</h3></td>
							</tr>
							<c:forEach var="x" items="${resultList}">
								<tr>
									<td>${x.category}</td>
									<td>${x.count}</td>
								</tr>
							</c:forEach>
						</c:when>

						<c:when test="${category == 2}">
							<tr>
								<td><h3>Permission</h3></td>
								<td><h3>Count</h3></td>
							</tr>
							<c:forEach var="x" items="${resultList}">
								<tr>
									<td>${x.permission}</td>
									<td>${x.count}</td>
								</tr>
							</c:forEach>
						</c:when>

						<c:when test="${category == 3}">
							
							<tr>Top 20 Permissions used in Category : <b>${selectedAppCategory}</b></tr>
							<tr>
								<td><h3>Permission</h3></td>
								<td><h3>Count</h3></td>
							</tr>
							<c:forEach var="x" items="${resultList}">
								<tr>
									<td>${x.permission}</td>
									<td>${x.count}</td>
								</tr>
							</c:forEach>
							
						</c:when>
						<c:when test="${category == 4}">
							<tr>
								<td><h3>ExternalPackage</h3></td>
								<td><h3>Count</h3></td>
							</tr>
							<c:forEach var="x" items="${resultList}">
								<tr>
									<td>${x.externalpackage}</td>
									<td>${x.count}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-collapse.js"></script>
</body>
</html>
