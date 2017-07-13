package com.mx.pdf2xls.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFUtility {
	/**
	 * Log de consola	
	 */
	private final Logger LOG = Logger.getLogger(PDFUtility.class);
	
	/**
	 * Convertir pdf a texto.
	 * @param fileName : url de pdfs
	 * @return parsedText
	 */
	public String pdftoText(String ruta){
		/* Variable donde se guardara el texto parseado */
		String parsedText;
		/* Objetos para perseo */
		PDFTextStripper pdfStripper;
	    PDDocument pdDoc = null;
		
        File f = new File(ruta);
  
        if (!f.isFile()) {
        	LOG.info("El archivo " + ruta + " no existe.");
            return null;
        }
  
        try {
            pdDoc = PDDocument.load(ruta);
            pdfStripper = new PDFTextStripper();
            parsedText = pdfStripper.getText(pdDoc);
        } catch (IOException e1) {
        	LOG.error("ERROR: "+ e1.getMessage(), e1);
            try {
            	if (pdDoc != null){
            		pdDoc.close();
            	}
               } catch (IOException e2) {
            	   LOG.error("ERROR: "+ e2.getMessage(), e2);
            }
            return "";
        }
        
        return parsedText;
	}
	
}