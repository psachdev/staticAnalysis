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
			<br>
			<div class="well sidebar-nav">
				<ul class="nav nav-list">
					<li class="nav-header">Types of External Packages Identified</li>
					<li class="active">Market Analysis</li>
					<li class="active">Payment</li>
					<li class="active">Social Gaming</li>
					<li class="active">Utility</li>
					<li class="active">Targeted Ads</li>
					<li class="active">Game Engine</li>
					<li class="active">Search Engine</li>
					<li class="active">UI component</li>
					<li class="active">Marketing Solution</li>
					<li class="active">SNS</li>
					<li class="active">Content Provider</li>
				</ul>
			</div>
		</div>
		<div class="span8">
			<c:if test="${errMsg}">
				<h1>${errMsg}
			</c:if>
			<table class="table table-hover">
				<tbody>
					<tr>
						<td><h3>Package Name</td>
						<td><h3>Related Info</td>
					</tr>
					<c:forEach var="x" items="${externalPackages}">
						<tr>
							<td>${x.externalPackageName}<br>
							 <c:if	test="${x.externalPackageCategory != ''}">
									
									<b>Package Type :</b>
									
								${x.externalPackageCategory}<br>
								</c:if>
							
							 <a href="<c:url value="${x.website}"></c:url>">${x.website}</a>
							</td>
							<td><b>Applications Using this package :</b><br>
								<ul>
									<c:forEach var="appname" items="${x.otherPackageNames}">
										<li>${appname}</li>
									</c:forEach>
								</ul> <br> <b>Number of apps Using this package :
									${x.numOtherPackages}</b></td>
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
