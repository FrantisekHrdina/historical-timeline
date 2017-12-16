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
</head>
<body>
	<h1>Welcome to the Historical Timeline project!</h1>
	
	<a href="#!/groups">Seminar groups</a>
  <a href="#!/events">Events</a>
	
	<div class="container">
		<div ng-app="historicalTimelineApp">
			<div ng-view></div>
		</div>
	</div>
</body>
</html>