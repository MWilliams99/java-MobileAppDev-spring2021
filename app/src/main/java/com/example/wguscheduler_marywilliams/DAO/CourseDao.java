package com.example.wguscheduler_marywilliams.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler_marywilliams.Entity.CourseEntity;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course_table ORDER BY course_id")
    List<CourseEntity> getAllCourses();

    @Query("SELECT * FROM course_table WHERE term_id_fk = :termId ORDER BY course_id")
    List<CourseEntity> getCoursesByTerm(int termId);

    @Query("SELECT * FROM course_table WHERE course_id = :courseId")
    CourseEntity getCourse(int courseId);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Insert
    void insertCourse(CourseEntity course);

    @Update
    void updateCourse(CourseEntity course);

    @Delete
    void deleteCourse(CourseEntity course);
}
