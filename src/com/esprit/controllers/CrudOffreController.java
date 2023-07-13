/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.esprit.controllers;

import com.esprit.entities.Offre;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceDomaineO;
import com.esprit.services.ServiceOffre;
import com.esprit.services.ServiceOffre.OffreView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CrudOffreController implements Initializable {

    @FXML
    private TextField txtTitre;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView<OffreView> tableOffre;
    @FXML
    private TableColumn<OffreView, String> titreCol;
    @FXML
    private TableColumn<OffreView, String> descCol;
    @FXML
    private TableColumn<OffreView,Date > datePubCol;
    @FXML
    private TableColumn<OffreView, Date> dateExpCol;
    @FXML
    private ComboBox<String> ChoiseBoxDomaine;
    @FXML
    private TableColumn<OffreView, String> NomDomaineCol;
    @FXML
    private Button btnAff;
    @FXML
    private TableColumn<OffreView, String> NomEntCol;
    
    ServiceOffre sf = new ServiceOffre();
    @FXML
    private ComboBox<String> ComboChercher;
    Integer index;
    String domaineRechercher = "";
    ObservableList<OffreView> listOffres;
    @FXML
    private TableColumn btnCol;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServiceDomaineO sd = new ServiceDomaineO();
        ComboChercher.getItems().add("All");
        ComboChercher.getItems().addAll(sd.getDomainesName());
        ChoiseBoxDomaine.getItems().addAll(sd.getDomainesName());

        titreCol.setCellFactory(TextFieldTableCell.forTableColumn());
        titreCol.setOnEditCommit(this::editTitre);
        
        descCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descCol.setOnEditCommit(this::editDesc);
        
//        datePubCol.setCellFactory(DatePickerTableCell.forTableColumn());
//        datePubCol.setOnEditCommit(this::editDatePub);
            
        table();
    }    

    
    
     @FXML
    private void changeRecherche(ActionEvent event) {
        domaineRechercher = ComboChercher.getSelectionModel().getSelectedItem();
        System.out.println(domaineRechercher);
        table();
    }


    @FXML
    private void add(ActionEvent event) {
        
        if (txtTitre.getText().equals("") || txtDescription.getText().equals("")||datePicker.getValue()== null || ChoiseBoxDomaine.getValue() == null ) {
            JOptionPane.showMessageDialog(null,"Champ Manquant !");
            return ;
        }
        ServiceDomaine sd = new ServiceDomaine();
        int result = JOptionPane.showConfirmDialog(null, "vouler vous ajouter ce offre ?","Confirmation",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                sf.ajouter(new Offre(txtTitre.getText(), txtDescription.getText(),sd.getIdDomaineByName(ChoiseBoxDomaine.getValue()),2, Date.valueOf(datePicker.getValue())));
                JOptionPane.showMessageDialog(null, "offre ajouter !");
                table();
            }else{
                return ;
            }
    }
    
 
    public void table(){
        if(domaineRechercher.equals("") || domaineRechercher.equals("All")){
        listOffres  = observableArrayList(sf.afficherOffres());
        }else{
            listOffres = observableArrayList(sf.afficherOffresByDomaine(domaineRechercher));
         }

        NomEntCol.setCellValueFactory(new PropertyValueFactory<OffreView,String>("nomEntreprise"));
        titreCol.setCellValueFactory(new PropertyValueFactory<OffreView,String>("titre"));
        descCol.setCellValueFactory(new PropertyValueFactory<OffreView,String>("description"));
        NomDomaineCol.setCellValueFactory(new PropertyValueFactory<OffreView,String>("nomDomaine"));
        datePubCol.setCellValueFactory(new PropertyValueFactory<OffreView,Date>("date_pub"));
        dateExpCol.setCellValueFactory(new PropertyValueFactory<OffreView,Date>("date_Exp"));
        
        
        Callback<TableColumn<OffreView,String>,TableCell<OffreView,String>> cellFactory =(param) -> {
            final TableCell<OffreView,String> cell = new TableCell<OffreView,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
                        deleteIcon.setStyle(
                                "-fx-cursor:hand;"
                                + "-glyph-size:35px;"
                                + "-fx-fill:#ff1744;"
                                
                        );
                        editIcon.setStyle(
                                "-fx-cursor:hand;"
                                + "-glyph-size:35px;"
                                + "-fx-fill:#02B875;"
                                
                        );
                        
                        deleteIcon.setOnMouseClicked((MouseEvent event) ->{
                        OffreView offre = getTableView().getItems().get(getIndex());
                            
                        int result = JOptionPane.showConfirmDialog(null, "vouler vous supprimer un offre ?","Confirmation",JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION){
                            sf.supprimerParId(offre.getId_offre());
                            JOptionPane.showMessageDialog(null, "offre Supprimer !");
                            table();
                        }else{
                            return ;
                        }
                        });
                        
                        
                        editIcon.setOnMouseClicked((MouseEvent event) ->{
                            try {
                                OffreView offre = getTableView().getItems().get(getIndex());
                                ServiceDomaineO sd = new ServiceDomaineO();
                                FXMLLoader loader =new FXMLLoader(getClass().getResource("../view/UpdateOffre.fxml"));
                                Parent root = loader.load();
                                UpdateOffreController uoc = loader.getController();
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                
                                uoc.setIdOffre(offre.getId_offre());
                                uoc.setTxtDescription(offre.getDescription());
                                uoc.setTxtTitre(offre.getTitre());
                                uoc.setChoiseBoxDomaine();
                                
                                uoc.setIDEntreprise(offre.getId_entreprise());
                                uoc.setDatePickerPub(Date.valueOf(offre.getDate_pub().toString()));
                                uoc.setDatePickerExp(Date.valueOf(offre.getDate_Exp().toString()));
                                
                                uoc.setOffreController(CrudOffreController.this);
                                uoc.setStage(stage);
                            } catch (IOException ex) {
                                Logger.getLogger(CrudOffreController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        });
                        
                        
                        
                        
                        
                        
                        HBox managebtn = new HBox(deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignement:center");
                        HBox.setMargin(deleteIcon, new Insets(2,25,0,2));
                        HBox.setMargin(editIcon, new Insets(2,3,0,2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell; 
        };

        btnCol.setCellFactory(cellFactory);
        tableOffre.setItems(listOffres);
    }

    @FXML
    private void AfficherOffre(ActionEvent event) throws IOException {
        index = tableOffre.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            JOptionPane.showMessageDialog(null,"il faut selectionner une ligne !");
        }else{
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OffreDetails.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
//            
            OffreDetailsController odc = loader.getController();
            odc.setLNomEntreprise(NomEntCol.getCellData(index).toString());
            odc.setLDesc(descCol.getCellData(index).toString());
            odc.setLTitle(titreCol.getCellData(index).toString());
            odc.setLNomDomaine(NomDomaineCol.getCellData(index));
            odc.setLDatePub(datePubCol.getCellData(index).toString());
            odc.setLDateExp(dateExpCol.getCellData(index).toString());
            odc.setIdoffre(tableOffre.getItems().get(index).getId_offre());
            System.out.println(tableOffre.getItems().get(index).getId_offre());
            odc.setStage(stage);
//            

            stage.show();
           
        }


        
    }
      
    @FXML
    private void editTitre(CellEditEvent event) {
        int index = tableOffre.getSelectionModel().getSelectedIndex();
        int id = tableOffre.getItems().get(index).getId_offre();
        Offre f = sf.chercherOffreByID(id);
        f.setTitre(event.getNewValue().toString());
        sf.modifier(f);
        table();
    }

    @FXML
    private void editDesc(CellEditEvent event) {
        int index = tableOffre.getSelectionModel().getSelectedIndex();
        int id = tableOffre.getItems().get(index).getId_offre();
        Offre f = sf.chercherOffreByID(id);
        f.setDescription(event.getNewValue().toString());
        sf.modifier(f);
        table();
    }

    @FXML
    private void editDatePub(CellEditEvent event) {
//        int index = tableOffre.getSelectionModel().getSelectedIndex();
//        int id = tableOffre.getItems().get(index).getId_offre();
//        Offre f = sf.chercherOffreByID(id);
//        f.setDate_offre((Date) event.getNewValue());
//        sf.modifier(f);
//        table();
    }

    private void supprimer(MouseEvent event) {
        int index;
        index = tableOffre.getSelectionModel().getSelectedIndex();
        ServiceOffre sf = new ServiceOffre();
        if(index < 0){
            JOptionPane.showMessageDialog(null, "Selectionner un offre !");
        }else{
            int id = Integer.parseInt(String.valueOf(tableOffre.getItems().get(index).getId_offre())); 
            int result = JOptionPane.showConfirmDialog(null, "vouler vous supprimer ce offre ?","Confirmation",JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                
              sf.supprimerParId(id);
             JOptionPane.showMessageDialog(null, "offre supprimer !");
                
                table();
               
            }else{
                return ;
            }
        }
    }

   
    

    
    
}
