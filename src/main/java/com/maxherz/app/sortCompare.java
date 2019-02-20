package com.maxherz.app;

import org.apache.poi.openxml4j.exceptions.*;
//import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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
        
        final int rowSize = 4408; // rowsize. saves space
        final int numSelected = 10; // how many random numbers to sample
        final int trials = 2; //how many times to randomly sample and time

        double avgSel = 0;
        double avgIns = 0;
        double avgMerge = 0;
        double avgQuick = 0;
        double avgBogo=0;

        for(int i=0;i<trials;i++){
            String[] selected = getRand(numSelected, row, rowSize); // select numSelected number of random cells and store

            String[] selected2 = selected.clone(); // copy unsorted list so we can retime
            double selSortTime = selSort(selected); // selection sort
            avgSel += (selSortTime)/trials;//add fraction to total

            selected = selected2.clone(); 
            double inSortTime = inSort(selected); 
            avgIns += (inSortTime)/trials;

            selected = selected2.clone(); 
            double mergeSortTime = mergeMaster(selected); 
            avgMerge += (mergeSortTime)/trials;
            
            selected = selected2.clone(); 
            double quickSortTime = quickSortMaster(selected);
            avgQuick += (quickSortTime)/trials;

            selected = selected2.clone(); 
            double bogoSortTime = bogoSortMaster(selected); 
            avgBogo += (bogoSortTime)/trials; 

        }

        System.out.println("Time in milliseconds for "+trials+" independent trials below. In each trial, data was reselected randomly");
        System.out.println("Average time for selection sort: " + Math.round(avgSel*100)/100 +" milliseconds"); 
        System.out.println("Average time for Insertion sort: " + Math.round(avgIns*100)/100 +" milliseconds"); 
        System.out.println("Average time for merge sort: " + Math.round(avgMerge*100)/100 +" milliseconds"); 
        System.out.println("Average time for quick sort: " + Math.round(avgQuick*100)/100 +" milliseconds"); 
        System.out.println("Average time for bogo sort: " + Math.round(avgBogo*100)/100 +" milliseconds");


    }

    /*
     * @param num - number of cells to select
     * @param all - row of all cells
     * @param rowSize - length of row to consider
     * @requires num>=0, row.length=rowSize
     * @ensures returned array contains num number of randomly chosen, unique, cells
     * from row
     */
    private static String[] getRand(int num, Row row, int rowSize) {
        var dataFormatter = new DataFormatter();
        String[] selected = new String[num];
        Random r = new Random();
        int high =rowSize/num;
        int low = 0;

        for (int index=0;index<num;index++) {
            int rand =Integer.MAX_VALUE;

            while(rand>=rowSize){
                rand = r.nextInt(high-low)+low; 
            }
            selected[index] = dataFormatter.formatCellValue(row.getCell(rand));
            high += rowSize/num;
            low+=rowSize/num;
        }
        return selected;
    }

    /*
     * @param arr - array to time
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
        return ((endTime - startTime)/1000000);
    }

    /*
     * @param arr - array to time
     * @returns time in milliseconds to sort arr
     */
    private static double inSort(String arr[]) {
        long startTime = System.nanoTime();
        int n = arr.length;
        int i;
        String key;
        int j;
        for (i = 1; i < n; i++) {
            key = arr[i];
            j = i - 1;
            
            while (j >= 0 && arr[j].compareTo(key)>0) {
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
     * @ensures values of l and r will be merged according to String preorder
     */
    private static void merge(String[] arr, String[] l, String[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].compareTo(r[j]) <= 0) {
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

    /*
     * @param arr - array to print
     * @ensures arr is printed to console
     */
    private static void printArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
    }

      /*
     * @param arr - array to partition
     * @param low - low index to partion
     * @param high - pivot item
     * @ensures arr is printed to console
     */
    private static int partition(String arr[], int low, int high) 
    { 
        String pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 

            if (arr[j].compareTo(pivot)<=0) 
            { 
                i++; 
                String temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        String temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 
  
     /*
     * @param arr - array to print
     * @param low - starting index of sort
     * @param high - ending index to sort
     * @ensures arr is sorted 
     */
    private static void quickSort(String arr[], int low, int high) 
    { 
        if (low < high) 
        { 
            int pi = partition(arr, low, high); 
            quickSort(arr, low, pi-1); 
            quickSort(arr, pi+1, high); 
        } 
    } 

    /*
     * @param arr - array to sort
     * @returns time in milliseconds to sort arr
     */
    private static double quickSortMaster(String arr[]){
        long startTime = System.nanoTime();
        quickSort(arr, 0, arr.length-1);
        long endTime = System.nanoTime();
        return ((endTime - startTime) / 1000000);
    }

     /*
     * @param arr - array to sort
     * @returns time in milliseconds to sort arr
     */
    private static double bogoSortMaster(String arr[]){
        long startTime = System.nanoTime();
        while(!isSorted(arr)){
            shuffle(arr);
        }
        long endTime = System.nanoTime();
        return ((endTime - startTime) / 1000000);
    }

     /*
     * @param arr - array to sort
     * @ensures arr is randomly shuffeled around
     */
    private static void shuffle(String arr[]) 
    { 
         for (int i=1; i <= arr.length-1; i++) 
             swap(arr, i, (int)(Math.random()*i)); 
    } 

     /*
     * @param arr - array to swap indecies
     * @param i - first index to swap
     * @param j - second index to swap
     * @ensures #arr[i] = arr[j] and #arr[j]=arr[i]
     */
    private static void swap(String arr[], int i, int j) 
    { 
        String temp = arr[i]; 
        arr[i] = arr[j]; 
        arr[j] = temp; 
    } 

     /*
     * @param arr - array to check
     * @returns true if arr is sorted, false otherwise 
     */
    private static boolean isSorted(String arr[]) 
    { 
        for (int i=1; i<arr.length; i++) 
            if (arr[i].compareTo(arr[i-1])<0) 
                return false; 
        return true; 
    } 

}
