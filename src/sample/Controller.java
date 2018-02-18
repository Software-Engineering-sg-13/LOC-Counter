package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button browseBtn;
    @FXML
    private Button countLinesBtn;
    @FXML
    private TextField addressField;
    @FXML
    private TextField file_name;
    @FXML
    private TextField no_of_lines;
    @FXML
    private TextField blank_lines;
    @FXML
    private TextField comments;
    @FXML
    private TextField loc;

    private String filepath;
    private String filename;
    private boolean cmt_status = false;
    private boolean quote_status = false;


    public void browseBtnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter javaFilter = new FileChooser.ExtensionFilter("Java Programmes", "*.java");
        FileChooser.ExtensionFilter cFilter = new FileChooser.ExtensionFilter("C Programmes", "*.c");
        FileChooser.ExtensionFilter cppFilter = new FileChooser.ExtensionFilter("Cpp Programmes", "*.cpp");
        FileChooser.ExtensionFilter phpFilter = new FileChooser.ExtensionFilter("PHP Programmes", "*.php");
        FileChooser.ExtensionFilter objectiveCFilter = new FileChooser.ExtensionFilter("Objective C Programmes", "*.m");
        FileChooser.ExtensionFilter cHashFilter = new FileChooser.ExtensionFilter("C# Programmes", "*.cs");
        fileChooser.getExtensionFilters().addAll(cFilter, javaFilter, cppFilter, phpFilter, objectiveCFilter, cHashFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
            addressField.setText(selectedFile.getAbsolutePath());
            filepath = selectedFile.getAbsolutePath();
            filename = selectedFile.getName();
        } else {
            System.out.println("file is not valid");
        }
    }
    public void countLinesBtnAction(ActionEvent event) {

        File currFile = new File(filepath);

        if(currFile.exists()) try {
            FileReader fr = new FileReader(currFile);

            int totalLinesCount = 0;
            int lineNumberCount = 0;
            int blankLinesCount = 0;
            int commentLinesCount = 0;
            boolean temp = false;


            BufferedReader br = new BufferedReader(fr);
            String line;

            line = br.readLine();
            while(line!=null) {

                System.out.println(line);
                totalLinesCount++;
                if(cmt_status==false) {
                    if(is_empty(line)) {
                        System.out.println("************Blank Space************ : " + totalLinesCount);
                        blankLinesCount++;
                    }
                    else if(is_comment(line)) {
                        System.out.println("************Single comment************ : " + totalLinesCount);
                        commentLinesCount++;
                    }
                    else {
                        if(is_mulcmt(line)) {
                            System.out.println(line);
                            System.out.println("length: "+ line.length());
                            System.out.println("************Multiple comment************ : " + totalLinesCount);
                            commentLinesCount++;
                        }
                        else lineNumberCount++;
                        update_cmt_status(line);
                    }
                }
                else {
                    if(is_empty(line)) {
                        System.out.println("************Multiple  _______ comment************ : " + totalLinesCount);
                        commentLinesCount++;
                    }
                    else {
                        int i;
                        for (i = 0; i < line.length() - 1; i++) {
                            if (line.charAt(i) == '*' && line.charAt(i + 1) == '/') break;
                        }
                        temp = false;
                        for (int j = i+2; j < line.length() - 1; j++) {
                            if (line.charAt(j) != ' ' && line.charAt(j) != '\t') {
                                cmt_status = false;
                                lineNumberCount++;
                                temp = true;
                            }
                        }
                        if(temp==false) {
                            System.out.println("******************In Comment*************: " + totalLinesCount);
                            commentLinesCount++;
                        }

                    }
                    update_cmt_status(line);
                }
                line = br.readLine();
            }


            file_name.setText(filename);
            no_of_lines.setText(totalLinesCount + "");
            blank_lines.setText(blankLinesCount+"");
            comments.setText(commentLinesCount+"");
            loc.setText(totalLinesCount-blankLinesCount-commentLinesCount+"");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void update_cmt_status(String line) {
        for(int i=0;i<line.length()-1;i++) {
            if(line.charAt(i)==' ' || line.charAt(i)=='\t') continue;
            if(quote_status==false && cmt_status==false) {
                if (line.charAt(i) == '/' && line.charAt(i + 1) == '*') {
                    cmt_status = true;
                }
            }
            else {
                if (line.charAt(i) == '*' && line.charAt(i + 1) == '/') {
                    cmt_status = false;
                }
            }
        }
    }

    private boolean is_mulcmt(String line) {
        if(cmt_status==false) {
            for(int i=0;i<line.length()-1;i++) {
                if(line.charAt(i)==' ' || line.charAt(i)=='\t') continue;
                if(!(line.charAt(i)=='/' && line.charAt(i+1)=='*')) {
                    update_cmt_status(line);
                    return false;
                }
                update_cmt_status(line);
                if((line.charAt(i)=='/' && line.charAt(i+1)=='*') && cmt_status) return true;
            }
            if(line.charAt(0)==' ') return true;
            else return false;
        }
        else {
            for(int i=line.length()-1;i>=0;i--) {
                if(line.charAt(i)==' ') continue;
                if(line.charAt(i)=='/' && line.charAt(i-1)=='*') {
                    System.out.println("*******************************s");
                    return true;
                }
                return true;
            }
        }
        return true;
    }

    private boolean is_empty(String line) {
        int i;
        for(i=0;i<line.length();i++) {
            if(!(line.charAt(i)==' ' || line.charAt(i)=='\t')) break;
        }
        if(i==line.length()) return true;
        else return false;
    }

    private boolean is_comment(String line) {
        System.out.println("length is: "+ line.length());
        for(int i=0;i<line.length()-1;i++) {
            if(line.charAt(i)==' ' || line.charAt(i)=='\t') continue;
            if(line.charAt(i)=='/' && line.charAt(i+1)=='/') return true;
            if(line.charAt(i)=='/' && line.charAt(i+1)=='*') {
                update_cmt_status(line);
                if(cmt_status==true) return true;
            } else {
                update_cmt_status(line);
                return false;
            }
        }
            return false;
    }
}
