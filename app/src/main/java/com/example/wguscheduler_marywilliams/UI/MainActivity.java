package com.example.wguscheduler_marywilliams.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wguscheduler_marywilliams.Database.SchedulerRepository;
import com.example.wguscheduler_marywilliams.Entity.AssessmentEntity;
import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.Entity.TermEntity;
import com.example.wguscheduler_marywilliams.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.Date;

public class MainActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toTermList(View view){
        //System.out.println("toTermList button just inside");
        schedulerRepository = new SchedulerRepository(getApplication());
        schedulerRepository.getAllTerms();
        //System.out.println("toTermList button after open db?? before go to next activity");
        Intent intent = new Intent(MainActivity.this, TermListActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        schedulerRepository = new SchedulerRepository(getApplication());
        int itemId = item.getItemId();
        if(itemId == R.id.main_populate){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Populate Database")
                    .setMessage("Clear and populate the database with sample data for testing? (This will take about 30 seconds.)")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            schedulerRepository.deleteAllAssessments();
                            schedulerRepository.deleteAllCourses();
                            schedulerRepository.deleteAllTerms();

                            //TermEntity term = new TermEntity(1,"Spring 2020", Date.valueOf("2020-01-01"),Date.valueOf("2020-06-30"));
                            //schedulerRepository.insertTerm(term);
                            TermEntity term = new TermEntity(1,"Fall 2020", Date.valueOf("2020-09-01"),Date.valueOf("2020-02-28"));
                            schedulerRepository.insertTerm(term);
                            term = new TermEntity(2,"Spring 2021", Date.valueOf("2021-03-01"),Date.valueOf("2021-08-31"));
                            schedulerRepository.insertTerm(term);


                            /*CourseEntity course = new CourseEntity(1,1,"Orientation",Date.valueOf("2020-01-01"),Date.valueOf("2020-01-01"),"Completed","An example course to allow students to become familiar with WGU's competency-based education."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            AssessmentEntity assessment = new AssessmentEntity(1,1,"GOM1","Performance Assessment",Date.valueOf("2020-01-01"),Date.valueOf("2020-01-01"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(2,1,"IT Foundations",Date.valueOf("2020-01-02"),Date.valueOf("2020-01-25"),"Completed","The first course in a two-part series that prepares students for the CompTIA A+ exam."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(2,2,"KEV1","Objective Assessment",Date.valueOf("2020-01-24"),Date.valueOf("2020-01-25"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(3,1,"IT Applications",Date.valueOf("2020-01-27"),Date.valueOf("2020-02-12"),"Completed","The second course in a two-part series that prepares students for the CompTIA A+ exam."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(3,3,"KFV1","Objective Assessment",Date.valueOf("2020-02-11"),Date.valueOf("2020-02-12"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(4,1,"Software Engineering",Date.valueOf("2020-02-15"),Date.valueOf("2020-02-28"),"Completed","Introduces the concepts of software engineering, expanding on previous knowledge of basic programming and project management."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(4,4,"NUP1","Performance Assessment",Date.valueOf("2020-02-25"),Date.valueOf("2020-02-28"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(5,1,"Web Development Applications",Date.valueOf("2020-03-01"),Date.valueOf("2020-03-15"),"Completed","Teaches how to develop web documents using the web development trifecta: HTML5, CSS3, and JavaScript."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(5,5,"PKVO","Pre-Assessment",Date.valueOf("2020-03-10"),Date.valueOf("2020-03-10"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(6,5,"KVO1","Objective Assessment",Date.valueOf("2020-03-15"),Date.valueOf("2020-03-15"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(6,1,"Data Management Applications",Date.valueOf("2020-03-16"),Date.valueOf("2020-04-07"),"Completed","Covers conceptual data modeling and provides an introduction to MySQL."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(7,6,"GSA1","Objective Assessment",Date.valueOf("2020-04-02"),Date.valueOf("2020-04-02"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(8,6,"PFJO","Pre-Assessment",Date.valueOf("2020-04-05"),Date.valueOf("2020-04-05"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(9,6,"FJO1","Objective Assessment",Date.valueOf("2020-04-07"),Date.valueOf("2020-04-07"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(7,1,"Ethics in Technology",Date.valueOf("2020-04-10"),Date.valueOf("2020-04-28"),"Completed","Examines the ethical considerations of technology in four categories: privacy, accuracy, property, and access."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(10,7,"PGWO","Pre-Assessment",Date.valueOf("2020-04-26"),Date.valueOf("2020-04-26"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(11,7,"GWO1","Objective Assessment",Date.valueOf("2020-04-28"),Date.valueOf("2020-04-28"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(8,1,"Operating Systems for Programmers",Date.valueOf("2020-04-29"),Date.valueOf("2020-06-13"),"Completed","Teaches students how an operating system acts as an interface between applications and hardware."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(12,8,"PAG7","Pre-Assessment",Date.valueOf("2020-06-07"),Date.valueOf("2020-06-07"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(13,8,"ACO1","Objective Assessment",Date.valueOf("2020-06-13"),Date.valueOf("2020-06-13"));
                            schedulerRepository.insertAssessment(assessment);
                             */


                            CourseEntity course = new CourseEntity(1,1,"Software Quality Assurance",Date.valueOf("2020-09-01"),Date.valueOf("2020-09-20"),"Completed","Teaches students to apply a QA focus to every phase of the software development life cycle."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            AssessmentEntity assessment = new AssessmentEntity(1,1,"PDUO","Pre-Assessment",Date.valueOf("2020-09-17"),Date.valueOf("2020-09-17"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(2,1,"DUO1","Objective Assessment",Date.valueOf("2020-09-20"),Date.valueOf("2020-09-20"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(2,1,"User Interface Design",Date.valueOf("2020-09-22"),Date.valueOf("2020-10-24"),"Completed","Covers tools and techniques for user interface design; including web and mobile applications."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(3,2,"FPV1","Objective Assessment",Date.valueOf("2020-10-23"),Date.valueOf("2020-10-24"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(3,1,"Software 1",Date.valueOf("2020-10-27"),Date.valueOf("2020-12-28"),"Completed","Builds object-oriented programming knowledge and introduces tools for Java application development."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(4,3,"QKM1","Performance Assessment",Date.valueOf("2020-12-25"),Date.valueOf("2020-12-28"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(4,1,"Software 2",Date.valueOf("2020-01-01"),Date.valueOf("2020-02-21"),"Completed","Refines object-oriented programming skills and covers database and file server application development skills."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(5,4,"QAM1","Performance Assessment",Date.valueOf("2020-02-19"),Date.valueOf("2020-02-21"));
                            schedulerRepository.insertAssessment(assessment);


                            course = new CourseEntity(5,2,"Mobile Application Development",Date.valueOf("2021-03-01"),Date.valueOf("2021-04-12"),"In progress","Introduces students to programming for mobile devices using a software development kit."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(6,5,"ABM2","Performance Assessment",Date.valueOf("2021-04-10"),Date.valueOf("2021-04-12"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(6,2,"Organizational Behavior and Leadership",Date.valueOf("2021-04-12"),Date.valueOf("2021-05-03"),"Plan to take","Introduces the underlying principles of culture, leadership, teamwork, and behavior in today's competitive global market."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(7,6,"PIBC","Pre-Assessment",Date.valueOf("2021-05-01"),Date.valueOf("2021-05-01"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(8,6,"IBC1","Objective Assessment",Date.valueOf("2021-05-03"),Date.valueOf("2021-05-03"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(7,2,"User Experience Design",Date.valueOf("2021-05-03"),Date.valueOf("2021-05-31"),"Plan to take","Explores multiple tools and techniques used in user experience design."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(9,7,"HJP1","Performance Assessment",Date.valueOf("2021-05-29"),Date.valueOf("2021-05-31"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(8,2,"Advanced Data Management",Date.valueOf("2021-05-31"),Date.valueOf("2021-06-30"),"Plan to take","Covers ways that advanced data management enables organizations to extract and analyze raw data to uncover trends, issues, and causes."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(10,8,"PLDV","Pre-Assessment",Date.valueOf("2021-06-27"),Date.valueOf("2021-06-27"));
                            schedulerRepository.insertAssessment(assessment);
                            assessment = new AssessmentEntity(11,8,"LDV1","Objective Assessment",Date.valueOf("2021-06-30"),Date.valueOf("2021-06-30"));
                            schedulerRepository.insertAssessment(assessment);

                            course = new CourseEntity(9,2,"Software Development Capstone",Date.valueOf("2021-07-01"),Date.valueOf("2021-08-13"),"Plan to take","This capstone assessment challenges students to demonstrate mastery of all the BSITSW program outcomes."
                                    ,"Example Instructor","(555)555-5555","instructor1@example.com","Sample Instructor","(555)555-5554","instructor2@example.com");
                            schedulerRepository.insertCourse(course);
                            assessment = new AssessmentEntity(12,9,"RYM2","Performance Assessment",Date.valueOf("2021-08-10"),Date.valueOf("2021-08-13"));
                            schedulerRepository.insertAssessment(assessment);
                        }
                    })
                    .setNegativeButton("No", null).show();
        }
        if(itemId == R.id.main_clear){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Clear Database")
                    .setMessage("Fully clear the database for testing?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            schedulerRepository.deleteAllAssessments();
                            schedulerRepository.deleteAllCourses();
                            schedulerRepository.deleteAllTerms();
                        }
                    })
                    .setNegativeButton("No", null).show();
        }

        return super.onOptionsItemSelected(item);
    }
}