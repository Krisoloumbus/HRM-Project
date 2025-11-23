package com.nhom1.hrm.Models;

public enum Gender {
    Default("---SELECT---","---SELECT---"),
    MALE("Male","Male"), 
    FEMALE("Female","Female");

    private final String toDB;
    private final String toUI;

    //Save as string type to DB and show in UI/Selection Box
    Gender(String toDB, String toUI){
        this.toDB = toDB;
        this.toUI = toUI;
    }

    public String getCodeDB(){return toDB;};
    public String getCodeUI(){return toUI;};

    //Show Item in Combo Box selection
    @Override 
    public String toString(){
        return toUI;
    }
    
    //Get and return DB string type back to enum
    public static Gender fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (Gender gender : values()) {
            if (gender.toDB.equals(toDB)) return gender;
        }
        return null;
    }
}
