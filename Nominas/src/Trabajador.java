
public class Trabajador {
	
	private String dni;
	private String apellidoUno;	
	private String apellidoDos;
	private String nombre;
	private String email;
	private String categoria;
	private String nombreEmpresa;
	private String cifEmpresa;
	private String altaEmpresa;
	private String altaLaboral;
	private String bajaLaboral;
	private double extraForazadas;
	private double extraVolunt;
	private boolean prorratExtra;
	private String cuentaBancaria;
	private String pais;
	private String iban;
	int mes;
	int anio;
	//Calculo de las Nominas Salario Bruto
	double brutoAnual;
	double sBrutoMes;
	double sNetoMes;
	double mensualConProrrateo;
	int trienios;
	double valorTrienios;
	double complementos;
	double sBase;
	double prorrat;
	//Trabajador
	double contGeneral;
	double desempleoT;
	double formacionT;
	double iRPF;
	//Empresario
	double contComunes;
	double desempleoE;
	double formacionE;
	double accTrabajo;
	double fOGASA;
	
		
		
		
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public double getValorTrienios() {
		return valorTrienios;
	}
	public void setValorTrienios(double valorTrienios) {
		this.valorTrienios = valorTrienios;
	}
	public double getProrrat() {
		return prorrat;
	}
	public void setProrrat(double prorrat) {
		this.prorrat = prorrat;
	}
	public double getsBase() {
		return sBase;
	}
	public void setsBase(double sBase) {
		this.sBase = sBase;
	}
	public double getTrienios() {
		return trienios;
	}
	public void setTrienios(int trienios) {
		this.trienios = trienios;
	}
	public double getComplementos() {
		return complementos;
	}
	public void setComplementos(double complementos) {
		this.complementos = complementos;
	}
	public double getBrutoAnual() {
		return brutoAnual;
	}
	public void setBrutoAnual(double brutoAnual) {
		this.brutoAnual = brutoAnual;
	}
	public double getsBrutoMes() {
		return sBrutoMes;
	}
	public void setsBrutoMes(double sBrutoMes) {
		this.sBrutoMes = sBrutoMes;
	}
	public double getsNetoMes() {
		return sNetoMes;
	}
	public void setsNetoMes(double sNetoMes) {
		this.sNetoMes = sNetoMes;
	}
	public double getMensualConProrrateo() {
		return mensualConProrrateo;
	}
	public void setMensualConProrrateo(double mensualSinProrrateo) {
		this.mensualConProrrateo = mensualSinProrrateo;
	}
	public double getContGeneral() {
		return contGeneral;
	}
	public void setContGeneral(double contGeneral) {
		this.contGeneral = contGeneral;
	}
	public double getDesempleoT() {
		return desempleoT;
	}
	public void setDesempleoT(double desempleoT) {
		this.desempleoT = desempleoT;
	}
	public double getFormacionT() {
		return formacionT;
	}
	public void setFormacionT(double formacionT) {
		this.formacionT = formacionT;
	}
	public double getiRPF() {
		return iRPF;
	}
	public void setiRPF(double iRPF) {
		this.iRPF = iRPF;
	}
	public double getContComunes() {
		return contComunes;
	}
	public void setContComunes(double contComunes) {
		this.contComunes = contComunes;
	}
	public double getDesempleoE() {
		return desempleoE;
	}
	public void setDesempleoE(double desempleoE) {
		this.desempleoE = desempleoE;
	}
	public double getFormacionE() {
		return formacionE;
	}
	public void setFormacionE(double formacionE) {
		this.formacionE = formacionE;
	}
	public double getAccTrabajo() {
		return accTrabajo;
	}
	public void setAccTrabajo(double accTrabajo) {
		this.accTrabajo = accTrabajo;
	}
	public double getfOGASA() {
		return fOGASA;
	}
	public void setfOGASA(double fOGASA) {
		this.fOGASA = fOGASA;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getApellidoUno() {
		return apellidoUno;
	}
	public void setApellidoUno(String apellidoUno) {
		this.apellidoUno = apellidoUno;
	}
	public String getApellidoDos() {
		return apellidoDos;
	}
	public void setApellidoDos(String apellidoDos) {
		this.apellidoDos = apellidoDos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getCifEmpresa() {
		return cifEmpresa;
	}
	public void setCifEmpresa(String cifEmpresa) {
		this.cifEmpresa = cifEmpresa;
	}
	public String getAltaEmpresa() {
		return altaEmpresa;
	}
	public void setAltaEmpresa(String altaEmpresa) {
		this.altaEmpresa = altaEmpresa;
	}
	public String getAltaLaboral() {
		return altaLaboral;
	}
	public void setAltaLaboral(String altaLaboral) {
		this.altaLaboral = altaLaboral;
	}
	public String getBajaLaboral() {
		return bajaLaboral;
	}
	public void setBajaLaboral(String bajaLaboral) {
		this.bajaLaboral = bajaLaboral;
	}
	public double getExtraForazadas() {
		return extraForazadas;
	}
	public void setExtraForazadas(double extraForazadas) {
		this.extraForazadas = extraForazadas;
	}
	public double getExtraVolunt() {
		return extraVolunt;
	}
	public void setExtraVolunt(double extraVolunt) {
		this.extraVolunt = extraVolunt;
	}
	public boolean isProrratExtra() {
		return prorratExtra;
	}
	public void setProrratExtra(boolean prorratExtra) {
		this.prorratExtra = prorratExtra;
	}
	public String getCuentaBancaria() {
		return cuentaBancaria;
	}
	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}

}