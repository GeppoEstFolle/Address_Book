package addressbook.view_controller;

import addressbook.StartApp;
import addressbook.model.Person;
import addressbook.resources.util.DateUtil;
import java.util.Comparator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Giuseppe Serra
 */
public class PersonOverviewController  {

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label eMailLabel;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private  StartApp startApp;
    private SortedList<Person> sortedData;
    private final Comparator<Person> HOTNESS_COMPARATOR = 
                                        (person, p) -> person.compareTo(p);
    private final ObjectProperty<Comparator<? super Person>> 
    HOTNESS_COMPARATOR_WRAPPER = new SimpleObjectProperty<>(HOTNESS_COMPARATOR);
    
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        // Initialize the person table with the two columns;
        firstNameColumn.setCellValueFactory(
                                      new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(
                                        new PropertyValueFactory<>("lastName"));
        //initializes TableView to sort;
        personTable.getSortOrder().addAll(firstNameColumn,lastNameColumn);
    }
    
    /**
     * Set a provvisorie data in a TableView.
     * @param startApp ObservableList 
     */
    public void setPersonList(StartApp startApp){
        this.startApp=startApp;
     //  Wrap the ObservableList in a FilteredList (initially display all data)
        FilteredList<Person> filteredData= new FilteredList<>(
                                             startApp.getPersonList(),p->true);
        //  Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((o,ov,nv)->{
            filteredData.setPredicate(person->{
                // If filter text is empty, display all persons.
                 if (nv == null || nv.isEmpty()) {
                 return true;
                 }
      // Compare first name and last name of every person with filter text.
                 String lowerCaseFilter = nv.toLowerCase();

                 if (person.getFirstName().toLowerCase()
                                                  .contains(lowerCaseFilter)) {
                     return true; // Filter matches first name.
                 } else if (person.getLastName().toLowerCase()
                                                   .contains(lowerCaseFilter)){
                     return true; // Filter matches last name.
                 }
                     return false; // Does not match.
            });
        });
        
        //  Wrap the FilteredList in a SortedList.
        sortedData = new SortedList<>(filteredData);
        //  Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(HOTNESS_COMPARATOR_WRAPPER);
        //Add sorted (and filtered) data to the table.
        personTable.setItems(sortedData);
        
        //Initilize label
        this.showPersonDetails(null);
       // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty()
                   .addListener((o,ov,nv)->showPersonDetails(nv));
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     */
    private void showPersonDetails(Person person){
        if(person!=null){
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            phoneLabel.setText(Integer.toString(person.getPhone()));
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            eMailLabel.setText(person.getEMail());
        }else{
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            phoneLabel.setText("");
            birthdayLabel.setText("");
            eMailLabel.setText("");
        }
    }
    
    /**
     * Called when the user click on the delete button.
     */
    @FXML
    private void handleDeletePerson(ActionEvent event){
       Person selectedPerson= personTable.getSelectionModel().getSelectedItem();
       if(selectedPerson!= null){
            startApp.getPersonList().remove(selectedPerson);
       } else{
           //Nothing Selected;
           Alert alert = new Alert(AlertType.WARNING);
           alert.initOwner(startApp.getPrimaryStage());
           alert.setTitle("No Selection");
           alert.setHeaderText("No Person Selected!");
           alert.setContentText("Please select a person in the table.");
           alert.showAndWait();
       }   
    }
    
    /**
     * Called when the user clicks the New button.Open a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson(ActionEvent event){
       Person tempPerson=new Person();
       boolean okClicked=startApp.showPersonEditDialog(tempPerson);
       if(okClicked){
           startApp.getPersonList().add(tempPerson);
       }
    }
    
    /**
     * Called when the user click edit button.
     * Opens a Dialog to edit detais for selected person.
     */
    @FXML
    private void handleEditPerson(ActionEvent event){
       Person selectedPerson= personTable.getSelectionModel().getSelectedItem();
       if(selectedPerson!=null){
           boolean okClicked = startApp.showPersonEditDialog(selectedPerson);
           if (okClicked) {
                showPersonDetails(selectedPerson);
            } else {
               // Nothing selected.
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(startApp.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Person Selected");
                alert.setContentText("Please select a person in the table.");
                alert.showAndWait();
           }
       }
    }
    
    @FXML
    public void handleSort(Event event){
         
       if(personTable.getSortOrder().size()== 0){
          // no sorting on column defined, use default comparator
            sortedData.comparatorProperty().bind(HOTNESS_COMPARATOR_WRAPPER);
          // TODO Not sure why this is needed
            personTable.setSortPolicy(param -> true);  
       }
    }
}