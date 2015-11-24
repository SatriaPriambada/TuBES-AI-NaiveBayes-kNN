/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */

package aitugasclassifier;

import kNN.ClassKNN;
/**
 * Class 
 *
 * Kelas yang digunakan untuk 
 *
 * @author Satria Priambada
 * @version 0.1
 */
public class CobaEksekusi {
    static String [][] Data;
    
    public static void InputToArray()
    {
        int NTotal = 14;
        Data = new String[NTotal][6];
            Data[0][0] = "sunny";
            Data[0][1] = "hot";
            Data[0][2] = "high";
            Data[0][3] = "FALSE";
            Data[0][4] = "no";
            Data[1][0] = "sunny";
            Data[1][1] = "hot";
            Data[1][2] = "high";
            Data[1][3] = "TRUE";
            Data[1][4] = "no";
            Data[2][0] = "overcast";
            Data[2][1] = "hot";
            Data[2][2] = "high";
            Data[2][3] = "FALSE";
            Data[2][4] = "yes";
            Data[3][0] = "rainy";
            Data[3][1] = "mild";
            Data[3][2] = "high";
            Data[3][3] = "FALSE";
            Data[3][4] = "yes";
            Data[4][0] = "rainy";
            Data[4][1] = "cool";
            Data[4][2] = "normal";
            Data[4][3] = "FALSE";
            Data[4][4] = "yes";
            Data[5][0] = "rainy";
            Data[5][1] = "cool";
            Data[5][2] = "normal";
            Data[5][3] = "TRUE";
            Data[5][4] = "no";
            Data[6][0] = "overcast";
            Data[6][1] = "cool";
            Data[6][2] = "normal";
            Data[6][3] = "TRUE";
            Data[6][4] = "yes";
            Data[7][0] = "sunny";
            Data[7][1] = "mild";
            Data[7][2] = "high";
            Data[7][3] = "FALSE";
            Data[7][4] = "no";
            Data[8][0] = "sunny";
            Data[8][1] = "cool";
            Data[8][2] = "normal";
            Data[8][3] = "FALSE";
            Data[8][4] = "yes";
            Data[9][0] = "rainy";
            Data[9][1] = "mild";
            Data[9][2] = "normal";
            Data[9][3] = "FALSE";
            Data[9][4] = "yes";
            Data[10][0] = "sunny";
            Data[10][1] = "mild";
            Data[10][2] = "normal";
            Data[10][3] = "TRUE";
            Data[10][4] = "yes";
            Data[11][0] = "overcast";
            Data[11][1] = "mild";
            Data[11][2] = "high";
            Data[11][3] = "TRUE";
            Data[11][4] = "yes";
            Data[12][0] = "overcast";
            Data[12][1] = "hot";
            Data[12][2] = "normal";
            Data[12][3] = "FALSE";
            Data[12][4] = "yes";
            Data[13][0] = "rainy";
            Data[13][1] = "mild";
            Data[13][2] = "high";
            Data[13][3] = "TRUE";
            Data[13][4] = "no";
    }
    public static void main(String[] args){
        ClassKNN Clsfr = new ClassKNN();
        InputToArray();
        String[][] Acuan = new String [14][6];
        Clsfr.KNNTenFold(Data, Acuan, 2);
    }
}
