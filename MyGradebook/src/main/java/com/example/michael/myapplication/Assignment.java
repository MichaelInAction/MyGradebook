package com.example.michael.myapplication;


/**
 * Defines the attributes of an Assignment
 * @author Michael Read
 * @version 15/6/15
 */
public class Assignment {

    private String name;
    private String due;
    private int pointsRecieved;
    private int pointsTotal;
    private String course;

    /**
     * Constructor for an Assignment
     * @param name the name of the assignment
     * @param due the due date of the assignment
     * @param pointsR the points recieved on the assignments
     * @param pointsT the total possible number of points on the assignment
     * @param course the name of the course
     */
    public Assignment(String name, String due, int pointsR, int pointsT, String course)
    {
        this.name = name;
        this.due = due;
        pointsRecieved = pointsR;
        pointsTotal = pointsT;
        this.course = course;
    }

    /**
     * Constructor for an Assignment
     * @param str the string representation of the Assignment from the file created in the MainActivity
     */
    public Assignment(String str)
    {
        String[] list = str.split("::");
        name = list[0];
        due = list[1];
        pointsRecieved = Integer.parseInt(list[2]);
        pointsTotal = Integer.parseInt(list[3]);
        course = list[4];
    }

    /**
     * returns the name of the assignment
     * @return the name of the assignment
     */
    public String getName()
    {
        return name;
    }

    /**
     * returns the due date of the assignment
     * @return the due date of the assignment
     */
    public String getDue()
    {
        return due;
    }

    /**
     * returns the points recieved on the assignment
     * @return the points recieved on the assignment
     */
    public int getPointsRecieved()
    {
        return pointsRecieved;
    }

    /**
     * returns the total points on the assignment
     * @return the total points on the assignment
     */
    public int getPointsTotal()
    {
        return pointsTotal;
    }

    /**
     * returns the course the assignment is from
     * @return the course the assignment is from
     */
    public String getCourse()
    {
        return course;
    }

    /**
     * returns the state of the Assignment
     * @return the state of the Assignment
     */
    public String toString() {
        return name + ", Due: " + due + " " + ((double) pointsRecieved / pointsTotal) * 100 + "%";
    }

    /**
     * returns the state of the assignment in the format stored in the file from the MainActivity
     * @return the state of the assignment
     */
    public String toFile() {
        return name + "::" + due + "::" + pointsRecieved + "::" + pointsTotal + "::" + course;
    }


}


