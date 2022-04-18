package patients.model;

import static java.lang.System.out;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Models a Patient register.
 *
 * @version 1.0, 16/02/2021
 */
public class Patient {

    // Primary key.
    private int registerId;

    private int age;
    private String ageGroup; // accepted intervals: fins a 45, 45-49, 50-54, 55-59, 60-64, 65-69, més de 69
    private Map<String,String> classificationValues; // accepted terms: *, normal, osteopenia, osteoporosi
    private int weight; // kg
    private int height; // cm
    private double imc; // imc = kg / m**2
    private String classification; // accepted terms: normal, osteopenia, osteoporosi
    private int menarche; // age when first menstruation period occurred
    private boolean menopause;
    private String menopauseType; // accepted terms: no consta, natural, ovariectomia, histeroctomia, ambdues
    
    private String name;
    private String surnames;
    /** 
     * Gender can be: gen-wom, gen-man, or gen-oth.
     */
    private String gender;
    /** 
     * Blood Type can be: A, O, AB, B.
     */
    private String bloodType;
    /** 
     * RH can be +, -.
     */
    private char RH;

    
    // Empty constructor
    public Patient() {
        this.setClassificationValues();
    }
    
    public Patient(int registerId) {
        this.registerId = registerId;
        this.setClassificationValues();
    }
    
    public Patient(int registerId, String classification) {
        this.registerId = registerId;
        this.classification = classification;
        this.setClassificationValues();
    }

    // Full standard constructor
    public Patient(int registerId, int age, String ageGroup, int weight, int height, double imc, String classification, int menarche, boolean menopause, String menopauseType) {
        this.registerId = registerId;
        this.age = age;
        this.ageGroup = ageGroup;
        this.weight = weight;
        this.height = height;
        this.imc = imc;
        this.classification = classification;
        this.menarche = menarche;
        this.menopause = menopause;
        this.menopauseType = menopauseType;
        this.setClassificationValues();
    }

    // Constructor without ageGroup and imc parameters
    public Patient(int registerId, int age, int weight, int height, String classification, int menarche, boolean menopause, String menopauseType) {
        this.registerId = registerId;
        this.age = age;
        setAgeGroup(age);
        this.weight = weight;
        this.height = height;
        setImc(weight, height);
        this.classification = classification;
        this.menarche = menarche;
        this.menopause = menopause;
        this.menopauseType = menopauseType;
        this.setClassificationValues();
    }

    public Patient(String name, String surnames, String gender, String bloodType, char RH, int weight, int height) {
        this.name = name;
        this.surnames = surnames;
        this.gender = gender;
        this.bloodType = bloodType;
        this.RH = RH;
        this.weight = weight;
        this.height = height;
        this.setClassificationValues();
    }

    public String getMenopauseType() {
        return menopauseType;
    }

    public void setMenopauseType(String menopauseType) {
        this.menopauseType = menopauseType;
    }

    public int getRegisterId() {
        return registerId;
    }

    public void setRegisterId(int registerId) {
        this.registerId = registerId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    // Custom ageGroup setter
    public void setAgeGroup(int age) {
        // less than 45, 45-49, 50-54, 55-59, 60-64, 65-69, more than 69
        String ageGroup = "";
        if (age < 45) {
            ageGroup = "<45";
        } else if (age >= 45 && age < 50) {
            ageGroup = "45-49";
        } else if (age >= 50 && age < 55) {
            ageGroup = "50-54";
        } else if (age >= 55 && age < 60) {
            ageGroup = "55-59";
        } else if (age >= 60 && age < 65) {
            ageGroup = "60-64";
        } else if (age >= 65 && age < 70) {
            ageGroup = "65-69";
        } else {
            ageGroup = ">69";
        }
        this.ageGroup = ageGroup;
    }
    
    public void setClassificationValues() {
            Map<String,String> classificationValues = new LinkedHashMap<String,String>();
                classificationValues.put("*","---");
                classificationValues.put("NORMAL","NORMAL");
                classificationValues.put("OSTEOPENIA","OSTEOPENIA");
                classificationValues.put("OSTEOPOROSI","OSTEOPOROSI");
            this.classificationValues = classificationValues;
    }
    
    public Map<String,String> getClassificationValues() {
       if(this.classificationValues==null) {
            Map<String,String> classificationValues = new LinkedHashMap<String,String>();
                classificationValues.put("*","---");
                classificationValues.put("NORMAL","NORMAL");
                classificationValues.put("OSTEOPENIA","OSTEOPENIA");
                classificationValues.put("OSTEOPOROSI","OSTEOPOROSI");
            this.classificationValues = classificationValues;
       }
       return this.classificationValues;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    // Custom IMC setter
    public void setImc(int weight, int height) {
        this.imc = weight / Math.pow(height / 100.0, 2);
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getMenarche() {
        return menarche;
    }

    public void setMenarche(int menarche) {
        this.menarche = menarche;
    }

    public boolean isMenopause() {
        return menopause;
    }

    public void setMenopause(boolean menopause) {
        this.menopause = menopause;
    }

    @Override
    public String toString() {
        String menopauseString = this.menopause ? "SI" : "NO";
        return String.format("%d;%d;%s;%d;%d;%.2f;%s;%d;%s;%s;\n",
                registerId, age, ageGroup, weight, height, imc, classification, menarche, menopauseString, menopauseType);
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public char getRH() {
        return RH;
    }

    public void setRH(char RH) {
        this.RH = RH;
    }
}

