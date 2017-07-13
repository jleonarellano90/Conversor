/**
 * Utility ExcelReporte
 * @author Moises Espino
 * @date 19/04/2015
 */
package com.mx.pdf2xls.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.mx.pdf2xls.dto.CamposDTO;

public class ExcelReporte {
	/**
	 * Log de consola
	 */
	private final Logger LOG = Logger.getLogger(ExcelReporte.class);

	/**
	 * Metodo para ingresar datos en archivo Excel.
	 * @param url2 : url de salida
	 * @param lista : lista de registros
	 * @param ban : bandera para saber si el archivo existe o no
	 * @param i : indice de registros 
	 * @throws IOException 
	 */
	protected void datosExcel(String url2, List<CamposDTO> lista, int ban, int i) throws IOException {
		LOG.info(" + Iniciando datosExcel() en ExcelReporte");
		LOG.info("  - Existe el archivo : "+ ban);
		LOG.info("  - Indice de registro: "+ i);
		
		String valor = "";
		Workbook libro = new HSSFWorkbook();
		HSSFCellStyle style = (HSSFCellStyle) libro.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.RED.index);

		if (ban == 0) {
			FileOutputStream archivo = new FileOutputStream(url2);
			/*
			 * Utilizamos la clase Sheet para crear una nueva hoja de trabajo
			 * dentro del libro que creamos anteriormente
			 */
			Sheet hoja = libro.createSheet("Registros");
			/* La clase Row nos permitira crear las filas */
			Row fila = hoja.createRow(0);
			/* Se define el nombre de las columnas (encabezado) */
			String encabezado[] = { "Fecha", "PDF", "Operacion", "Referencia",
					"IdServicio", "Servicio", "Nombre", "Pax", "Modalidad",
					"Tipo de servicio", "Hotel", "Vuelo", "Hr",
					"Hora de recogida", "Piso", "Guia", "OPC", "Notas", "Costo" };
			/* Se crea la fila del encabezado de datos */
			for (int c = 0; c < encabezado.length; c++) {
				/* Creamos la celda a partir de la fila actual */
				Cell celda = fila.createCell(c);
				/* Si la fila es la numero 0, estableceremos los encabezados */
				celda.setCellValue(encabezado[c]);
			}
			/* Se crean los filtros en los encabezados de cada columna */
			hoja.setAutoFilter(CellRangeAddress.valueOf("A1:R1"));
			fila = hoja.createRow(1);

			// for(int c=0;c<lista.size();c++)
			// {
			Cell celda = fila.createCell(0);
			valor = lista.get(i).getFecha() == null ? "00-NA-00" : lista.get(i).getFecha();
			/* Traducción de la fecha */
			if (valor.contains("JAN")) {
				valor = valor.replace("JAN", "ENE");
			} else if (valor.contains("APR")) {
				valor = valor.replace("APR", "ABR");
			} else if (valor.contains("AUG")) {
				valor = valor.replace("AUG", "AGO");
			} else if (valor.contains("DEC")) {
				valor = valor.replace("DEC", "DIC");
			}
			celda.setCellValue(valor);

			celda = fila.createCell(1);
			valor = (lista.get(i).getPdf()==null) ? "":lista.get(i).getPdf();
			celda.setCellValue(valor);

			celda = fila.createCell(2);
			valor = (lista.get(i).getOperacion() == null) ? "" : lista.get(i).getOperacion();
			if (valor.equals("CANCELACION") || valor.equals("Cancelación Parcial")) {
				celda.setCellStyle(style);
			}
			celda.setCellValue(valor);

			celda = fila.createCell(3);
			valor = (lista.get(i).getReferencia() == null) ? "" : lista.get(i).getReferencia();
			celda.setCellValue(valor);

			celda = fila.createCell(4);
			valor = (lista.get(i).getIdServicio() == null) ? "" : lista.get(i).getIdServicio();
			celda.setCellValue(valor);

			celda = fila.createCell(5);
			valor = (lista.get(i).getServicio() == null) ? "" : lista.get(i).getServicio();
			celda.setCellValue(valor);

			celda = fila.createCell(6);
			valor = (lista.get(i).getNombre() == null) ? "" : lista.get(i).getNombre();
			celda.setCellValue(valor);

			celda = fila.createCell(7);
			valor = (lista.get(i).getPaxes() == null) ? "" : lista.get(i).getPaxes();
			celda.setCellValue(valor);

			celda = fila.createCell(8);
			valor = (lista.get(i).getModalidad() == null) ? "" : lista.get(i).getModalidad();
			celda.setCellValue(valor);

			celda = fila.createCell(9);
			valor = (lista.get(i).getTipoServicio() == null) ? "" : lista.get(i).getTipoServicio();
			celda.setCellValue(valor);

			celda = fila.createCell(10);
			valor = (lista.get(i).getHotel() == null) ? "" : lista.get(i).getHotel();
			celda.setCellValue(valor);

			celda = fila.createCell(11);
			valor = (lista.get(i).getVuelo() == null) ? "" : lista.get(i).getVuelo();
			celda.setCellValue(valor);

			celda = fila.createCell(12);
			valor = (lista.get(i).getHora() == null) ? "" : lista.get(i).getHora();
			celda.setCellValue(valor);

			celda = fila.createCell(13);
			valor = (lista.get(i).getHoraRecogida() == null) ? "" : lista.get(i).getHoraRecogida();
			celda.setCellValue(valor);

			// }
			/* Escribimos en el libro */
			libro.write(archivo);
			/* Cerramos el flujo de datos */
			archivo.close();
			/* Y abrimos el archivo con la clase Desktop */
			// Desktop.getDesktop().open(archivoXLS);
		}

		else {
			FileInputStream archivo = new FileInputStream(url2);
			POIFSFileSystem file = new POIFSFileSystem(archivo);
			HSSFWorkbook libro1 = new HSSFWorkbook(file);
			HSSFSheet hoja = libro1.getSheetAt(0);
			int h = hoja.getLastRowNum();
			Row fila = hoja.createRow(h + 1);
			HSSFCellStyle style1 = (HSSFCellStyle) libro1.createCellStyle();
			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style1.setFillForegroundColor(HSSFColor.RED.index);

			// for(int c=0;c<lista.size();c++){
			Cell celda = fila.createCell(0);
			valor = lista.get(i).getFecha() == null ? "00-NA-00" : lista.get(i).getFecha();
			/* Traducción de la fecha */
			if (valor.contains("JAN")) {
				valor = valor.replace("JAN", "ENE");
			} else if (valor.contains("APR")) {
				valor = valor.replace("APR", "ABR");
			} else if (valor.contains("AUG")) {
				valor = valor.replace("AUG", "AGO");
			} else if (valor.contains("DEC")) {
				valor = valor.replace("DEC", "DIC");
			}
			celda.setCellValue(valor);
			
			celda = fila.createCell(1);
			valor = (lista.get(i).getPdf()==null) ? "":lista.get(i).getPdf();
			celda.setCellValue(valor);

			celda = fila.createCell(2);
			valor = (lista.get(i).getOperacion() == null) ? "" : lista.get(i)
					.getOperacion();
			if (valor.equals("CANCELACION") || valor.equals("Cancelación Parcial")) {
				celda.setCellStyle(style1);
			}
			celda.setCellValue(valor);

			celda = fila.createCell(3);
			valor = (lista.get(i).getReferencia() == null) ? "" : lista.get(i)
					.getReferencia();
			celda.setCellValue(valor);

			celda = fila.createCell(4);
			valor = (lista.get(i).getIdServicio() == null) ? "" : lista.get(i)
					.getIdServicio();
			celda.setCellValue(valor);

			celda = fila.createCell(5);
			valor = (lista.get(i).getServicio() == null) ? "" : lista.get(i)
					.getServicio();
			celda.setCellValue(valor);

			celda = fila.createCell(6);
			valor = (lista.get(i).getNombre() == null) ? "" : lista.get(i)
					.getNombre();
			celda.setCellValue(valor);

			celda = fila.createCell(7);
			valor = (lista.get(i).getPaxes() == null) ? "" : lista.get(i)
					.getPaxes();
			celda.setCellValue(valor);

			celda = fila.createCell(8);
			valor = (lista.get(i).getModalidad() == null) ? "" : lista.get(i)
					.getModalidad();
			celda.setCellValue(valor);

			celda = fila.createCell(9);
			valor = (lista.get(i).getTipoServicio() == null) ? "" : lista
					.get(i).getTipoServicio();
			celda.setCellValue(valor);

			celda = fila.createCell(10);
			valor = (lista.get(i).getHotel() == null) ? "" : lista.get(i)
					.getHotel();
			celda.setCellValue(valor);

			celda = fila.createCell(11);
			valor = (lista.get(i).getVuelo() == null) ? "" : lista.get(i)
					.getVuelo();
			celda.setCellValue(valor);

			celda = fila.createCell(12);
			valor = (lista.get(i).getHora() == null) ? "" : lista.get(i)
					.getHora();
			celda.setCellValue(valor);

			celda = fila.createCell(13);
			valor = (lista.get(i).getHoraRecogida() == null) ? "" : lista
					.get(i).getHoraRecogida();
			celda.setCellValue(valor);
			// }

			FileOutputStream outFile = new FileOutputStream(url2);
			libro1.write(outFile);
			outFile.close();
		}
		
	}

}
