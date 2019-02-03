import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.regex.ParseException;

public class ConexionExcell {
	
	//Coge el path donde esta el archivo xls
	String fileName = "resources/SistemasInformacionII.xlsx";
	//String fileName = "resources/dos.xlsx";
	//Almacena los trabajadores creados
	public ArrayList<Trabajador> listaTrabajador = new <Trabajador>ArrayList();
	
	//LISTAS USADAS PARA LEER HOJA 2
	public ArrayList<DatosSalario> listaTiposTrabajo = new <DatosSalario>ArrayList();
	public ArrayList<DatosSalario> listaCuotas = new <DatosSalario>ArrayList();
	public ArrayList<DatosSalario> listaRetenciones = new <DatosSalario>ArrayList();
	public ArrayList<DatosSalario> listaTrienios = new <DatosSalario>ArrayList();
	
	
	
	public ArrayList<Trabajador> leerExcell() throws IOException{
		
		try {
			
			//Archivo con el nombre excel
			FileInputStream file = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Coge la primera hoja del archivo
			XSSFSheet hoja = workbook.getSheetAt(0);

			//Guarda todos los datos de la hoja en la clase trabajador
			 for(int i = 1 ;   i < hoja.getLastRowNum()+1; i++){
				 Trabajador trabajador = new Trabajador();
				 XSSFRow row  = hoja.getRow(i);
				
				 XSSFCell celda = row.getCell(0);
				 
				 SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
				 SimpleDateFormat fecha1 = new SimpleDateFormat("dd/MM/yyyy");
				 SimpleDateFormat fecha2 = new SimpleDateFormat("dd/MM/yyyy");
				
				 if(celda==null){
					trabajador.setDni("-1");
					
				 }else 
					trabajador.setDni(row.getCell(0).getStringCellValue());
				 
				if(trabajador.getDni().equals(""))
					trabajador.setDni("-1");
				 
				
				 if(row.getCell(1) == null){
					 trabajador.setApellidoUno("-1");
				 }else
					 trabajador.setApellidoUno(row.getCell(1).getStringCellValue());
				 
				 if(row.getCell(2) == null){
					 trabajador.setApellidoDos("-1");
				 }else
					 trabajador.setApellidoDos(row.getCell(2).getStringCellValue());
				 
				 if(row.getCell(3) == null){
					 trabajador.setNombre("-1");
				 }else
					 trabajador.setNombre(row.getCell(3).getStringCellValue());
				 
				 if(row.getCell(4) == null){
					 trabajador.setEmail("-1");
				 }else
					 trabajador.setEmail(row.getCell(4).getStringCellValue());
				 
				 if(row.getCell(5) == null){
					 trabajador.setCategoria("-1");
				 }else
					 trabajador.setCategoria(row.getCell(5).getStringCellValue());
				 
				 if(row.getCell(6) == null){
					 trabajador.setNombreEmpresa("-1");
				 }else
					 trabajador.setNombreEmpresa(row.getCell(6).getStringCellValue());
				 
				 if(row.getCell(7) == null){
					 trabajador.setCifEmpresa("-1");
				 }else
					 trabajador.setCifEmpresa(row.getCell(7).getStringCellValue());
				
				 if(row.getCell(8) != null){
					trabajador.setAltaEmpresa(fecha.format(row.getCell(8).getDateCellValue()));
				 }else {
					 trabajador.setAltaEmpresa("-1");
				 }
				
				 if(row.getCell(9) != null){
					 trabajador.setAltaLaboral(fecha.format(row.getCell(9).getDateCellValue()));
				 }else {
					 trabajador.setAltaLaboral("-1");
				 }
				 /*
				 if(row.getCell(10) != null) {
					 trabajador.setBajaLaboral(fecha.format(row.getCell(10).getDateCellValue()));
				 }else {
					 //EHHHH NO FUNCIONA DE LA FILA 75-79 No entiendo porque
					 trabajador.setBajaLaboral("-1");
				 }*/
				
				 if(row.getCell(11) == null){
					 trabajador.setExtraForazadas(0);
				 }else
					 trabajador.setExtraForazadas(row.getCell(11).getNumericCellValue());
				 
				 if(row.getCell(12) == null){
					 trabajador.setExtraVolunt(0);
				 }else
					 trabajador.setExtraVolunt(row.getCell(12).getNumericCellValue());
				 
				 if(row.getCell(13) == null){
					 trabajador.setProrratExtra(false);
				 }else {
					 if(row.getCell(13).getStringCellValue().equals("SI")) {
						 trabajador.setProrratExtra(true);
					 }
					 else 
						 trabajador.setProrratExtra(false);
				 }
				 
				 if(row.getCell(14) == null){
					 trabajador.setCuentaBancaria("-1");
				 }else
					 trabajador.setCuentaBancaria(row.getCell(14).getStringCellValue());
				 
				 if(row.getCell(15) == null){
					 trabajador.setPais("-1");
				 }else
					 trabajador.setPais(row.getCell(15).getStringCellValue());
				 
				 if(row.getCell(16) == null){
					 trabajador.setIban("-1");
				 }else
					 trabajador.setIban(row.getCell(16).getStringCellValue());
				 
				 
				 listaTrabajador.add(trabajador);
					 					 
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return listaTrabajador;
		
	}

	public void escribir(ArrayList<Trabajador> listaTrabajador) throws IOException, java.text.ParseException {
		FileInputStream file= new FileInputStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet hoja  = workbook.getSheetAt(0);	  
		int cont = 1;
		
		for(int i = 0; i < listaTrabajador.size(); i++) {
			
			if(!listaTrabajador.get(i).getDni().equals("-1") && !listaTrabajador.get(i).getDni().equals("-2"))
				hoja.getRow(cont).createCell(0).setCellValue(listaTrabajador.get(i).getDni());
			if(!listaTrabajador.get(i).getApellidoUno().equals("-1"))
				hoja.getRow(cont).createCell(1).setCellValue(listaTrabajador.get(i).getApellidoUno());
			if(!listaTrabajador.get(i).getApellidoDos().equals("-1"))
				hoja.getRow(cont).createCell(2).setCellValue(listaTrabajador.get(i).getApellidoDos());
			if(!listaTrabajador.get(i).getNombre().equals("-1"))
				hoja.getRow(cont).createCell(3).setCellValue(listaTrabajador.get(i).getNombre());
			if(!listaTrabajador.get(i).getEmail().equals("-1"))
				hoja.getRow(cont).createCell(4).setCellValue(listaTrabajador.get(i).getEmail());
			if(!listaTrabajador.get(i).getCategoria().equals("-1"))
				hoja.getRow(cont).createCell(5).setCellValue(listaTrabajador.get(i).getCategoria());
			if(!listaTrabajador.get(i).getNombreEmpresa().equals("-1"))
				hoja.getRow(cont).createCell(6).setCellValue(listaTrabajador.get(i).getNombreEmpresa());
			if(!listaTrabajador.get(i).getCifEmpresa().equals("-1"))
				hoja.getRow(cont).createCell(7).setCellValue(listaTrabajador.get(i).getCifEmpresa());
			
			Date fechaAltaE;
			Date fechaAltaL;
			Date fechaBajaL;
			SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
			
			//Las fechas no hay que modificarlas
			
			hoja.getRow(i+1).createCell(11).setCellValue(listaTrabajador.get(i).getExtraForazadas());
			
			hoja.getRow(i+1).createCell(12).setCellValue(listaTrabajador.get(i).getExtraVolunt());
			
			 if(listaTrabajador.get(i).isProrratExtra() == true)
				 hoja.getRow(i+1).createCell(13).setCellValue("SI");
			 else if(listaTrabajador.get(i).isProrratExtra() == false)
				 hoja.getRow(i+1).createCell(13).setCellValue("NO");
			 
			if(!listaTrabajador.get(i).getCuentaBancaria().equals("-1"))
				hoja.getRow(cont).createCell(14).setCellValue(listaTrabajador.get(i).getCuentaBancaria());
			
			if(!listaTrabajador.get(i).getPais().equals("-1"))
				hoja.getRow(cont).createCell(15).setCellValue(listaTrabajador.get(i).getPais());
			
			if(!listaTrabajador.get(i).getIban().equals("-1"))
				hoja.getRow(cont).createCell(16).setCellValue(listaTrabajador.get(i).getIban());
			cont++;
		}
		
		FileOutputStream output_file =new FileOutputStream(new File("resources/SistemasInformacionII.xlsx"));
		workbook.write(output_file);
		output_file.close();
		file.close();
		
		System.out.println("Excell modificado con exito");
	}
	
	public void imprime(ArrayList<Trabajador> listaTrabajador) {//Imprime todos los datos guardados en la clase trabajador
		
		for(int i  = 0; i < listaTrabajador.size(); i++) {
			System.out.print(listaTrabajador.get(i).getDni()+" || ");
			System.out.print(listaTrabajador.get(i).getNombre()+" ");
			System.out.print(listaTrabajador.get(i).getApellidoUno()+" ");
			System.out.print(listaTrabajador.get(i).getApellidoDos()+ " ||");
			System.out.print(listaTrabajador.get(i).getEmail()+ " ||");
			System.out.print(listaTrabajador.get(i).getCategoria()+ " ||");
			System.out.print(listaTrabajador.get(i).getNombreEmpresa()+ " ||");
			System.out.print(listaTrabajador.get(i).getCifEmpresa()+ " ||");
			System.out.print(listaTrabajador.get(i).getAltaEmpresa()+ " ||");
			System.out.print(listaTrabajador.get(i).getAltaLaboral()+ " ||");
			System.out.print(listaTrabajador.get(i).getBajaLaboral()+ " ||");
			System.out.print(listaTrabajador.get(i).getExtraForazadas()+ " ||");
			System.out.print(listaTrabajador.get(i).getExtraVolunt()+ " ||");
			
			if(listaTrabajador.get(i).isProrratExtra() == false)
				System.out.print("NO"+ " ||");
			else
				System.out.print("SI"+ " ||");
			
			System.out.print(listaTrabajador.get(i).getCuentaBancaria()+ " ||");	
			System.out.println(listaTrabajador.get(i).getIban());
		}	
	}
	
		 

	
}
