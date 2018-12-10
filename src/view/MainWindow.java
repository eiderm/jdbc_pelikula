/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import controller.GestionListaEnMemoria;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.ModTableCell;

import model.Pelikula;
import model.NumberTextField;

/**
 *
 * @author Eider
 */
public class MainWindow extends Application {

    private final TableView<Pelikula> table = new TableView<>();

    final HBox hb = new HBox();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());

        stage.setTitle("Pelikulak");
        stage.setWidth(880);
        stage.setHeight(520);
        final Label label = new Label("Pelikula");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        Callback<TableColumn<Pelikula, String>, TableCell<Pelikula, String>> comboBoxCellFactory
                = (TableColumn<Pelikula, String> param) -> new ModTableCell();
        
        TableColumn<Pelikula, String> izenaCol = new TableColumn<>("Izena");
        izenaCol.setMinWidth(130);
        izenaCol.setCellValueFactory(
                new PropertyValueFactory<>("izena"));
        izenaCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        izenaCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setIzena(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });
        

        TableColumn<Pelikula, String> direkCol = new TableColumn<>("Direktorea");
        direkCol.setMinWidth(130);
        direkCol.setCellValueFactory(new PropertyValueFactory<>("direktorea"));
        direkCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        direkCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDirektorea(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });        

        TableColumn<Pelikula, String> urteCol = new TableColumn<>("Urtea");
        urteCol.setMinWidth(100);
        urteCol.setCellValueFactory(new PropertyValueFactory<>("urtea"));
        urteCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        urteCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setUrtea(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });
        
        TableColumn<Pelikula, String> generoCol = new TableColumn<>("Generoa");
        generoCol.setMinWidth(150);
        generoCol.setCellValueFactory(new PropertyValueFactory<>("generoa"));
        generoCol.setCellFactory(TextFieldTableCell.<Pelikula>forTableColumn());
        generoCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
            ((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setGeneroa(t.getNewValue());
            GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                    t.getTablePosition().getRow()));
        });
        
        TableColumn<Pelikula, String> ikusCol = new TableColumn<>("Ikusita");
        ikusCol.setMinWidth(100);
        ikusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIkusita()));
        ikusCol.setCellFactory(comboBoxCellFactory);
        ikusCol.setOnEditCommit((TableColumn.CellEditEvent<Pelikula, String> t) -> {
                ((Pelikula) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIkusita(t.getNewValue());
                GestionListaEnMemoria.guardarDatos((Pelikula) t.getTableView().getItems().get(
                            t.getTablePosition().getRow()));
                });

        table.setItems(FXCollections.observableList(GestionListaEnMemoria.cargarDatos()));
        table.getColumns().addAll(izenaCol, direkCol, urteCol, generoCol, ikusCol);

        final TextField addIzena = new TextField();
        addIzena.setPromptText("Izena");
        addIzena.setMaxWidth(izenaCol.getPrefWidth());

        final TextField addDirektorea = new TextField();
        addDirektorea.setMaxWidth(direkCol.getPrefWidth());
        addDirektorea.setPromptText("Direktorea");

        final NumberTextField addUrtea = new NumberTextField();
        addUrtea.setMaxWidth(urteCol.getPrefWidth());
        addUrtea.setPromptText("Urtea");

        final TextField addGeneroa = new TextField();
        addGeneroa.setMaxWidth(generoCol.getPrefWidth());
        addGeneroa.setPromptText("Generoa");

        final ComboBox addIkusita = new ComboBox(FXCollections.observableList(GestionListaEnMemoria.cargarDatosIkusita()));
        addIkusita.setMaxWidth(100);
        addIkusita.setPromptText("Ikusita");

        final Button addButton = new Button("Gehitu");
        addButton.setOnAction((ActionEvent e) -> {
            if ("".equals(addIzena.getText()) || "".equals(addDirektorea.getText()) || addIkusita.getSelectionModel().isEmpty() || addUrtea.getText() == "" || addGeneroa.getText() == "") {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ezin da gehitu!");
                alert.setContentText("Ezin da daturik gorde taulan guztiak bete barik.");
                alert.showAndWait();
            } else {
                Pelikula p = new Pelikula(addIzena.getText(), addDirektorea.getText(), addUrtea.getText(), addGeneroa.getText(), addIkusita.getSelectionModel().getSelectedItem().toString());
                table.getItems().add(p);
                addIzena.clear();
                addDirektorea.clear();
                addUrtea.clear();
                addGeneroa.clear();
                addIkusita.getSelectionModel().clearSelection();
                GestionListaEnMemoria.escribirDatos(table);
            }
        });

        final Button removeButton = new Button("Ezabatu");
        removeButton.setOnAction((ActionEvent e) -> {
            Pelikula mon = table.getSelectionModel().getSelectedItem();
            GestionListaEnMemoria.borrarDatos(mon);
            table.getItems().remove(mon);

        });

        hb.getChildren().addAll(addIzena, addDirektorea, addUrtea, addGeneroa, addIkusita, addButton, removeButton);
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest((WindowEvent event) -> {
            GestionListaEnMemoria.guardarDatosCompletos(table);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
