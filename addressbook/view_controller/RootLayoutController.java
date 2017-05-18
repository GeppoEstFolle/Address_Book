package addressbook.view_controller;

import addressbook.StartApp;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Giuseppe Serra
 */
public class RootLayoutController  {

    @FXML
    private MenuItem newBookItem;
    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem saveAsItem;
    @FXML
    private MenuItem exitItem;
    @FXML
    private MenuItem aboutItem;
    
    private StartApp startApp;
    
    /**
    * Is called by the main application to give a reference  back to itself.
    *
    * @param startApp reference a personList;
    */
    public void setInitialize(StartApp startApp){
        this.startApp=startApp;
    }
    
    /**
     * Create a empty address book.
     * @param event click;
     */
    @FXML
    private void handleNewBook(ActionEvent event){
        startApp.getPersonList().clear();
        startApp.setPersonFilePath(null);
    }
    
    /**
     * Opens a FileChooser to let the user select an address book to load.
     * @param event click;
    */
    @FXML
    private void handleOpenBook(ActionEvent event){
        FileChooser fileChooser=new FileChooser();
        
        // Set extension filter
        FileChooser.ExtensionFilter exFilter= 
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(exFilter);
        
        // Show save file dialog
        File file=fileChooser.showOpenDialog(startApp.getPrimaryStage());
        if(file!=null){
            startApp.loadPersonDataFromFile(file);
        }    
    }
    
    /**
     * Saves the file to the person file that is currently open.
     * If there is no open file, the "save as" dialog is shown.
     * @param event click;
    */
    @FXML
    public void handleSave(ActionEvent event){
        File personFile= startApp.getPersonFilePath();
        if(personFile!=null){
            startApp.savePersonDataToFile(personFile);
        }else{
            handleSaveAs(event);
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs(ActionEvent event){
        FileChooser fileChooser=new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter exFilter= 
                new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(exFilter);
        
        // Show save file dialog
        File file=fileChooser.showOpenDialog(startApp.getPrimaryStage());
        if(file!=null){
            startApp.savePersonDataToFile(file);
        }
        
    }
    
    /**
     * Close the application.
     * @param event click;
     */
    @FXML
    public void handleClose(ActionEvent event){
        System.exit(0);
    }

   /**
     * Opens an about dialog.
    */
    @FXML
    private void handleAbout() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("AddressApp");
    alert.setHeaderText("About");
    alert.setContentText("Author: Giuseppe Serra \n "
                                    + "Web:https://github.com/GeppoEstFolle");
    alert.showAndWait();
    }   
}