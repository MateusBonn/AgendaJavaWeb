<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="model.JavaBeans"%>
<%@page import="java.util.ArrayList"%>
<%
	ArrayList<JavaBeans> list = (ArrayList<JavaBeans>) 
	request.getAttribute("contatos");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="Style.css">
<link rel="icon" href="images/phone.png" />
<title>Agenda de contatos</title>
</head>
<body>

	<h1>Agenda de contatos</h1>
	<a class="botaoInicial" href="NewContact.jsp">Novo contato</a>
	<br>
	<h1>Contatos de sua agenda</h1>
	<table>
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Fone</th>
				<th>E-mail</th>
			</tr>
		</thead>
		<tbody>
			<%for (int i=0; i<list.size(); i++){%>
			<tr>
				<td><%=list.get(i).getIdcon()%></td>
				<td><%=list.get(i).getNome() %></td>
				<td><%=list.get(i).getFone() %></td>
				<td><%=list.get(i).getEmail() %></td>
			</tr>	
			<%}%>
		</tbody>
	</table>

</body>
</html>