package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter number of Subjects: ");
        int number_of_subjects=Integer.parseInt(scanner.nextLine());
        System.out.println();
        ArrayList<Subject> subjects = new ArrayList<>();
        for(int i = 0; i <number_of_subjects; i++){
            System.out.print("Enter Subject Name: ");
            String subName=scanner.nextLine();
            System.out.println();
            System.out.print("Enter Number Of Groups for "+subName+": ");
            int number_of_groups=Integer.parseInt(scanner.nextLine());
            System.out.println();
            System.out.print("Enter Number of Periods for Subject: ");
            int number_of_periods = Integer.parseInt(scanner.nextLine());
            System.out.println();
            Subject mySubject= new Subject(subName,number_of_groups,number_of_periods);
            subjects.add(mySubject);
        }
        scanner.close();
      ArrayList<Schedule> schds= generateValidSchedules(subjects);
       schds.sort(Comparator.comparingInt(o -> o.numberOfDays));
        for(Schedule schedule:schds){
            System.out.println(schedule+"\n");
        }

    }

    public static ArrayList<Schedule> generate(ArrayList<Subject> subjects){

        calculateFlipCycles(subjects);
        int numberOfPossibleSchedules = 1;
        for(Subject sub: subjects){
            numberOfPossibleSchedules *= sub.number_of_groups;
        }
        ArrayList<Schedule> schedules= new ArrayList<>(numberOfPossibleSchedules);
        for(int i=0;i<numberOfPossibleSchedules;i++){
            schedules.add(new Schedule(subjects.size()));
        }

        for(int i = 0; i < subjects.size();i++){

            int flipCycle=subjects.get(i).flipCycle;
            int groupNumber=0;
            for(int j = 0 ; j<numberOfPossibleSchedules;j++){
                schedules.get(j).subjects.add(subjects.get(i).groups[groupNumber]);
                if((j+1)%flipCycle==0)
                {
                    groupNumber++;
                }
                if(groupNumber>=subjects.get(i).number_of_groups){
                    groupNumber=0;
                }
            }
        }
        return schedules;

    }

    public static void printSchedules(ArrayList<Schedule> sds){
        System.out.println("Printing All Possible Schedules");
        int counter=1;


        for (Schedule sd: sds) {
            System.out.print("#"+counter+": ");
            counter++;
                      for (SubjectGroup sg: sd.subjects) {
                System.out.print(sg.groupNumber+"  ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------");
    }

    public static void printInValidSchedules(ArrayList<Schedule> sds){
        int counter=1;
        System.out.println("Printing inValid Schedules");
        for (Schedule sd: sds) {

            if(!sd.isValid)
            {
                System.out.print("#"+counter+": ");
                counter++;
                for (SubjectGroup sg: sd.subjects) {

                    System.out.print(sg.groupNumber+"  ");

                }
                System.out.println();
            }

        }
        System.out.println("---------------------------------");
    }

    public static int printValidSchedules(ArrayList<Schedule> sds){
        int counter=1;
        System.out.println("Printing Valid Schedules");
        for (Schedule sd: sds) {

            if(sd.isValid)
            {
                System.out.print("#"+String.valueOf(counter)+": ");
                counter++;
                for (SubjectGroup sg: sd.subjects) {

                    System.out.print(sg.groupNumber+"  ");

                }
                System.out.println();
            }

        }
        System.out.println("---------------------------------");
        return counter;
    }

    public static void calculateFlipCycles(ArrayList<Subject> subjects){
        int[] res  =  new int[subjects.size()];
        int index = subjects.size()-1;
        res[index]= 1;
        for (int i = index-1; i>=0;i--) {
            res[i]=res[i+1]* subjects.get(i+1).groups.length;
            subjects.get(i).flipCycle=subjects.get(i+1).flipCycle*subjects.get(i+1).groups.length;
        }
        System.out.print("The flip Cycles Are: ");
        for(int i=0;i<subjects.size();i++){
            System.out.print(subjects.get(i).flipCycle+",");

        }
        System.out.println("----------------------\n");
    }

    public static int[] husseinConflictMethod(Schedule schd){
        if(schd.isValid){
            int[] dayPeriods= new int [72];
            for(int khra=0;khra<72;khra++){dayPeriods[khra]=-1;}
            for(int khoro =0;khoro<6;khoro++){
                schd.days[khoro]=false;
            }
            for(int i=0;i<schd.subjects.size();i++){

                SubjectGroup subj=schd.subjects.get(i);
                for(int j: subj.periods){
                    schd.days[j/12]=true;
                    if(dayPeriods[j]!=-1){
                        schd.isValid=false;
                        return new int[]{dayPeriods[j],i};
                    }
                    else{
                        dayPeriods[j]= i;
                    }
                }
            }
            for(boolean day:schd.days)
            {
                if(day)
                {
                    schd.numberOfDays++;
                }

            }
        }

        return new int[] {-1,-1};
}


    public static void invalidElimination(ArrayList<Schedule> schds,ArrayList<Subject> subjects){
        int[] errorIndexes;
        for(int i=0;i<schds.size();i++){
            errorIndexes=husseinConflictMethod(schds.get(i));
            if(errorIndexes[1]!=-1){
               int Gb=errorIndexes[0];
               int Gs= errorIndexes[1];
                for(int j=i;j<schds.size();j+=subjects.get(Gb).flipCycle*subjects.get(Gb).number_of_groups){
                    for(int l=j;(l-j)<subjects.get(Gb).flipCycle;l+=subjects.get(Gs).flipCycle*subjects.get(Gs).number_of_groups){
                        for(int k=l;(k-l)<subjects.get(Gs).flipCycle;k++){
                            schds.get(k).isValid=false;
                        }
                    }

                }
            }
        }
    }

    public static ArrayList<Schedule> generateValidSchedules(ArrayList<Subject> subjects){
        ArrayList<Schedule> schedules=generate(subjects);

        printSchedules(schedules);
        invalidElimination(schedules,subjects);
        int size =printValidSchedules(schedules);
        printInValidSchedules(schedules);

        ArrayList<Schedule> validSchedules= new ArrayList<>(size);
        for(Schedule schedule :schedules){
            if(schedule.isValid){
                validSchedules.add(schedule);
            }
        }
        return validSchedules;
    }
}
