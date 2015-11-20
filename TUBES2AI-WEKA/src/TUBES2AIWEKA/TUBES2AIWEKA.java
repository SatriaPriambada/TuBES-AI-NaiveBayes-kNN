package TUBES2AIWEKA;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
/**
 *
 * @author User
 */
public class TUBES2AIWEKA {

    
    private static String[][] DataSet;
    private static String[][] Acuan;
    private static int temp,yes,no,truePlus,trueMin,falsePlus,falseMin,jumlahMark,mark,endMark,NTotal=14;
    private static String kelas;
    
    public static void KNNFullTraining (String[][] DataSet, String[][] Acuan, int num)
    {
        truePlus=0;
        trueMin=0;
        falsePlus=0;
        falseMin=0;
        
        for (int k=0; k<NTotal; k++)
        {
            for (int i=0; i<NTotal; i++)
            {
                for (int j=0; j<5; j++)
                {
                    Acuan[i][j]=DataSet[i][j];
                }
            }
            
            for (int i=0; i<NTotal; i++)
            {
                temp = 0;
                for (int j=0; j<4; j++)
                {
                    if (DataSet[i][j] != Acuan[k][j])
                    {
                        temp++;
                    }
                }
                Acuan[i][5] = Integer.toString(temp);
            }
            
            Arrays.sort(Acuan, new Comparator<String[]>() 
            {
                @Override
                public int compare(String[] s1, String[] s2) 
                {
                    String t1 = s1[5];
                    String t2 = s2[5];
                    return t1.compareTo(t2);
                }
            });
            
            yes = 0;
            no = 0;
            
            for (int i=0; i<num; i++)
            {
                if (Acuan[i][4] == "yes")
                {
                    yes++;
                }
                else
                {
                    no++;
                }
            }
            
            if (yes>=no)
            {
                kelas = "yes";
            }
            else
            {
                    kelas = "no";
            }
            
            if (kelas==DataSet[k][4])
            {
                if (kelas=="yes")
                {
                    truePlus++;
                }
                else
                {
                    trueMin++;
                }
            }
            else
            {
                if (kelas=="yes")
                {
                    falsePlus++;
                }
                else
                {
                    falseMin++;
                }
            }
            
            for (int i=0; i<num; i++)
            {
                for (int j=0; j<6; j++)
                {
                    System.out.print (Acuan[i][j] + " ");
                }
                System.out.println();
            }
            
            System.out.println (kelas + " " + DataSet[k][4]);
            System.out.println (truePlus + " " + falsePlus + " " + falseMin + " " + trueMin );
            
        }
           
            
        System.out.println ("True Positif = " + truePlus);
        System.out.println ("False Positif = " + falsePlus);
        System.out.println ("False Negatif = " + falseMin);
        System.out.println ("True Negatif = " + trueMin);
    }
    
    public static void KNNTenFold(String[][] DataSet, String[][] Acuan, int num)
    {
        int i, j, k, l, currIndex = 0;
        truePlus=0;
        trueMin=0;
        falsePlus=0;
        falseMin=0;
        
        jumlahMark = NTotal;
        mark = 0;
                
        for (i=0; i<9; i++)
        {            
            endMark = (int) Math.ceil(jumlahMark/10.0);
            for (j=0; j<endMark; j++)
            {
                for (k=0; k<NTotal; k++)
                {
                    for (l=0; l<5; l++)
                    {
                        Acuan[k][l]=DataSet[k][l];
                    }
                }
                for (k=0; k<NTotal; k++)
                {
                    if ((k < currIndex) || (k >= currIndex+endMark))
                    {
                        temp = 0;
                        for (l=0; l<5; l++)
                        {
                            if (DataSet[k][l] != Acuan[currIndex][l])
                                temp++;
                        }
                        Acuan[k][5] = Integer.toString(temp);
                    }
                    else
                        Acuan[k][5] = "9";
                }
                Arrays.sort(Acuan, new Comparator<String[]>() 
                {
                    @Override
                    public int compare(String[] s1, String[] s2) 
                    {
                        String t1 = s1[5];
                        String t2 = s2[5];
                        return t1.compareTo(t2);
                    }
                });

                yes = 0;
                no = 0;

                for (k=0; k<num; k++)
                {
                    if (Acuan[k][4] == "yes")
                    {
                        yes++;
                    }
                    else
                    {
                        no++;
                    }
                }

                if (yes>=no)
                {
                    kelas = "yes";
                }
                else
                {
                        kelas = "no";
                }

                if (kelas==DataSet[i][4])
                {
                    if (kelas=="yes")
                    {
                        truePlus++;
                    }
                    else
                    {
                        trueMin++;
                    }
                }
                else
                {
                    if (kelas=="yes")
                    {
                        falsePlus++;
                    }
                    else
                    {
                        falseMin++;
                    }
                }                

                for (k=0; k<num; k++)
                {
                    for (l=0; l<6; l++)
                    {
                        System.out.print (Acuan[k][l] + " ");
                    }
                    System.out.println();
                }
                
                System.out.println (kelas + " " + DataSet[k][4]);
                System.out.println (truePlus + " " + falsePlus + " " + falseMin + " " + trueMin );

                currIndex++;
            }
            System.out.println(jumlahMark+" "+endMark);
            
            
            jumlahMark-=endMark;
        }
        
        for (i=currIndex;i<NTotal;i++)
        {
            for (k=0; k<NTotal; k++)
                {
                    for (l=0; l<5; l++)
                    {
                        Acuan[k][l]=DataSet[k][l];
                    }
                }
                for (k=0; k<NTotal; k++)
                {
                    if ((k < currIndex) || (k >= currIndex+endMark))
                    {
                        temp = 0;
                        for (l=0; l<5; l++)
                        {
                            if (DataSet[k][l] != Acuan[currIndex][l])
                                temp++;
                        }
                        Acuan[k][5] = Integer.toString(temp);
                    }
                    else
                        Acuan[k][5] = "9";
                }
                Arrays.sort(Acuan, new Comparator<String[]>() 
                {
                    @Override
                    public int compare(String[] s1, String[] s2) 
                    {
                        String t1 = s1[5];
                        String t2 = s2[5];
                        return t1.compareTo(t2);
                    }
                });

                yes = 0;
                no = 0;

                for (k=0; k<num; k++)
                {
                    if (Acuan[k][4] == "yes")
                    {
                        yes++;
                    }
                    else
                    {
                        no++;
                    }
                }

                if (yes>=no)
                {
                    kelas = "yes";
                }
                else
                {
                        kelas = "no";
                }

                if (kelas==DataSet[i][4])
                {
                    if (kelas=="yes")
                    {
                        truePlus++;
                    }
                    else
                    {
                        trueMin++;
                    }
                }
                else
                {
                    if (kelas=="yes")
                    {
                        falsePlus++;
                    }
                    else
                    {
                        falseMin++;
                    }
                }                                

                for (k=0; k<num; k++)
                {
                    for (l=0; l<6; l++)
                    {
                        System.out.print (Acuan[k][l] + " ");
                    }
                    System.out.println();
                }
                
                System.out.println (kelas + " " + DataSet[i][4]);
                System.out.println (truePlus + " " + falsePlus + " " + falseMin + " " + trueMin );

                currIndex++;
        }
        
        System.out.println ("True Positif = " + truePlus);
        System.out.println ("False Positif = " + falsePlus);
        System.out.println ("False Negatif = " + falseMin);
        System.out.println ("True Negatif = " + trueMin);

    }
    
    public static void InputToArray()
    {
        DataSet = new String[NTotal][6];
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
    }
    
    public static void main(String[] args) 
    {    
        InputToArray();
        
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();    
  
        Acuan = new String[NTotal][6];      
        KNNTenFold (DataSet,Acuan,num);
    }
}

