/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes2ai.weka;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
/**
 *
 * @author User
 */
public class TUBES2AIWEKA {

    
    private static String[][] DataSet;
    private static String[] Acuan;
    private static int temp,yes,no;
    private static String kelas;
    
    public static void KNN ()
    {
        
    }
    
    public static void NaiveBayes ()
    {
        
    }
    
    public static void main(String[] args) 
    {
        TUBES2AIWEKA.DataSet = new String[14][6];
            DataSet[0][0] = "sunny";
            DataSet[0][1] = "hot";
            DataSet[0][2] = "high";
            DataSet[0][3] = "FALSE";
            DataSet[0][4] = "no";
            DataSet[1][0] = "sunny";
            DataSet[1][1] = "hot";
            DataSet[1][2] = "high";
            DataSet[1][3] = "TRUE";
            DataSet[1][4] = "no";
            DataSet[2][0] = "overcast";
            DataSet[2][1] = "hot";
            DataSet[2][2] = "high";
            DataSet[2][3] = "FALSE";
            DataSet[2][4] = "yes";
            DataSet[3][0] = "rainy";
            DataSet[3][1] = "mild";
            DataSet[3][2] = "high";
            DataSet[3][3] = "FALSE";
            DataSet[3][4] = "yes";
            DataSet[4][0] = "rainy";
            DataSet[4][1] = "cool";
            DataSet[4][2] = "normal";
            DataSet[4][3] = "FALSE";
            DataSet[4][4] = "yes";
            DataSet[5][0] = "rainy";
            DataSet[5][1] = "cool";
            DataSet[5][2] = "normal";
            DataSet[5][3] = "TRUE";
            DataSet[5][4] = "no";
            DataSet[6][0] = "overcast";
            DataSet[6][1] = "cool";
            DataSet[6][2] = "normal";
            DataSet[6][3] = "TRUE";
            DataSet[6][4] = "yes";
            DataSet[7][0] = "sunny";
            DataSet[7][1] = "mild";
            DataSet[7][2] = "high";
            DataSet[7][3] = "FALSE";
            DataSet[7][4] = "no";
            DataSet[8][0] = "sunny";
            DataSet[8][1] = "cool";
            DataSet[8][2] = "normal";
            DataSet[8][3] = "FALSE";
            DataSet[8][4] = "yes";
            DataSet[9][0] = "rainy";
            DataSet[9][1] = "mild";
            DataSet[9][2] = "normal";
            DataSet[9][3] = "FALSE";
            DataSet[9][4] = "yes";
            DataSet[10][0] = "sunny";
            DataSet[10][1] = "mild";
            DataSet[10][2] = "normal";
            DataSet[10][3] = "TRUE";
            DataSet[10][4] = "yes";
            DataSet[11][0] = "overcast";
            DataSet[11][1] = "mild";
            DataSet[11][2] = "high";
            DataSet[11][3] = "TRUE";
            DataSet[11][4] = "yes";
            DataSet[12][0] = "overcast";
            DataSet[12][1] = "hot";
            DataSet[12][2] = "normal";
            DataSet[12][3] = "FALSE";
            DataSet[12][4] = "yes";
            DataSet[13][0] = "rainy";
            DataSet[13][1] = "mild";
            DataSet[13][2] = "high";
            DataSet[13][3] = "TRUE";
            DataSet[13][4] = "no";
            
        TUBES2AIWEKA.Acuan = new String[5];
            Acuan[0] = "sunny";
            Acuan[1] = "hot";
            Acuan[2] = "normal";
            Acuan[3] = "TRUE";
            Acuan[4] = "yes";
        
            for (int i=0; i<14; i++)
            {
                temp = 0;
                for (int j=0; j<5; j++)
                {
                    if (DataSet[i][j] != Acuan[j])
                    {
                        temp++;
                    }
                }
                DataSet[i][5] = Integer.toString(temp);
            }
            
            Arrays.sort(DataSet, new Comparator<String[]>() 
            {
                @Override
                public int compare(String[] s1, String[] s2) {
                    String t1 = s1[5];
                    String t2 = s2[5];
                    return t1.compareTo(t2);
                }
            });

            Scanner in = new Scanner(System.in);
            int num = in.nextInt();
            
            yes = 0;
            no = 0;
            
            for (int i=0; i<num; i++)
            {
                if (DataSet[i][4] == "yes")
                {
                    yes++;
                }
                else
                {
                    no++;
                }
            }
            
            if (yes>no)
            {
                kelas = "yes";
            }
            else
            {
                if (yes<no)
                {
                    kelas = "no";
                }
                else
                {
                    kelas = "Unclassified";
                }
            }
            
            for (int i=0; i<num; i++)
            {
                for (int j=0; j<6;j++)
                {
                    System.out.print (DataSet[i][j] + " ");
                }
                System.out.println();
            }
           
            System.out.println ("Jumlah Yes = " + yes);
            System.out.println ("Jumlah No = " + no);
            System.out.println ("Kelas = " + kelas);
    }
}

