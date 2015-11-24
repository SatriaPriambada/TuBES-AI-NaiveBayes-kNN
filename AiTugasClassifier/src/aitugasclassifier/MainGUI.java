/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * All code and works here are created by Satria Priambada and team
 * You are free to use and distribute the code
 * We do not take responsibilities for any damage caused by using this code
 */

package aitugasclassifier;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import kNN.ClassKNN;
import naivebayes.NaiveBayes;
import naivebayes.TenFoldNaiveBayes;
 
public class MainGUI extends JFrame {
    /**
     * The text area which is used for displaying logging information.
     */
    private JTextArea textArea;
    private JLabel LabelEntry;
    private JTextField entry;
    private PrintStream standardOut;
     
    public MainGUI() {
        super("Log for Results");
         
        textArea = new JTextArea(50, 10);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
         
        // keeps reference of standard output stream
        standardOut = System.out;
         
        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);
 
        // creates the GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
         
         
        constraints.gridx = 1;
         
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
         
        add(new JScrollPane(textArea), constraints);
         
        
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 320);
        setLocationRelativeTo(null);    // centers on screen
    }
     
    /**
     * Prints log statements for testing in a thread
     */
    private void printLog() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        thread.start();
    }
     
    /**
     * Runs the program
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().setVisible(true);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                //Scanner scanData;
                String[][] data = new String[2000][10];
                String[][] dataTest = new String[2000][10];
                JPanel openPanel = new JPanel();
                openPanel.setLayout(null);
                final JButton openBtn = new JButton("Open");
                final JButton openTestBtn = new JButton("Open Test");
                final JButton startBtn = new JButton("Start");
                final JLabel LabelEntry = new JLabel("number of neighbour");
                final JTextField entry = new JTextField();
                JFileChooser fopen = new JFileChooser();
                //initialize all element with empty string
                for (int i=0; i <2000; i++){
                    for (int j=0; j <10; j++){
                        data[i][j] = "";
                        dataTest[i][j] = "";
                    }
                }
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Dataset", "txt");
                fopen.setFileFilter(filter);


                openBtn.addActionListener((ActionEvent e) -> {
                    int returnVal = fopen.showOpenDialog(openBtn);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        StringBuffer tempbuffer = new StringBuffer();
                        String temp;
                        int i, j, k, count, countattr;
                        Scanner scanData;
                        File file = fopen.getSelectedFile();
                        //This is where a real application would open the file.
                        System.out.println("Opening: " + file.getName() + ".\n");
                        try {
                            scanData = new Scanner(new FileInputStream(file.getAbsoluteFile()));
                            i = 0;
                            count = 0;
                            countattr = 0;
                            while (scanData.hasNext()) {
                                j = 0;
                                countattr = 0;
                                k = 0;
                                temp = scanData.nextLine();
                                while (k < temp.length()) {
                                    tempbuffer.delete(0, tempbuffer.length());
                                    //data[i][j] = "";
                                    while (k < temp.length() && temp.charAt(k) != ',') {
                                        if (temp.charAt(k) != ' ') {
                                            tempbuffer.append(temp.charAt(k));
                                            //System.out.println(tempbuffer);
                                        }
                                        k++;
                                    }
                                    //System.out.println("berhasil?");
                                    data[i][j] = tempbuffer.toString();
                                    j++;
                                    countattr++;
                                    k++;
                                }
                                i++;
                                count++;
                            }
                            for (i=0;i<count;i++) {
                                for (j=0;j<countattr;j++) {
                                    System.out.print(data[i][j] + " ");
                                }
                                System.out.println();
                            }
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        System.out.println("Open command cancelled by user.\n");
                    }
                });

                openTestBtn.addActionListener((ActionEvent e) -> {
                int returnVal = fopen.showOpenDialog(openBtn);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    StringBuffer tempbuffer = new StringBuffer();
                    String temp;
                    int i, j, k, count, countattr;
                    Scanner scanData;
                    File file = fopen.getSelectedFile();
                    //This is where a real application would open the file.
                    System.out.println("Opening test file : " + file.getName() + ".\n");
                    try {
                        scanData = new Scanner(new FileInputStream(file.getAbsoluteFile()));
                        i = 0;
                        count = 0;
                        countattr = 0;
                        while (scanData.hasNext()) {
                            j = 0;
                            countattr = 0;
                            k = 0;
                            temp = scanData.nextLine();
                            while (k < temp.length()) {
                                tempbuffer.delete(0, tempbuffer.length());
                                //data[i][j] = "";
                                while (k < temp.length() && temp.charAt(k) != ',') {
                                    if (temp.charAt(k) != ' ') {
                                        tempbuffer.append(temp.charAt(k));
                                        //System.out.println(tempbuffer);
                                    }
                                    k++;
                                }
                                //System.out.println("berhasil?");
                                dataTest[i][j] = tempbuffer.toString();
                                j++;
                                countattr++;
                                k++;
                            }
                            dataTest[i][j] = "temp";
                            i++;
                            count++;
                        }
                        for (i=0;i<count;i++) {
                            for (j=0;j<countattr;j++) {
                                System.out.print(dataTest[i][j] + " ");
                            }
                            System.out.println();
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Open test command cancelled by user.\n");
                }
            });

                //show open button
                openBtn.setBounds(0, 0, 100, 50);
                openPanel.add(openBtn);
                //show start button
                startBtn.setBounds(200, 10, 75, 40);
                openPanel.add(startBtn);
                //show open test button
                openTestBtn.setBounds(0, 50, 100, 40);
                openPanel.add(openTestBtn);
                openTestBtn.setVisible(false);
                //Set field label
                LabelEntry.setBounds(120, 50, 100, 25);
                openPanel.add(LabelEntry);
                LabelEntry.setVisible(false);
                //Set text field input
                entry.setBounds(230, 50, 25, 25);
                openPanel.add(entry);
                entry.setVisible(false);
                //Create choice box
                Choice AlgoType = new Choice();
                //Adding option in choice box
                AlgoType.add("kNN test set");
                AlgoType.add("kNN 10-cross-fold");
                AlgoType.add("NaiveBayes test set");
                AlgoType.add("NaiveBayes 10-cross-fold");
                AlgoType.setBounds(100,25,100,50);
                openPanel.add(AlgoType);
            
                //Action on click of start button
                startBtn.addActionListener((ActionEvent e) -> {
                    //Counter Data from input
                    int cData = 0;
                    while (!data[cData][0].equals("")){
                        cData++;
                    }
                    //Counter kolom from input
                    int cKolomData = 0;
                    while (!data[0][cKolomData].equals("")){
                        cKolomData++;
                    }
                    //Counter Data from Test
                    int cTest = 0;
                    while (!dataTest[cTest][0].equals("")){
                        cTest++;
                    }
                    //Counter kolom from Test
                    int cKolomTest = 0;
                    while (!dataTest[0][cKolomTest].equals("")){
                        cKolomTest++;
                    }
                   if (AlgoType.getSelectedIndex()==0){
                       System.out.println("kNN test");
                       //show open test button
                       openTestBtn.setVisible(true);
                       LabelEntry.setVisible(true);
                       entry.setVisible(true);
                       //check jika elemen pertama data dan dataTest tidak null maka mulai algoritma
                       if (!(data[0][0].equals(""))  && !(dataTest[0][0].equals("")) ) {
                           System.out.println("Starting Algorithm:");
                           ClassKNN ClassifierKNN = new ClassKNN();
                           System.out.println(cTest+" "+ cKolomTest);
                           int num = Integer.parseInt(entry.getText());
                           ClassifierKNN.KNNFullTraining(data,dataTest,num,cTest,cKolomTest);
                       } else {
                           System.out.println("Please input data first");
                       }
                       
                   }else if (AlgoType.getSelectedIndex()==1){
                       System.out.println("kNN 10-cross-fold");
                       //hide test button
                       openTestBtn.setVisible(false);
                       LabelEntry.setVisible(true);
                       entry.setVisible(true);
                       //check jika elemen pertama data tidak null maka mulai algoritma
                       if (!(data[0][0].equals(""))) {
                           System.out.println("Starting Algorithm:");
                           ClassKNN ClassifierKNN = new ClassKNN();
                           System.out.println(cData+" "+ cKolomData);
                           int num = Integer.parseInt(entry.getText());
                           String[][] Acuan = new String[cData][cKolomData+1];
                           ClassifierKNN.KNNTenFold(data,Acuan,num,cData,cKolomData);
                           
                       } else {
                           System.out.println("Please input data first");
                           
                       }
                   }else if (AlgoType.getSelectedIndex()==2){
                       System.out.println("NaiveBayes test");
                       LabelEntry.setVisible(false);
                       entry.setVisible(false);
                       //show open test button
                       openTestBtn.setVisible(true);
                       //check jika elemen pertama data dan dataTest tidak null maka mulai algoritma
                       if (!(data[0][0].equals(""))  && !(dataTest[0][0].equals("")) ) {
                           System.out.println("Starting Algorithm:");
                           NaiveBayes ClassiferNB = new NaiveBayes();
                           System.out.println(cTest);
                           try {
                               ClassiferNB.TestNaiveBayes(data,dataTest);
                           } catch (IOException ex) {
                               Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           
                       } else {
                           System.out.println("Please input data first");
                           
                       }
                   }else if (AlgoType.getSelectedIndex()==3){
                       System.out.println("NaiveBayes 10-cross-fold");
                       //hide test button
                       openTestBtn.setVisible(false);
                       LabelEntry.setVisible(false);
                       entry.setVisible(false);
                       //check jika elemen pertama data tidak null maka mulai algoritma
                       if (!(data[0][0].equals(""))) {
                           System.out.println("Starting Algorithm:");
                           TenFoldNaiveBayes ClassiferTFNB;
                           ClassiferTFNB = new TenFoldNaiveBayes ();
                           System.out.println(cData);
                           try {
                               ClassiferTFNB.TenCrossFoldNaiveBayes(data);
                           } catch (IOException ex) {
                               Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                           }
                       }else {
                           System.out.println("Please input data first");
                       }

                   }
                });

                //Start frame
                JFrame frame = new JFrame("Main Controller");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.pack();
                frame.add(openPanel);
                openPanel.setLocation(0, 0);

                frame.setSize(300,200);
                frame.setResizable(true);
                frame.setVisible(true);
            }
        });
        // TODO code application logic here
        
    }
}
