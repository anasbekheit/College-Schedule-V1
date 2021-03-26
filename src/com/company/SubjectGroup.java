package com.company;
import java.util.Scanner;

public class SubjectGroup {
    public int[] periods;
    public int groupNumber;
    Subject subject;

    @Override
    public String toString() {
        return  this.subject.name+" G"+
                this.groupNumber+" "
                ;
    }

    public SubjectGroup(int number, int number_of_periods, Subject subject) {
        this.subject =subject;
        this.groupNumber = number;
        periods= new int[number_of_periods];

        Scanner scanner  = new Scanner(System.in);
        int flag,day = 0,period=0;

        for (int i=0;i<number_of_periods;i++){
             flag=0;
            while(flag!=1)
            {
                 System.out.println("Enter the day of the period For "+this.subject.name+" Group: "+this.groupNumber);
                 day = Integer.parseInt(scanner.nextLine());
                //day = period = 1;
                 System.out.println("Enter the period number: "+this.subject.name+" Group: "+this.groupNumber);
                 period = Integer.parseInt(scanner.nextLine()) - 1;
                 if((((day * 12) + period) <= 71) && ((day * 12) + period)>=0){
                     flag=1;
                 }
                 else{
                     System.out.println("Please enter a valid period");
                 }
            }

            periods[i]=day*12+period;

        }
    }
}
