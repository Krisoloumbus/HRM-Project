package com.nhom1.hrm.Models;

public enum Department {
    Default("---SELECT---","---SELECT---"),
    HR("HR", "HR"), 
    IT("IT","IT"), 
    FINANCE("FINANCE","FINANCE"), 
    MARKETING("MARKETING","MARKETING"), 
    SALES("SALES","SALES"), 
    OPERATIONS("OPERATIONS","OPERATIONS"), 
    DESIGN("DESIGN","DESIGN");

    //Save as string type to DB and UI
    private final String toDB;
    private final String toUI;

    Department(String toDB, String toUI){
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
    
    public static Department fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (Department dept : values()) {
            if (dept.toDB.equals(toDB)) return dept;
        }
        return null;
    }
}
