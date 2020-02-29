import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

public class controller {
    @FXML
    private Pane mainPane;
    @FXML
    private GridPane correlationMatrix;
    private String path;

    public void setPath(String path){
        this.path = path;
    }

    @FXML
   public void initialize() throws IOException {
    }

    public void printTable() throws IOException {
        int row = 1;
        programSimilarityChecker test =  new programSimilarityChecker(path);
        ArrayList<StackPane> names = new ArrayList<>();
        names.add(new StackPane(new Text("Scores")));
        for (String name : test.crossCompare().keySet()) {
//            name = name.substring(0, name.lastIndexOf('.'));
            names.add(new StackPane(new Text(name)));
        }
        StackPane[] nameList = new StackPane[names.size()];
        names.toArray(nameList);
        correlationMatrix.addRow(row++, nameList);
        final HashMap<String, ArrayList<Double>> comparisons = test.crossCompare();
        for (Map.Entry<String, ArrayList<Double>> entry1 : comparisons.entrySet()) {
            String name = entry1.getKey();
            ArrayList<Double> scores = entry1.getValue();
            ArrayList<Pane> scorePanes = new ArrayList<>();
            Pane namePane = new StackPane();
//            name = name.substring(0, name.lastIndexOf('.'));
            namePane.getChildren().add(new Text(name));
            scorePanes.add(namePane);
            for (Double score : scores){
                Pane newPane = new StackPane();
                if (score <= 100.0 && score >= 0.0)
                    newPane.setBackground(new Background(new BackgroundFill(Color.color(score / 100, 1 - (score / 100), 0), null,null)));
                newPane.getChildren().add(new Text(String.format("%.2f", score)));
                scorePanes.add(newPane);
            }

            Pane[] scorePaneArray = new Pane[scorePanes.size()];
            scorePanes.toArray(scorePaneArray);
            correlationMatrix.addRow(row++, scorePaneArray);
        }

        List<ResultTrio> results = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Double>> entry1 : comparisons.entrySet()){
            String firstName = entry1.getKey();
            ArrayList<Double> scores = entry1.getValue();
            int currentIndex = 0;
            for (Double score : scores) {
                String secondName = (String) comparisons.keySet().toArray()[currentIndex++];
                ResultTrio newResult = new ResultTrio(firstName, secondName, score);
                if (firstName.equals(secondName) || results.contains(newResult)) continue;
                results.add(newResult);
            }
        }
        Collections.sort(results);
        Collections.reverse(results);
        GridPane rankGridPane = new GridPane();
        mainPane.getChildren().add(rankGridPane);
        rankGridPane.addRow(0, new Text("Top 5"));
        for (int i = 1; i != 6; i++){
            rankGridPane.addRow(i,
                    new Text(results.get(i).getFirst()),
                    new Text(results.get(i).getSecond()),
                    new Text(String.format("%.2f", results.get(i).getScore())));
        }





    }
}
