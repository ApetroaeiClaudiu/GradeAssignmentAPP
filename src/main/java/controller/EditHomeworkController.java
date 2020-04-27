package controller;


import domain.Homework;
import domain.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.HomeworkService;
import service.StudentService;
import validator.ValidationException;



public class EditHomeworkController {
    @FXML
    private TextField textFieldDeadlineWeek;
    @FXML
    private TextField textFieldDescription;
    @FXML
    private TextField textFieldID;

    private HomeworkService homeworkService;
    Stage dialogStage;
    Homework homework;

    @FXML
    private void initialize() {
    }


    public void setService(HomeworkService homeworkService,  Stage stage,Homework homework) {
        this.homeworkService = homeworkService;
        this.dialogStage = stage;
        this.homework = homework;
        if (null != homework) {
            setFields(homework);
            textFieldID.setEditable(false);
        }
    }

    @FXML
    public void handleSave(){
        int id = -1;
        int deadlineWeek = -1;
        try{
            id = Integer.parseInt(textFieldID.getText());
            deadlineWeek = Integer.parseInt(textFieldDeadlineWeek.getText());
        }
        catch(NumberFormatException e){
            MessageAlert.showErrorMessage(null,"ID and deadline week should be numbers!");
        }
        String description = textFieldDescription.getText();
        if(id !=-1 && deadlineWeek!=-1){
            if (null == this.homework)
                saveHomework(id,deadlineWeek,description);
            else
                updateHomework(id,deadlineWeek,description);
        }
    }

    private void updateHomework(int id,int deadlineWeek,String description)
    {
        try {
            Homework updatedHomework = this.homeworkService.update(id, deadlineWeek, description);
            if (updatedHomework== null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Updating homework...","Homework updated successfully!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }


    private void saveHomework(int id,int deadlineWeek,String description)
    {
        try {
            Homework aux = this.homeworkService.findById(id);
            if(aux==null){
                Homework newHomework = this.homeworkService.add(id,deadlineWeek,description);
                if (newHomework == null)
                    dialogStage.close();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Saving homework...","Homework saved successfully!");
            }
            else{
                MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Trying to save...","There already is a homework with given ID");
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
    }

    private void setFields(Homework homework)
    {
        textFieldID.setText(((Integer) homework.getId()).toString());
        textFieldDeadlineWeek.setText(((Integer) homework.getDeadlineWeek()).toString());
        textFieldDescription.setText(homework.getDescription());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

