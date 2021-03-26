package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Schedule {
    boolean isValid=true;
    public ArrayList<SubjectGroup> subjects = new ArrayList<>();
    Boolean[] days = new Boolean[6];
    int numberOfDays=0;
    int numberOfSubjects = 0;
    public Schedule(int  noOfSubj){
        numberOfSubjects = noOfSubj;
    }

    @Override
    public String toString() {
        String myString="";
        for(SubjectGroup subj: subjects){
            myString+=subj.toString()+"\n";
        }
        myString+="Days In College: "+numberOfDays;
        return myString;
    }

    public boolean hasConflict(){
        
        for (int i = 0; i < subjects.size(); i++) {


        }
        return false;
    }

    public  short offDays(){
        return (short) Arrays.stream(days)
                                .filter(o-> o)
                                .count();
    }
}
