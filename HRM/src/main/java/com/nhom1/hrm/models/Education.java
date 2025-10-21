package com.nhom1.hrm.models;

public enum Education {
    UNIVERSITY("UNIVERSITY","UNIVERSITY"), 
    COLLEDGE("COLLEDGE","COLLEDGE");
        
    private final String toDB; // code to save in DB
    private final String toUI; // code to show item in combo box

    Education(String toDB, String toUI){
        this.toDB = toDB;
        this.toUI = toUI;
    }

    public String getCodeDB(){return toDB;};
    public String getCodeUI(){return toUI;};
    
    @Override 
    public String toString(){
        return toUI;
    } // combo box show item
    
    public static Education fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (Education edu : values()) {
            if (edu.toDB.equals(toDB)) return edu;
        }
        return null;
    }
}
