<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Agenda de contatos</title>
<%!String iconeBase = "images/Agenda.png";%>
<%!String iconeEnd = "images/phone.png";%>
<link rel="icon" href=<%=iconeEnd%> />
<link rel="stylesheet" href="Style.css">
<script src="scripts/ScreenBegin.js"></script>
</head>
<body>
	<div id="inicio">
		<h1>Agenda de contatos</h1>
		<img class="imgBegin" src=<%=iconeBase%>>
	</div>
	<div class="container">
		<a href="main" class="botaoInicial">Acessar</a>
	</div>
</body>
</html>