/**
 * Business Object Compilador
 * @author Jose Leon Arellano
 * @date 20/04/2015
 */
package com.mx.pdf2xls.bo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.mx.pdf2xls.dto.CamposDTO;
import com.mx.pdf2xls.utils.ExcelPrincipal;
import com.mx.pdf2xls.utils.Reservadas;


public class CompiladorBO {
	/**
	 * Log de consola	
	 */
	private final Logger LOG = Logger.getLogger(CompiladorBO.class);
	
	/**
	 * Metodo para acortar texto de pdf.
	 * @param pdfTexto : texto a acotado
	 * @param urlPdf : url del archivo
	 * @param pdf : pdf a convertir
	 * @param urlExcel : url de salida
	 */
	protected void acotarTexto(String pdfTexto, String urlPdf, String pdf, String urlExcel){
		/* Lineas a procesar */
		List<String> lineas = new ArrayList<String>(Arrays.asList(pdfTexto.split("\n")));
		/* Titulos de operaciones */
		List<String> titOperaciones = new ArrayList<String>();
		
		/* Contadores */
		int saltos=0, ref=0, nue=0, mod=0, can=0, canpar=0, anul=0, reen=0;
		/* Bandera para paseos sin encabezado */
		int ban=0;
		/* Operacion temporal */
		String tmpOpe="";
		
		for(int i=0; i<lineas.size(); i++){
			String linea = lineas.get(i);
			char salto = linea.charAt(0);
			/*Eliminar enters*/
			if(Character.isSpaceChar(salto) && linea.length()==2){
				lineas.remove(i);
				saltos++;
				i--;
			}
			if(linea.contains(Reservadas.REFERENCIA)){
				ref++;
			}else if(linea.contains(Reservadas.REFERENCE)){
				ref++;
			}else if(linea.contains("NUEVA")){
				if(ban==0){
					tmpOpe = "NUEVA";
					titOperaciones.add(tmpOpe);
					nue++;
					ban=1;
				}else{
					ban=0;
				}
			}else if(linea.contains("MODIFICACION")){
				if(ban==0){
					tmpOpe="MODIFICACION";
					titOperaciones.add(tmpOpe);
					mod++;
					ban=1;
				}else{
					ban=0;
				}
			}else if(linea.contains("CANCELACION")){
				if(ban==0){
					tmpOpe="CANCELACION";
					titOperaciones.add(tmpOpe);
					can++;
					ban=1;
				}else{
					ban=0;
				}
			}else if(linea.contains("Cancelación Parcial")){
				if(ban==0){
					tmpOpe="Cancelación Parcial";
					titOperaciones.add(tmpOpe);
					canpar++;
					ban=1;
				}else{
					ban=0;
				}
			}else if(linea.contains("ANULACION")){
				if(ban==0){
					tmpOpe="ANULACION";
					titOperaciones.add(tmpOpe);
					anul++;
					ban=1;
				}else{
					ban=0;
				}
			}else if(linea.contains("REENVIO")){
				if(ban==0){
					tmpOpe="REENVIO";
					titOperaciones.add(tmpOpe);
					reen++;
					ban=1;
				}else{
					ban=0;
				}
			}
			
			/* Para las operaciones sin titular */
			if(linea.contains(Reservadas.C_ID_SERVICIO)||linea.contains(Reservadas.C_LOC_AGENCIA)){
				if(ban==1){
					ban=0;
				}else{
					titOperaciones.add(tmpOpe);
				}
			}	
			
			/* Eliminar HEADER */
			if(linea.contains(Reservadas.TURAVIA)){
				boolean result = false;
				while(!result || !linea.equals(Reservadas.HEADER)){
					lineas.remove(i);
					linea = lineas.get(i);
					Pattern patron = Pattern.compile("-{60,}\r");
					Matcher match = patron.matcher(linea);
					result = match.matches();
					if(result || linea.equals(Reservadas.HEADER)){
						lineas.remove(i);
					}
				}
			}
			
		}
		LOG.info(" + Operaciones a procesar:");
		LOG.info("  - Saltos: "+ saltos);
		LOG.info("  - Referencias: "+ ref);
		LOG.info("  - Nuevas: "+ nue);
		LOG.info("  - Modificaciones: "+ mod);
		LOG.info("  - Cancelaciones: "+ can);
		LOG.info("  - Cancelaciones Parciales: "+ canpar);
		LOG.info("  - Anulaciones: "+ anul);
		LOG.info("  - Reenvios: "+ reen);
		
		/* Convertir a String (quitar las ",") */
		String Text = "";
		for(String val : lineas){
			Text = Text + val;
		}
		
		/* Compilar texto acotado */
		separarOperaciones(Text, urlPdf, pdf, titOperaciones, urlExcel);
		
	}
	
	/**
	 * Metodo para separar operaciones.
	 * @param pdfTexto : texto a separar
	 * @param urlPdf : url del archivo
	 * @param pdf : pdf a convertir
	 * @param titOperaciones : titulos de operaciones
	 * @param urlExcel : url de salida
	 */
	private void separarOperaciones(String pdfTexto, String urlPdf, String pdf, List<String> titOperaciones, String urlExcel){
		LOG.info(" + Iniciando separarOperaciones() en CompiladorBO");
		LOG.info("  - Parametro titOperaciones: "+ titOperaciones);
		
		/* Separar pdfTexto por operaciones */
		List<String> lineas = new ArrayList<String>(Arrays.asList(pdfTexto.split(Reservadas.REFERENCE+"|"+
				Reservadas.REFERENCIA+"|"+
				Reservadas.NUEVA+"|"+
				Reservadas.MODIFICACION+"|"+
				Reservadas.CANCELACION+"|"+
				Reservadas.ANULACION+"|"+
				Reservadas.REENVIO+"|"+
				Reservadas.C_NUM_CONFIRMACION)));
		
		/* Lista de los campos de cada operacion */
		List<CamposDTO> lista = armarLista(lineas, urlPdf, pdf, titOperaciones, urlExcel);
		
		ExcelPrincipal exportar = new ExcelPrincipal();
		/* Exportar cada pdf */
		exportar.crearArchivo(urlExcel, lista);
		
	}
	
	/**
	 * Metodo para armar lista de registros.
	 * @param lineas : lineas a separar
	 * @param urlPdf : url del archivo
	 * @param pdf : pdf a convertir
	 * @param titOperaciones : titulos de operaciones
	 * @param urlExcel : url de salida
	 * @return lista : lista de operaciones separadas
	 */
	private List<CamposDTO> armarLista(List<String> lineas, String urlPdf, String pdf, List<String> titOperaciones, String urlExcel){
		
		/* Objeto para invocar separaciones */
		SeparadorBO separar = new SeparadorBO();
		/* Lista para almacenar operaciones no cubiertas */
		List<String> basura = new ArrayList<String>();
		/* Definir listado de datos por operacion */
		List<String> datos = null;
		/* Lista de los campos de cada operacion */
		List<CamposDTO> lista = new ArrayList<CamposDTO>();
		/* Campo de cada operacion */
		CamposDTO campo = new CamposDTO();		
		/* Definir variable para cada operacion */
		String operacion = null;
		/* Referencia temporal */
		String refTemporal = "";
		/* Nombre de ES temporal */
		String nomTemporal = "";
		/* Especificar el total de elementos de la lista */
		int total = lineas.size();
		/* Indice de operaciones */
		int iOperacion=0;
		
		for(int i=0;i<total;i++){
			operacion = lineas.get(i).trim();
			campo.setPdf(pdf);
			if(operacion.contains(Reservadas.NOMBRE)){
				datos = new ArrayList<String>(Arrays.asList(operacion.split("\r")));
				for(int j=0;j<datos.size();j++){
					/* Eliminar espacios en blanco y simbolos innecesarios */
					String linea = datos.get(j).replaceAll("[|]|(\\s{2,})"," ");
					linea = linea.replaceAll("\n|\r", " ");
					linea = linea.trim();
					if(linea.contains(Reservadas.NOMBRE)){
						campo = separar.separaReferencia(linea);
						refTemporal = campo.getReferencia();
						nomTemporal = campo.getNombre();
					}
					
				}
			}
			/* Entradas */
			if(operacion.contains(Reservadas.C_ENTRADA)){
				datos = new ArrayList<String>(Arrays.asList(operacion.split("\r")));
				campo.setServicio("ENTRADA");
				campo.setReferencia(refTemporal);
				campo.setNombre(nomTemporal);
				if(iOperacion < titOperaciones.size()){
					campo.setOperacion(titOperaciones.get(iOperacion++));
				}
				for(int j=0;j<datos.size();j++){
					/* Eliminar espacios en blanco y simbolos innecesarios */
					String linea = datos.get(j).replaceAll("[|]|(\\s{2,})"," ");
					linea = linea.replaceAll("\n|\r", " ");
					linea = linea.trim();
					campo = separar.separaEntrada(linea, campo);
				}
				lista.add(campo);
			/* Salidas */
			}else if(operacion.contains(Reservadas.C_SALIDA)){
				datos = new ArrayList<String>(Arrays.asList(operacion.split("\r")));
				campo.setServicio("SALIDA");
				campo.setReferencia(refTemporal);
				campo.setNombre(nomTemporal);
				if(iOperacion < titOperaciones.size()){
					campo.setOperacion(titOperaciones.get(iOperacion++));
				}
				for(int j=0;j<datos.size();j++){
					/* Eliminar espacios en blanco y simbolos innecesarios */
					String linea = datos.get(j).replaceAll("[|]|(\\s{2,})"," ");
					linea = linea.replaceAll("\n|\r", " ");
					linea = linea.trim();
					campo = separar.separaSalida(linea, campo);
				}
				lista.add(campo);
			/* Paseos */
			}else if(operacion.contains(Reservadas.C_LOC_AGENCIA)){
				datos = new ArrayList<String>(Arrays.asList(operacion.split("\r")));
				campo.setServicio("PASEO");
				if(iOperacion < titOperaciones.size()){
					campo.setOperacion(titOperaciones.get(iOperacion++));
				}
				for(int j=0;j<datos.size();j++){
					/* Eliminar espacios en blanco y simbolos innecesarios */
					String linea = datos.get(j).replaceAll("[|]|(\\s{2,})"," ");
					linea = linea.replaceAll("\n|\r", " ");
					linea = linea.trim();
					//Variable temporal para obtener id_servicio y nombre
					String temp="";
					if(j>0){
						temp = datos.get(j-1).replaceAll("[|]|(\\s{2,})"," ");
						temp = temp.replaceAll("\n|\r", " ");
						temp = temp.trim();
					}
					if(linea.contains(Reservadas.C_OBSERVACIONES)){
						String hotel = "";
						int cont = 1;
						while(!linea.equals("\r") && (j+cont) < datos.size()){
							linea = datos.get(j + cont++).replaceAll("[|]|(\\s{2,})"," ");
							linea = linea.replaceAll("\n|\r", " ");
							linea = linea.trim();
							hotel = hotel +" "+ linea;
						}
						campo = separar.separaPaseo(hotel, campo, "hotel");
					}else{
						campo = separar.separaPaseo(linea, campo, temp);
					}
				}
				lista.add(campo);
			/* Basura */
			}else{
				basura.add("archivo: {"+ urlPdf +"} - datos: {\n "+operacion+" \n}");
			}
			
			/* Reiniciar campos */
			campo = new CamposDTO();
		}
		
		/* Generar archivo para la basura */
		generarBasura(basura, urlExcel);
		
		return lista;
	}
	
	/**
	 * Metodo para generar basura de los pdfs.
	 * @param basura : lista de lineas para basura
	 * @param urlExcel : url de salida
	 */
	private void generarBasura(List<String> basura, String urlExcel){
		LOG.info(" + Basura generada en: "+urlExcel+"basura.txt");
		
		try {
            File archivo = new File(urlExcel+"basura.txt");
            FileWriter escribir = new FileWriter(archivo,true);
            /* Escribir basura en archivo */
            for(String line : basura){
    			escribir.write(line+"\n");
    		}
            escribir.close();
             
        } catch (IOException e) {
        	LOG.error("ERROR: "+ e.getMessage(), e);
        }
		
	}
	
}