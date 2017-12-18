<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<!DOCTYPE html>
<html lang="en" >
<head>
	<meta charset="UTF-8">
	<title>Historical Timeline project</title>
	
	<link rel="stylesheet" href="./node_modules/bootstrap/dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="./node_modules/bootstrap/dist/css/bootstrap-theme.min.css">

	<script type="text/javascript" src="./node_modules/jquery/dist/jquery.js"></script>
	<script type="text/javascript" src="./node_modules/angular/angular.js"></script>
	<script type="text/javascript" src="./node_modules/angular-route/angular-route.min.js"></script>
	<script type="text/javascript" src="./node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="./angular_app.js"></script>
	<script type="text/javascript" src="./event_controller.js"></script>
	<script type="text/javascript" src="./group_controller.js"></script>
	<script type="text/javascript" src="./timeline_controller.js"></script>
	<script type="text/javascript" src="./user_controller.js"></script>

</head>
<body>

<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Historical timeline</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="#!/groups">Seminar groups</a></li>
				<li><a href="#!/events">Events</a></li>
				<li><a href="#!/timelines">Timelines</a></li>
                                <li><a href="#!/users">Users</a></li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>
	<div class="container">
		<div ng-app="historicalTimelineApp">
			<div ng-view></div>
		</div>
	</div>
</body>
</html>