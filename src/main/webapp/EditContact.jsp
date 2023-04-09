<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="Style.css">
<link rel="icon" href="images/phone.png" />
<script src="scripts/ScreenBegin.js"></script>
<title>Agenda de contatos</title>
</head>
<body>
	<h1>Editar contato</h1>
	<form class="frmContato" name="frmContato" action="update">
		<table>

			<tr>
				<td><input type="text" name="idcon" readonly
					value="<%out.print(request.getAttribute("idcon"));%>"></td>
			<tr>
				<td><input type="text" name="name"
					value=<%out.print(request.getAttribute("name"));%>></td>
			</tr>
			<tr>
				<td><input type="text" name="phone"
					value=<%out.print(request.getAttribute("phone"));%>></td>
			</tr>
			<tr>
				<td><input class="caixaEmail" type="text" name="email"
					value=<%out.print(request.getAttribute("email"));%>></td>
			</tr>
		</table>
		<input type="button" value="Salvar" class="botaoInicial"
			onclick="validar()"> <a type="button" class="botaoInicial"
			href="Index.jsp">Cancelar</a>
	</form>
</body>
</html>