package com.maxherz.app;

import org.apache.poi.openxml4j.exceptions.*;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * Hello world!
 *
 */
public class sortCompare {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String SAMPLE_XLSX_FILE_PATH = "./testData.xlsx"; // path of excel file
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH)); // get workbook
        Sheet sheet = workbook.getSheetAt(0); // first sheet
        Row row = sheet.getRow(0); // row
        final int rowSize = 6; // rowsize. saves space
        int numSelected = 4; // how many random numbers to sample

        String[] selected = getRand(numSelected, row, rowSize); // select numSelected number of random cells and store

        System.out.println("Selected:"); //show chosen numbers
        printArray(selected);
        System.out.println();

        String[] selected2 = selected.clone(); //copy unsorted list so we can retime
        double selSortTime = selSort(selected); //selection sort
        System.out.println("Insertion Sorted:"); //array after selection sort
        printArray(selected); 
        System.out.println();
        System.out.println("Time for selection sort: " + selSortTime); //time for selection sort
    
        selected = selected2.clone(); //copy back unsorted array to resort
        double mergeSortTime = mergeMaster(selected); //time merge sort
        System.out.println("Merge Sorted:"); //array after merge sort
        printArray(selected); 
        System.out.println();
        System.out.println("Time for merge sort: " + mergeSortTime); //time for merge sort

    }

    /*
     * @param num - number of cells to select
     * 
     * @param all - row of all cells
     * 
     * @param rowSize - length of row to consider
     * 
     * @requires num>=0, row.length=rowSize
     * 
     * @ensures returned array contains num number of randomly chosen, unique, cells
     * from row
     */
    private static String[] getRand(int num, Row row, int rowSize) {
        var dataFormatter = new DataFormatter();
        int i = 0;
        String[] selected = new String[num];
        HashSet<Integer> selIndex = new HashSet<>(); // chosen indecies, prevent picking the same one

        while (i < num) {
            int rand = (int) (Math.random() * rowSize); // pick a random index. if it hasnt been chosen, use it
            if (!selIndex.contains(rand)) {
                selected[i] = dataFormatter.formatCellValue(row.getCell(rand));
                selIndex.add(rand);
                i++;
            }
        }
        return selected;
    }

    /*
     * @param arr - array to time
     * 
     * @returns time in milliseconds to sort arr
     */
    private static double selSort(String arr[]) {
        long startTime = System.nanoTime();
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            String key = arr[i];
            int j = i - 1;

            /*
             * Move elements of arr[0..i-1], that are greater than key, to one position
             * ahead of their current position
             */
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        long endTime = System.nanoTime();
        return ((endTime - startTime) / 1000000);
    }

    /*
     * @param arr - array to time
     * 
     * @returns time in milliseconds to sort arr
     */
    private static double mergeMaster(String[] arr) {
        long startTime = System.nanoTime();
        mergeSort(arr, arr.length);
        long endTime = System.nanoTime();
        return ((endTime - startTime) / 1000000);
    }

    /*
     * @param arr - array to sort
     * @param n - length to sort through
     * 
     * @ensures arr will be sorted according to String preorder
     */
    private static void mergeSort(String[] arr, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        String[] l = new String[mid];
        String[] r = new String[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = arr[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = arr[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(arr, l, r, mid, n - mid);
    }

    /*
     * @param arr - array to store values
     * @param l - left array to merge
     * @param r - right array to merge
     * @param left - left index
     * @param right - right index
     * 
     * @ensures values of l and r will be merged according to String preorder
     */
    private static void merge(String[] arr, String[] l, String[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].compareTo(r[j])<=0) {
                arr[k++] = l[i++];
            } else {
                arr[k++] = r[j++];
            }
        }
        while (i < left) {
            arr[k++] = l[i++];
        }
        while (j < right) {
            arr[k++] = r[j++];
        }
    }

    /* @param arr - array to print
     *
     * @ensures arr is printed to console
     */
    private static void printArray(String[]arr){
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+",");
        }
    }
}
