/**
 * Business Object Separador
 * @author Jose Leon Arellano
 * @date 20/04/2015
 */
package com.mx.pdf2xls.bo;

import org.apache.log4j.Logger;

import com.mx.pdf2xls.dto.CamposDTO;
import com.mx.pdf2xls.utils.Reservadas;

public class SeparadorBO {
	/**
	 * Log de consola	
	 */
	private final Logger LOG = Logger.getLogger(SeparadorBO.class);
	
	/**
	 * Metodo para separar lineas de Referencia.
	 * @param linea : la linea a leer
	 * @return respuesta separada
	 */
	protected CamposDTO separaReferencia(String linea){
		LOG.info(" + Iniciando separaReferencia() en SeparadorBO");
		LOG.info("  - linea: "+ linea);
		
		CamposDTO respuesta = new CamposDTO();
		String[] separadas = linea.split("Nombre:");
		
		/* Referencia*/
		respuesta.setReferencia(separadas[0].trim());
		//Nombre
		respuesta.setNombre(separadas[1].trim());
		
		return respuesta;
	}	
	/**
	 * Metodo para separar lineas de Entradas.
	 * @param linea : la linea a leer
	 * @param input : el DTO de entrada
	 * @return input con valores
	 */
	protected CamposDTO separaEntrada(String linea, CamposDTO input){
		LOG.info(" + Iniciando separaEntrada() en SeparadorBO");
		LOG.info("  - linea: "+ linea);
		
		String[] separador;
		
		if(linea.contains(Reservadas.C_ID_SERVICIO)){
			separador = linea.split(Reservadas.C_ID_SERVICIO);
			input.setIdServicio(separador[1].trim());
			
		}else if(linea.contains(Reservadas.C_TIPO_SERV)){
			separador = linea.split(Reservadas.C_TIPO_SERV+"|"+Reservadas.C_TRANSPORTE);
			input.setTipoServicio(separador[1].trim());
			
		}else if(linea.contains(Reservadas.C_ENTRADA)){
			separador = linea.split(Reservadas.C_ENTRADA+"|"+Reservadas.C_PAXES);
			input.setFecha(separador[1].trim());
			input.setPaxes(separador[2].trim());
			
		}else if(linea.contains(Reservadas.C_TRANSPORTE)){
			separador = linea.split(Reservadas.C_TRANSPORTE+"|"+Reservadas.C_HORA_LLEGADA+"|"
					+Reservadas.C_HORA_DE_LLEGADA+"|"+Reservadas.C_ORIGEN+"|"+Reservadas.C_DESTINO);
			input.setVuelo(separador[1].trim());
			input.setHora(separador[2].trim());
			
		}else if(linea.contains(Reservadas.C_A)){
			separador = linea.split(Reservadas.C_A);
			input.setHotel(separador[1].trim());
		}
		
		return input;
	}
	/**
	 * Metodo para separar lineas de Salidas.
	 * @param linea : la linea a leer
	 * @param input : el DTO de entrada
	 * @return input con valores
	 */
	protected CamposDTO separaSalida(String linea, CamposDTO input){
		LOG.info(" + Iniciando separaSalida() en SeparadorBO");
		LOG.info("  - linea: "+ linea);
		String[] separador;
		
		if(linea.contains(Reservadas.C_ID_SERVICIO)){
			separador = linea.split(Reservadas.C_ID_SERVICIO);
			input.setIdServicio(separador[1].trim());
		}else if(linea.contains(Reservadas.C_TIPO_SERV)){
			separador = linea.split(Reservadas.C_TIPO_SERV+"|"+Reservadas.C_TRANSPORTE);
			input.setTipoServicio(separador[1].trim());
			
		}else if(linea.contains(Reservadas.C_SALIDA) && !linea.contains(Reservadas.C_HORA_SALIDA)){
			separador = linea.split(Reservadas.C_SALIDA+"|"+Reservadas.C_PAXES);
			input.setFecha(separador[1].trim());
			input.setPaxes(separador[2].trim());
			
		}else if(linea.contains(Reservadas.C_DE)){
			separador = linea.split(Reservadas.C_DE);
			input.setHotel(separador[1].trim());
			
		}else if(linea.contains(Reservadas.C_HORA_RECOGIDA)){
			separador = linea.split(Reservadas.C_HORA_RECOGIDA+"|"+Reservadas.C_PUNTO_RECOGIDA+"|"
					+Reservadas.C_HORA_DE_RECOGIDA+"|"+Reservadas.C_PUNTO_DE_RECOGIDA);
			input.setHoraRecogida(separador[1].trim());
			
		}else if(linea.contains(Reservadas.C_TRANSPORTE)){
			separador = linea.split(Reservadas.C_TRANSPORTE+"|"+Reservadas.C_HORA_SALIDA+"|"
					+Reservadas.C_HORA_DE_SALIDA+"|"+Reservadas.C_ORIGEN+"|"+Reservadas.C_DESTINO);
			input.setVuelo(separador[1].trim());
			input.setHora(separador[2].trim());
			
		}
		
		return input;
	}	
	/**
	 * Metodo para separar lineas de Paseos.
	 * @param linea : la linea a leer
	 * @param input : el DTO de entrada
	 * @param temp : la linea anterior a leer
	 * @return input con valores
	 */
	protected CamposDTO separaPaseo(String linea, CamposDTO input, String temp){
		LOG.info(" + Iniciando separaPaseo() en SeparadorBO");
		LOG.info("  - linea: "+ linea);
		LOG.info("  - temp: "+ temp);
		String[] separador;
		
		if(linea.contains(Reservadas.C_LOC_AGENCIA)){
			String ref = temp.substring(0,temp.indexOf("-")+8);
			String nombre = temp.substring(temp.indexOf("-")+8);
			input.setReferencia(ref.trim());
			input.setNombre(nombre.trim());
			
		}else if(linea.contains(Reservadas.C_FECHA)){
			separador = linea.split(Reservadas.C_FECHA);
			input.setFecha(separador[1].trim());
			
		}else if(linea.contains(Reservadas.C_SERVICIO) && !linea.contains(Reservadas.C_DESC_SERVICIO)){
			separador = linea.split(Reservadas.C_SERVICIO+"|"+Reservadas.C_MODALIDAD);
			input.setTipoServicio(separador[1].trim());
			input.setModalidad(separador[2].trim());
			
		}else if(linea.contains(Reservadas.C_PASAJEROS)){
			separador = linea.split(Reservadas.C_PASAJEROS);
			input.setPaxes(separador[1].trim());
			
		}
		
		if("hotel".equals(temp)){
			separador = linea.split("-");
			if(separador.length > 1){
				input.setHotel(separador[1]);
			}
		}
		
		return input;
	}	
	
}