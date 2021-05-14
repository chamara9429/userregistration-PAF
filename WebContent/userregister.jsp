<%@ page import="com.userregister.user"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/register.js"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">

				<div class="container pt-5 pb-5">

					<center>
						<u><h4 class="mb-3 pb-3">Registration Form</h4></u>
					</center>
					<form class="needs-validation" novalidate id="registrationform">
						<input type='hidden' id='hiddenIDSave' name='hiddenIDSave'
							value=''>
						<div class="row">
							
						</div>

						<div class="mb-3">
							<label for="name">User Name:</label> <input type="text"
								class="form-control" id="name" name="name">

						</div>

						<div class="mb-3">
							<label for="email">Email:</label>
							<textarea class="form-control" id="email" name="email"></textarea>
						</div>

						<div class="mb-3">
							<label for="contact">Contact:</label>
							<textarea class="form-control" id="contact" name="contact"></textarea>
						</div>

						<div class="row">
							<div class="col-md-5 mb-3">
								<label for="type">Type:</label> <select
									class="custom-select d-block w-100" id="type" name="type">
									<option>-</option>
									<option>User</option>
									<option>Resercher</option>
									<option>Funder</option>
								</select>

							</div>


						</div>


						<button class="btn btn-primary btn-lg btn-block" type="button"
							id="adduser">SAVE USER</button>
					</form>
				</div>
			</div>

			<br> <br> <br> <br> <br> <br>

		</div>
		<div id='alertSuccess' name='alertSuccess' class='alert alert-success'></div>
		<div id='alertError' name='alertError' class='alert alert-danger'></div>
		<br>

		<div id="divuserGrid">
			<%
				user userObject = new user();
				out.print(userObject.getUser());
			%>
		</div>
	</div>
</body>
</html>