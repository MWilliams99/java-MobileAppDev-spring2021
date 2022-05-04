package com.example.wguscheduler_marywilliams.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "course_table",
        foreignKeys = @ForeignKey(
                entity = TermEntity.class,
                parentColumns = "term_id",
                childColumns = "term_id_fk",
                onDelete = CASCADE
        ),
        indices = {@Index("term_id_fk")}
)
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int courseId;
    @ColumnInfo(name = "term_id_fk")
    private int termId;

    @ColumnInfo(name = "course_name")
    private String courseName;
    @ColumnInfo(name = "course_start")
    private Date courseStart;
    @ColumnInfo(name = "course_end")
    private Date courseEnd;
    @ColumnInfo(name = "course_status")
    private String courseStatus;
    @ColumnInfo(name = "course_notes")
    private String courseNotes;

    @ColumnInfo(name = "instructor1_name")
    private String instructor1Name;
    @ColumnInfo(name = "instructor1_phone")
    private String instructor1Phone;
    @ColumnInfo(name = "instructor1_email")
    private String instructor1Email;

    @ColumnInfo(name = "instructor2_name")
    private String instructor2Name;
    @ColumnInfo(name = "instructor2_phone")
    private String instructor2Phone;
    @ColumnInfo(name = "instructor2_email")
    private String instructor2Email;


    public CourseEntity(int courseId, int termId, String courseName, Date courseStart, Date courseEnd, String courseStatus, String courseNotes, String instructor1Name, String instructor1Phone, String instructor1Email, String instructor2Name, String instructor2Phone, String instructor2Email) {
        this.courseId = courseId;
        this.termId = termId;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseNotes = courseNotes;
        this.instructor1Name = instructor1Name;
        this.instructor1Phone = instructor1Phone;
        this.instructor1Email = instructor1Email;
        this.instructor2Name = instructor2Name;
        this.instructor2Phone = instructor2Phone;
        this.instructor2Email = instructor2Email;
    }

    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTermId() {
        return termId;
    }
    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStart() {
        return courseStart;
    }
    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }
    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }
    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseNotes() {
        return courseNotes;
    }
    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public String getInstructor1Name() {
        return instructor1Name;
    }
    public void setInstructor1Name(String instructor1Name) {
        this.instructor1Name = instructor1Name;
    }

    public String getInstructor1Phone() {
        return instructor1Phone;
    }
    public void setInstructor1Phone(String instructor1Phone) {
        this.instructor1Phone = instructor1Phone;
    }

    public String getInstructor1Email() {
        return instructor1Email;
    }
    public void setInstructor1Email(String instructor1Email) {
        this.instructor1Email = instructor1Email;
    }

    public String getInstructor2Name() {
        return instructor2Name;
    }
    public void setInstructor2Name(String instructor2Name) {
        this.instructor2Name = instructor2Name;
    }

    public String getInstructor2Phone() {
        return instructor2Phone;
    }
    public void setInstructor2Phone(String instructor2Phone) {
        this.instructor2Phone = instructor2Phone;
    }

    public String getInstructor2Email() {
        return instructor2Email;
    }
    public void setInstructor2Email(String instructor2Email) {
        this.instructor2Email = instructor2Email;
    }
}
