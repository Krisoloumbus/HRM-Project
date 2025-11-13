package com.nhom1.hrm.Models;

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

    //Save as string type to DB and UI
    private final String toDB;
    private final String toUI;

    JobLevel(String toDB, String toUI){
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
    
    public static JobLevel fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (JobLevel lvl : values()) {
            if (lvl.toDB.equals(toDB)) return lvl;
        }
        return null;
    }
}