<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<c:choose>
			<c:when test="${selectedApp != ''}">
				<div class="span4">
					<h1>
						<h1>
							<a href="<c:url value="/"></c:url>">Static Analysis</a>
						</h1>
					</h1>
				</div>
				<div class="span8">
					<h3>
						<b> <a href="<c:url value="/"></c:url>">HOME</a> >> <a
							href="getNext.do?category=${selectedCategory}&Current=1">${selectedCategory}</a>
							>> <a href="appSelected.do">${selectedApp}</a>
						</b>
					</h3>
			</c:when>
			<c:otherwise>
				<div class="span10">
					<h1>
						<a href="<c:url value="/"></c:url>">Static Analysis</a>
					</h1>
				</div>
				<div class="span2">
					<div class="pagination">
						<ul>
							<li><a href="getNext.do?category=${selectedCategory}&Prev=1">Prev</a></li>
							<li><a href="getNext.do?category=${selectedCategory}&Next=1">Next</a></li>
						</ul>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="container-fluid">

		<div class="span4">
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li class="nav-header">Display Options</li>
					<li class="active"><a
						href="<c:url value="appSelected.do"></c:url>">Display
							ExternalPackages</a></li>
					<li class="active"><a
						href="<c:url value="appSelectedPerm.do"></c:url>">Display
							Permissions</a></li>
				</ul>
			</div>
		</div>
		<div class="span8">
			<c:if test="${errMsg}">
				<h1>${errMsg}</h1>
			</c:if>
			<table class="table table-hover">
				<tbody>
					<tr>
						<td><h3>Permission</h3></td>
						<td><h3>Description</h3></td>
						<td><h3>Permission Type</h3></td>
						<td><h3>External Packages</h3></td>
					</tr>
					<c:forEach var="x" items="${permissionsList}">
						<tr>
							<td>${x.permission}
							</td>
							<td>${x.description}
							</td>
							<td>${x.group}</td>
							<td>
								<ul>
									<c:forEach var="appname" items="${x.externalPackages}">
										<li>${appname}</li>
									</c:forEach>
								</ul> 
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</div>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
