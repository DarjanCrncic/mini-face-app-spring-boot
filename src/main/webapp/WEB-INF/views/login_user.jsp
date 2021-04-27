<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<%@ include file="partials/header.jsp"%>

</head>
<body>
	<div class="login-form-div">
		<div class="card">
			<div class="card-header">Login</div>
			<div class="card-body">
				<form method="post" action="login">
					<div class="form-group">
						<label for="username">Username</label> <input type="text" class="form-control" name="username" placeholder="Username" value="${user.username}">
					</div>
					<div class="form-group">
						<label for="password">Password</label> <input type="password" class="form-control" name="password" placeholder="Password">
					</div>
					<button type="submit" class="btn btn-primary submit-button-login">Submit</button>
				</form>
			</div>
			<div class="card-footer">
				<a href="register">
					<button class="btn btn-secondary move-button">Register</button>
				</a> <a href="main">
					<button class="btn btn-secondary move-button">Main Page</button>
				</a>

				<div class="error-message-div">
					<c:if test="${param.error == true}">
						<div id="error">
							<p class="error">Ivalid username or password.</p>
						</div>
					</c:if>
				</div>
			</div>
		</div>



	</div>



	<%@ include file="partials/footer.jsp"%>

	<script type="text/javascript">
		
	</script>

</body>
</html>