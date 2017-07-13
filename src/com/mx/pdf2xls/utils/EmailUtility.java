/**
 * Utility Email
 * @author Jose Leon Arellano
 * @date 10/09/2015
 */
package com.mx.pdf2xls.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class EmailUtility {
	/**
	 * Log de consola	
	 */
	private final Logger LOG = Logger.getLogger(EmailUtility.class);
	/**
	 * Metódo para enviar correo electronico.
	 * @param email : correo del cliente
	 * @param comment : comentario del cliente
	 * @throws AddressException : excepcion de direccion de correo
	 * @throws MessagingException : excepcion de mensaje de correo
	 */
	public void enviarCorreo(String email, String comment)throws AddressException, MessagingException {
		LOG.info(" + Iniciando enviarCorreo() en EmailUtility");
		LOG.info("  - Parametro email: "+ email);
		
		/* Definir las propiedades del servidor SMTP */
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "mail.redfoxintelligence.com");
		properties.put("mail.smtp.port", "26");
		
		/* Definir la instancia de sesion */
		Session session = Session.getInstance(properties);
		/* Crear un nuevo mensaje de email */
		Message msg = new MimeMessage(session);
		/* Dar formato a la fecha */
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy, HH:mm");
				
		msg.setFrom(new InternetAddress(email));
		InternetAddress[] toAddresses = { new InternetAddress("contacto@redfoxintelligence.com") };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject("Inicidencia PDFtoXLS");
		msg.setSentDate(new Date());
		msg.setText("Fecha: "+ dateFormat.format(new Date()) +"\n\nIncidencia:\n "+ commentFormat(comment)+"");
		
		/* Enviar el email */
		Transport.send(msg);
	}
	
	/**
	 * Metodo para dar formato al mensaje.
	 * @param comment : el mensaje a separar
	 * @return comment
	 */
	private String commentFormat(String comment){
		/* Obtener longitud total del mensaje */
		int total = comment.length();
		/* Numero de columnas */
		int cols = 50;
		/* Numero de lineas */
		int rows = total/cols;
		/* Indice */
		int i = 0;
		/* Mensaje formateado */
		String temp = "";
		
        if(rows>0){
            while(i<total){
                if(i+cols < total){
                    temp = temp + comment.substring(i, i+cols)+"\n ";
                }else if(i+cols >=total){
                    temp = temp + comment.substring(i, total);
                }
                i = i + cols;
            }
        }else{
            temp = comment;
        }
        /* Mensaje formateado */
        return temp;
	}
}