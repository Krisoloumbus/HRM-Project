package com.nhom1.hrm.models;

public enum Department {
    Default("---SELECT---","---SELECT---"),
    HR("HR", "HR"), 
    IT("IT","IT"), 
    FINANCE("FINANCE","FINANCE"), 
    MARKETING("MARKETING","MARKETING"), 
    SALES("SALES","SALES"), 
    OPERATIONS("OPERATIONS","OPERATIONS"), 
    DESIGN("DESIGN","DESIGN");

    private final String toDB; // code to save in DB
    private final String toUI; // code to show item in combo box

    Department(String toDB, String toUI){
        this.toDB = toDB;
        this.toUI = toUI;
    }

    public String getCodeDB(){return toDB;};
    public String getCodeUI(){return toUI;};
    
    @Override 
    public String toString(){
        return toUI;
    } // combo box show item
    
    public static Department fromCodeToDB(String toDB){
        if (toDB == null) return null;
        for (Department dept : values()) {
            if (dept.toDB.equals(toDB)) return dept;
        }
        return null;
    }
}
