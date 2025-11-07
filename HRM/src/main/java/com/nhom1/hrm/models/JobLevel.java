package com.nhom1.hrm.models;

public enum JobLevel {
    Default("---SELECT---","---SELECT---"),
    INTERN("INTERN","INTERN"), 
    FRESHER("FRESHER","FRESHER"), 
    JUNIOR("JUNIOR","JUNIOR"), 
    MIDDLE("MIDDLE","MIDDLE"), 
    SENIOR("SENIOR","SENIOR"), 
    LEAD("LEAD","LEAD"), 
    MANAGER("MANAGER","MANAGER"), 
    DIRECTOR("DIRECTOR","DIRECTOR");

    private final String toDB; // code to save in DB
    private final String toUI; // code to show item in combo box

    JobLevel(String toDB, String toUI){
        this.toDB = toDB;
        this.toUI = toUI;
    }

    public String getCodeDB(){return toDB;};
    public String getCodeUI(){return toUI;};
    
    @Override 
    public String toString(){
        return toUI;
    } // combo box show item
    
    public static JobLevel fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (JobLevel lvl : values()) {
            if (lvl.toDB.equals(toDB)) return lvl;
        }
        return null;
    }
}