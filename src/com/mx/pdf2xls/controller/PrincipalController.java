/**
 * Controller Principal
 * @author Jose Leon Arellano
 * @date 27/03/2015
 */
package com.mx.pdf2xls.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mx.pdf2xls.bo.PrincipalBO;
import com.mx.pdf2xls.utils.EmailUtility;

@Controller
@RequestMapping("/principal")
public class PrincipalController {
	/** Log de consola */
	private final Logger LOG = Logger.getLogger(PrincipalController.class);
	/** URL para pdfs */
	private final String URL_PDFS = "C:/pdfs/";
	/** URL para exceles */
	private final String URL_EXCEL = "C:/excel/";
	
	/**
	 * Metodo de Inicio.
	 * @param req : entrada de datos del JSP
	 * @param modelo : mapeo de datos salida
	 * @return "inicio"
	 * @throws ServletException : Excepcion de Servlet
	 */
	@RequestMapping("/inicio.do")
	public String iniciarApp(HttpServletRequest req, ModelMap modelo)
		throws ServletException{
		LOG.info("* Inicio de la aplicacion PDFtoXLS");
		
		String titulo="CONVERTIR";
		modelo.addAttribute("titulo", titulo);
		
		/* Regresar a la vista de inicio */
		return "inicio";
	}
	/**
	 * Metodo para inicializar la conversión.
	 * @param req : entrada de datos del JSP
	 * @param modelo : mapeo de datos de salida
	 * @return "inicio"
	 * @throws ServletException : Excepcion de Servlet
	 */
	@RequestMapping("/convertir.do")
	public String convertirXLS(HttpServletRequest req, @RequestParam("archivos") MultipartFile[] files, ModelMap modelo)
		throws ServletException{
		LOG.info("* Iniciando convertirXLS() en PrincipalController");
		/* Generar lista para archivos PDF */
		List<String> arrPdfs = new ArrayList<String>();
		
		try{
			for(MultipartFile file : files){
				/* Crear el directorio de almacenamiento */
				File dirPdfs = new File(URL_PDFS);
	            if (!dirPdfs.exists()){
	            	dirPdfs.mkdirs();
	            }
	            /* Crear el archivo en el servidor */
	            File pdf = new File(URL_PDFS+file.getOriginalFilename());
	            arrPdfs.add(file.getOriginalFilename());
	            byte[] bytes = file.getBytes();
	            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(pdf));
	            stream.write(bytes);
	            stream.close();
			}
		}catch(IOException e){
			LOG.error("PrincipalController.convertirXLS() - ERROR: "+ e.getMessage());
		}
		/* Instancia para clase PDFtoXLSBO */
		PrincipalBO principalBO = new PrincipalBO();
		/* Se obtiene el resultado de la operacion */
		String resultado = principalBO.convertirXLS(URL_PDFS, URL_EXCEL, arrPdfs);
		
		/* Variable para mensaje de la pagina */
		String titulo="CONVERTIDOS";
		
		/* Se agrega la lista de pdfs convertidos al modelo de la vista JSP*/
		modelo.addAttribute("convertidos", resultado);
		/* Se agrega el titulo al modelo de la vista JSP */
		modelo.addAttribute("titulo", titulo);
		
		LOG.info("* Terminando convertirXLS() en PrincipalController");
		/* Regresar a la vista de inicio */
		return "inicio";
	}
	
	/**
	 * Método para inicializar formulario contacto.
	 * @param req : entrada de datos del JSP
	 * @param modelo : mapeo de datos de salida
	 * @return "contacto"
	 */
	@RequestMapping("/contacto.do")
	public String contacto(HttpServletRequest req, ModelMap modelo){
		LOG.info("* Iniciando contacto() en PrincipalController");
		
		String titulo="CONTACTO";
		modelo.addAttribute("titulo", titulo);
		
		return "contacto";
	}
	
	/**
	 * Método para inicializar envio de correo.
	 * @param req : entrada de datos del JSP
	 * @param modelo : mapeo de datos de salida
	 * @return "resultado"
	 */
	@RequestMapping("/correo.do")
	public String enviarCorreo(HttpServletRequest req, ModelMap modelo){
		LOG.info("* Iniciando enviarCorreo() en PrincipalController");
		String email = req.getParameter("email");
		String comment = req.getParameter("comment");
		String titulo="ENVIO DE CORREO";
		String operacion = "";
		String resultado = "";
		EmailUtility correo = new EmailUtility();
		
		try {
			correo.enviarCorreo(email, comment);
			operacion = "Mensaje Enviado: ";
			resultado="El email fue enviado exitosamente. <br><textarea style='border:0 none #FFF; "
					+"background:#FFF; resize: none' rows='8' maxlength='600' readonly>"
					+ comment
					+"</textarea>";
		} catch (AddressException ex1) {
			LOG.error(":: Hubo un error: "+ ex1.getMessage());
			operacion = "ERROR";
			resultado = ":: Hubo un error: " + ex1.getMessage();
		} catch (MessagingException ex2) {
			LOG.error(":: Hubo un error: "+ ex2.getMessage());
			operacion = "ERROR";
			resultado = ":: Hubo un error: " + ex2.getMessage();
		}
		
		modelo.addAttribute("titulo", titulo);
		modelo.addAttribute("operacion", operacion);
		modelo.addAttribute("resultado", resultado);
		
		LOG.info("* Terminando enviarCorreo() en PrincipalController");
		return "resultado";
	}
}