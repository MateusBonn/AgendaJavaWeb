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
	<h1>Criar novo contato</h1>
	<form class="frmContato" name="frmContato" action="">
		<table>
			<tr>
				<td><input type="text" name="name" placeholder="Nome">
				</td>
			</tr>
			<tr>
				<td><input type="text" name="phone" placeholder="Fone">
				</td>
			</tr>
			<tr>
				<td><input class="caixaEmail" type="text" name="email" placeholder="E-mail">
				</td>
			</tr>
		</table>
		<input type="button" value="Adicionar" class="botaoInicial" onclick="validar()">
	</form>
</body>
</html>