package assignment4.gui;

import assignment4.driver.TestDriver;
import assignment4.runners.TestRunner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGUI extends Application {

    static ArrayList<String> toRun = new ArrayList<>();
    ArrayList<String> arr = new ArrayList<>();
    static String FilteredString;
    static int runCount = 1;
    ObservableList<String> testoptions;
    static ComboBox<String> dropbox;
    @Override
    public void start(Stage applicationStage) {
        //print stream initialization
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        applicationStage.setTitle("GUI Testing");
        GridPane grid = new GridPane();

        testoptions = FXCollections.observableArrayList(arr);
        dropbox = new ComboBox<>(testoptions);

        //have user input package for testing
        Stage promptStage = new Stage();
        GridPane packagePrompt = new GridPane();
        Label question = new Label("What package would you like to test from?");
        packagePrompt.add(question,0,0);
        TextField input = new TextField();
        packagePrompt.add(input,0,1);
        Button submit = new Button();
        submit.setText("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String s = input.getText();
                arr = TestDriver.getTestPackage(s);
                if (arr == null) {
                    Stage errorStage = new Stage();
                    GridPane pane = new GridPane();
                    errorStage.setTitle("Error");
                    Label errorMessage = new Label("Package does not exist. Try again.");
                    pane.add(errorMessage, 0,0);
                    errorStage.setScene(new Scene(pane, 200, 25));
                    errorStage.setAlwaysOnTop(true);
                    errorStage.initOwner(promptStage);
                    errorStage.initModality(Modality.WINDOW_MODAL);
                    errorStage.show();
                } else {
                    testoptions = FXCollections.observableArrayList(arr);
                    dropbox = new ComboBox<>(testoptions);
                    dropbox.setPrefWidth(500);
                    grid.add(dropbox, 0, 3);
                    promptStage.close();
                }
            }
        });
        packagePrompt.add(submit,0,2);

        Rectangle Rect = new Rectangle(500,150,Color.GOLD);
        grid.add(Rect,0,0);
        //image properties
        Image myImage = new Image("JavaTestLogo.png", true);
        ImageView imageView = new ImageView(myImage);
        imageView.setX(500);
        imageView.setY(0);
        imageView.setFitHeight(455);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        Group root = new Group(imageView);

        ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
        ListView<String> newSelected = new ListView<>(selectedList);
        Label subHeader = new Label("Tests to run:");
        
        //run button
        Button run = new Button();
        run.setText("Run");
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextArea Output2 = new TextArea();
                Output2.setPrefHeight(400);
                Output2.setPrefWidth(550);
                
                Stage newWindow = new Stage();
                GridPane Output = new GridPane();
                Rectangle Rect = new Rectangle(550,150,Color.LIGHTBLUE);
                Output.add(Rect,0,0);
                Image myImage = new Image("JavaTestLogo.png", true);
                ImageView imageView = new ImageView(myImage);
                imageView.setX(500);
                imageView.setY(0);
                imageView.setFitHeight(455);
                imageView.setFitWidth(525);
                imageView.setPreserveRatio(true);
                Group root = new Group(imageView);
                Output.add(root,0,0);
                

                TestDriver.runTests(toRun.toArray(new String[0])/*, Output2, baos, Output*/);

                TestDriver.printSummary();
                System.out.flush();
                Output2.setText("Run " + runCount + " Results:" + "\n" + baos + "\n");
                baos.reset();
                runCount++;
                TestDriver.clearResults();

                Label OutputLabel = new Label("Output:");

                Output.add(OutputLabel,0,1);
                Scene OutputScene = new Scene(Output, 550, 500);
               
                Output.add(Output2, 0, 2);

                newWindow.setTitle("Output");
                newWindow.setScene(OutputScene);
                newWindow.show();

            }
        });


        //add button
        Button addbutton = new Button();
        addbutton.setText("Add");
        addbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent event) {
            	 if(dropbox.getValue() != null) {
                if (!toRun.contains(dropbox.getValue())) {
                    String[] TestCaseName = new String[1];
                    TestCaseName[0]=dropbox.getValue();
                    Annotation[] Anno = TestDriver.AnnotationsGet(TestCaseName);
                    if(Anno.length==0) {
                        Class<?> className;
                        try {
                            className = Class.forName(dropbox.getValue());
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        TestRunner runner = new TestRunner(className);
                        Method[] methodlist = runner.methods;
                        List<String> methodarray = new ArrayList<>();
                        for(Method m : methodlist) {
                            methodarray.add(m.getName());
                        }

                        FilterGUI(methodarray, dropbox.getValue());
                        toRun.add(FilteredString);

                        FilteredString="";
                    }
                    else {
                        toRun.add(dropbox.getValue());
                    }
                }
                ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
                ListView<String> newSelected = new ListView<>(selectedList);
                grid.add(newSelected, 0, 8);
            }
            }
        });


        //addAll button
        Button addAllButton = new Button();
        addAllButton.setText("Add All");
        addAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (String s : arr) {
                    if (!toRun.contains(s)) {
                        toRun.add(s);
                    }
                }
                ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
                ListView<String> newSelected = new ListView<>(selectedList);
                grid.add(newSelected, 0, 8);
            }
        });


        //remove button
        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toRun.removeIf(s -> s.contains(dropbox.getValue()));
                ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
                ListView<String> newSelected = new ListView<>(selectedList);
                grid.add(newSelected, 0, 8);
            }
        });

        //removeAll button
        Button removeAllButton = new Button();
        removeAllButton.setText("Remove All");
        removeAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toRun.clear();
                ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
                ListView<String> newSelected = new ListView<>(selectedList);
                grid.add(newSelected, 0, 8);
            }
        });


        run.setPrefWidth(150);
        addbutton.setPrefWidth(150);
        addAllButton.setPrefWidth(150);
        removeButton.setPrefWidth(150);
        removeAllButton.setPrefWidth(150);
        dropbox.setPrefWidth(500);
        run.setLayoutX(100);
        grid.add(root,0,0); //logo position
        grid.add(dropbox, 0, 3); //dropbox position
        grid.add(run, 1, 6);
        grid.add(addbutton, 0, 4);
        grid.add(addAllButton,1,4);
        grid.add(removeButton, 0, 5);
        grid.add(removeAllButton,1,5);
        grid.add(newSelected, 0, 8); //selected list position  **also updates inside buttons
        grid.add(subHeader, 0, 7); //Label position

        applicationStage.setScene(new Scene(grid, 650, 550));
        applicationStage.show();

        promptStage.setScene(new Scene(packagePrompt, 250, 250));
        promptStage.setAlwaysOnTop(true);
        promptStage.initOwner(applicationStage);
        promptStage.initModality(Modality.WINDOW_MODAL);
        promptStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch application
    }

    public static void OutputGUI(GridPane pane, TextArea textArea, ByteArrayOutputStream baos, Stage stage) throws InterruptedException {
        for (String s : toRun) {
            String[] sa = new String[]{s};
            TestDriver.runTests(sa);
            textArea.setText(baos + "\n");
            //pane.getChildren().add(textArea);
            //stage.setScene(new Scene(pane, 500,500));
            stage.show();
            //Thread.sleep(1000);
        }
    }

    public static void FilterGUI(List<String> methods, String TestCase){
        Stage newWindow = new Stage();
        ArrayList<String> toRun = new ArrayList<>();
        Label OutputLabel = new Label("FilterWindow");
        GridPane Filter = new GridPane();
        Filter.getChildren().add(OutputLabel);
        int size = methods.size();
        String[] arr = new String[size];
        for(int i=0; i<size;i++) {
            arr[i]=methods.get(i);
        }
        ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
        ListView<String> newSelected = new ListView<>(selectedList);
        Filter.add(newSelected, 0, 6);
        Arrays.sort(arr);
        ObservableList<String> testoptions = FXCollections.observableArrayList(arr);
        final ComboBox<String> dropbox = new ComboBox<>(testoptions);
        //add button
        Button addbutton = new Button();
        addbutton.setText("Add");
        addbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(dropbox.getValue() != null) {
                    if (!toRun.contains(dropbox.getValue())) {

                        toRun.add(dropbox.getValue());

                    }
                    ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
                    ListView<String> newSelected = new ListView<>(selectedList);
                    Filter.add(newSelected, 0, 6);
                }
            }
        });


        //addAll button
        Button addAllButton = new Button();
        addAllButton.setText("Add All");
        addAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                for (String s : arr) {
//                    if (!toRun.contains(s)) {
//                        toRun.add(s);
//                    }
//                }
//                ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
//                ListView<String> newSelected = new ListView<>(selectedList);
//                Filter.add(newSelected, 0, 6);
////                selectedTests.setText(toRun.toString());
                FilteredString=TestCase;
                newWindow.close();
            }
        });


        //remove button
        Button removeButton = new Button();
        removeButton.setText("Remove");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toRun.remove((String)(dropbox.getValue()));
//                selectedTests.setText(toRun.toString());
                ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
                ListView<String> newSelected = new ListView<>(selectedList);
                Filter.add(newSelected, 0, 6);
            }
        });

        //removeAll button
        Button removeAllButton = new Button();
        removeAllButton.setText("Remove All");
        removeAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toRun.clear();
//                selectedTests.setText(toRun.toString());
                ObservableList<String> selectedList = FXCollections.observableArrayList(toRun);
                ListView<String> newSelected = new ListView<>(selectedList);
                Filter.add(newSelected, 0, 6);
            }
        });
        //run button
        Button run = new Button();
        run.setText("Run");
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(toRun.size()>0) {
                    String toRunString= toRun.toString().replace(" ", "").replace("[", "").replace("]", "");  //remove the left bracket
                    toRunString.trim();
                    FilteredString = (TestCase+"#"+toRunString);
                }
                else {
                    FilteredString = (TestCase);
                }
                newWindow.close();
            }
        });


        //grid.add(root,0,0); //logo position
        run.setPrefWidth(150);
        addbutton.setPrefWidth(150);
        addAllButton.setPrefWidth(150);
        removeButton.setPrefWidth(150);
        removeAllButton.setPrefWidth(150);
        dropbox.setPrefWidth(500);
        Filter.add(dropbox, 0, 1); //dropbox position
        Filter.add(run, 0, 4);
        Filter.add(addbutton, 0, 2);
        Filter.add(addAllButton,1,2);
        Filter.add(removeButton, 0, 3);
        Filter.add(removeAllButton,1,3);

        //Filter.add(newSelected, 0, 6); //selected list position  **also updates inside buttons

        Scene OutputScene = new Scene(Filter, 650, 500);

        newWindow.setTitle("Filter");
        newWindow.setScene(OutputScene);
        newWindow.showAndWait();

    }


}