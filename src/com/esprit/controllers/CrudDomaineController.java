/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Domaine;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceDomaineO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CrudDomaineController implements Initializable {

    @FXML
    private TableView<Domaine> tableDomaine;
    @FXML
    private TableColumn<Domaine, String> domaineCol;
    @FXML
    private TextField TFnomDomaine;
    ServiceDomaineO sd = new ServiceDomaineO();
    @FXML
    private TableColumn deleteCol;
    @FXML
    private FontAwesomeIconView addIcon;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        tableDomaine.setEditable(true);
        domaineCol.setCellFactory(TextFieldTableCell.forTableColumn());
        domaineCol.setOnEditCommit(this::OnEditDomaineName);
        Refreshtable();
    }    
  
    public void Refreshtable(){
        ObservableList<Domaine> listDomaine = FXCollections.observableArrayList(sd.afficher());
        
        domaineCol.setCellValueFactory(new PropertyValueFactory<>("nom_domaine"));
        Callback<TableColumn<Domaine,String>,TableCell<Domaine,String>> cellFactory=(param) -> {
            final TableCell<Domaine,String> cell = new TableCell<Domaine,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteIcon.setStyle(
                                "-fx-cursor:hand;"
                                + "-glyph-size:35px;"
                                + "-fx-fill:#ff1744;"
                                
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) ->{
                        Domaine d = getTableView().getItems().get(getIndex());
                        
                        
                        int result = JOptionPane.showConfirmDialog(null, "vouler vous supprimer un domaine ?","Confirmation",JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION){
                            sd.supprimer(d);
                            JOptionPane.showMessageDialog(null, "Domaine Supprimer !");
                            Refreshtable();
                        }else{
                            return ;
                        }

                        });
                        setGraphic(deleteIcon);
                        setText(null);
                    }
                }
            };
            
            
            return cell; 
        };
        
        
        deleteCol.setCellFactory(cellFactory);
        
        tableDomaine.setItems(listDomaine);
    }

    @FXML
    private void OnEditDomaineName(CellEditEvent event) {
        Domaine d = tableDomaine.getSelectionModel().getSelectedItem();
        if(event.getNewValue().toString().equals("")){
            JOptionPane.showMessageDialog(null, "donner le nom du domaine  !");
            d.setNom_domaine(event.getOldValue().toString());
            Refreshtable();
            return ;
        }
        if(sd.chercherNomDomaine(event.getNewValue().toString())){
            JOptionPane.showMessageDialog(null, "Domaine Existe Deja !");
            d.setNom_domaine(event.getOldValue().toString());
            Refreshtable();
            return ;
        }
        
        
        
        int result = JOptionPane.showConfirmDialog(null, "vouler vous modifier un domaine ?","Confirmation",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION){
                d.setNom_domaine(event.getNewValue().toString());
                sd.modifier(d);
            }else{
                d.setNom_domaine(event.getOldValue().toString());
                Refreshtable();
            }
        
        
        
    }

    @FXML
    private void addDomaine(MouseEvent event) {
        
        if(TFnomDomaine.getText().equals("")){
            JOptionPane.showMessageDialog(null, "donner le nom du domaine  !");
            return ;
        }
        if(sd.chercherNomDomaine(TFnomDomaine.getText())){
            JOptionPane.showMessageDialog(null, "Domaine Existe Deja !");
            return ;
        }
        
        
            int result = JOptionPane.showConfirmDialog(null, "vouler vous ajouter un domaine ?","Confirmation",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                sd.ajouter(new Domaine(TFnomDomaine.getText()));
                JOptionPane.showMessageDialog(null, "Domaine Ajouter !");
                TFnomDomaine.clear();
                Refreshtable();
            }else{
                return ;
            }
        
    }
    
}
