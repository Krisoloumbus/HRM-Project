package com.nhom1.hrm.models;

public enum Education {
    Default("---SELECT---","---SELECT---"),
    UNIVERSITY("UNIVERSITY","UNIVERSITY"), 
    COLLEDGE("COLLEDGE","COLLEDGE");

    //Save as string type to DB and UI
    private final String toDB;
    private final String toUI;

    Education(String toDB, String toUI){
        this.toDB = toDB;
        this.toUI = toUI;
    }

    public String getCodeDB(){return toDB;};
    public String getCodeUI(){return toUI;};
    
    //To show Item in Combo Box selection
    @Override 
    public String toString(){
        return toUI;
    }
    
    public static Education fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (Education edu : values()) {
            if (edu.toDB.equals(toDB)) return edu;
        }
        return null;
    }
}
