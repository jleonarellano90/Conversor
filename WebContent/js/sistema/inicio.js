/**
 * METODOS COMUNES
 * @author Jose Leon Arellano
 * @date 27/03/2015
 */
/* Arreglo de pdfs */
var arrPdfs = [];

/* METODO PARA CARGAR ARCHIVO */
if (window.FileReader) {
	var openFile = function(event) {
		var archivos = event.target.files;
		var tabla = document.getElementById("tabla");
		var total =  archivos.length;
		var reader = new FileReader();
		
		/* REINICIAR TABLA */
		tabla.innerHTML = "";
		arrPdfs = [];
		
		/* DEFINIR HEADERS*/
		var row0 = tabla.insertRow(0);
		var cell0 = row0.insertCell(0);
		cell0.innerHTML = "<b>Archivo</b>";
		var cell1 = row0.insertCell(1);
		cell1.innerHTML = "<b>Descripción</b>";
		var cell2 = row0.insertCell(2);
		cell2.innerHTML = " ";
		
		/* INSERTAR VALORES EN LA TABLA */
		for(var i=0; i<total; i++){
			var row = tabla.insertRow(i+1); //Insertar row en la tabla
			var ind = row.id = i+1; //Definir indice de row
			var pdf = archivos[i]; //Obtener archivo pdf
			arrPdfs.push(pdf.name); //Insertar archivo
			
			var val1 = row.insertCell(0); //Archivo
			val1.innerHTML = pdf.name; //Nombre archivo
			var val2 = row.insertCell(1); //Descripcion
			val2.innerHTML = "<b>Tipo: </b>" + pdf.type+ //Tipo archivo
						 "<br><b>Tamaño: </b>"+(pdf.size/1024).toFixed(2)+"KB";//Tamaño archivo
			var boton = row.insertCell(2); //Boton eliminar
			boton.innerHTML = "<input id='"+ind+"' name='"+pdf.name+"' type='button' class='readmore' alt='Eliminar' onclick='eliminaRow(this.id, this.name)' value='-'>";
			reader.readAsText(pdf);
		}
		
	};
}else{
	document.getElementById('vista_previa').innerHTML = "El navegador no soporta vista previa";
}

/* METODO PARA ELIMINAR ROW */
function eliminaRow(rowid, elem){
	var item = arrPdfs.indexOf(elem); //Obtener indice
	var row = document.getElementById(rowid); //Obtener elemento de la tabla
	
	row.parentNode.removeChild(row); //Eliminar row
    arrPdfs.splice(item,1,""); //Eliminar elemento del arreglo de pdfs
    arrPdfs = clean(arrPdfs); //Limpiar los valores vacios
    
    if(arrPdfs.length==0){ //Reinicia el contenido de tabla
    	tabla.innerHTML = "";
    }
}

/* METODO PARA CONECTAR AL CONTROLLER */
function convertir(){
	var pdfs = clean(arrPdfs); //Limpiar el arreglo
	
	if(pdfs.length==0){
		alert("No se han seleccionado archivos.");
	}else{
		document.forms["frmConvert"].action ="convertir.do?pdfs="+ pdfs;
		document.forms["frmConvert"].submit();
	}
}

/* METODO PARA ENVIO DE CORREOS */
function enviarMail(){
	var email = document.getElementById("email").value; //Obtener email
	var comment = document.getElementById("comment").value; //Obtener comment
	
	if(email.length==0){
		alert("Es necesario especificar un correo.");
	} else if(comment.length==0){
		alert("Es necesario especificar un mensaje.");
	}else{		
		document.forms["frmEmail"].action ="correo.do?email="+ email +"&comment="+ comment;
		document.forms["frmEmail"].submit();
	}
}

/*METODO PARA FALSY VALUES*/
function clean(array){
	var total = array.length; //Obtener total de elementos
	
	for(var i=0; i<total; i++){
		//Se compara logicamente el valor: NaN,false,"",null
		var ban = Boolean(array[i]);
		
		if(!ban){
			array.splice(i,1);
			/* Regresan los valores ya que al eliminar 
			 * se recorre el tamaño total del arreglo */
			i--;
			total--;
		}
	}
	return array;
}