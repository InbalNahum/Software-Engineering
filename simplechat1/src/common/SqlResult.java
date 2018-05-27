package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SqlResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5187591932230606046L;
	public List<String> columnNames;
	public List<Object> rowData;
	
	public SqlResult(){
		columnNames = new ArrayList<>();
		rowData = new ArrayList<>();
	}
	
	public int getRowCount(){
		return rowData.size()/columnNames.size();
	}
	
	public int getColumnCount(){
		return columnNames.size();
	}
	
	@Override
	public String toString() {
		String toRet = "";
		int rowIndex = 0;
		while (rowIndex < getRowCount()) {
		    rowIndex++;
		    toRet += String.format("Row %d%n", rowIndex);
		    for (int colIndex = 0; colIndex < columnNames.size(); colIndex++) {
		        String objType = "null";
		        String objString = "";
		        int absolutPosition = colIndex + (rowIndex-1)*getColumnCount();
		        Object columnObject = rowData.get(absolutPosition);
		        if (columnObject != null) {
		            objString = columnObject.toString() + " ";
		            objType = columnObject.getClass().getName();
		        }
		        toRet += String.format("  %s: %s(%s)%n",
		                columnNames.get(colIndex), objString, objType);
		    }
		}
		return toRet;
	}
	
}
