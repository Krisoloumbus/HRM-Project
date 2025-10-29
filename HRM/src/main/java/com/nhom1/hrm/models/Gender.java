package com.nhom1.hrm.models;

public enum Gender {
    MALE("Male","Male"), 
    FEMALE("Female","Female");

    private final String toDB; // code to save in DB
    private final String toUI; // code to show item in combo box

    Gender(String toDB, String toUI){
        this.toDB = toDB;
        this.toUI = toUI;
    }

    public String getCodeDB(){return toDB;};
    public String getCodeUI(){return toUI;};
    
    @Override 
    public String toString(){
        return toUI;
    } // combo box show item
    
    public static Gender fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (Gender gender : values()) {
            if (gender.toDB.equals(toDB)) return gender;
        }
        return null;
    }
}
