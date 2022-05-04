package com.example.wguscheduler_marywilliams.Database;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wguscheduler_marywilliams.DAO.AssessmentDao;
import com.example.wguscheduler_marywilliams.DAO.CourseDao;
import com.example.wguscheduler_marywilliams.DAO.TermDao;
import com.example.wguscheduler_marywilliams.Entity.AssessmentEntity;
import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.Entity.TermEntity;

import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 3 , exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class SchedulerDatabase extends RoomDatabase {
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    private static volatile SchedulerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SchedulerDatabase getDatabase(final Context context){
        //System.out.println("inside db getDatabase");
        if(INSTANCE == null){
            synchronized (SchedulerDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDatabase.class, "scheduler_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    //System.out.println("inside db getdb ifInstance=null");
                }
            }
        }
        //System.out.println("Just before return instance");
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            //System.out.println("Inside room db callback onOpen");
            //To keep data through app restarts, comm
            databaseWriteExecutor.execute(() -> {
                //System.out.println("Inside room db callback onOpen execute");

                TermDao mTermDao = INSTANCE.termDao();
                CourseDao mCourseDao = INSTANCE.courseDao();
                AssessmentDao mAssessmentDao = INSTANCE.assessmentDao();

                /*
                //Start app clean every time - not needed if you only populate on create
                mAssessmentDao.deleteAllAssessments();
                mCourseDao.deleteAllCourses();
                mTermDao.deleteAllTerms();

                //Starting data for the db - change at some point
                //Statuses - in progress, completed, dropped, plan to take
                //Spring 2020
                TermEntity term = new TermEntity(1, "Spring 2020", Date.valueOf("2020-01-01"),Date.valueOf("2020-06-30"));
                mTermDao.insertTerm(term);
                term = new TermEntity(2, "Fall 2020", Date.valueOf("2020-07-01"), Date.valueOf("2020-12-31"));
                mTermDao.insertTerm(term);



                CourseEntity course = new CourseEntity(1, 1, "Orientation - ORA1", Date.valueOf("2020-01-01"),Date.valueOf("2020-01-02"),"Completed", "Example course to get used to WGU.","", "", "", "", "", "");
                mCourseDao.insertCourse(course);
                course = new CourseEntity(2, 1, "IT Foundations - C393", Date.valueOf("2020-01-03"),Date.valueOf("2020-02-29"),"Completed", "First part of Comptia A+.",null, null, null, null, null, null);
                mCourseDao.insertCourse(course);
                course = new CourseEntity(3, 1, "IT Applications - C394", Date.valueOf("2020-03-01"),Date.valueOf("2020-04-30"),"Completed", "Second part of Comptia A+.",null, null, null, null, null, null);
                mCourseDao.insertCourse(course);
                course = new CourseEntity(4, 1, "Software Engineering - C188", Date.valueOf("2020-05-01"),Date.valueOf("2020-06-30"),"Completed", "Introduces the concepts of software engineering.",null, null, null, null, null, null);
                mCourseDao.insertCourse(course);
                course = new CourseEntity(5, 2, "Web Development Applications - C777", Date.valueOf("2020-07-01"),Date.valueOf("2020-08-31"),"Completed", "Teaches how to develop web documents and pages using the web development trifecta.",null, null, null, null, null, null);
                mCourseDao.insertCourse(course);
                course = new CourseEntity(6, 2, "Data Management Applications - C170", Date.valueOf("2020-09-01"),Date.valueOf("2020-10-31"),"Completed", "Conceptual data modeling and an introduction to MySQL.",null, null, null, null, null, null);
                mCourseDao.insertCourse(course);
                course = new CourseEntity(7, 2, "Ethics in Technology - C961", Date.valueOf("2020-11-01"),Date.valueOf("2020-12-31"),"Completed", "Examines the ethical considerations of technology in each of four categories: privacy, accuracy, property, and access.",null, null, null, null, null, null);
                mCourseDao.insertCourse(course);


                AssessmentEntity assessment = new AssessmentEntity(1, 1, "GOM1", "Performance Assessment", Date.valueOf("2020-01-02"), Date.valueOf("2020-01-02"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(2, 2, "KEV1", "Objective Assessment", Date.valueOf("2020-02-29"), Date.valueOf("2020-02-29"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(3, 3, "KFV1", "Objective Assessment", Date.valueOf("2020-04-30"), Date.valueOf("2020-04-30"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(4, 4, "NUP1", "Performance Assessment", Date.valueOf("2020-06-30"), Date.valueOf("2020-06-30"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(5, 5, "PKVO", "Pre-Assessment", Date.valueOf("2020-08-29"), Date.valueOf("2020-08-29"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(6, 5, "KVO1", "Objective Assessment", Date.valueOf("2020-08-31"), Date.valueOf("2020-08-31"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(7, 6, "PFJO", "Pre-Assessment", Date.valueOf("2020-10-27"), Date.valueOf("2020-10-27"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(8, 6, "FJO1", "Objective Assessment", Date.valueOf("2020-10-29"), Date.valueOf("2020-10-29"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(9, 6, "GSA1", "Objective Assessment", Date.valueOf("2020-10-31"), Date.valueOf("2020-10-31"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(10, 7, "PGWO", "Pre-Assessment", Date.valueOf("2020-12-29"), Date.valueOf("2020-12-29"));
                mAssessmentDao.insertAssessment(assessment);
                assessment = new AssessmentEntity(11, 7, "GWO1", "Objective Assessment", Date.valueOf("2020-12-31"), Date.valueOf("2020-12-31"));
                mAssessmentDao.insertAssessment(assessment);*/
            });

        }
    };
}
