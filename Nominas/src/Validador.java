import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Validador {
	private static String dni;
	
	public Validador(){
		
	}
	public Validador(String dni){
		this.dni= dni;
		
	}
	public CreateXML oXML =new CreateXML();
	
	public String getNumero(){
		String numero="0";
		if(dni.length()>0){
		if(Character.isDigit(dni.charAt(0))){	//espa�oles
			numero=dni.substring(0, dni.length()-1);
		}else{	//extranjeros
			numero=dni.substring(1, dni.length()-1);
			if(dni.charAt(0) =='X'){
				numero= '0'+numero;
				
			}else if(dni.charAt(0) =='Y'){
				numero= '1'+numero;
				
			}else if(dni.charAt(0)=='Z'){
				numero= '2'+numero;
				
			}
		}
		}
		return numero;
	}
	
	public char getLetra(){
		return dni.charAt(dni.length()-1);
	}
	
	public boolean comprobar(){
		String dniNumero = getNumero();
		int numero =  Integer.parseInt(dniNumero);
		char  letra= "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero%23);
		char letraDos = this.getLetra();
		if(dniNumero.length() ==8 &&Character.isDigit(dni.charAt(0))&&letra!=letraDos){
			return true;
			
		}else if(dni.substring(1, dni.length()-1).length()  ==7&&letra!=letraDos){
			return true;
		//Condicion para el espacio 
		}else if((getNumero().equals(0)) ||(getNumero().equals("-1)") ||(getNumero().equals(" ")))){
			return false;
		}
		
		return false;
	}

	
	
	public String actualizar(){
		String dniNumero = getNumero();
		int numero =  Integer.parseInt(dniNumero);
		char  letra= "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero%23);
		String dniNuevo ="";
		char letraDos = this.getLetra();
		if(dniNumero.length() ==8 &&Character.isDigit(dni.charAt(0))&&letra!=letraDos){
			dniNuevo=dniNumero+letra;
			
		}else{
			dniNuevo= dni.substring(0, dni.length()-1)+letra;
			
		}
		
		return dniNuevo;

	}


	public void dniRepetidos(ArrayList<Trabajador> listaTrabajador) {
		
		for(int i = 0; i < listaTrabajador.size(); i++) {
			for(int j = i+1; j < listaTrabajador.size(); j++) {
				if(listaTrabajador.get(i).getDni().equals(listaTrabajador.get(j).getDni())) {
					listaTrabajador.get(j).setDni("-2");
				}
			}
		}
		
		
	}
	
	public void verificarDni(ArrayList<Trabajador> listaTrabajador) throws IOException {
		for(int i  = 1; i < listaTrabajador.size(); i++) {
			String dni = listaTrabajador.get(i).getDni();
			Validador oValidador = new Validador(dni);
			if(dni.equals("-1")) {
			
			}else
			if(oValidador.comprobar()){
				
				listaTrabajador.get(i).setDni(oValidador.actualizar());
			}
		}
		dniRepetidos(listaTrabajador);
	}
	
	//Cuenta bancaria
	public void verificarCuenta(ArrayList<Trabajador> listaTrabajador){
		for(int i  = 1; i < listaTrabajador.size(); i++) {
			String cuenta = listaTrabajador.get(i).getCuentaBancaria();
			if(cuenta.equals(-1)){	//si no tiene nada
				
			}else if ((cuenta.length()==20) && Character.isDigit(dni.charAt(0))){	
				//que tenga longitud 20 y q sean todos numeros 
				String nuevaCuenta = comprobarCuenta(cuenta, listaTrabajador.get(i).getNombre(), listaTrabajador.get(i).getApellidoUno(), listaTrabajador.get(i).getApellidoDos(), listaTrabajador.get(i).getNombreEmpresa(), listaTrabajador.get(i).getIban(), i+1);
				listaTrabajador.get(i).setCuentaBancaria(nuevaCuenta);
				//calculo digito de control
				//String controlAntes = listaTrabajador.get(i).getPais()+"00"+cuenta;
				//String controlDespues = cuenta+listaTrabajador.get(i).getPais()+"00";
				String pais = listaTrabajador.get(i).getPais();
				
				char abecedario[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
				int valor =0;
				String numeroPais="";
				for (int j = 0; j < abecedario.length; j++) {
					if(abecedario[j]==pais.charAt(0)){
						valor=0;
						valor=10+j;
						numeroPais = valor+numeroPais;
					}
					if(abecedario[j]==pais.charAt(1)){
						valor=0;
						valor=10+j;
						numeroPais = numeroPais+ valor;
					}
				}
				String preIban =nuevaCuenta+numeroPais+"00";
				BigInteger ccc = new BigInteger(preIban);
				BigInteger noventaYSiete = new BigInteger("97");
				ccc = ccc.mod(noventaYSiete);	
				int dcIb = ccc.intValue();
			    dcIb = 98 - dcIb;
			    String dC="";
			    if(dcIb<10){
			    	 dC= "0"+Integer.toString(dcIb);
			    }else
			    	dC=Integer.toString(dcIb);
			    String iban = pais+dC+cuenta;
				String ibanVerificar = nuevaCuenta+ numeroPais+dC;
				BigInteger iban_2 = new BigInteger(ibanVerificar);
				iban_2 = iban_2.mod(noventaYSiete);
				if(iban_2.intValue()==1){
					listaTrabajador.get(i).setIban(iban);
				}
				
				/*if(!cuenta.equals(iban.substring(4)))
					System.out.println(cuenta+ " "+iban.substring(4));*/
			}else{					// y si no tiene longitud 20
				
			}
				
		}
	}
	private String comprobarCuenta(String cuenta, String nombre, String apellido1, String apellido2, String empresa, String iban, int numFila) {
		String primeraCuenta="";
		String nuevaCuenta="";
		
		//Entidad Bancaria y oficina--8primeros numeros 
		String entidadOficina ="00"+cuenta.substring(0,8);
		int suma = Integer.parseInt(entidadOficina.substring(0, 1))*1+
				   Integer.parseInt(entidadOficina.substring(1, 2))*2+
				   Integer.parseInt(entidadOficina.substring(2, 3))*4+
				   Integer.parseInt(entidadOficina.substring(3, 4))*8+
				   Integer.parseInt(entidadOficina.substring(4, 5))*5+
				   Integer.parseInt(entidadOficina.substring(5, 6))*10+
				   Integer.parseInt(entidadOficina.substring(6, 7))*9+
				   Integer.parseInt(entidadOficina.substring(7, 8))*7+
				   Integer.parseInt(entidadOficina.substring(8, 9))*3+
				   Integer.parseInt(entidadOficina.substring(9, 10))*6;
		
		int digitoControl = 11-(suma%11);
		
		if (digitoControl==11)digitoControl=0;
		else if (digitoControl==10) digitoControl=1;
		
		int controlBanco = Integer.parseInt(cuenta.substring(8,9));
		
		if (controlBanco!=digitoControl) 
			primeraCuenta= cuenta.substring(0,8)+ digitoControl;
		else 
			primeraCuenta=cuenta.substring(0,9);
		
		String segundaCuenta="";
		//los diez ultimos numeros 
		suma = Integer.parseInt(cuenta.substring(10,11))*1+
				Integer.parseInt(cuenta.substring(11,12))*2+
				Integer.parseInt(cuenta.substring(12,13))*4+
				Integer.parseInt(cuenta.substring(13,14))*8+
				Integer.parseInt(cuenta.substring(14,15))*5+
				Integer.parseInt(cuenta.substring(15,16))*10+
				Integer.parseInt(cuenta.substring(16,17))*9+
				Integer.parseInt(cuenta.substring(17,18))*7+
				Integer.parseInt(cuenta.substring(18,19))*3+
				Integer.parseInt(cuenta.substring(19,20))*6;
	 
		digitoControl= 11 - (suma%11);
		
		if (digitoControl==11)
			digitoControl=0;
		else if (digitoControl==10) 
			digitoControl=1;
		
		int cCuenta = Integer.parseInt(cuenta.substring(9,10));
		
		if (cCuenta!=digitoControl) { 
			//Para el xml de cuenta bancaria
			oXML.rellenarListaCuenta(cuenta, nombre,apellido1,apellido2,empresa,iban, numFila);
			
			segundaCuenta= digitoControl +cuenta.substring(10,20);
		}else 
			segundaCuenta= cuenta.substring(9,20);
			nuevaCuenta= primeraCuenta+ segundaCuenta;
		//System.out.println(cuenta.substring(0,9)+"="+ primeraCuenta+"||"+cuenta.substring(9,20)+"="+ segundaCuenta);
		return nuevaCuenta;
	}
	
	
	
	
	
	
	public void crearEmails(ArrayList<Trabajador> listaTrabajador) {
		String nombre;
		String apellido1;
		String apellido2 = "";
		String nombreEmpresa;
		String email;
		String numero="";
		ArrayList<String> lista = new ArrayList<String>();getClass();
		
		for(int i = 1;  i < listaTrabajador.size(); i++) {
			
			//Pasa los datos a string
			nombre = listaTrabajador.get(i).getNombre();
			apellido1 = listaTrabajador.get(i).getApellidoUno();
			
			if(!listaTrabajador.get(i).getApellidoDos().equals("-1")) {
				apellido2 = listaTrabajador.get(i).getApellidoDos();
				apellido2 = apellido2.substring(0, 2);
				apellido2 = apellido2.toLowerCase();
			}
			nombreEmpresa = listaTrabajador.get(i).getNombreEmpresa();
			
			//Corta los string segun lo indicado a la practica y las pasa todas a minusculas
			nombre = nombre.substring(0, 3);
			nombre = nombre.toLowerCase();
			
			apellido1 = apellido1.substring(0, 2);
			apellido1 = apellido1.toLowerCase();
			email = nombre + apellido1 + apellido2 + nombreEmpresa;
			
			listaTrabajador.get(i).setEmail(email);
			int cont = -1;
			//numero de email raucafe00
			for(int j = 0; j < listaTrabajador.size(); j++) {
				if(listaTrabajador.get(i).getEmail().equals(listaTrabajador.get(j).getEmail()))
						cont++;
				numero = String.valueOf(cont);
				if(cont < 10)
					email = nombre + apellido1 + apellido2+ "0" + numero + "@" + nombreEmpresa + ".es";
				else
					email = nombre + apellido1 + apellido2+ numero + "@" + nombreEmpresa + ".es";
			}
			lista.add(email);
		}	//No se porque si lo introduces antes de meterlo a la lista no lo hace de forma incorrecta
		for(int k = 1; k < listaTrabajador.size(); k++) {
			listaTrabajador.get(k).setEmail(lista.get(k-1));
		}
	}
	
	
	
	
	
	

		
}
