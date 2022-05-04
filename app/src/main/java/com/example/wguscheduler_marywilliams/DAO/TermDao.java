package com.example.wguscheduler_marywilliams.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguscheduler_marywilliams.Entity.TermEntity;

import java.util.List;

@Dao
public interface TermDao {
    @Query("SELECT * FROM term_table ORDER BY term_id")
    List<TermEntity> getAllTerms();

    @Query("SELECT * FROM term_table WHERE term_id = :termId ORDER BY term_id")
    TermEntity getTerm(int termId);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Insert
    void insertTerm(TermEntity term);

    @Update
    void updateTerm(TermEntity term);

    @Delete
    void deleteTerm(TermEntity term);
}
