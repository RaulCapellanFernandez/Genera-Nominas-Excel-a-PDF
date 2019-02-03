
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		
		ArrayList<Trabajador> listaTrabajador = new <Trabajador>ArrayList();
		ArrayList<DatosSalario> listaTiposTrabajo = new <DatosSalario>ArrayList();
		ArrayList<DatosSalario> listaCuotas = new <DatosSalario>ArrayList();
		ArrayList<DatosSalario> listaRetenciones = new <DatosSalario>ArrayList();
		ArrayList<DatosSalario> listaTrienios = new <Integer>ArrayList();
		
		ConexionExcell oConexion = new ConexionExcell();
		Validador oValidador = new Validador("");
		CreateXML oCreateXML = new CreateXML(); 
		ConexionexcellHoja2 oConexion2 = new ConexionexcellHoja2();
		CrearPDF oPDF = new CrearPDF();
		Nomina oNomina = new Nomina();
		
		//Lee el excel y lo mete en una lista
		listaTrabajador = oConexion.leerExcell();
		//Verifica el DNI
		oValidador.verificarDni(listaTrabajador);
		//Crea XML
		oCreateXML.crearListaErroresDni(listaTrabajador);
		//Verifica la cuenta
		oValidador.verificarCuenta(listaTrabajador);
		//Crea XML
		oCreateXML.crearXMLCuenta();
		//Crea los Emails
		oValidador.crearEmails(listaTrabajador);
		
		//Imprime toda la lista de trabajadores Solo es para testear que funcionaba
		//oConexion.imprime(listaTrabajador);
		
		//Lee la hoja 2
		oConexion2.leerExcelHoja2();
		//Guarda las listas de la hoja 2
		listaTiposTrabajo = oConexion2.pasarListaTiposTrabajador();
		listaTrienios = oConexion2.pasarListaTrienios();
		listaRetenciones = oConexion2.pasarListaRetenciones();
		listaCuotas = oConexion2.pasarListaCuotas();

		//Escribe el Excel
		oConexion.escribir(listaTrabajador);
		
		/*-----------------Pedir por consola------------*/
		String fecha ="";
		System.out.println("Introduce el mes y el ano que deseas generar las nominas de los trabajadores.(mm/aaaa)");
		Scanner reader = new Scanner(System.in);
		fecha = reader.nextLine (); 
		
		//Calcula Brutos
		oNomina.calcularBrutoAnual(fecha , listaTrabajador, listaTiposTrabajo, listaTrienios);
		oNomina.calculoBrutoMensual(fecha, listaTrabajador, listaTrienios, listaCuotas,listaRetenciones);
		//Crea Los PDF
		oPDF.hacePDF(listaTrabajador, fecha, listaCuotas, listaRetenciones, listaTrienios);
		oPDF.hacePDFextra(listaTrabajador, fecha, listaCuotas, listaRetenciones, listaTrienios);
	}

}
