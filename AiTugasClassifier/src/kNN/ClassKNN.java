package kNN;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Comparator;
/**
 *
 * @author User
 */
public class ClassKNN {

    
    private static String[][] Data;
    private static String[][] AcuanAwal;
    private static int temp,yes,no,truePlus,trueMin,falsePlus,falseMin,jumlahMark,mark,endMark,NTotal=14;
    private static String kelas;
    
    public void KNNFullTraining (String[][] DataSet, String[][] Acuan, int num, int TotalBaris, int TotalKolom)
    {
        truePlus=0;
        trueMin=0;
        falsePlus=0;
        int i,j;
        
        for (int k=0; k < NTotal; k++)
        {
            //copy acuan data set
            for (i=0; i < NTotal; i++)
            {
                for (j=0; j < 5; j++)
                {
                    Acuan[i][j] = DataSet[i][j];
                }
            }
            //hitung jumlah perbedaan
            for (i=0; i<NTotal; i++)
            {
                temp = 0;
                for (j=0; j<4; j++)
                {
                    if (!(DataSet[i][j].equals(Acuan[k][j])))
                    {
                        temp++;
                    }
                }
                Acuan[i][5] = Integer.toString(temp);
            }
            //bandingkan jumlah perbedaan
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
            //hitung banyak yes dan no
            for (i=0; i<num; i++)
            {
                if (Acuan[i][4].equals("yes"))
                {
                    yes++;
                }
                else
                {
                    no++;
                }
            }
            //klasifikasi sementara
            if (yes >= no)
            {
                kelas = "yes";
            }
            else
            {
                kelas = "no";
            }
            //lihat dengan data set sebenarnya apakah true positif etc
            if (kelas.equals(DataSet[k][4]))
            {
                if (kelas.equals("yes"))
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
                if (kelas.equals("yes"))
                {
                    falsePlus++;
                }
                else
                {
                    falseMin++;
                }
            }
            
            for (i=0; i<num; i++)
            {
                for (j=0; j<6; j++)
                {
                    System.out.print (Acuan[i][j] + " ");
                }
                System.out.println();
            }
            
            System.out.println (kelas + " " + DataSet[k][4]);
            System.out.println (truePlus + " " + falsePlus + " " + falseMin + " " + trueMin );
            
        }
           
        //print out hasil akhir    
        System.out.println ("True Positif = " + truePlus);
        System.out.println ("False Positif = " + falsePlus);
        System.out.println ("False Negatif = " + falseMin);
        System.out.println ("True Negatif = " + trueMin);
    }
    
    public void KNNTenFold(String[][] DataSet, String[][] Acuan, int num)
    {
        int i, j, k, l, currIndex = 0;
        truePlus=0;
        trueMin=0;
        falsePlus=0;
        falseMin=0;
        
       
        jumlahMark = NTotal;
        mark = 0;
        //untuk 9 iterasi pertama        
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
                for (k=0; k < NTotal; k++)
                {
                    if ((k < currIndex) || (k >= currIndex+endMark))
                    {
                        temp = 0;
                        for (l=0; l<5; l++)
                        {
                            if (!(DataSet[k][l].equals(Acuan[currIndex][l])))
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
                    if (Acuan[k][4].equals("yes"))
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

                if (kelas.equals(DataSet[i][4]))
                {
                    if (kelas.equals("yes"))
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
                    if (kelas.equals("yes"))
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
        //untuk iterasi terakhir
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
                            if (!(DataSet[k][l].equals(Acuan[currIndex][l])))
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
                    if (Acuan[k][4].equals("yes"))
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

                if (kelas.equals(DataSet[i][4]))
                {
                    if (kelas.equals("yes"))
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
                    if (kelas.equals("yes"))
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
}

