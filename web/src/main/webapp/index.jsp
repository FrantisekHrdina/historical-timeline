<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	trimDirectiveWhitespaces="true" session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Historical Timeline project</title>

<link rel="stylesheet"
	href="./node_modules/bootstrap/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="./node_modules/bootstrap/dist/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="./style.css">

<script type="text/javascript"
	src="./node_modules/jquery/dist/jquery.js"></script>
<script type="text/javascript" src="./node_modules/angular/angular.js"></script>
<script type="text/javascript"
	src="./node_modules/angular-route/angular-route.min.js"></script>
<script type="text/javascript"
	src="./node_modules/angular-cookies/angular-cookies.min.js"></script>
<script type="text/javascript"
	src="./node_modules/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="./angular_app.js"></script>
<script type="text/javascript" src="./event_controller.js"></script>
<script type="text/javascript" src="./group_controller.js"></script>
<script type="text/javascript" src="./timeline_controller.js"></script>
<script type="text/javascript" src="./user_controller.js"></script>
<script type="text/javascript" src="./config.js"></script>
<script type="text/javascript" src="./auth_controller.js"></script>

</head>
<body>
	<div ng-app="historicalTimelineApp" style="font-size: large">
		<nav class="navbar navbar-inverse"
			ng-if="$location.path() !== '/login'">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#!/dashboard">Historical timeline</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li ng-if="userRole === 'teacher'"><a href="#!/groups">Seminar groups</a></li>
					<li ng-if="userRole === 'teacher'"><a href="#!/events">Events</a></li>
					<li ng-if="userRole === 'teacher'"><a href="#!/timelines">Timelines</a></li>
					<li ng-if="userRole === 'teacher'"><a href="#!/users">Users</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#profil" class="glyphicon glyphicon-user"> {{username}}</a></li>
					<li><button class="btn btn-default" ng-click="logout()">Logout</button></li>
				</ul>
			</div>
		</nav>
		<div class="container" style="width: 90%;">
			<div ng-show="errorAlert" class="alert alert-danger">
				{{errorAlert}}
    			</div>	
			<div ng-show="successAlert" class="alert alert-success">
				{{successAlert}}
			</div>
			<div ng-view></div>
		</div>
	</div>
</body>
</html>