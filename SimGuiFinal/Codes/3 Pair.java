package GUI;

import Algorithms.Files;
import Algorithms.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class mainController {
    @FXML
    private GridPane similarityMatrix;
    @FXML
    private ScrollPane scrollPane;
    private static DecimalFormat twoPoint = new DecimalFormat("#,##0.00");
    ArrayList<String> files = new ArrayList<>();
    ArrayList<String> fileName = new ArrayList<>();

    @FXML
    private void checkSimilarity(ActionEvent event) throws FileNotFoundException {
        Calculator calculator = new Calculator();
        //Get list of file names directory
        Files dir = new Files();
        fileName = dir.getFileNames();
        files = dir.getFiles("Directory");
        double[][] matrix = calculator.getScore();

        /*Set scene details */
        similarityMatrix.setVisible(true);
        scrollPane.setVisible(true);

        //Iterate through all files to get and to print the names
        for (int i = 0; i < files.size(); i++) {
            //Create new column and rows
            similarityMatrix.add(new Label(fileName.get(i)), 0, i + 1);
            similarityMatrix.add(new Label(fileName.get(i)), i + 1, 0);

            //Column formatting
            similarityMatrix.getColumnConstraints().addAll(new ColumnConstraints(45));
            //NOTE: row constraints are required to properly format the the printing of the grid
            similarityMatrix.getRowConstraints().addAll(new RowConstraints(18));

            //Iterate through the files
            for (int j = 0; j < files.size(); j++) {
                //Print out the corresponding similarity scores
                Label label = new Label("   "+twoPoint.format(matrix[i][j])+"    ");
                if(matrix[i][j]==1.0){
                    label.setStyle("-fx-background-color:RED");
                    similarityMatrix.add(label, i + 1, j + 1);
                }
                else if(matrix[i][j]<1.0 && matrix[i][j]>=0.75){
                    label.setStyle("-fx-background-color:ORANGE");
                    similarityMatrix.add(label, i + 1, j + 1);
                }
                else if(matrix[i][j]<0.75 && matrix[i][j]>=0.50){
                    label.setStyle("-fx-background-color:YELLOW");
                    similarityMatrix.add(label, i + 1, j + 1);
                }
                else if(matrix[i][j]<0.50 && matrix[i][j]>=0.25){
                    label.setStyle("-fx-background-color:#ADFF2F");
                    similarityMatrix.add(label, i + 1, j + 1);
                }
                else if(matrix[i][j]<0.25 && matrix[i][j]>=0){
                    label.setStyle("-fx-background-color:GREEN");
                    similarityMatrix.add(label, i + 1, j + 1);
                }
            }
        }


    }
}
