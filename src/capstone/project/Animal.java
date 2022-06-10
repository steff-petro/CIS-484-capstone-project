/*
This is the Animal class
 */
package capstone.project;

// Imports



public class Animal {

    // Class variables and class array list
    private String animalId;
    private String animalName;
    private String animalType;
    private int animalAge;

    // Default Constructor
    public Animal() {

        this.animalId = "Default Animal ID";
        this.animalName = "Default Animal Name";
        this.animalType = "Default Animal Type";
        this.animalAge = 0;

    }

    // Overriden constructor
   public Animal(String animalId, String animalName, String animalType, int animalAge) {
        setAnimalId(animalId);
        setAnimalName(animalName);
        setAnimalType(animalType);
        setAnimalAge(animalAge);

    }

    // @return the animalId
    public String getAnimalId() {
        return animalId;
    }

    // @param animalId the animalID to set
    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    // @return the animalName
    public String getAnimalName() {
        return animalName;
    }

    // @param animalName the animalName to set
    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    // @return the animalType
    public String getAnimalType() {
        return animalType;
    }

    // @param animalType the animalType to set
    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    // @return the animalAge
    public int getAnimalAge() {
        return animalAge;
    }

    // @param animalAge the animalAge to set
    public void setAnimalAge(int animalAge) {
        this.animalAge = animalAge;
    }

}
