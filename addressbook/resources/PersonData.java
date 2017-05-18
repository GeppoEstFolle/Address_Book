package addressbook.resources;

import addressbook.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Giuseppe Serra
 */
public final class PersonData {
    
    private static final ObservableList<Person> personData
                                         = FXCollections.observableArrayList(); 
    
    private PersonData(){
             
    }
    
   
     
    
    public  static ObservableList<Person> getPersonData(){
         // Add some sample data
        personData.add(new Person("Antoine Laurent","de Lavoisier"));
        personData.add(new Person("Dmitrij Ivanovic", "Mendeleev"));
        personData.add(new Person("Rita", "Levi-Montalcini"));
        personData.add(new Person("John", "Dalton"));
        personData.add(new Person("Ernest", "Rutherford"));
        personData.add(new Person("Irene", "Jolit-Curie"));
        personData.add(new Person("Guglielmo", "Marconi"));
        personData.add(new Person("Marie", "Curie"));
        personData.add(new Person("Renato", "Cacciopoli"));
        personData.add(new Person("Albert", "Einstein"));  
        return personData;
    }
}
