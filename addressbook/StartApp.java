package addressbook;

import addressbook.model.Person;
import addressbook.model.PersonListWrapper;
import addressbook.resources.PersonData;
import addressbook.view_controller.PersonEditDialogController;
import addressbook.view_controller.PersonOverviewController;
import addressbook.view_controller.RootLayoutController;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Giuseppe Serra
 */
public class StartApp extends Application {
   
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Person> personList= FXCollections.observableArrayList(); 
    private Preferences pref;
    
    public StartApp(){
        personList=PersonData.getPersonData();
    }
    
    @Override
    public void start(Stage primaryStage)  {
        
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Adress Book");
         
        initRootLayout();
        
        showPersonOverview();
    }

    /**
     * @param args the command line arguments;
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Inizialize root layout
     */
    public  void initRootLayout()  {
       try{
           //load layout from fxml file
           FXMLLoader loader=new FXMLLoader();
           loader.setLocation(StartApp.class
                            .getResource("view_controller/RootLayout.fxml"));
           rootLayout = (BorderPane) loader.load();
           
           // Show the scene containing the root layout.
           Scene scene = new Scene(rootLayout);
           primaryStage.setScene(scene);
           primaryStage.getIcons().add(new Image(StartApp.class
                    .getResourceAsStream("resources/images/addressBook.png")));
           primaryStage.setResizable(false);
           primaryStage.show();
           
           //Give the controller acces to Start App.
           RootLayoutController controller = loader.getController();
           controller.setInitialize(this);
           // Try to load last opened person file.
           if(pref != null){
            File file = getPersonFilePath();
             if (file != null) {
               loadPersonDataFromFile(file);}
           }
        } catch (IOException e) {
          e.printStackTrace();
        
       
        }  
    } 
    
/**
* Shows the person overview inside the root layout;
*/
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class
                    .getResource("view_controller/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setPersonList(this);
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    /**
     * Returns the main Stage;
     * @return primaryStage;
     */ 
    public Stage getPrimaryStage(){
        return primaryStage;
    }
    
    /**
     * Returns ObservableList.
     * @return person data;
     */ 
    public ObservableList<Person> getPersonList(){
        return personList;
    }

    /**
    * Opens a dialog to edit details for the specified person. If the user
    * clicks OK, the changes are saved into the provided person object and true
    * is returned.
    *
    * @param person the person object to be edited
    * @return true if the user clicked OK, false otherwise.
    */
    public boolean showPersonEditDialog(Person person){
        try{
            //load the fxml file and create stage for popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartApp.class
                    .getResource("view_controller/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            //Create a Dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
             return controller.isOkCliked();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
    * Returns the person file preference, i.e. the file that was last opened.
    * The preference is read from the OS specific registry. If no such
    * preference can be found, null is returned.
    *
    * @return new file or null;
    */
    public File getPersonFilePath(){
        pref=Preferences.userNodeForPackage(StartApp.class);
        String filePath=pref.get("filePath", null);
        if(filePath!=null){
            return new File(filePath);
        }else{
            return null;
        }
        
    }
    
    /**
    * Sets the file path of the currently loaded file. The path is persisted in
    * the OS specific registry.
    *
    * @param file the file or null to remove the path
    */
    public void setPersonFilePath(File file){
       pref=Preferences.userNodeForPackage(StartApp.class);
       if(file!=null){
           pref.put("filePath", file.getPath());
           //update the stage title.
           primaryStage.setTitle("Address Book - "+file.getName());
        }else{
           pref.remove("filePath");
           //update the stage title.
           primaryStage.setTitle("Adress Book");
       }
    }
    
    /**
    * Loads person data from the specified file. The current person
    * data will  be replaced.
    * @param file load;
    */
    public void loadPersonDataFromFile(File file){
        try{
            JAXBContext context = 
                              JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um=context.createUnmarshaller();
            
            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper=(PersonListWrapper)um.unmarshal(file);
            personList.clear();
            personList.addAll(wrapper.getPersons());
            
            // Save the file path to the registry.
            this.setPersonFilePath(file);
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n"
                                                             + file.getPath());
            alert.showAndWait(); 
        }
    }
        
    /**
    * Saves the current person data to the specified file.
    * @param file  to save current data;
    */
    public void savePersonDataToFile(File file){
        try{
           JAXBContext context=
                              JAXBContext.newInstance(PersonListWrapper.class);
           Marshaller m = context.createMarshaller();
           m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

           // Wrapping our person data.
           PersonListWrapper wrapper = new PersonListWrapper();
           wrapper.setPersons(personList);

           // Marshalling and saving XML to the file.
           m.marshal(wrapper, file);
           // Save the file path to the registry.
           this.setPersonFilePath(file);

        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" +
                                                           file.getPath());
            alert.showAndWait();
        }
    }
}        