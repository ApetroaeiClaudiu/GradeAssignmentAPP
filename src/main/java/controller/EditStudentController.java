package controller;

import domain.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.StudentService;
import validator.ValidationException;



public class EditStudentController {
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldGroup;
    @FXML
    private TextField textFieldSurname;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldProfessorLab;

    private StudentService studentService;
    Stage dialogStage;
    Student student;

    @FXML
    private void initialize() {
    }


    public void setService(StudentService studentService,  Stage stage, Student student) {
        this.studentService = studentService;
        this.dialogStage = stage;
        this.student = student;
        if (null != student) {
            setFields(student);
            textFieldID.setEditable(false);
        }
    }

    @FXML
    public void handleSave(){
        String id = textFieldID.getText();
        int group = -1 ;
        try {
            group = Integer.parseInt(textFieldGroup.getText());
        }
        catch (NumberFormatException e){
            MessageAlert.showErrorMessage(null,"Group should be a number !");
        }
        String surname = textFieldSurname.getText();
        String name = textFieldName.getText();
        String email = textFieldEmail.getText();
        String professorLab = textFieldProfessorLab.getText();
        Student student = new Student(id,group,surname,name,email,professorLab);
        if(group != -1){
            if (null == this.student)
                saveStudent(student);
            else
                updateStudent(student);
        }
    }

    private void updateStudent(Student student)
    {
        try {
            Student updatedStudent = this.studentService.update(student.getId(),student.getGroup(),student.getSurname(),student.getName(),student.getEmail(),student.getProfessorLab());
            if (updatedStudent == null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Updating student...","Student updated successfully!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }


    private void saveStudent(Student student)
    {
        try {
            Student aux = this.studentService.findById(student.getId());
            if(aux==null){
                Student newStudent = this.studentService.add(student.getId(),student.getGroup(),student.getSurname(),student.getName(),student.getEmail(),student.getProfessorLab());
                if (newStudent == null)
                    dialogStage.close();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Saving student...","Student saved successfully!");
            }
            else{
                MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Trying to save...","There already is a student with given ID");
            }
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
    }

    private void clearFields() {
        textFieldID.setText("");
        textFieldGroup.setText("");
        textFieldSurname.setText("");
        textFieldName.setText("");
        textFieldEmail.setText("");
        textFieldProfessorLab.setText("");
    }
    private void setFields(Student student)
    {
        textFieldID.setText(student.getId());
        textFieldGroup.setText(((Integer) student.getGroup()).toString());
        textFieldSurname.setText(student.getSurname());
        textFieldName.setText(student.getName());
        textFieldEmail.setText(student.getEmail());
        textFieldProfessorLab.setText(student.getProfessorLab());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

