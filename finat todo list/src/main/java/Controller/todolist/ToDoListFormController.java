package Controller.todolist;

import Controller.completedtask.CompletedTaskController;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.ToDoList;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ToDoListFormController implements Initializable {

    public Label usenametxt;
    public TextField newTaskNametxt;
    public DatePicker newTaskDatetxt;
    public JFXListView todolistview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
        loadtask();
    }

    private void loadtask() {
        try {
            ArrayList<ToDoList> todoListArrayList = ToDoListController.getInstance().loadTasks();

            if (todoListArrayList == null || todoListArrayList.isEmpty()) {
                System.out.println("No tasks to display.");
                return;
            }

            for (ToDoList todoList : todoListArrayList) {
                HBox hBox = new HBox();
                hBox.setSpacing(30);
                hBox.setStyle("-fx-background-color: linear-gradient(to right, #E6F7E6, #D1F0D1); " + // Light green gradient
                        "-fx-border-color: #90C490; " + // Soft green border
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 3, 0, 1, 1);");

                Label taskNameLabel = new Label("Task: " + todoList.getTaskName());
                taskNameLabel.setStyle("-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #2F4F2F;"); // Dark green for task name
                hBox.getChildren().add(taskNameLabel);

                String taskDate = todoList.getDate();
                if (taskDate != null && !taskDate.isEmpty()) {
                    Label dateLabel = new Label("Date: " + taskDate);
                    dateLabel.setStyle("-fx-font-size: 14px; " +
                            "-fx-text-fill: #6B8E6B;"); // Soft green for date
                    hBox.getChildren().add(dateLabel);
                }

                CheckBox checkBox = new CheckBox("Completed");
                checkBox.setStyle("-fx-font-size: 14px; " +
                        "-fx-text-fill: #4C7A4C; " + // Medium green for checkbox text
                        "-fx-cursor: hand; " +
                        "-fx-alignment: center;");
                hBox.getChildren().add(checkBox);

                todolistview.getItems().add(hBox);

                checkBox.setOnAction(actionEvent -> {
                    if (checkBox.isSelected()) {
                        String currentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

                        if (CompletedTaskController.getInstance().completedTask(todoList.getTaskName(), currentDate) &&
                                ToDoListController.getInstance().deleteCompletedTask(todoList.getTaskName())) {
                            new Alert(Alert.AlertType.INFORMATION, "Task Finished").show();
                            todolistview.getItems().remove(hBox);
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Task operation failed").show();
                        }
                    }
                });
            }

            System.out.println("Tasks loaded successfully!");
        } catch (Exception e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error loading tasks: " + e.getMessage()).show();
        }
    }

    private void setName() {
        String name = ToDoListController.getInstance().getuUserName();
        usenametxt.setText(name);
    }

    public void btnAddTaskOnAction(ActionEvent actionEvent) {
        if (newTaskDatetxt.getValue() == null || newTaskNametxt.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields!").show();
            return;
        }

        HBox hBox = new HBox();
        hBox.setSpacing(30);
        hBox.setStyle("-fx-background-color: linear-gradient(to right, #E6F7E6, #D1F0D1); " +
                "-fx-border-color: #90C490; " +
                "-fx-border-width: 1.5px; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8; " +
                "-fx-padding: 12; " +
                "-fx-spacing: 30; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 3, 0, 1, 1);");

        Label taskNameLabel = new Label("Task: " + newTaskNametxt.getText().trim());
        taskNameLabel.setStyle("-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #2F4F2F;"); // Dark green
        hBox.getChildren().add(taskNameLabel);

        String taskDate = newTaskDatetxt.getValue().toString();
        Label dateLabel = new Label("Date: " + taskDate);
        dateLabel.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: #6B8E6B;");
        hBox.getChildren().add(dateLabel);

        CheckBox checkBox = new CheckBox("Completed");
        checkBox.setStyle("-fx-font-size: 14px; " +
                "-fx-text-fill: #4C7A4C; " +
                "-fx-cursor: hand; " +
                "-fx-alignment: center;");
        hBox.getChildren().add(checkBox);

        todolistview.getItems().add(hBox);

        checkBox.setOnAction(e -> {
            if (checkBox.isSelected()) {
                String taskName = taskNameLabel.getText().replace("Task: ", "").trim();
                String currentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

                if (CompletedTaskController.getInstance().completedTask(taskName, currentDate) &&
                        ToDoListController.getInstance().deleteCompletedTask(taskName)) {
                    new Alert(Alert.AlertType.INFORMATION, "Task Finished").show();
                    todolistview.getItems().remove(hBox);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Task operation failed").show();
                }
            }
        });

        boolean taskAdded = ToDoListController.getInstance().addTask(new ToDoList(null, newTaskNametxt.getText().trim(), taskDate, null));

        if (taskAdded) {
            new Alert(Alert.AlertType.INFORMATION, "Task Added Successfully").show();
            cleartxts();
        } else {
            new Alert(Alert.AlertType.ERROR, "Task Addition Failed").show();
        }
    }

    private void cleartxts() {
        newTaskNametxt.clear();
        newTaskDatetxt.setValue(null);
    }

    public void btcViewCompletedTasksOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/completed_task.fxml"))));
        stage.show();
    }
}
