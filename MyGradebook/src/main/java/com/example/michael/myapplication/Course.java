package com.example.michael.myapplication;

/**
 * Defines the attributes of a Course
 * @author Michael Read
 * @version 15/6/15
 */
public class Course {
    private String courseName;
    private String teacherName;
    private String term;

    /**
     * constructor for a Course
     * @param course the name of the course
     * @param teacher the name of the teacher
     * @param term the term of the course
     */
    public Course(String course, String teacher, String term)
    {
        courseName = course;
        teacherName = teacher;
        this.term = term;
    }

    /**
     * constructor for a course
     * @param str the string representation of the Course from the file created in the MainActivity
     */
    public Course(String str)
    {
        String[] list = str.split("::");
        courseName = list[0];
        teacherName = list[1];
        term = list[2];
    }

    /**
     * returns the name of the course
     * @return the name of the course
     */
    public String getCourse()
    {
        return courseName;
    }

    /**
     * returns the name of the teahcer
     * @return the name of the teacher
     */
    public String getTeacher()
    {
        return teacherName;
    }

    /**
     * returns the term of the course
     * @return the term of the course
     */
    public String getTerm()
    {
        return term;
    }

    /**
     * returns the state of the Course
     * @return the state of the Course
     */
    public String toString()
    {
        return courseName + ", " + teacherName + " Term: " + term;
    }

    /**
     * returns the state of the Course in the format stored in the file from the MainActivity
     * @return the state of the Course
     */
    public String toFile() {return courseName + "::" + teacherName + "::" + term;}

}



