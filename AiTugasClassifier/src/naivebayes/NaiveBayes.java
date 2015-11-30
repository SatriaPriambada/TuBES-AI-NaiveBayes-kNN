/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */
package naivebayes;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Satria
 */
public class NaiveBayes {

    public NaiveBayes(){};
       
    /**
     * @param args the command line arguments
     */
    public int SearchMatrix(ArrayList<String> a, String s){
        for(int i=0;i<a.size();i++){
          if(a.get(i).equals(s)){
           return i;
          }
        }
        return -999;
    }
    
    public void TestNaiveBayes(String[][] data, String[][] dataTest) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> TestMatrix = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<ArrayList<String>>> mKelas = new ArrayList<ArrayList<ArrayList<String>>>();

        ArrayList<String> row1 = new ArrayList<String>();
        Set<String> h = new HashSet<String>(Arrays.asList());
        Set<String> JenisKelas = new HashSet<String>(Arrays.asList());
        ArrayList<String> row2 = new ArrayList<String>();
        ArrayList<ArrayList<String>> CounterEachClass = new ArrayList<ArrayList<String>>();

        int counter;
        
        int[] listTotal;
        double[] Peluang;
        int[][] ConfusionMatrix;
       
        //Acquisition of inputs
        int cData = 0;
        int cattrData =0;
        int IndexKelas = 0;
        while (!(data[cData][0].equals(""))){
            row1 = new ArrayList<String>();
            cattrData=0;
            while (!(data[cData][cattrData].equals(""))){
                row1.add(data[cData][cattrData]);
                cattrData++;
            }
            IndexKelas = cattrData - 1; // kurangi satu karena array dimulai dari index 0
            matrix.add(row1);
            cData++;
        }
        listTotal = new int[matrix.size()];
        System.out.println(IndexKelas); 
        
        //Acquisition of inputs for TestMatrix
        cData = 0;
        cattrData =0;
        while (!(dataTest[cData][0].equals(""))){
            row1 = new ArrayList<String>();
            cattrData=0;
            while (!(dataTest[cData][cattrData].equals(""))){
                row1.add(dataTest[cData][cattrData]);
                cattrData++;
            }
            TestMatrix.add(row1);
            cData++;
        }
        
        //Masukkan jenis kelas ke dalam sebuah set
        for (int i = 0; i < matrix.size();i++){
            JenisKelas.add(matrix.get(i).get(IndexKelas));
        }
        List<String> listJenisKelas = new ArrayList<String>(JenisKelas);
        ConfusionMatrix = new int[JenisKelas.size()][JenisKelas.size()];
        Peluang = new double[JenisKelas.size()];
        out.println(JenisKelas.toString());
        out.println(Arrays.toString(Peluang));
        out.println(Arrays.toString(ConfusionMatrix));
        
        //Hash Table
         for(int k=0; k<matrix.size();k++){
                for(int l=0;l<row1.size()-1;l++){
                    row2.add(matrix.get(k).get(l));
                for(int m=0; m<row2.size();m++){
                    h.add(row2.get(m));
                }
            }
        }
        row2 = new ArrayList<String>(h);
        ArrayList<String> TempCounter = new ArrayList<String>();
        out.println("Row hash :"+row2);
    
        //Classified as class Yes or No sesuai jenis kelas
            ArrayList<ArrayList<String>> TempArray = new ArrayList<ArrayList<String>>();
            for(int iterKelas = 0; iterKelas < JenisKelas.size(); iterKelas++){
                for (int j = 0; j < matrix.size(); j++){
                    //jika kelas dari matrix J sama dengan kelas dari list e.g. yes dan no maka buat 2 buah matrix yaitu matrixYES dan matrixNO
                    
                    if (matrix.get(j).get(IndexKelas).contentEquals(listJenisKelas.get(iterKelas))){
                        TempArray.add(matrix.get(j));
                    }
                    
                }
                mKelas.add((ArrayList<ArrayList<String>>)TempArray.clone());
                //clear temp aray untuk kelas baru
                TempArray.clear();
                out.println("Matrix kelas " + listJenisKelas.get(iterKelas) + " adalah : " + mKelas.get(iterKelas));
                
                //Counter Jumlah elemen dalam kelas tersebut
                for(int l=0; l<row2.size();l++){
                    //out.println("cek elemen :"+ row2.get(l));
                    counter = 0;
                    //cek semua baris dan kolom setiap jenis kelas hingga Index Kelas (tidak perlu dicek)
                    for(int n=0; n < IndexKelas; n++){
                       for(int m=0; m < mKelas.get(iterKelas).size(); m++){
                           //out.println(mKelas.get(iterKelas).get(m).get(n));
                           if(mKelas.get(iterKelas).get(m).get(n).equals(row2.get(l))){
                               counter++;
                           }
                       }
                   }
                   //out.println("jumlah elemen:" + counter);
                   TempCounter.add(String.valueOf(counter));
                }
                CounterEachClass.add((ArrayList<String>) TempCounter.clone());
                TempCounter.clear();
                listTotal[iterKelas] = mKelas.get(iterKelas).size();
                System.out.println("Counter "+listJenisKelas.get(iterKelas)+ " : "+ CounterEachClass.get(iterKelas));
                System.out.println("Jumlah "+listJenisKelas.get(iterKelas)+ " : "+ listTotal[iterKelas]);
            }
             
             //Write Naive Bayes to TEXT FILE
            out.println(JenisKelas.size());
            try
            {
                //Print Attribute Name
                PrintWriter pr = new PrintWriter("nb.txt");
                for (int i=0; i<row2.size()-1; i++)
                {
                    pr.print(row2.get(i)+",");
                }
                pr.print(row2.get(row2.size()-1));
                pr.println();
                //Print Semua elemen kelas
                for (int iterator = 0; iterator < JenisKelas.size(); iterator++){
                    //print all the table
                    for (int i=0; i<mKelas.get(iterator).size()-1; i++)
                    {
                        pr.print(mKelas.get(iterator).get(i)+",");
                    }
                    //print elemen terakhir
                    pr.print(mKelas.get(iterator).get(mKelas.get(iterator).size()-1));
                    pr.println();
                    //print the number of element
                    pr.println(CounterEachClass.get(iterator));
                    //Print Jumlah total kelas tersebut
                    pr.println(listTotal[iterator]);
                    
                }
                pr.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("No such file exists.");
            }
            mKelas.clear();
             //do checking for the TestMatrix from model\
            
            //for all test matrix element
             for(int i=0; i<TestMatrix.size(); i++){
                 //instantiaton value of class
                 
                out.println(TestMatrix.get(i));
                for (int jClass = 0; jClass < JenisKelas.size(); jClass++){
                    Peluang[jClass] = (double)listTotal[jClass]/ ((double)matrix.size());
                }
                //Hingga index kelas
                 for(int j=0; j<IndexKelas; j++){
                  String compareString = TestMatrix.get(i).get(j);
                     int hasilsearch = SearchMatrix(row2,compareString);
                     for (int jClass = 0; jClass < JenisKelas.size(); jClass++){
                         //case of NaN
                         if (listTotal[jClass] == 0){
                             listTotal[jClass] = 99999;
                         }
                         //out.println("pel " + listJenisKelas.get(jClass)+ " sebelum : "+Peluang[jClass] +" * "+CounterEachClass.get(jClass).get(hasilsearch) +" / "+listTotal[jClass]);
                         Peluang[jClass] = Peluang[jClass] * Double.valueOf(CounterEachClass.get(jClass).get(hasilsearch)) / listTotal[jClass];
                         //out.println("pel :"+Peluang[jClass]);
                     }
                 }
                 //sorting asceding. Element terakhir pasti paling besar
                 double[] IndexPeluang = new double[Peluang.length];
                 System.arraycopy(Peluang, 0, IndexPeluang, 0, Peluang.length);
                 Arrays.sort(Peluang);
                 out.println("Peluang:" + Arrays.toString(Peluang));
                 out.println("Idx Peluang:" +Arrays.toString(IndexPeluang));
                 //Search index dari IndexPeluang yang merupakan tebakan klasifikasi kelas
                 int cariPeluangTerbesar=0;
                 while (Peluang[Peluang.length-1] != IndexPeluang[cariPeluangTerbesar]){
                     cariPeluangTerbesar++;
                 }
                 //cari kelas seharusnya
                int RealClass = 0;
                while (!(TestMatrix.get(i).get(IndexKelas).equals(listJenisKelas.get(RealClass)))){
                    RealClass++;
                }
                out.println("Kelas seharusnya = "+RealClass+TestMatrix.get(i).get(IndexKelas));
                out.println("Idx peluang terbesar = "+cariPeluangTerbesar);
                if (TestMatrix.get(i).get(IndexKelas).equals(TestMatrix.get(i).get(cariPeluangTerbesar))){
                    //Correctly classifying true positif or true negative is in diagonal
                    ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar] = ConfusionMatrix[cariPeluangTerbesar][cariPeluangTerbesar]+1;
                } else {

                    ConfusionMatrix[RealClass][cariPeluangTerbesar] = ConfusionMatrix[RealClass][cariPeluangTerbesar]+1;
                }
                 
             }
        
             out.println("Classified as :");
             out.println(JenisKelas.toString());
             for (int i =0; i < JenisKelas.size();i++){     
                out.println(Arrays.toString(ConfusionMatrix[i])+ listJenisKelas.get(i));
             }
    }
    
}
