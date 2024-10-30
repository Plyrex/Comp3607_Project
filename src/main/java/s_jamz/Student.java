package s_jamz;

public class Student {
    public String studentID;
    public String studentName;
    public String assignmentNumber;

    public Student(String studentID, String studentName, String assignmentNumber) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.assignmentNumber = assignmentNumber;
    }
    public String getStudentID() {
        return studentID;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getAssignmentNumber() {
        return assignmentNumber;
    }
}
