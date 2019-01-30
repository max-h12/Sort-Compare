package com.maxherz.app;

import org.apache.poi.openxml4j.exceptions.*;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class sortCompare {
    public static final String SAMPLE_XLSX_FILE_PATH = "./testData.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH)); // get workbook
        Sheet sheet = workbook.getSheetAt(0); // first sheet
        DataFormatter dataFormatter = new DataFormatter();
        ArrayList<String>all = new ArrayList<>();
        Row row = sheet.getRow(0); //row
        int numSelected = 2; //how many random numbers to sample

        Iterator<Cell> cellIterator = row.cellIterator();
        int i=0;
        while (cellIterator.hasNext()) { //populate arraylist with all row numbers
            Cell cell = cellIterator.next(); 
            String val = dataFormatter.formatCellValue(cell);
            all.add(i, val);
        }

        System.out.println(all);
        String[] selected = getRand(numSelected,all); //select numSelected number of random cells and store in array
    }

    /*
        @param num - number of cells to select
        @param all - arraylist of all cells

        @requires rowSize>0, num>=0, all!=null
        @ensures returned array contains num number of randomly chosen, unique, cells from all
    */
    private static String[] getRand(int num, ArrayList<String> all){
        int i=0;
        String[]selected = new String[num]; 
        HashSet<Integer>selIndex = new HashSet<>(); //chosen indecies, prevent picking the same one
        
        while(i<num){ 
            int rand = (int)(Math.random()*all.size()); //pick a random index. if it hasnt been chosen, use it
            if(!selIndex.contains(rand)){ 
                selected[i]=all.get(rand);
                selIndex.add(rand);
                i++;
            }
        }
        
        return selected;
    }

}
