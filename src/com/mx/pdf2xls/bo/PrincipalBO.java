/**
 * Business Object Principal
 * @author Jose Leon Arellano
 * @date 27/03/2015
 */
package com.mx.pdf2xls.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mx.pdf2xls.utils.PDFUtility;
import com.mx.pdf2xls.utils.Reservadas;

public class PrincipalBO {
	/**
	 * Log de consola
	 */
	private final Logger LOG = Logger.getLogger(PrincipalBO.class);
	
	/**
	 * Metodo main para conversion.
	 * @param urlPdfs : url de pdfs
	 * @param urlExcel : url de salida
	 * @param pdfs : array de pdfs a convertir
	 * @return mensaje : resultado de la operacion
	 */
	public String convertirXLS(String urlPdfs, String urlExcel, List<String> arrPdfs){
		LOG.info("** Iniciando convertirXLS() en PrincipalBO");
		LOG.info("*** Parametro URL PDFs: "+ urlPdfs);
		LOG.info("*** Parametro URL de Salida: "+ urlExcel);
		
		/* Enviamos el arreglo de pdfs a convertir */
		String mensaje = pdfConverter(urlPdfs, urlExcel, arrPdfs);
		
		/* Mensaje de resultado */
		return mensaje;
	}	
	/**
	 * Metodo para realizar la conversión.
	 * @param urlPdfs : url de pdfs
	 * @param urlExcel : url de salida
	 * @param arrPdfs : array de pdfs a convertir
	 * @return mensaje : resultado de la operacion
	 */
	private String pdfConverter(String urlPdfs, String urlExcel, List<String> arrPdfs){
		/* Lista de pdfs exitosos */
		List<String> exitosos = new ArrayList<String>();
		/* Lista de pdfs erroneos */
		List<String> erroneos = new ArrayList<String>();
		
		for(String pdf : arrPdfs){
			/* Variable */
			String ruta = urlPdfs + pdf;
			/* Objeto para obtener texto parseado */
			PDFUtility pdfConverter = new PDFUtility();
			/*Se obtiene el texto parseado*/
			String pdfTexto = pdfConverter.pdftoText(ruta);
			try{
				if(pdfTexto == null){
					LOG.error("ERROR: El archivo: ["+ pdf +"] no existe.");
					/* Agregar pdf erroneo */
					erroneos.add(pdf);
				}else{
					/* Convertir cada pdf */
					CompiladorBO compilar = new CompiladorBO();
					pdfTexto = pdfTexto.substring(pdfTexto.indexOf(Reservadas.HEADER)+82);
					compilar.acotarTexto(pdfTexto, ruta, pdf, urlExcel);
					
					/* Modificar nombre de pdfs procesados */
					procesadosPdf(urlPdfs, pdf);
		            
					LOG.info("*** Conversión Exitosa: ["+ pdf +"]");
					/* Agregar pdf exitoso */
					exitosos.add(pdf);
				}
			}catch(Exception e){
				LOG.error("ERROR: "+ e.getMessage(), e);
				/* Agregar pdf erroneo */
				erroneos.add(pdf);
			}
		}
		
		LOG.info("*** Archivos convertidos : "+ exitosos);
		LOG.info("*** Archivos erroneos: "+ erroneos);
		return "Archivos convertidos : "+ exitosos +"<br>Archivos erroneos: "+ erroneos;
	}
	/**
	 * Verificar archivos procesados.
	 * @param ruta : ruta del archivo
	 * @param pdf : nombre del pdf
	 */
	private void procesadosPdf(String ruta, String pdf){
		LOG.info("*** PDF procesado: "+ ruta + pdf);
		
		/* PDF original */
		File oldfile;
		/* PDF procesado */
		File newfile;
		
		oldfile = new File(ruta + pdf);
		newfile = new File(ruta +"PROCESADO_"+ pdf);
		oldfile.renameTo(newfile);
    }
}