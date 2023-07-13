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
 * InterfaceOffreUserController
 */
public class InterfaceOffreUserController implements Initializable {

    @FXML
    private TableView<ServiceOffre.OffreView> tableOffre;
    @FXML
    private TableColumn<ServiceOffre.OffreView, String> titreCol;
    @FXML
    private TableColumn<ServiceOffre.OffreView, String> descCol;
    @FXML
    private TableColumn<ServiceOffre.OffreView,Date > datePubCol;
    @FXML
    private TableColumn<ServiceOffre.OffreView, Date> dateExpCol;

    @FXML
    private TableColumn<ServiceOffre.OffreView, String> NomDomaineCol;
    @FXML
    private Button btnAff;
    @FXML
    private TableColumn<ServiceOffre.OffreView, String> NomEntCol;
    
    ServiceOffre sf = new ServiceOffre();
    @FXML
    private ComboBox<String> ComboChercher;
    Integer index;
    String domaineRechercher = "";
    ObservableList<ServiceOffre.OffreView> listOffres;
    @FXML
    private TableColumn btnCol;
    private int iduser;

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println( " id user offre ==== "+iduser);
        ServiceDomaineO sd = new ServiceDomaineO();
        ComboChercher.getItems().add("All");
        ComboChercher.getItems().addAll(sd.getDomainesName());

        table();
    }    

     @FXML
    private void changeRecherche(ActionEvent event) {
        domaineRechercher = ComboChercher.getSelectionModel().getSelectedItem();
        System.out.println(domaineRechercher);
        table();
    }


 
    public void table(){
        if(domaineRechercher.equals("") || domaineRechercher.equals("All")){
        listOffres  = observableArrayList(sf.afficherOffres());
        }else{
            listOffres = observableArrayList(sf.afficherOffresByDomaine(domaineRechercher));
         }

        NomEntCol.setCellValueFactory(new PropertyValueFactory<ServiceOffre.OffreView,String>("nomEntreprise"));
        titreCol.setCellValueFactory(new PropertyValueFactory<ServiceOffre.OffreView,String>("titre"));
        descCol.setCellValueFactory(new PropertyValueFactory<ServiceOffre.OffreView,String>("description"));
        NomDomaineCol.setCellValueFactory(new PropertyValueFactory<ServiceOffre.OffreView,String>("nomDomaine"));
        datePubCol.setCellValueFactory(new PropertyValueFactory<ServiceOffre.OffreView,Date>("date_pub"));
        dateExpCol.setCellValueFactory(new PropertyValueFactory<ServiceOffre.OffreView,Date>("date_Exp"));
        
        
        Callback<TableColumn<ServiceOffre.OffreView,String>,TableCell<ServiceOffre.OffreView,String>> cellFactory =(param) -> {
            final TableCell<ServiceOffre.OffreView,String> cell = new TableCell<ServiceOffre.OffreView,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView show = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                        
                        show.setStyle(
                                "-fx-cursor:hand;"
                                + "-glyph-size:35px;"
                                + "-fx-fill:#00B489;"
                                + "-fx-alignement:center"
                                
                        );
                        
                        show.setOnMouseClicked((MouseEvent event) ->{
                        ServiceOffre.OffreView offre = getTableView().getItems().get(getIndex());
                            
                        index = tableOffre.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            JOptionPane.showMessageDialog(null,"il faut selectionner une ligne !");
        }else{ 
            try {
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
                odc.setIdu(iduser);
                odc.setStage(stage);
                stage.show();   
            } catch (IOException ex) {
                Logger.getLogger(InterfaceOffreUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
                        });
                        
                        
                        
                        

                        
                        HBox managebtn = new HBox(show);
                        managebtn.setStyle("-fx-alignement:center");
                    
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

   
    
}
