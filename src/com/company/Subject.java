package com.company;

import java.util.Scanner;

public class Subject {
   public String name;
   public SubjectGroup[] groups;
   public int number_of_periods;
   public int number_of_groups;
   public int flipCycle=1;

    public Subject(String name, int number_of_groups, int number_of_periods) {
        Scanner scanner= new Scanner(System.in);

        while(number_of_groups<0){
            System.out.println("Please enter a valid number of groups");
            number_of_groups=Integer.parseInt(scanner.nextLine());
        }


        while(number_of_periods<0){
            System.out.println("Please enter a valid number of groups");
            number_of_periods=Integer.parseInt(scanner.nextLine());
        }
        this.name = name;
        this.number_of_groups= number_of_groups;
        this.number_of_periods= number_of_periods;
        this.groups=new SubjectGroup[number_of_groups];

        for(int i=0;i<number_of_groups;i++){

            int group_number=i+1;
            while(group_number<=0){
                System.out.println("Please enter a valid group number");
                group_number=Integer.parseInt(scanner.nextLine());
            }

            System.out.println();
        groups[i]= new SubjectGroup(group_number,number_of_periods,this);
        }



    }

    @Override
    public String toString() {
        String retuner="Subject name: "+this.name+"\nNumber Of Periods: "+Integer.toString(number_of_periods)+"\nNumber Of Groups: "+Integer.toString(groups.length);
        for(int i=0;i<number_of_groups;i++)
        {
            retuner+="\n"+groups[i];
        }

        return retuner;
    }
}