
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class CreateXML {
	String dni;
	String nombre;
	String apellido1;
	String apellido2;
	String mensaje;
	String cuentaB;
	String iban;
	String empresa;
	int numFila;
	
	public ArrayList<CreateXML> listaDNIMal = new <CreateXML>ArrayList();
	public ArrayList<CreateXML> listaCuenta = new <CreateXML>ArrayList();

	public CreateXML() {
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getCuentaB() {
		return cuentaB;
	}

	public void setCuentaB(String cuentaB) {
		this.cuentaB = cuentaB;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public int getNumFila() {
		return numFila;
	}

	public void setNumFila(int numFila) {
		this.numFila = numFila;
	}

	public void crearListaErroresDni(ArrayList<Trabajador> listaTrabajador)throws Exception {
		
		for(int i = 0; i < listaTrabajador.size(); i++) {
			if(listaTrabajador.get(i).getDni() == "-1") {
				CreateXML trabajadorDNI = new CreateXML();
				trabajadorDNI.setNombre(listaTrabajador.get(i).getNombre());
				trabajadorDNI.setApellido1(listaTrabajador.get(i).getApellidoUno());
				trabajadorDNI.setApellido2(listaTrabajador.get(i).getApellidoDos());
				//trabajadorDNI.setMensaje("La celda del DNI esta vacia");
				listaDNIMal.add(trabajadorDNI);
			}
			if(listaTrabajador.get(i).getDni() == "-2") {
				CreateXML trabajadorDNI = new CreateXML();
				trabajadorDNI.setNombre(listaTrabajador.get(i).getNombre());
				trabajadorDNI.setApellido1(listaTrabajador.get(i).getApellidoUno());
				trabajadorDNI.setApellido2(listaTrabajador.get(i).getApellidoDos());
				//trabajadorDNI.setMensaje("El DNI esta repetido");
				listaDNIMal.add(trabajadorDNI);
			}
		}
		
		crearXMLDNI(listaDNIMal);
	}

	private void crearXMLDNI(ArrayList<CreateXML> listaDNIMal) {
		
		try {
			Element worker = new Element("EMPRESA");
			Document doc = new Document(worker);
			doc.setRootElement(worker);
			
			for(int i = 0; i < listaDNIMal.size(); i++) {
				
				String cad = String.valueOf(i);
						
				Element workers = new Element("Trabajador");
				workers.setAttribute(new Attribute("Id", cad));
				workers.addContent(new Element("Nombre").setText(listaDNIMal.get(i).getNombre()));
				workers.addContent(new Element("Apellido1").setText(listaDNIMal.get(i).getApellido1()));
				workers.addContent(new Element("Apellido2").setText(listaDNIMal.get(i).getApellido2()));
				workers.addContent(new Element("Mensaje").setText(listaDNIMal.get(i).getMensaje()));
				
				doc.getRootElement().addContent(workers);
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("resources/erroresDNI.xml"));
			
			System.out.println("Archivo dniErrores Guardado");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
	
	public void rellenarListaCuenta(String cuenta, String nombre, String apellido1, String apellido2, String empresa, String iban, int numFila) {
		CreateXML oTrabajador = new CreateXML();
		oTrabajador.setCuentaB(cuenta);
		oTrabajador.setNombre(nombre);
		oTrabajador.setApellido1(apellido1);
		oTrabajador.setApellido2(apellido2);
		oTrabajador.setEmpresa(empresa);
		oTrabajador.setIban(iban);
		oTrabajador.setEmpresa(empresa);
		oTrabajador.setNumFila(numFila);
		oTrabajador.setMensaje("Numero de cuenta erroneo");
		listaCuenta.add(oTrabajador);
		
	}

	public void crearXMLCuenta() {
		
		try {
			Element worker = new Element("EMPRESA");
			Document doc = new Document(worker);
			doc.setRootElement(worker);
			
			for(int i = 0; i < listaCuenta.size(); i++) {
				
				String cad = String.valueOf(listaCuenta.get(i).getNumFila());
						
				Element workers = new Element("Trabajador");
				workers.setAttribute(new Attribute("Id", cad));
				workers.addContent(new Element("Nombre").setText(listaCuenta.get(i).getNombre()));
				workers.addContent(new Element("Apellido1").setText(listaCuenta.get(i).getApellido1()));
				workers.addContent(new Element("Apellido2").setText(listaCuenta.get(i).getApellido2()));
				workers.addContent(new Element("Nombre Empresa").setText(listaCuenta.get(i).getEmpresa()));
				workers.addContent(new Element("Cuenta Bancaria").setText(listaCuenta.get(i).getCuentaB()));
				workers.addContent(new Element("Iban").setText(listaCuenta.get(i).getIban()));
				workers.addContent(new Element("Mensaje Error").setText(listaCuenta.get(i).getMensaje()));
				
				doc.getRootElement().addContent(workers);
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("resources/cuentasErroneas.xml"));
			
			System.out.println("Archivo cuentasErroneas Guardado");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}


