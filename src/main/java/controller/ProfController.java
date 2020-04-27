package controller;

import domain.Grade;
import domain.Intrebare;
import domain.Raspuns;
import domain.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.IntrebareService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProfController {
    private IntrebareService intrebareService;
    private ArrayList<String> array;
    private ArrayList<StudController> listacontrol;
    ObservableList<Intrebare> model = FXCollections.observableArrayList();
    ObservableList<Raspuns> modelRaspuns = FXCollections.observableArrayList();

    @FXML
    TableView<Intrebare> tableView;
    @FXML
    TableColumn<Intrebare,String> tableNrIntrebare;
    @FXML
    TableColumn<Intrebare, String> tableDescriere;
    @FXML
    TableColumn<Intrebare, String> tableRaspuns;
    @FXML
    TableColumn<Intrebare, String> tablePunctaj;

    @FXML
    TableView<Raspuns> tableViewRaspuns;
    @FXML
    TableColumn<Raspuns,String> tableNr;
    @FXML
    TableColumn<Raspuns,String> tableNume;
    @FXML
    TableColumn<Raspuns, String> tablePunct;

    public void setService(IntrebareService intrebareService, ArrayList<String> array,ArrayList<StudController> listacontrol){
        this.intrebareService = intrebareService;
        this.array = array;
        this.listacontrol = listacontrol;
        initModel();
    }
    private List<Intrebare> getIntrebari() {
        Iterable<Intrebare> intrebari= intrebareService.findAll();
        List<Intrebare> lista = StreamSupport.stream(intrebari.spliterator(), false)
                .collect(Collectors.toList());
        return lista;
    }
    public void initModel(){
        model.setAll(getIntrebari());
    }
    public void initialize(){
//        tableNrIntrebare.setCellValueFactory(new Callback<>() {
//            @Override
//            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Intrebare,Integer> param) {
//                return new ReadOnlyObjectWrapper<Integer>(param.getValue().getNrintrebare());
//            }
//        });
        tableNrIntrebare.setCellValueFactory(new PropertyValueFactory<Intrebare, String>("nrintrebare"));
        tableDescriere.setCellValueFactory(new PropertyValueFactory<Intrebare, String>("descriere"));
        tableRaspuns.setCellValueFactory(new PropertyValueFactory<Intrebare, String>("raspunsCorect"));
        tablePunctaj.setCellValueFactory(new PropertyValueFactory<Intrebare, String>("punctaj"));
        tableView.setItems(model);
    }
    public void handlePlaseaza(){
        Intrebare selected = (Intrebare) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int id = selected.getId();
            Intrebare deleted = intrebareService.delete(id);
        }
        initModel();
        for(int i =0;i<=listacontrol.size();i++){
            listacontrol.get(i).primesteintrebare(selected);
        }

    }

    public void handleTrimite(){

    }
}
