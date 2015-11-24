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
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Satria
 */
public class TenFoldNaiveBayes {

    public TenFoldNaiveBayes(){};
       
    /**
     * @param args the command line arguments
     */
    public static int SearchMatrix(ArrayList<String> a, String s){
        for(int i=0;i<a.size();i++){
          if(a.get(i).equals(s)){
           return i;
          }
        }
        return -999;
    }
    public void TenCrossFoldNaiveBayes(String[][] data) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<String>> AkuisisiMatrix = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> TestMatrix = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> matrixy = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> matrixn = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> NaiveBayes = new ArrayList<ArrayList<String>>();
        ArrayList<String> row1 = new ArrayList<String>();
        Set<String> h = new HashSet<String>(Arrays.asList());
        ArrayList<String> row2 = new ArrayList<String>();
        ArrayList<String> row3 = new ArrayList<String>();
        ArrayList<String> row4 = new ArrayList<String>();
        int totalyes;
        int totalno;
        int truepos = 0;
        int trueneg = 0;
        int falsepos = 0;
        int falseneg = 0;
        Double Pyes;
        Double Pno;
        System.out.println(AkuisisiMatrix);//print empty matrix
       // ArrayList<String> row1 = new ArrayList<String>();
        
        String strLine;
        String temp;
        String[] str;
        int counter = 0;
        //Read File Line By Line
        int cData = 0;
        int cattrData =0;
        while (data[cData][0] != null){
            row1 = new ArrayList<String>();
            cattrData=0;
            while (data[cData][cattrData] != null){
                row1.add(data[cData][cattrData]);
                cattrData++;
            }
            AkuisisiMatrix.add(row1);
            matrix.add(row1);
            cData++;
        }
          
        //Print matrix
        for(int j=0; j<AkuisisiMatrix.size(); j++){
            out.println(AkuisisiMatrix.get(j));
        }
        int tenPercent = 0;
        int allMatrixElement = AkuisisiMatrix.size();
        int jumlahTotalBaris = AkuisisiMatrix.size();
        
//Do loop 10 k cross fold
        for (int iterLoop =0; iterLoop<10 ; iterLoop++){
            tenPercent = (int) Math.ceil((double) jumlahTotalBaris/10.0);
            //Print rows needed to be checked
            System.out.println(tenPercent);
            //Need -1 because array start not from 1 but from 0
            int StartChecked = jumlahTotalBaris - 1;
            int LastChecked = jumlahTotalBaris - tenPercent;
            if (iterLoop == 9) {
                LastChecked = 0;
            }
            for (int i = 0; i < allMatrixElement; i++){
                
                if ((i <= StartChecked) && (i >= LastChecked)){
                    out.println("SKIPPED THIS ELEMENT:" + AkuisisiMatrix.get(i));
                    TestMatrix.add(AkuisisiMatrix.get(i));
                } else {
                    
                    matrix.add(AkuisisiMatrix.get(i));
                    //out.println(matrix.get(i));
                }
            }
            //Go to next tested
            jumlahTotalBaris -= tenPercent;
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



            //Yes or No
            for(int j=0; j< matrix.size(); j++){
                if(matrix.get(j).get(row1.size()-1).contentEquals("yes")){
                    matrixy.add(matrix.get(j));
                }
                else if(matrix.get(j).get(row1.size()-1).contentEquals("no")){
                    matrixn.add(matrix.get(j));
                }
            }
            System.out.println("MatrixYES = "+matrixy);
            System.out.println("MatrixNO = "+matrixn);
            //Counter Yes
            for(int l=0; l<row2.size();l++){
                counter = 0;
             for(int n=0; n<row1.size(); n++){
                for(int m=0; m<matrixy.size(); m++){
                    if(matrixy.get(m).get(n).equals(row2.get(l))){
                    counter++;
                    }
                }

            }row3.add(String.valueOf(counter));
            }

            //counter No
            counter=0;
            for(int l=0; l<row2.size();l++){
                counter = 0;
             for(int n=0; n<row1.size(); n++){
                for(int m=0; m<matrixn.size(); m++){
                    if(matrixn.get(m).get(n).equals(row2.get(l))){
                    counter++;
                    }
                }

            }row4.add(String.valueOf(counter));
            }
            System.out.println("Row2 = "+row2);
            System.out.println("Row3 yes = "+row3.toString());
            System.out.println("Row4 no = "+row4.toString());
            totalyes = matrixy.size();
            totalno = matrixn.size();
            System.out.println("Jumlah yes = "+totalyes);
            System.out.println("Jumlah no = "+totalno);
            NaiveBayes.add(row2);
            NaiveBayes.add(row3);
            NaiveBayes.add(row4);
            System.out.println("Naive Bayes ="+NaiveBayes.toString());

            //Write Naive Bayes to TEXT FILE
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
                    //Print YES
                    for (int i=0; i<row3.size()-1; i++)
                    {
                        pr.print(row3.get(i)+",");
                    }
                    pr.print(row3.get(row3.size()-1));
                    pr.println();
                    //Print NO
                    for (int i=0; i<row4.size()-1; i++)
                    {
                        pr.print(row4.get(i)+",");
                    }
                    pr.print(row4.get(row4.size()-1));
                    //Print Jumlah YES
                    pr.println();
                    pr.println(totalyes);
                    //Print Jumlah NO
                    pr.println(totalno);
                    pr.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.out.println("No such file exists.");
                }
            /*if(row2.isEmpty()){
                row2.add(matrix.get(0).get(0));
            }
            //for (int j=0;j<row2.size();j++){
                for(int k=1;k<matrix.size();k++){
                    if(row2.get(0).contentEquals(matrix.get(k).get(0))){
                    }
                    else {
                      row2.add(matrix.get(k).get(0));
                      System.out.println(row2.size());
                    }
                }
            //}*/
             System.out.println("MatrixFULL = "+matrix);
             System.out.println("Matrix Baris 1 = "+matrix.get(0));
             Pno = (double)totalno/ ((double)totalyes + (double)totalno);
             Pyes = (double)totalyes/ ((double)totalyes + (double)totalno);
             System.out.println("Pno = "+Pno);
             System.out.println("Pyes = "+Pyes);
             
             //do checking for the TestMatrix for model
             for(int i=0; i<TestMatrix.size(); i++){
                 for(int j=0; j<TestMatrix.get(i).size()-1; j++){
                  String compareString = TestMatrix.get(i).get(j);
                     int hasilsearch = SearchMatrix(row2,compareString);
                     Pno = Pno * Double.valueOf(row4.get(hasilsearch)) / totalno;
                     Pyes = Pyes * Double.valueOf(row3.get(hasilsearch)) / totalyes;

                 }
                 System.out.println("baris ke "+ i);
                 if(Pno >= Pyes){
                 //Kesimpulan Baris ke-i diklasifikasikan sebagai no
                    if(TestMatrix.get(i).get(4).equals("no")){
                        trueneg++;
                    }
                    else{
                        falseneg++;
                    }
                 }
                 else {
                 //Kesimpulan Baris ke-i diklasifikasikan sebagai yes
                    if(TestMatrix.get(i).get(4).equals("yes")){
                        truepos++;
                    }
                    else{
                        falsepos++;
                    }
                 }

                 Pno = (double)totalno/ ((double)totalyes + (double)totalno);
                 Pyes = (double)totalyes/ ((double)totalyes + (double)totalno);
             }
            System.out.println("TruePositif = "+truepos);
            System.out.println("TrueNegatif = "+trueneg);
            System.out.println("FalsePositif = "+falsepos);
            System.out.println("FalseNegatif = "+falseneg);
            Double akurasi = (double)(truepos+trueneg) / (truepos+trueneg+falsepos+falseneg);
            Double presisi = (double) (truepos)/(truepos+trueneg); 
            Double recall = (double) (truepos) / (truepos+falsepos);
            System.out.println("Akurasi = "+akurasi);
            System.out.println("Presisi = "+presisi);
            System.out.println("Recall = "+recall);


            TestMatrix.clear();
            row3.clear();
            row4.clear();
            matrix.clear();
            matrixy.clear();
            matrixn.clear();
            totalyes=0;
            totalno=0;
            counter=0;
            // TODO code application logic here
        
        }

    }
    
}
