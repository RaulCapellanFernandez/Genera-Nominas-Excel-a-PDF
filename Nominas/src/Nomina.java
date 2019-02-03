import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Nomina {
	
	public void calcularBrutoAnual(String fecha, ArrayList<Trabajador> listaTrabajador,ArrayList<DatosSalario> listaTiposTrabajo, ArrayList<DatosSalario> listaTrienios) {
		int mes = Integer.parseInt(fecha.substring(0,2));
		int anio = Integer.parseInt(fecha.substring(3,7));
		int mesEntrada;
		int anioEntrada;
		int nTrienios;
		
		for(int i = 0; i < listaTrabajador.size();i++) {
			mesEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(3,5));
			anioEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(6,10));
			
			//Asignas sueldo Base y complementos
			for(int j = 0; j < listaTiposTrabajo.size(); j++) {
				if(listaTrabajador.get(i).getCategoria().equals(listaTiposTrabajo.get(j).getCategoria())){
					listaTrabajador.get(i).setsBase(listaTiposTrabajo.get(j).getSalarioBase());
					listaTrabajador.get(i).setComplementos(listaTiposTrabajo.get(j).getComplementos());
					listaTrabajador.get(i).setTrienios((anio-anioEntrada)/3);
				}		
			}
			
			if(anioEntrada > anio)
				System.out.println("Anio introducido mas pequenio que el anio de entrada");
			if(anioEntrada== anio && mesEntrada > mes) 
				System.out.println("Anio igual mes de entrada mayor que mes teclado");
			else {
				
				nTrienios = (anio - anioEntrada)/3;
				double bruto =0;
				double valorTrienio = 0;
				double valorTrienio1 =0;
				double aux1 = 0, aux = 0;
				
				//Calcula el valor de los trienios
				for(int t = 0; t < listaTrienios.size(); t++) {
					if(listaTrabajador.get(i).getTrienios() == (int) listaTrienios.get(t).getnTrienios()) {
						valorTrienio = listaTrienios.get(t).getImporteTrienio();
					}
					if(listaTrabajador.get(i).getTrienios()-1 == (int) listaTrienios.get(t).getnTrienios()) {
						valorTrienio1 = listaTrienios.get(t).getImporteTrienio();
					}
					
				}
				bruto = listaTrabajador.get(i).getsBase() + listaTrabajador.get(i).getComplementos();
				//Caso 0 Menos de 1 año en la empresa
				if(anioEntrada - anio == 0) {
					int mesesTrabajados = 13 -mesEntrada;
					if(13 - mesEntrada >= 6 && 13 -mesEntrada !=0)
						mesesTrabajados++;
					if(12- mesEntrada == 0)
						mesesTrabajados = mesesTrabajados +2;
					
					listaTrabajador.get(i).setBrutoAnual(bruto * mesesTrabajados / 14);
					
				}
				//Caso 1 no hay cambio de trienio
				if((anio-anioEntrada) %3 !=0) {
					for(int j = 1; j < 15; j++) {
						aux =  valorTrienio +aux;
					}
					listaTrabajador.get(i).setBrutoAnual(bruto+aux);
					
				}
				//Caso 2 hay cambio de trienio
				else if((anio-anioEntrada) %3 ==0) {
					for(int j = 1; j < mesEntrada; j++) {
						aux  = aux + valorTrienio1; 
					}
					for(int j = mesEntrada; j < 13; j++) {
						aux1 = aux1 + valorTrienio;
					}
					//Primera extra
					if(mesEntrada < 6)
						aux  = aux + valorTrienio1;
					else
						aux = aux + valorTrienio;
					//Segunda Extra
					if(mesEntrada == 12)
						aux = aux + valorTrienio1;
					else
						aux = aux + valorTrienio;
					
					listaTrabajador.get(i).setBrutoAnual(bruto+aux+aux1);
					
				}
	
				
				
			}
		}
	}

	public void calculoBrutoMensual(String fecha, ArrayList<Trabajador> listaTrabajador, ArrayList<DatosSalario> listaTrienios, ArrayList<DatosSalario> listaCuotas, ArrayList<DatosSalario> listaRetenciones) {
		int mes = Integer.parseInt(fecha.substring(0,2));
		int anio = Integer.parseInt(fecha.substring(3,7));
		int mesEntrada;
		int anioEntrada;
		double valueTrienio = 0, valueTrienio1 = 0;
		double totalDeducciones = 0;
		double totalDevengos = 0;
		double valueTrienioProrrat = 0;
		
		for(int i = 0; i < listaTrabajador.size();i++) {
			listaTrabajador.get(i).setMes(mes);
			listaTrabajador.get(i).setAnio(anio);
			mesEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(3,5));
			anioEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(6,10));
			
			for(int j = 0; j < listaTrienios.size(); j++) {
				if((listaTrabajador.get(i).getTrienios()) == listaTrienios.get(j).getnTrienios())
					valueTrienio = listaTrienios.get(j).getImporteTrienio();
			}
			
			//Caso 0 No Hay Cambio De Trienio
			if((anioEntrada-anio)%3 !=0) {
				listaTrabajador.get(i).setsBrutoMes((listaTrabajador.get(i).getBrutoAnual()/14));
				for(int j = 0; j < listaTrienios.size(); j++) {
					if((listaTrabajador.get(i).getTrienios()) == listaTrienios.get(j).getnTrienios())
						valueTrienio = listaTrienios.get(j).getImporteTrienio();
						listaTrabajador.get(i).setValorTrienios(valueTrienio);
				}
			}
			//Caso 1 Hay Cambio de Trienio
			if((anioEntrada-anio)%3 ==0) {
				if(mes <= mesEntrada) {
					for(int j = 0; j < listaTrienios.size(); j++) {
						if((listaTrabajador.get(i).getTrienios()-1) == listaTrienios.get(j).getnTrienios())
							valueTrienio = listaTrienios.get(j).getImporteTrienio();
							listaTrabajador.get(i).setValorTrienios(valueTrienio);
					}
				}
				listaTrabajador.get(i).setsBrutoMes((listaTrabajador.get(i).getsBase()/14 + listaTrabajador.get(i).getComplementos()/14+ valueTrienio));
			}
				if(mes > mesEntrada) {
					for(int j = 0; j < listaTrienios.size(); j++) {
						if((listaTrabajador.get(i).getTrienios()) == listaTrienios.get(j).getnTrienios()) {
							valueTrienio1 = listaTrienios.get(j).getImporteTrienio();
							listaTrabajador.get(i).setValorTrienios(valueTrienio1);
						}
					}
				}
			
				
			
			//Prorrateos
			for(int j = 0; j < listaTrienios.size(); j++) {
				
				if((listaTrabajador.get(i).getTrienios()) == listaTrienios.get(j).getnTrienios()) 
					valueTrienioProrrat = listaTrienios.get(j).getImporteTrienio();
			}
			listaTrabajador.get(i).setProrrat((listaTrabajador.get(i).getsBase()/14+listaTrabajador.get(i).getComplementos()/14+valueTrienioProrrat)/6);
			listaTrabajador.get(i).setMensualConProrrateo(listaTrabajador.get(i).getsBase()/14+listaTrabajador.get(i).getComplementos()/14+listaTrabajador.get(i).getProrrat()+listaTrabajador.get(i).getValorTrienios());
			
			if(listaTrabajador.get(i).isProrratExtra()) {
				double bruto = listaTrabajador.get(i).getsBrutoMes();
				listaTrabajador.get(i).setsBrutoMes(listaTrabajador.get(i).getsBase()/14+listaTrabajador.get(i).getComplementos()/14+listaTrabajador.get(i).getProrrat()+listaTrabajador.get(i).getValorTrienios());
			}
			
			
			listaTrabajador.get(i).setContGeneral(listaCuotas.get(0).getCuotaObreraT()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			listaTrabajador.get(i).setDesempleoT(listaCuotas.get(0).getCuotaDesempleoT()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			listaTrabajador.get(i).setFormacionT(listaCuotas.get(0).getCuotaFormacionT()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			//System.out.println(listaTrabajador.get(i).getBrutoAnual());
			for(int j= 0; j < listaRetenciones.size(); j++) {
				if(listaTrabajador.get(i).getBrutoAnual() >= listaRetenciones.get(j).getBrutoAnual()) {
					listaTrabajador.get(i).setiRPF(listaRetenciones.get(j+1).getRetencion()/100 * listaTrabajador.get(i).getsBrutoMes());
				}
			}
			
			listaTrabajador.get(i).setContComunes(listaCuotas.get(0).getContingenciasE()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			listaTrabajador.get(i).setDesempleoE(listaCuotas.get(0).getDesempleoE()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			listaTrabajador.get(i).setFormacionE(listaCuotas.get(0).getFormaconE()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			listaTrabajador.get(i).setAccTrabajo(listaCuotas.get(0).getAccidentesTrabajoE()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			listaTrabajador.get(i).setfOGASA(listaCuotas.get(0).getFogasaE()/100 * listaTrabajador.get(i).getMensualConProrrateo());
			
			
			if(listaTrabajador.get(i).isProrratExtra())
				totalDevengos = listaTrabajador.get(i).getValorTrienios() + listaTrabajador.get(i).getComplementos()/14 + listaTrabajador.get(i).getProrrat() + listaTrabajador.get(i).getsBase()/14;
			else
				totalDevengos = listaTrabajador.get(i).getValorTrienios() + listaTrabajador.get(i).getComplementos()/14 + listaTrabajador.get(i).getsBase()/14;

			totalDeducciones = listaTrabajador.get(i).getContGeneral() + listaTrabajador.get(i).getDesempleoT() + listaTrabajador.get(i).getFormacionT() + listaTrabajador.get(i).getiRPF();
			listaTrabajador.get(i).setsNetoMes(totalDevengos - totalDeducciones);
			System.out.println(i+" Devengos "+listaTrabajador.get(i).getTrienios());
			System.out.println(i+" "+listaTrabajador.get(i).getTrienios());
			System.out.println(i+" "+listaTrabajador.get(i).getValorTrienios());
		}
		
	}
	
}
