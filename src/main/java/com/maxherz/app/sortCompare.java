package com.maxherz.app;

import org.apache.poi.openxml4j.exceptions.*;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Hello world!
 *
 */
public class sortCompare {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String SAMPLE_XLSX_FILE_PATH = "./testData.xlsx"; //path of excel file
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH)); // get workbook
        Sheet sheet = workbook.getSheetAt(0); // first sheet
        Row row = sheet.getRow(0); //row
        final int rowSize = 6; //rowsize. saves space
        int numSelected = 2; //how many random numbers to sample

        String[] selected = getRand(numSelected,row,rowSize); //select numSelected number of random cells and store in array

    }

    /*
        @param num - number of cells to select
        @param all - row of all cells
        @param rowSize - length of row to consider

        @requires num>=0, row.length=rowSize
        @ensures returned array contains num number of randomly chosen, unique, cells from row
    */
    private static String[] getRand(int num, Row row, int rowSize){
        var dataFormatter = new DataFormatter();
        int i=0;
        String[]selected = new String[num]; 
        HashSet<Integer>selIndex = new HashSet<>(); //chosen indecies, prevent picking the same one
        
        while(i<num){ 
            int rand = (int)(Math.random()*rowSize); //pick a random index. if it hasnt been chosen, use it
            if(!selIndex.contains(rand)){ 
                selected[i]=dataFormatter.formatCellValue(row.getCell(rand));
                selIndex.add(rand);
                i++;
            }
        }
        return selected;
    }

}
