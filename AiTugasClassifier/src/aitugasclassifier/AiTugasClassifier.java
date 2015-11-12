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
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 *
 * @author Satria
 */
public class AiTugasClassifier {
    private static String[][] data; 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        
        //Scanner scanData;
        data = new String[2000][10];
        JPanel openPanel = new JPanel();
        openPanel.setLayout(null);
        final JButton openBtn = new JButton("Open");
        JFileChooser fopen = new JFileChooser();
        
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
                    Logger.getLogger(AiTugasClassifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Open command cancelled by user.\n");
            }
        });
        
        openBtn.setBounds(0, 0, 100, 50);
        openPanel.add(openBtn);
        
        JFrame frame = new JFrame("Opener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.add(openPanel);
        openPanel.setLocation(0, 0);
        frame.setSize(300,300);
        frame.setResizable(true);
        frame.setVisible(true);
    }
    
}
