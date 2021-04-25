<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>


<%@ include file="partials/header.jsp"%>

</head>


<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#" style="color: #007bff;">MiniFace</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><button class="nav-link nav-button navigation" id="homePage" onclick="MainObject.navigationPage(this.id)">Home 
					<span class="sr-only">(current)</span>
				</button></li>
				
				<li class="nav-item"><button class="nav-link nav-button navigation" id="showFriends" onclick="MainObject.navigationPage(this.id)">Friends</button></li>
				
				<li class="nav-item"><button class="nav-link nav-button navigation" id="showGroups" onclick="MainObject.navigationPage(this.id)">Groups</button></li>

				<li class="nav-item">
						<button class="nav-link nav-button navigation" id="showVissiblePosts" onclick="MainObject.navigationPage(this.id)">Posts</button>		
				</li>
				<li class="nav-item">
						<button class="nav-link nav-button navigation" id="chatroom" onclick="MainObject.showTertiary(this.id)">Chat</button>		
				</li>
				<li class="nav-item">
						<button class="nav-link nav-button navigation" id="reportPage" onclick="MainObject.navigationPage(this.id)">Report</button>		
				</li>
				
		
			</ul>
		<div class="dropdown">
			<a id="nameDropdown" class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>
        	<div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
          		<a class="dropdown-item navigation" href="#" id="profilePage" onclick="MainObject.navigationPage(this.id)">View Profile</a>
         		<a class="dropdown-item" href="logout" id="logout">Logout</a>
         	</div>
		</div>	
		

		</div>
	</nav>
	<div class="loader"></div>
	<div id="primary" class="container"></div>
	<div id="secondary" class="container"></div>
	<div id="tertiary" class="container"></div>

	
	<%@ include file="partials/footer.jsp"%>
	
	<script src="js/fragments/mainPage.js"></script>

</body>
</html>