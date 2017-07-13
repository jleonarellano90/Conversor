/**
 * Utility ExcelPrincipal
 * @author Moises Espino
 * @date 19/04/2015
 */
package com.mx.pdf2xls.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mx.pdf2xls.dto.CamposDTO;

public class ExcelPrincipal {
	/**
	 * Log de consola	
	 */
	private final Logger LOG = Logger.getLogger(ExcelPrincipal.class);

	/**
	 * Metodo para crear archivo Excel.
	 * @param url2 : url de salida
	 * @param lista : lista de registros  
	 */
	public void crearArchivo(String url2, List<CamposDTO> lista) {
		LOG.info(" + Iniciando crearArchivo() en ExcelPrincipal");
		
		for (int i = 0; i < lista.size(); i++) {
			/* Obtener la fecha */
			String fecha = lista.get(i).getFecha() == null ? "00-NA-00" : lista.get(i).getFecha();
			/* Traducción de la fecha */
			if (fecha.contains("JAN")) {
				fecha = fecha.replace("JAN", "ENE");
			} else if (fecha.contains("APR")) {
				fecha = fecha.replace("APR", "ABR");
			} else if (fecha.contains("AUG")) {
				fecha = fecha.replace("AUG", "AGO");
			} else if (fecha.contains("DEC")) {
				fecha = fecha.replace("DEC", "DIC");
			}
			
			/* Patrón para la fecha */
			Pattern pat = Pattern.compile("[0-9]+-([a-zA-Z]+-[0-9]+)");
			/* Comparador para la fecha */
			Matcher mat = pat.matcher(fecha);
			/* Objeto para reporte de Excel */
			ExcelReporte reporte = new ExcelReporte();

			try {
				if (mat.matches()) {
					/* Nombre de archivo */
					String archivo = mat.group(1) + ".xls";
					LOG.info("  - archivo: "+ archivo);
					/* Crear archivo excel */
					File excel = new File(url2, archivo);

					if (excel.exists()) {
						reporte.datosExcel(url2 + archivo, lista, 1, i);
					} else {
						if (excel.createNewFile()) {
							reporte.datosExcel(url2 + archivo, lista, 0, i);
						} else {
							LOG.error("ERROR: No se ha podido crear " + archivo);
						}
					}
				}

				else {
					LOG.error("ERROR: Formato de fecha no valido");
				}

			}

			catch (IOException e) {
				LOG.error("ERROR: "+ e.getMessage(), e);
			}

		}
	}
	
}