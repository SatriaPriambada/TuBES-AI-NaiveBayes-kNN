package kNN;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author User
 */
public class ClassKNN {

    private static int temp,jumlahMark,endMark,NTotal;
    
    public void KNNFullTraining (String[][] DataSet, String[][] TestData, int num, int TotalBaris, int TotalKolom)
    {
        int i,j;
        NTotal = TotalBaris;
        
        int[] Peluang;
        int[][] ConfusionMatrix;
        
        Set<String> JenisKelas = new HashSet<String>(Arrays.asList());
        //Total Kolom melambangkan batas akhir dari kolom yang berisi "temp"
        int KolomPembanding = TotalKolom - 1; // Dalam kasus contoh nilainnya = 5 yang menyimpan jarak euclid dari data yang sedang ditest
        int IndexKelas = TotalKolom - 2; //Dalam kasus contoh nilainnya = 4 berisi play = "yes" atau "no"
        //Masukkan jenis kelas ke dalam sebuah set
        for (i = 0; i < TotalBaris;i++){
            JenisKelas.add(DataSet[i][IndexKelas]);
        }
        List<String> listJenisKelas = new ArrayList<String>(JenisKelas);
        ConfusionMatrix = new int[JenisKelas.size()][JenisKelas.size()];
        Peluang = new int[JenisKelas.size()];
        String[][] Acuan = new String[TotalBaris][TotalKolom];
        for (int k=0; k < NTotal; k++)
        {
            //copy acuan data set
            for (i=0; i < NTotal; i++)
            {
                for (j=0; j < KolomPembanding; j++)
                {
                    Acuan[i][j] = TestData[i][j];
                }
            }
            //hitung jumlah perbedaan
            for (i=0; i<NTotal; i++)
            {
                temp = 0;
                for (j=0; j<IndexKelas; j++)
                {
                    if (!(DataSet[i][j].equals(Acuan[k][j])))
                    {
                        temp++;
                    }
                }
                Acuan[i][KolomPembanding] = Integer.toString(temp);
            }
            //bandingkan jumlah perbedaan
            Arrays.sort(Acuan, new Comparator<String[]>() 
            {
                @Override
                public int compare(String[] s1, String[] s2) 
                {
                    String t1 = s1[KolomPembanding];
                    String t2 = s2[KolomPembanding];
                    return t1.compareTo(t2);
                }
            });
            
            //hitung banyak yes dan no
            for (i=0; i < num; i++)
            {
                for (int iKelas = 0; iKelas < JenisKelas.size(); iKelas++) {
                    if (Acuan[i][IndexKelas].equals(listJenisKelas.get(iKelas))){
                        Peluang[iKelas] = Peluang[iKelas] + 1;
                    }
                }
            }
            //sorting asceding. Element terakhir pasti paling besar
            int[] IndexPeluang = new int[Peluang.length];
            System.arraycopy(Peluang, 0, IndexPeluang, 0, Peluang.length);
            Arrays.sort(Peluang);
            out.println("Peluang:" + Arrays.toString(Peluang));
            out.println("Idx Peluang:" +Arrays.toString(IndexPeluang));
            
            //Search index dari IndexPeluang yang merupakan tebakan klasifikasi kelas sementara
            int cariPeluangTerbesar=0;
            //Peluang[Peluang.length-1] pasti merupakan kelas paling akhir dengan count terbesar;
            while (Peluang[Peluang.length-1] != IndexPeluang[JenisKelas.size() - 1 - cariPeluangTerbesar]){
                cariPeluangTerbesar++;
            }
            //cari peluang dari elemen terkanan
            cariPeluangTerbesar = JenisKelas.size() - 1 - cariPeluangTerbesar;
            Peluang = new int[JenisKelas.size()];
            //cari indeks dari kelas seharusnya dalam dataset
           int RealClass = 0;
           while (!(DataSet[k][IndexKelas].equals(listJenisKelas.get(RealClass)))){
               RealClass++;
            }
            out.println("Kelas seharusnya = "+RealClass+DataSet[k][IndexKelas]);
            out.println("Idx peluang terbesar = "+cariPeluangTerbesar);
            if (DataSet[k][IndexKelas].equals(DataSet[k][cariPeluangTerbesar])){
              //Correctly classifying true positif or true negative is in diagonal
              ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar] = ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar]+1;
            } else {
              ConfusionMatrix[RealClass][cariPeluangTerbesar] = ConfusionMatrix[RealClass][cariPeluangTerbesar]+1;
            }
            
            
        }
           
        //print out hasil akhir
        out.println("Classified as :");
        out.println(JenisKelas.toString());
        for (i =0; i < JenisKelas.size();i++){     
           out.println(Arrays.toString(ConfusionMatrix[i])+ listJenisKelas.get(i));
        }
    }
    
    public void KNNTenFold(String[][] DataSet, String[][] Acuan, int num, int TotalBaris, int TotalKolom)
    {
        int i, j, k, l, currIndex = 0;
        
        NTotal = TotalBaris;
        jumlahMark = NTotal;
        int[] Peluang;
        int[][] ConfusionMatrix;
        
        Set<String> JenisKelas = new HashSet<String>(Arrays.asList());
        //Total Kolom melambangkan batas akhir dari kolom yang berisi "temp"
        int KolomPembanding = TotalKolom - 1; // Dalam kasus contoh nilainnya = 5 yang menyimpan jarak euclid dari data yang sedang ditest
        int IndexKelas = TotalKolom - 1; //Dalam kasus contoh nilainnya = 4 berisi play = "yes" atau "no"
        
        //Masukkan jenis kelas ke dalam sebuah set
        for (i = 0; i < TotalBaris;i++){
            JenisKelas.add(DataSet[i][IndexKelas]);
        }
        List<String> listJenisKelas = new ArrayList<String>(JenisKelas);
        ConfusionMatrix = new int[JenisKelas.size()][JenisKelas.size()];
        Peluang = new int[JenisKelas.size()];
        
        int mark = 0;
        //untuk 9 iterasi pertama        
        for (i=0; i<9; i++)
        {            
            endMark = (int) Math.ceil(jumlahMark/10.0);
            for (j=0; j<endMark; j++)
            {
                for (k=0; k<NTotal; k++)
                {
                    for (l=0; l<TotalKolom; l++)
                    {
                        Acuan[k][l] = DataSet[k][l];
                    }
                }
                for (k=0; k < NTotal; k++)
                {
                    if ((k < currIndex) || (k >= currIndex+endMark))
                    {
                        temp = 0;
                        for (l=0; l<TotalKolom; l++)
                        {
                            if (!(DataSet[k][l].equals(Acuan[currIndex][l])))
                                temp++;
                        }
                        Acuan[k][TotalKolom] = Integer.toString(temp);
                    }
                    else
                        Acuan[k][TotalKolom] = "99";
                }
                Arrays.sort(Acuan, new Comparator<String[]>() 
                {
                    @Override
                    public int compare(String[] s1, String[] s2) 
                    {
                        String t1 = s1[TotalKolom];
                        String t2 = s2[TotalKolom];
                        return t1.compareTo(t2);
                    }
                });
                
                //hitung banyak yes dan no
                for (i=0; i < num; i++)
                {
                    for (int iKelas = 0; iKelas < JenisKelas.size(); iKelas++) {
                        if (Acuan[i][IndexKelas].equals(listJenisKelas.get(iKelas))){
                            Peluang[iKelas] = Peluang[iKelas] + 1;
                        }
                    }
                }
                //sorting asceding. Element terakhir pasti paling besar
                int[] IndexPeluang = new int[Peluang.length];
                System.arraycopy(Peluang, 0, IndexPeluang, 0, Peluang.length);
                Arrays.sort(Peluang);
                out.println("Peluang:" + Arrays.toString(Peluang));
                out.println("Idx Peluang:" +Arrays.toString(IndexPeluang));

                //Search index dari IndexPeluang yang merupakan tebakan klasifikasi kelas sementara
                int cariPeluangTerbesar=0;
                //Peluang[Peluang.length-1] pasti merupakan kelas paling akhir dengan count terbesar;
                while (Peluang[Peluang.length-1] != IndexPeluang[JenisKelas.size() - 1 - cariPeluangTerbesar]){
                    cariPeluangTerbesar++;
                }
                //cari peluang dari elemen terkanan
                cariPeluangTerbesar = JenisKelas.size() - 1 - cariPeluangTerbesar;
                Peluang = new int[JenisKelas.size()];
                //cari indeks dari kelas seharusnya dalam dataset
                int RealClass = 0;
                out.println(DataSet[currIndex][0] + ","+DataSet[currIndex][1] +","+ DataSet[currIndex][2]+","+ DataSet[currIndex][3]);
                while (!(DataSet[currIndex][IndexKelas].equals(listJenisKelas.get(RealClass)))){
                   RealClass++;
                }
                out.println("Kelas seharusnya = "+RealClass+DataSet[k][IndexKelas]);
                out.println("Idx peluang terbesar = "+cariPeluangTerbesar);
                if (DataSet[currIndex][IndexKelas].equals(DataSet[currIndex][cariPeluangTerbesar])){
                  //Correctly classifying true positif or true negative is in diagonal
                  ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar] = ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar]+1;
                } else {
                  ConfusionMatrix[RealClass][cariPeluangTerbesar] = ConfusionMatrix[RealClass][cariPeluangTerbesar]+1;
                }
            
                currIndex++;
            }
            jumlahMark-=endMark;
        }
        //untuk iterasi terakhir
        for (i=currIndex;i<NTotal;i++)
        {
            
            for (k=0; k<NTotal; k++)
            {
                for (l=0; l<TotalKolom; l++)
                {
                    Acuan[k][l]=DataSet[k][l];
                }
            }
            
            for (k=0; k<NTotal; k++)
            {
                if ((k < currIndex) || (k >= currIndex+endMark))
                {
                    temp = 0;
                    for (l=0; l<TotalKolom; l++)
                    {
                        if (!(DataSet[k][l].equals(Acuan[currIndex][l])))
                            temp++;
                    }
                    Acuan[k][TotalKolom] = Integer.toString(temp);
                }
                else
                    Acuan[k][TotalKolom] = "99";
            }
            
            Arrays.sort(Acuan, new Comparator<String[]>() 
            {
                @Override
                public int compare(String[] s1, String[] s2) 
                {
                    String t1 = s1[TotalKolom];
                    String t2 = s2[TotalKolom];
                    return t1.compareTo(t2);
                }
            });

            //hitung banyak yes dan no
            for (i=0; i < num; i++)
            {
                for (int iKelas = 0; iKelas < JenisKelas.size(); iKelas++) {
                    if (Acuan[i][IndexKelas].equals(listJenisKelas.get(iKelas))){
                        Peluang[iKelas] = Peluang[iKelas] + 1;
                    }
                }
            }
            //sorting asceding. Element terakhir pasti paling besar
            int[] IndexPeluang = new int[Peluang.length];
            System.arraycopy(Peluang, 0, IndexPeluang, 0, Peluang.length);
            Arrays.sort(Peluang);
            out.println("Peluang:" + Arrays.toString(Peluang));
            out.println("Idx Peluang:" +Arrays.toString(IndexPeluang));

            //Search index dari IndexPeluang yang merupakan tebakan klasifikasi kelas sementara
            int cariPeluangTerbesar=0;
            //Peluang[Peluang.length-1] pasti merupakan kelas paling akhir dengan count terbesar;
            while (Peluang[Peluang.length-1] != IndexPeluang[JenisKelas.size() - 1 - cariPeluangTerbesar]){
                cariPeluangTerbesar++;
            }
            //cari peluang dari elemen terkanan
            cariPeluangTerbesar = JenisKelas.size() - 1 - cariPeluangTerbesar;
            Peluang = new int[JenisKelas.size()];
            //cari indeks dari kelas seharusnya dalam dataset
           int RealClass = 0;
           while (!(DataSet[currIndex][IndexKelas].equals(listJenisKelas.get(RealClass)))){
               RealClass++;
            }
            out.println("Kelas seharusnya = "+RealClass+DataSet[currIndex][IndexKelas]);
            out.println("Idx peluang terbesar = "+cariPeluangTerbesar);
            if (DataSet[currIndex][IndexKelas].equals(DataSet[currIndex][cariPeluangTerbesar])){
              //Correctly classifying true positif or true negative is in diagonal
              ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar] = ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar]+1;
            } else {
              ConfusionMatrix[RealClass][cariPeluangTerbesar] = ConfusionMatrix[RealClass][cariPeluangTerbesar]+1;
            }

            currIndex++;
        }
        //print out hasil akhir
        out.println("Classified as :");
        out.println(JenisKelas.toString());
        for (i =0; i < JenisKelas.size();i++){     
           out.println(Arrays.toString(ConfusionMatrix[i])+ listJenisKelas.get(i));
        }

    }
}

