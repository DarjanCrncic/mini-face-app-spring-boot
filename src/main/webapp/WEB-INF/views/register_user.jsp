
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>

<script src="js/libs/jquery-3.5.1.js" type="text/javascript"></script>
<%@ include file="partials/header.jsp"%>

</head>
<body>
	<div class="register-form-div">
		<div class="card">
			<div class="card-header">Register</div>
			<div class="card-body">
				<form method="post" action="register">
					<div class="form-group">
						<input type="text" class="form-control" name="name" placeholder="First Name" value="${faceUser.name}">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" name="surname" placeholder="Last Name" value="${faceUser.surname}">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" name="username" placeholder="Username" value="${faceUser.username}">
					</div>
					<div class="form-group">
						<input type="email" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Enter email" value="${faceUser.email}"> 
						<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
					</div>
					<div class="form-group">
						<input type="password" class="form-control" name="password" placeholder="Password" value="${faceUser.password}">
					</div>
					<button type="submit" class="btn btn-primary submit-button-registration">Submit</button>

				</form>
			</div>
			<div class="card-footer">
				<a href="login">
					<button class="btn btn-secondary move-button">Login</button>
				</a> <a href="main">
					<button class="btn btn-secondary move-button">Main Page</button>
				</a>

				<div class="error-message-div">

					<ul>
						<c:forEach items="${errors}" var="error">
							<li class="error"><c:out value="${error.defaultMessage}" /></li>
						</c:forEach>
					</ul>
					<p class="error"><c:out value="${constraintError}" /></p>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="partials/footer.jsp"%>
</body>
</html>