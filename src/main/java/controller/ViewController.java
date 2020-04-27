package controller;

import domain.Grade;
import domain.GradePair;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.GradeService;
import validator.ValidationException;

import java.lang.reflect.InvocationTargetException;

public class ViewController {
    @FXML
    private TextField textFieldHomework;
    @FXML
    private TextField textFieldStudent;
    @FXML
    private TextField textFieldGradeValue;
    @FXML
    private TextField textFieldMinusPoints;
    @FXML
    private TextField textFieldMotivatedWeeks;

    private GradeService gradeservice;
    Stage dialogStage;
    int idHomework;
    String idStudent;
    float gradeValue;
    int minusPoints;
    int motivatedWeeks;
    int deliveryWeek;
    String professor;
    String feedback;
    String name;
    String surname;

    public void setService(GradeService gradeservice, Stage dialogStage, int idHomework, String idStudent, float gradeValue, int minusPoints, int motivatedWeeks, int deliveryWeek, String professor, String feedback,String name,String surname){
        this.gradeservice =gradeservice;
        this.dialogStage=dialogStage;
        this.idHomework=idHomework;
        this.idStudent=idStudent;
        this.gradeValue=gradeValue;
        this.minusPoints=minusPoints;
        this.motivatedWeeks=motivatedWeeks;
        this.deliveryWeek=deliveryWeek;
        this.professor=professor;
        this.feedback=feedback;
        this.name= name;
        this.surname = surname;
        setFields(idHomework,name,surname,gradeValue,minusPoints,motivatedWeeks);
    }
    @FXML
    private void initialize() {
    }

    @FXML
    public void handleSave(){
        try {
            GradePair pair = new GradePair(idStudent,idHomework);
            Grade aux = this.gradeservice.findById(pair);
            if(aux==null){
                Grade newGrade = this.gradeservice.add(idStudent,idHomework,deliveryWeek,professor,gradeValue,feedback);
                if (newGrade == null)
                    dialogStage.close();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Saving grade...","Grade saved successfully!");
            }
            else{
                MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Trying to save...","There already is a grade for this student at this homework !");
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
    }

    private void setFields(int idHomework, String name,String surname, float gradeValue, int minusPoints, int motivatedWeeks){
        textFieldHomework.setText(((Integer)idHomework).toString());
        textFieldHomework.setEditable(false);
        textFieldStudent.setText(surname+ " " + name);
        textFieldStudent.setEditable(false);
        textFieldGradeValue.setText(((Float)gradeValue).toString());
        textFieldGradeValue.setEditable(false);
        textFieldMinusPoints.setText(((Integer)minusPoints).toString());
        textFieldMinusPoints.setEditable(false);
        textFieldMotivatedWeeks.setText(((Integer)motivatedWeeks).toString());
        textFieldMotivatedWeeks.setEditable(false);
    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
