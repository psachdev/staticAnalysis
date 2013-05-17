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
			<c:when test="${selectedCategory != 'all'}">
				<div class="span4">
					<h1>
						<h1>
							<a href="<c:url value="/"></c:url>">Static Analysis</a>
						</h1>
					</h1>
				</div>
				<div class="span6">
					<h3>
						<b> <a href="<c:url value="/"></c:url>">HOME</a> >> <a
							href="getNext.do?category=${selectedCategory}&Current=1">${selectedCategory}</a>
						</b>
					</h3>
				</div>
				<div class="span2">
					<div class="pagination">
						<ul>
							<li><a href="getNext.do?category=${selectedCategory}&Prev=1">Prev</a></li>
							<li><a href="getNext.do?category=${selectedCategory}&Next=1">Next</a></li>
						</ul>
					</div>
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
		<div class="row">
			<div class="span4">
				<div class="well sidebar-nav">
					<ul class="nav nav-list">
						<li class="nav-header">Display Options</li>
						<li class="active"><a
							href="<c:url value="/showStatistics.do"></c:url>"><b>SHOW
								STATISTICS</b></a></li>
						<c:forEach var="x" items="${categories}">
							<li class="active"><a
								href="<c:url value="getNext.do?category=${x.category}"></c:url>">${x.category}</a>
							</li>
						</c:forEach>
					</ul>
				</div>

			</div>
			<div class="span8">
				<ul class="gallery" id="gallery_id">
					<c:forEach var="x" items="${result}">
						<li>
							<form action='appSelected.do' method='post'>
								<input name="image" id="submit" type="image" src="${x.imageSrc}"><br>
								<b>${x.appname}<br> <b>INSTALLS ${x.installs_max} -
										${x.installs_min}<br> <b>Rating : ${x.rating_value} 
										<input name="packagename" type="hidden" value="${x.packagename}">
										<input name="appname" type="hidden" value="${x.appname}">
							</form>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
