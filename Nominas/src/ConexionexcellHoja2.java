import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.regex.ParseException;

public class ConexionexcellHoja2 {
	String fileName = "resources/SistemasInformacionII.xlsx";
	
	//LISTAS USADAS PARA LEER HOJA 2
	public ArrayList<DatosSalario> listaTiposTrabajo = new <DatosSalario>ArrayList();
	public ArrayList<DatosSalario> listaCuotas = new <DatosSalario>ArrayList();
	public ArrayList<DatosSalario> listaRetenciones = new <DatosSalario>ArrayList();
	public ArrayList<DatosSalario> listaTrienios = new <DatosSalario>ArrayList();
	
	
	public void leerExcelHoja2() throws IOException{
		
		FileInputStream file = new FileInputStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet hoja = workbook.getSheetAt(1);
		
		//Lee de la fila 1 a la fila 15 para la ListaTiposTrabajador
		for(int i = 1 ;   i < 15; i++){
			 DatosSalario datos = new DatosSalario();
			 XSSFRow row  = hoja.getRow(i);
			 XSSFCell celda = row.getCell(0);
			 
			 if(row.getCell(0) != null)
				 datos.setCategoria(row.getCell(0).getStringCellValue());
			 else
				 datos.setCategoria("-1");
			 
			 if(row.getCell(1) != null)
				 datos.setSalarioBase(row.getCell(1).getNumericCellValue());
			 else
				 datos.setSalarioBase(-1);
			 
			 if(row.getCell(2) != null)
				 datos.setComplementos(row.getCell(2).getNumericCellValue());
			 else
				 datos.setComplementos(-1);
			 
			 if(row.getCell(3) != null)
				 datos.setCodigoCotizacion(row.getCell(3).getNumericCellValue());
			 else
				 datos.setCodigoCotizacion(-1);
			 
			 
			 listaTiposTrabajo.add(datos); 
			 
		}
		
		DatosSalario dato0 = new DatosSalario();
		dato0.setnTrienios(0);
		dato0.setImporteTrienio(0);
		listaTrienios.add(dato0);
		//Lee de la fila 18 a la fila 36 TRIENIOS
		for(int j = 18 ;   j < 36; j++){
			DatosSalario datos2 = new DatosSalario();
			XSSFRow rows  = hoja.getRow(j);
			 //XSSFCell celdas = row.getCell(0);
			
			 if(rows.getCell(3) != null)
				 datos2.setnTrienios(rows.getCell(3).getNumericCellValue());
			 else
				 datos2.setnTrienios(-1);
			 
			 if(rows.getCell(4) != null)
				 datos2.setImporteTrienio(rows.getCell(4).getNumericCellValue());
			 else
				 datos2.setImporteTrienio(-1);
			 
			 listaTrienios.add(datos2);
		}
		//Lee de la fila 2 a la fila 50 LISTA RETENCION
		for(int t = 1 ;   t < 50; t++){
			 DatosSalario datosRet = new DatosSalario();
			 XSSFRow row  = hoja.getRow(t);
			 XSSFCell celdas = row.getCell(0);
			 
			 if(row.getCell(5) != null)
				 datosRet.setBrutoAnual(row.getCell(5).getNumericCellValue());
			 else
				 datosRet.setBrutoAnual(-1);
			 
			 if(row.getCell(6) != null)
				 datosRet.setRetencion(row.getCell(6).getNumericCellValue());
			 else
				 datosRet.setRetencion(-1);
			 
			 listaRetenciones.add(datosRet);
		}
		
		
		//Lee de la fila 18 a la fila 25
		DatosSalario datos = new DatosSalario();
		XSSFRow row  = hoja.getRow(17);
		XSSFCell celda = row.getCell(0);

		if(row.getCell(1) != null)
			datos.setCuotaObreraT(row.getCell(1).getNumericCellValue());
		else
			 datos.setCuotaObreraT(-1);
		
		row = hoja.getRow(18);
		if(row.getCell(1) != null)
			datos.setCuotaDesempleoT(row.getCell(1).getNumericCellValue());
		else
			 datos.setCuotaDesempleoT(-1);
		
		row = hoja.getRow(19);
		if(row.getCell(1) != null)
			datos.setCuotaFormacionT(row.getCell(1).getNumericCellValue());
		else
			 datos.setCuotaFormacionT(-1);
		
		row = hoja.getRow(20);
		if(row.getCell(1) != null)
			datos.setContingenciasE(row.getCell(1).getNumericCellValue());
		else
			 datos.setContingenciasE(-1);
		
		row = hoja.getRow(21);
		if(row.getCell(1) != null)
			datos.setFogasaE(row.getCell(1).getNumericCellValue());
		else
			 datos.setFogasaE(-1);
		
		row = hoja.getRow(22);
		if(row.getCell(1) != null)
			datos.setDesempleoE(row.getCell(1).getNumericCellValue());
		else
			 datos.setDesempleoE(-1);
		
		row = hoja.getRow(23);
		if(row.getCell(1) != null)
			datos.setFormaconE(row.getCell(1).getNumericCellValue());
		else
			 datos.setFormaconE(-1);
		
		row = hoja.getRow(24);
		if(row.getCell(1) != null)
			datos.setAccidentesTrabajoE(row.getCell(1).getNumericCellValue());
		else
			 datos.setAccidentesTrabajoE(-1);
		
		listaCuotas.add(datos);
		
		
		pasarListaTiposTrabajador();
		pasarListaCuotas();
		pasarListaTrienios();
		pasarListaRetenciones();
		
	}
	
	public ArrayList<DatosSalario> pasarListaRetenciones() {
		return listaRetenciones;
		
	}

	public ArrayList<DatosSalario> pasarListaTrienios() {
		return listaTrienios;
		
	}

	public ArrayList<DatosSalario> pasarListaCuotas() {
		return listaCuotas;
		
	}

	public ArrayList<DatosSalario> pasarListaTiposTrabajador() {
		return listaTiposTrabajo;
	}
}
