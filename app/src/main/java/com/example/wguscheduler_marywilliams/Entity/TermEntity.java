package com.example.wguscheduler_marywilliams.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_id")
    private int termId;

    @ColumnInfo(name = "term_name")
    private String termName;
    @ColumnInfo(name = "term_start")
    private Date termStart;
    @ColumnInfo(name = "term_end")
    private Date termEnd;


    public TermEntity(int termId, String termName, Date termStart, Date termEnd) {
        this.termId = termId;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermId() {
        return termId;
    }
    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }
    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStart() {
        return termStart;
    }
    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }
    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }
}
