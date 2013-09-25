package xlsdiff;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class Read {
	
	private String filename = null;
	private int x = 0;
	private int y = 0;
	private HashMap<String, String> map = new HashMap<String, String>();
	private String sheet_name = null;
	
	Read(String filename){
		this.filename = filename;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public String getFilename(){
		return this.filename;
	}
	
	public HashMap<String, String> getMap(){
		return this.map;
	}
	
	public String getSheetName(){
		return this.sheet_name;
	}
	
	public void setSheetName(String sheet_name){
		this.sheet_name = sheet_name;
	}
	
	/***
	 * ["row:colmumn", "value"]のハッシュを作る
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setData()throws FileNotFoundException, IOException{
		HSSFWorkbook book = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(filename)));
		HSSFSheet sheet = book.getSheetAt(0); //0を指定すると最初のシート。シート名を使う場合はgetSheet()
		
		this.y = sheet.getLastRowNum();
		
		Iterator<?> it = sheet.rowIterator();
		while (it.hasNext()){
			HSSFRow row = (HSSFRow)it.next();
			Iterator<?> itc = row.cellIterator();
			while (itc.hasNext()){
				HSSFCell cell = (HSSFCell)itc.next();
				map.put(cell.getRowIndex()+":"+cell.getColumnIndex(),filter(cell));
				if(this.x < cell.getColumnIndex()){
					this.x = cell.getColumnIndex();
				}
			}
		}
	}
	
	/***
	 * 日付をマスクするメソッド。
	 * @param cell
	 * @return
	 */
	private static String filter(HSSFCell cell){
		String str;
		switch (cell.getCellType()){
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)){
				cell.setCellValue("date");//日付の時はdateにする
				str = cell.getStringCellValue();
			}else{
				//numeric
				str =String.valueOf(cell.getNumericCellValue());
			}
			break;

		case Cell.CELL_TYPE_STRING:
			str = String.valueOf(cell.getRichStringCellValue());
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			str = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_BLANK:
			str = "";
			break;
		
		default:
			str = null;
			break;
		}
		return str;
	}
}
