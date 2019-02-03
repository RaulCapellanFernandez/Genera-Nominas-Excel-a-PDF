import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

public class CrearPDF {

	public void hacePDF(ArrayList<Trabajador> listaTrabajador, String fecha,ArrayList<DatosSalario> listaCuotas,ArrayList<DatosSalario> listaRetenciones, ArrayList<DatosSalario> listaTrienios) throws FileNotFoundException, DocumentException {
		DecimalFormat df = new DecimalFormat("#.00");
		int mes = Integer.parseInt(fecha.substring(0, 2));
		int anio = Integer.parseInt(fecha.substring(3,7));
		double totalDeducciones = 0;
		double totalDevengos = 0;
		double totalEmpresario = 0;
		double totalTrabajador = 0;
		
		double importTri = 0;
		double nTri = 0;
		
		for(int i = 0; i < listaTrabajador.size(); i++){
			
			File ficheroPDF=new File("resources/PDF/factura"+i+".pdf");
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(ficheroPDF));
			document.open();
			if(listaTrabajador.get(i).getDni().equals("-1") || listaTrabajador.get(i).getDni().equals("-2"))
				document.add(new Paragraph("ESTE DNI NO ES VALIDO NO SE GENERA NOMINA PARA ESTE EMPLEADO POR SER ERRONEO"));
			else {
				int mesEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(3,5));
				int anioEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(6,10));
				if((anioEntrada-anio)%3 ==0) {
					if(mes <= mesEntrada) {
						for(int j = 0; j < listaTrienios.size(); j++) {
							if((listaTrabajador.get(i).getTrienios()-1) == listaTrienios.get(j).getnTrienios()) {
								importTri = listaTrienios.get(j).getImporteTrienio();
								nTri = listaTrabajador.get(i).getTrienios()-1;
							}
						}
					}else {
						for(int j =0; j < listaTrienios.size(); j++) {
							if(listaTrabajador.get(i).getTrienios() == listaTrienios.get(j).getnTrienios()) {
								importTri = listaTrienios.get(j).getImporteTrienio();
								nTri = listaTrabajador.get(i).getTrienios();
							}	
						}
					}
				}
				if((anioEntrada-anio)%3 !=0) {
					nTri = listaTrabajador.get(i).getTrienios();
				}
			
				if(listaTrabajador.get(i).isProrratExtra())
					totalDevengos = listaTrabajador.get(i).getValorTrienios() + listaTrabajador.get(i).getComplementos()/14 + listaTrabajador.get(i).getProrrat() + listaTrabajador.get(i).getsBase()/14;
				else
					totalDevengos = listaTrabajador.get(i).getValorTrienios() + listaTrabajador.get(i).getComplementos()/14 + listaTrabajador.get(i).getsBase()/14;

				totalDeducciones = listaTrabajador.get(i).getContGeneral() + listaTrabajador.get(i).getDesempleoT() + listaTrabajador.get(i).getFormacionT() + listaTrabajador.get(i).getiRPF();
				totalEmpresario = listaTrabajador.get(i).getContComunes() + listaTrabajador.get(i).getDesempleoE() + listaTrabajador.get(i).getFormacionE() + listaTrabajador.get(i).getAccTrabajo() + listaTrabajador.get(i).getfOGASA();
				totalTrabajador = totalDevengos + totalEmpresario;
				listaTrabajador.get(i).setsNetoMes(totalDevengos - totalDeducciones);
				//Crea la fuente del titulo
				Font fuente  = new Font();
				fuente.setStyle(Font.BOLD);
				fuente.setStyle(Font.UNDERLINE);
				fuente.setSize(16);
				//Fuente subrayada
				Font fuente1  = new Font();
				fuente1.setStyle(Font.BOLD);
				//Fuente de todo lo de la empresa
				Font fGris = new Font();
				fGris.setColor(136,136,136);
				
				if(listaTrabajador.get(i).getApellidoDos().equals("-1"))
					document.add(new Paragraph(listaTrabajador.get(i).getNombre() +" "+ listaTrabajador.get(i).getApellidoUno()+"     "+mes+"/"+anio, fuente));	
				else
					document.add(new Paragraph(listaTrabajador.get(i).getNombre() +" "+ listaTrabajador.get(i).getApellidoUno() +" "+ listaTrabajador.get(i).getApellidoDos()+"     "+mes+"/"+anio, fuente));
				
				document.add(new Paragraph(" "));
				document.add(new Paragraph("DNI"+"............................................................."+listaTrabajador.get(i).getDni()));
				document.add(new Paragraph("Empresa"+"....................................................."+listaTrabajador.get(i).getNombreEmpresa()));
				document.add(new Paragraph("CIF"+".............................................................."+listaTrabajador.get(i).getCifEmpresa()));
				document.add(new Paragraph("Fecha Alta Empresa"+"..................................."+listaTrabajador.get(i).getAltaEmpresa()));
				document.add(new Paragraph("Categoria"+"...................................................."+listaTrabajador.get(i).getCategoria()));
				document.add(new Paragraph("Bruto Anual"+"................................................."+listaTrabajador.get(i).getBrutoAnual()+"€"));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph("                                                                      NÓMINA", fuente1));
				document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
				document.add(new Paragraph("                                                cant               Imp.Unit               Dev                      Deducc"));
				document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
				document.add(new Paragraph("Salario Base                          "+"30 días                                      "+df.format(listaTrabajador.get(i).getsBase()/14)));
				if(listaTrabajador.get(i).isProrratExtra())
					document.add(new Paragraph("Prorrateo                               "+"30 días                                       "+df.format(listaTrabajador.get(i).getProrrat())));
				else
					document.add(new Paragraph("Prorrateo                               "+"30 días                                       "+0));
				document.add(new Paragraph("Complemento                        "+"30 días                                      "+df.format(listaTrabajador.get(i).getComplementos()/14)));
				if(listaTrabajador.get(i).getValorTrienios() !=0)
					document.add(new Paragraph("Antiguedad                            "+(int)nTri +" trienio                                      "+df.format(listaTrabajador.get(i).getValorTrienios())));
				else
					document.add(new Paragraph("Antiguedad                            "+(int)nTri +" trienio                                      "+0));
				document.add(new Paragraph(" ")); 
				document.add(new Paragraph("Cont General                         "+listaCuotas.get(0).getCuotaObreraT()+"%                de "+df.format(listaTrabajador.get(i).getMensualConProrrateo())+"                                        "+df.format(listaTrabajador.get(i).getContGeneral())));
				document.add(new Paragraph("Desempleo                            "+listaCuotas.get(0).getCuotaDesempleoT()+"%                de "+df.format(listaTrabajador.get(i).getMensualConProrrateo())+"                                        "+df.format(listaTrabajador.get(i).getDesempleoT())));
				document.add(new Paragraph("Cuota Formacion                   "+listaCuotas.get(0).getCuotaFormacionT()+"%                de "+df.format(listaTrabajador.get(i).getMensualConProrrateo())+"                                        "+df.format(listaTrabajador.get(i).getFormacionT())));
				double retencion =0 ;
				for(int k = 0; k < listaRetenciones.size(); k++) {
					if(listaTrabajador.get(i).getBrutoAnual() > listaRetenciones.get(k).getBrutoAnual())
						retencion = listaRetenciones.get(k+1).getRetencion();
				}
				document.add(new Paragraph("IRPF                                      "+retencion+"%            de "+df.format(listaTrabajador.get(i).getsBrutoMes())+"                                         "+df.format(listaTrabajador.get(i).getiRPF())));
				document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
				document.add(new Paragraph("Total Deducciones                                                                                                    "+df.format(totalDeducciones), fuente1));
				document.add(new Paragraph("Total Devengos                                                                        "+df.format(totalDevengos), fuente1));
				document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
				document.add(new Paragraph("                                                                                         Liquido A Percibir:        "+df.format(listaTrabajador.get(i).getsNetoMes()), fuente1));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Calculo Empresario:BASE                                                                                    "+df.format(listaTrabajador.get(i).getMensualConProrrateo()),fGris));
				document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------",fGris));
				document.add(new Paragraph("Contingencias Comunes "+listaCuotas.get(0).getContingenciasE()+"%                                                                             "+df.format(listaTrabajador.get(i).getContComunes()),fGris));
				document.add(new Paragraph("Desempleo "+listaCuotas.get(0).getDesempleoE()+"%                                                                                                    "+df.format(listaTrabajador.get(i).getDesempleoE()),fGris));
				document.add(new Paragraph("Formacion "+listaCuotas.get(0).getFormaconE()+"%                                                                                                     "+df.format(listaTrabajador.get(i).getFormacionE()),fGris));
				document.add(new Paragraph("Accidentes Trabajo "+listaCuotas.get(0).getAccidentesTrabajoE()+"%                                                                                        "+df.format(listaTrabajador.get(i).getAccTrabajo()),fGris));
				document.add(new Paragraph("FOGASA "+listaCuotas.get(0).getFogasaE()+"%                                                                                                        "+df.format(listaTrabajador.get(i).getfOGASA()),fGris));
				document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------",fGris));
				document.add(new Paragraph("Total Empresario                                                                                                    "+df.format(totalEmpresario),fGris));
				document.add(new Paragraph("Coste Total Trabajador                                                                                          "+df.format(totalTrabajador),fGris));
			}
			document.close();	
		}
   			
	}

	public void hacePDFextra(ArrayList<Trabajador> listaTrabajador, String fecha, ArrayList<DatosSalario> listaCuotas, ArrayList<DatosSalario> listaRetenciones, ArrayList<DatosSalario> listaTrienios) throws FileNotFoundException, DocumentException {	
			DecimalFormat df = new DecimalFormat("#.00");
			int mes = Integer.parseInt(fecha.substring(0, 2));
			int anio = Integer.parseInt(fecha.substring(3,7));
			double totalDeducciones = 0;
			double totalDevengos = 0;
			double totalEmpresario = 0;
			double totalTrabajador = 0;
			double importTri = 0;
			double nTri = 0;
			
			for(int i = 0; i < listaTrabajador.size(); i++){
				if(mes == 6 || mes == 12 && listaTrabajador.get(i).isProrratExtra() == false) {
					File ficheroPDF=new File("resources/PDF/facturaExtra"+i+".pdf");
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream(ficheroPDF));
					document.open();
					if(listaTrabajador.get(i).getDni().equals("-1") || listaTrabajador.get(i).getDni().equals("-2"))
						document.add(new Paragraph("ESTE DNI NO ES VALIDO NO SE GENERA NOMINA PARA ESTE EMPLEADO POR SER ERRONEO"));
					else {
						int mesEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(3,5));
						int anioEntrada = Integer.parseInt(listaTrabajador.get(i).getAltaEmpresa().substring(6,10));
						if((anioEntrada-anio)%3 ==0) {
							if(mes <= mesEntrada) {
								for(int j = 0; j < listaTrienios.size(); j++) {
									if((listaTrabajador.get(i).getTrienios()-1) == listaTrienios.get(j).getnTrienios()) {
										importTri = listaTrienios.get(j).getImporteTrienio();
										nTri = listaTrabajador.get(i).getTrienios()-1;
									}
								}
							}else {
								for(int j =0; j < listaTrienios.size(); j++) {
									if(listaTrabajador.get(i).getTrienios() == listaTrienios.get(j).getnTrienios()) {
										importTri = listaTrienios.get(j).getImporteTrienio();
										nTri = listaTrabajador.get(i).getTrienios();
									}	
								}
							}	
						}
						
					
						if(listaTrabajador.get(i).isProrratExtra())
							totalDevengos = importTri + listaTrabajador.get(i).getComplementos()/14 + listaTrabajador.get(i).getProrrat() + listaTrabajador.get(i).getsBase()/14;
						else
							totalDevengos = importTri + listaTrabajador.get(i).getComplementos()/14 + listaTrabajador.get(i).getsBase()/14;
						
						totalDeducciones = listaTrabajador.get(i).getContGeneral() + listaTrabajador.get(i).getDesempleoT() + listaTrabajador.get(i).getFormacionT() + listaTrabajador.get(i).getiRPF();
						totalEmpresario = listaTrabajador.get(i).getContComunes() + listaTrabajador.get(i).getDesempleoE() + listaTrabajador.get(i).getFormacionE() + listaTrabajador.get(i).getAccTrabajo() + listaTrabajador.get(i).getfOGASA();
						totalTrabajador = totalDevengos + totalEmpresario;
						listaTrabajador.get(i).setsNetoMes(totalDevengos - totalDeducciones);
						//Crea la fuente del titulo
						Font fuente  = new Font();
						fuente.setStyle(Font.BOLD);
						fuente.setStyle(Font.UNDERLINE);
						fuente.setSize(16);
						//Fuente subrayada
						Font fuente1  = new Font();
						fuente1.setStyle(Font.BOLD);
						//Fuente de todo lo de la empresa
						Font fGris = new Font();
						fGris.setColor(136,136,136);
						
						if(listaTrabajador.get(i).getApellidoDos().equals("-1"))
							document.add(new Paragraph(listaTrabajador.get(i).getNombre() +" "+ listaTrabajador.get(i).getApellidoUno()+"     "+mes+"/"+anio, fuente));	
						else
							document.add(new Paragraph(listaTrabajador.get(i).getNombre() +" "+ listaTrabajador.get(i).getApellidoUno() +" "+ listaTrabajador.get(i).getApellidoDos()+"     "+mes+"/"+anio, fuente));
						
						document.add(new Paragraph(" "));
						document.add(new Paragraph("DNI"+"............................................................."+listaTrabajador.get(i).getDni()));
						document.add(new Paragraph("Empresa"+"....................................................."+listaTrabajador.get(i).getNombreEmpresa()));
						document.add(new Paragraph("CIF"+".............................................................."+listaTrabajador.get(i).getCifEmpresa()));
						document.add(new Paragraph("Fecha Alta Empresa"+"..................................."+listaTrabajador.get(i).getAltaEmpresa()));
						document.add(new Paragraph("Categoria"+"...................................................."+listaTrabajador.get(i).getCategoria()));
						document.add(new Paragraph("Bruto Anual"+"................................................."+listaTrabajador.get(i).getBrutoAnual()+"€"));
						document.add(new Paragraph(" "));
						document.add(new Paragraph(" "));
						document.add(new Paragraph("                                                                      NÓMINA EXTRA"));
						document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
						document.add(new Paragraph("                                                cant               Imp.Unit               Dev                      Deducc"));
						document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
						document.add(new Paragraph("Salario Base                          "+"30 días                                      "+df.format(listaTrabajador.get(i).getsBase()/14)));
						if(listaTrabajador.get(i).isProrratExtra())
							document.add(new Paragraph("Prorrateo                               "+"30 días                                       "+df.format(listaTrabajador.get(i).getProrrat())));
						else
							document.add(new Paragraph("Prorrateo                               "+"30 días                                       "+0));
						document.add(new Paragraph("Complemento                        "+"30 días                                      "+df.format(listaTrabajador.get(i).getComplementos()/14)));
						document.add(new Paragraph("Antiguedad                            "+(int)nTri +" trienio                                      "+df.format(importTri)));
						document.add(new Paragraph(" ")); 
						document.add(new Paragraph("Cont General                         "+listaCuotas.get(0).getCuotaObreraT()+"%                de "+df.format(listaTrabajador.get(i).getMensualConProrrateo())+"                                        "+df.format(listaTrabajador.get(i).getContGeneral())));
						document.add(new Paragraph("Desempleo                            "+listaCuotas.get(0).getCuotaDesempleoT()+"%                de "+df.format(listaTrabajador.get(i).getMensualConProrrateo())+"                                        "+df.format(listaTrabajador.get(i).getDesempleoT())));
						document.add(new Paragraph("Cuota Formacion                   "+listaCuotas.get(0).getCuotaFormacionT()+"%                de "+df.format(listaTrabajador.get(i).getMensualConProrrateo())+"                                        "+df.format(listaTrabajador.get(i).getFormacionT())));
						double retencion =0 ;
						for(int k = 0; k < listaRetenciones.size(); k++) {
							if(listaTrabajador.get(i).getBrutoAnual() > listaRetenciones.get(k).getBrutoAnual())
								retencion = listaRetenciones.get(k+1).getRetencion();
						}
						document.add(new Paragraph("IRPF                                      "+retencion+"%            de "+df.format(listaTrabajador.get(i).getsBrutoMes())+"                                         "+df.format(listaTrabajador.get(i).getiRPF())));
						document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
						document.add(new Paragraph("Total Deducciones                                                                                                    "+df.format(totalDeducciones), fuente1));
						document.add(new Paragraph("Total Devengos                                                                        "+df.format(totalDevengos), fuente1));
						document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------"));
						document.add(new Paragraph("                                                                                         Liquido A Percibir:        "+df.format(listaTrabajador.get(i).getsNetoMes()), fuente1));
						document.add(new Paragraph(" "));
						document.add(new Paragraph(" "));
						document.add(new Paragraph(" "));
						document.add(new Paragraph("Calculo Empresario:BASE                                                                                     "+0,fGris));
						document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------",fGris));
						document.add(new Paragraph("Contingencias Comunes "+listaCuotas.get(0).getContingenciasE()+"%                                                                             "+0,fGris));
						document.add(new Paragraph("Desempleo "+listaCuotas.get(0).getDesempleoE()+"%                                                                                                    "+0,fGris));
						document.add(new Paragraph("Formacion "+listaCuotas.get(0).getFormaconE()+"%                                                                                                     "+0,fGris));
						document.add(new Paragraph("Accidentes Trabajo "+listaCuotas.get(0).getAccidentesTrabajoE()+"%                                                                                       "+0,fGris));
						document.add(new Paragraph("FOGASA "+listaCuotas.get(0).getFogasaE()+"%                                                                                                       "+0,fGris));
						document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------",fGris));
						document.add(new Paragraph("Total Empresario                                                                                                   "+0,fGris));
						document.add(new Paragraph("Coste Total Trabajador                                                                                          "+0,fGris));
					}
					document.close();	
			}		
		}
	}
}
