package com.example.micro1;

public class StudentGrade {
    private int id;
    private String studentName;
    private String rollNumber;
    private String subject;
    private double assessmentMarks;
    private double midtermMarks;
    private double finalMarks;
    private double totalMarks;
    private double percentage;
    private String letterGrade;
    private double gpa;
    private String performanceStatus;

    public StudentGrade() {}

    public StudentGrade(int id, String studentName, String rollNumber, String subject,
                        double assessmentMarks, double midtermMarks, double finalMarks,
                        double totalMarks, double percentage, String letterGrade,
                        double gpa, String performanceStatus) {
        this.id = id;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.subject = subject;
        this.assessmentMarks = assessmentMarks;
        this.midtermMarks = midtermMarks;
        this.finalMarks = finalMarks;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.letterGrade = letterGrade;
        this.gpa = gpa;
        this.performanceStatus = performanceStatus;
    }

    public StudentGrade(String studentName, String rollNumber, String subject,
                        double assessmentMarks, double midtermMarks, double finalMarks,
                        double totalMarks, double percentage, String letterGrade,
                        double gpa, String performanceStatus) {
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.subject = subject;
        this.assessmentMarks = assessmentMarks;
        this.midtermMarks = midtermMarks;
        this.finalMarks = finalMarks;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.letterGrade = letterGrade;
        this.gpa = gpa;
        this.performanceStatus = performanceStatus;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public double getAssessmentMarks() { return assessmentMarks; }
    public void setAssessmentMarks(double assessmentMarks) { this.assessmentMarks = assessmentMarks; }

    public double getMidtermMarks() { return midtermMarks; }
    public void setMidtermMarks(double midtermMarks) { this.midtermMarks = midtermMarks; }

    public double getFinalMarks() { return finalMarks; }
    public void setFinalMarks(double finalMarks) { this.finalMarks = finalMarks; }

    public double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(double totalMarks) { this.totalMarks = totalMarks; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public String getLetterGrade() { return letterGrade; }
    public void setLetterGrade(String letterGrade) { this.letterGrade = letterGrade; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getPerformanceStatus() { return performanceStatus; }
    public void setPerformanceStatus(String performanceStatus) { this.performanceStatus = performanceStatus; }
}