package controller;

import domain.Intrebare;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import repository.IntrebareFileRepository;
import service.IntrebareService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudController {
    private IntrebareService intrebareService;
    private String corect ;
    @FXML
    TextField textFieldFilterName;
    @FXML
    RadioButton var1;
    @FXML
    RadioButton var2;
    @FXML
    RadioButton var3;

    public void setService(IntrebareService intrebareService){
        this.intrebareService = intrebareService;
    }
    public void primesteintrebare(Intrebare selected){
        Iterable<Intrebare> intrebari= intrebareService.findAll();
        List<Intrebare> lista = StreamSupport.stream(intrebari.spliterator(), false)
                .collect(Collectors.toList());
        String descriere = textFieldFilterName.getText();
        String variante[] = {};
        Intrebare in = new Intrebare(0,"","",1,variante);
        int da = 0;
        for(int i =0;i<lista.size();i++){
            if(lista.get(i).getDescriere().equals(descriere)){
                in = lista.get(i);
                da = 1;
                break;
            }
        }
        if(in.getId()!=0){
            corect = in.getRaspunsCorect();
            var1.setText(in.getVariante()[1]);
            var2.setText(in.getVariante()[2]);
            var3.setText(in.getVariante()[3]);
        }
    }
    public void handleTrimiteRaspuns(){
        if(var1.isSelected() && corect.equals(var1.getText())){
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete student...", "gresit");
        }
        if(var2.isSelected()&& corect.equals(var2.getText())){
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete student...", "corect");
        }
        if(var3.isSelected()&& corect.equals(var3.getText())){
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete student...", "gresit");
        }

    }
}
