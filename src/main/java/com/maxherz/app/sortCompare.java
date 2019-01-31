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
        int numSelected = 4; //how many random numbers to sample

        String[] selected = getRand(numSelected,row,rowSize); //select numSelected number of random cells and store in array
        double selSortTime = selSort(selected);
        System.out.println("Time for selection sort: "+ selSortTime);
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

    /*
        @param arr - array to time 
        @returns time in milliseconds to sort arr
    */
    private static double selSort(String arr[]) 
    { 
        long startTime = System.nanoTime();
        int n = arr.length; 
        for (int i=1; i<n; ++i) 
        { 
            String key = arr[i]; 
            int j = i-1; 
  
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j>=0 && arr[j].compareTo(key)>0) 
            { 
                arr[j+1] = arr[j]; 
                j = j-1; 
            } 
            arr[j+1] = key; 
        } 
        long endTime = System.nanoTime();
        return ((endTime - startTime)/1000000);
    } 

}
