function trocarImagem(elemento, novaImagem) {
	elemento.src = novaImagem;
}

function validar() {
	let nome = frmContato.name.value
	let fone = frmContato.phone.value
	let email = frmContato.email.value

	if (nome === "") {
		alert('Campo nome obrigatório!')
		frmContato.name.focus()
		return false
	}
	if (fone === "") {
		alert('Campo fone obrigatório!')
		frmContato.phone.focus()
		return false
	}
	if (email === "") {
		alert('Campo email obrigatório!')
		frmContato.email.focus()
		return false
	}
	else {
		document.forms["frmContato"].submit()
	}
}

function confirmar(idcon) {

	let resposta = confirm("Você confirma o cancelamento deste contato? ")

	if (resposta === true) {
		window.location.href = "delete?idcon=" + idcon
	}
}

/*function validarExiste(msg){
	validar()
	if(msg === null){
		window.location.href = "insert"
	}
	alert(msg)
}

function enviarFormulario(event) {
  event.target.submit(); 
}*/