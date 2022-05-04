package com.example.wguscheduler_marywilliams.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler_marywilliams.Entity.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessment_table ORDER BY assessment_id")
    List<AssessmentEntity> getAllAssessments();

    @Query("SELECT * FROM assessment_table WHERE course_id_fk = :courseId ORDER BY assessment_id")
    List<AssessmentEntity> getAssessmentsByCourse(int courseId);

    @Query("SELECT * FROM assessment_table WHERE assessment_id = :assessmentId")
    AssessmentEntity getAssessment(int assessmentId);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Insert
    void insertAssessment(AssessmentEntity assessment);

    @Update
    void updateAssessment(AssessmentEntity assessment);

    @Delete
    void deleteAssessment(AssessmentEntity assessment);
}
