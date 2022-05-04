package com.example.wguscheduler_marywilliams.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.wguscheduler_marywilliams.DAO.AssessmentDao;
import com.example.wguscheduler_marywilliams.DAO.CourseDao;
import com.example.wguscheduler_marywilliams.DAO.TermDao;
import com.example.wguscheduler_marywilliams.Entity.AssessmentEntity;
import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.Entity.TermEntity;

import java.util.List;

public class SchedulerRepository {
    private TermDao mTermDao;
    private CourseDao mCourseDao;
    private AssessmentDao mAssessmentDao;

    private List<TermEntity> mAllTerms;
    private List<CourseEntity> mAllCourses;
    private List<CourseEntity> mAssosiatedCourses;
    private List<AssessmentEntity> mAllAssessments;
    private List<AssessmentEntity> mAssociatedAssessments;

    private TermEntity mTerm;
    private CourseEntity mCourse;
    private AssessmentEntity mAssessment;

    //Do I ever define associated courses with a term and associated assessments with a course in the repository?? -- constructor?

    public SchedulerRepository(Application application){
        SchedulerDatabase db = SchedulerDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mCourseDao = db.courseDao();
        mAssessmentDao = db.assessmentDao();

        //mAllTerms = mTermDao.getAllTerms();

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Get/Queries
    public List<TermEntity> getAllTerms(){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAllTerms = mTermDao.getAllTerms();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mAllTerms;
    }

    public TermEntity getTerm(int termId){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mTerm = mTermDao.getTerm(termId);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mTerm;
    }

    public List<CourseEntity> getAllCourses(){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAllCourses = mCourseDao.getAllCourses();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mAllCourses;
    }

    public List<CourseEntity> getCoursesByTerm(int termId){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAssosiatedCourses = mCourseDao.getCoursesByTerm(termId);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mAssosiatedCourses;
    }

    public CourseEntity getCourse(int courseId){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mCourse = mCourseDao.getCourse(courseId);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mCourse;
    }


    public List<AssessmentEntity> getAllAssessments(){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDao.getAllAssessments();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mAllAssessments;
    }

    public List<AssessmentEntity> getAssessmentsByCourse(int courseId){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAssociatedAssessments = mAssessmentDao.getAssessmentsByCourse(courseId);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mAssociatedAssessments;
    }

    public AssessmentEntity getAssessment(int assessmentId){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAssessment = mAssessmentDao.getAssessment(assessmentId);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        return mAssessment;
    }

    //Inserts
    public void insertTerm(TermEntity term){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.insertTerm(term);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insertCourse(CourseEntity course){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.insertCourse(course);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insertAssessment(AssessmentEntity assessment){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.insertAssessment(assessment);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Updates
    public void updateTerm(TermEntity term){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.updateTerm(term);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updateCourse(CourseEntity course){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.updateCourse(course);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updateAssessment(AssessmentEntity assessment){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.updateAssessment(assessment);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //Deletes
    public void deleteTerm(TermEntity term){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.deleteTerm(term);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteAllTerms(){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.deleteAllTerms();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteCourse(CourseEntity course){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.deleteCourse(course);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteAllCourses(){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.deleteAllCourses();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteAssessment(AssessmentEntity assessment){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.deleteAssessment(assessment);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteAllAssessments(){
        SchedulerDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.deleteAllAssessments();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
