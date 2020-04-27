package controller;

import domain.Grade;
import domain.Homework;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.HomeworkService;
import utils.event.HomeworkChangeEvent;
import utils.observer.Observer;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeworkController implements Observer<HomeworkChangeEvent> {
    HomeworkService homeworkservice;
    ObservableList<Homework> model = FXCollections.observableArrayList();


    @FXML
    TableView<Homework> tableView;
    @FXML
    TableColumn<Homework,Integer> tableColumnIDHomework;
    @FXML
    TableColumn<Homework, String> tableColumnStartWeek;
    @FXML
    TableColumn<Homework, String> tableColumnDeadlineWeek;
    @FXML
    TableColumn<Homework, String> tableColumnDescription;

    public void setHomeworkService(HomeworkService homeworkService) {
        homeworkservice = homeworkService;
        homeworkservice.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnIDHomework.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Homework, Integer> param) {
                return new ReadOnlyObjectWrapper<Integer>(param.getValue().getId());
            }
        });
        tableColumnStartWeek.setCellValueFactory(new PropertyValueFactory<Homework, String>("startWeek"));
        tableColumnDeadlineWeek.setCellValueFactory(new PropertyValueFactory<Homework, String>("deadlineWeek"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<Homework, String>("description"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<Homework> homework = homeworkservice.findAll();
        model.setAll(getHomework());
    }

    private List<Homework> getHomework() {
        Iterable<Homework> homework = homeworkservice.findAll();
        List<Homework> homeworkList = StreamSupport.stream(homework.spliterator(), false)
                .collect(Collectors.toList());
        return homeworkList;
    }

    @Override
    public void update(HomeworkChangeEvent homeworkChangeEvent) {
        initModel();
    }

    public void handleDeleteHomework(ActionEvent actionEvent) {
        Homework selected = (Homework) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int id = selected.getId();
            Homework deleted = homeworkservice.delete(id);
            if (null != deleted)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete homework...", "Homework deleted successfully!");
        } else MessageAlert.showErrorMessage(null, "No homework selected!");
    }

    @FXML
    public void handleAddHomework(ActionEvent actionEvent) {
        showHomeworkEditDialog(null);
    }

    public void showHomeworkEditDialog(Homework homework) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/editHomeworkView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit homework");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("/images/logonou.png"));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            EditHomeworkController editHomeworkController = loader.getController();
            editHomeworkController.setService(homeworkservice,dialogStage,homework);
            dialogStage.setWidth(500);
            dialogStage.setHeight(400);
            dialogStage.setMinHeight(300);
            dialogStage.setMinWidth(400);
            dialogStage.setMaxHeight(600);
            dialogStage.setMaxWidth(600);
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateHomework(ActionEvent actionEvent) {
        Homework selected = (Homework) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showHomeworkEditDialog(selected);
        } else
            MessageAlert.showErrorMessage(null, "No homework selected!");
    }
}


