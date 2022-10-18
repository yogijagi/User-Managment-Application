<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script>
</script>


</head>
<body>
	<div align="center">
		<h3>Unlock Account Here</h3>
		
		<font color='green'>${succMsg}</font>
		<font color='red'>${failMsg}</font>
		
		<form action="forgotPwd" method="POST">
			<table>
				<tr>
					<td>Email : </td>
					<td><input type="text" name="email"></td>
				</tr>				
				<tr>
					<td><input type="reset" value="Reset"></td>
					<td><input type="submit" value="Submit" id="Recover Password"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>