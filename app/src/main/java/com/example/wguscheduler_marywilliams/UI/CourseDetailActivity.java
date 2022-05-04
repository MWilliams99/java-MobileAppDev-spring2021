package com.example.wguscheduler_marywilliams.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wguscheduler_marywilliams.Database.SchedulerRepository;
import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CourseDetailActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;

    public static int numAlert;
    Long date;

    private EditText courseTitleET;
    private EditText courseStartET;
    private EditText courseEndET;
    //private EditText courseStatusEditText;
    private EditText courseNotesET;
    private EditText courseInst1NameET;
    private EditText courseInst1PhoneET;
    private EditText courseInst1EmailET;
    private EditText courseInst2NameET;
    private EditText courseInst2PhoneET;
    private EditText courseInst2EmailET;

    private Spinner courseStatusSpinner;

    static int termId;
    private int courseId;

    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        schedulerRepository = new SchedulerRepository(getApplication());

        courseTitleET = findViewById(R.id.courseTitleEditText);
        courseStartET = findViewById(R.id.courseStartEditText); //Date
        courseEndET = findViewById(R.id.courseEndEditText);     //Date
        //courseStatusET = findViewById(R.id.courseStatusEditText);
        courseStatusSpinner = findViewById(R.id.spinner_CourseStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.course_status,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(adapter);

        courseNotesET = findViewById(R.id.courseNotesEditText);
        courseInst1NameET = findViewById(R.id.courseInst1Name);
        courseInst1PhoneET = findViewById(R.id.courseInst1Phone);
        courseInst1EmailET = findViewById(R.id.courseInst1Email);
        courseInst2NameET = findViewById(R.id.courseInst2Name);
        courseInst2PhoneET = findViewById(R.id.courseInst2Phone);
        courseInst2EmailET = findViewById(R.id.courseInst2Email);

        Intent intent = getIntent();
        termId = intent.getIntExtra("termId",-1);
        courseId = intent.getIntExtra("courseId",-1);

        if(courseId == -1){
            courseId = AssessmentListActivity.courseId;
        }

        //Testing output and logic flow
        //System.out.println(termId + " " + courseId);
        if(termId == -1){
            //System.out.println("Term id is -1 for SOME REASON??");
        }
        //End testing

        if(courseId != -1){
            CourseEntity course = schedulerRepository.getCourse(courseId);

            if(termId == -1){
                termId = course.getTermId();
            }

            courseName = course.getCourseName();

            courseTitleET.setText(course.getCourseName());
            //courseStatusET.setText(course.getCourseStatus());
            courseNotesET.setText(course.getCourseNotes());
            courseInst1NameET.setText(course.getInstructor1Name());
            courseInst1PhoneET.setText(course.getInstructor1Phone());
            courseInst1EmailET.setText(course.getInstructor1Email());
            courseInst2NameET.setText(course.getInstructor2Name());
            courseInst2PhoneET.setText(course.getInstructor2Phone());
            courseInst2EmailET.setText(course.getInstructor2Email());


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date courseStart = course.getCourseStart();
            courseStartET.setText(dateFormat.format(courseStart));

            Date courseEnd = course.getCourseEnd();
            courseEndET.setText(dateFormat.format(courseEnd));

            //STATUS SPINNER SELECTION HERE
            String courseStatus = course.getCourseStatus();
            //System.out.println(courseStatus);
            if(courseStatus.equalsIgnoreCase("Plan to take")){
                courseStatusSpinner.setSelection(0);
            }
            else if(courseStatus.equalsIgnoreCase("In progress")){
                courseStatusSpinner.setSelection(1);
            }
            else if(courseStatus.equalsIgnoreCase("Completed")){
                courseStatusSpinner.setSelection(2);
            }
            else if(courseStatus.equalsIgnoreCase("Dropped")){
                courseStatusSpinner.setSelection(3);
            }
        }
        else{
            findViewById(R.id.assessmentListButton).setVisibility(View.GONE);
            setTitle("Add Course");
        }
    }

    public void toAssessmentList(View view){
        Intent intent = new Intent(CourseDetailActivity.this, AssessmentListActivity.class);
        intent.putExtra("termId", termId);
        intent.putExtra("courseId",courseId);
        startActivity(intent);
    }

    public void saveCourse(View view){
        schedulerRepository = new SchedulerRepository(getApplication());

        String courseName = courseTitleET.getText().toString().trim(); //not null
        String courseStart = courseStartET.getText().toString().trim(); //not null
        String courseEnd = courseEndET.getText().toString().trim(); //not null
        //String courseStatus = courseStatusET.getText().toString().trim(); //not null
        String courseNotes = courseNotesET.getText().toString().trim();
        String courseInst1Name = courseInst1NameET.getText().toString().trim();
        String courseInst1Phone = courseInst1PhoneET.getText().toString().trim();
        String courseInst1Email = courseInst1EmailET.getText().toString().trim();
        String courseInst2Name = courseInst2NameET.getText().toString().trim();
        String courseInst2Phone = courseInst2PhoneET.getText().toString().trim();
        String courseInst2Email = courseInst2EmailET.getText().toString().trim();

        String courseStatus = courseStatusSpinner.getSelectedItem().toString().trim();
        //System.out.println(courseStatus);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date courseStartDate = new Date();
        Date courseEndDate = new Date();

        //Check that necessary fields are not left empty
        if(courseName.isEmpty() || courseStart.isEmpty() || courseEnd.isEmpty() || courseStatus.isEmpty()
            ||courseInst1Name.isEmpty()||courseInst1Phone.isEmpty()||courseInst1Email.isEmpty()){
            Toast.makeText(this,"Please input data for Course Name, Start, End, Status, and at least Instructor 1 fields.", Toast.LENGTH_LONG).show();
            return;
        }



        try{
            courseStartDate = dateFormat.parse(courseStart);
        }
        catch(ParseException e){
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format in Start field; Please use YYYY-MM-DD.", Toast.LENGTH_LONG).show();
            return;
        }

        try{
            courseEndDate = dateFormat.parse(courseEnd);
        }
        catch(ParseException e){
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format in End field; Please use YYYY-MM-DD.", Toast.LENGTH_LONG).show();
            return;
        }

        if(courseId != -1){
            CourseEntity updateCourse = new CourseEntity(courseId,termId,courseName,courseStartDate,courseEndDate,courseStatus,courseNotes,courseInst1Name,courseInst1Phone,courseInst1Email,courseInst2Name,courseInst2Phone,courseInst2Email);
            schedulerRepository.updateCourse(updateCourse);
            Toast.makeText(this, "Course updated.", Toast.LENGTH_LONG).show();
        }
        else{
            CourseEntity newCourse = new CourseEntity(0,termId,courseName,courseStartDate,courseEndDate,courseStatus,courseNotes,courseInst1Name,courseInst1Phone,courseInst1Email,courseInst2Name,courseInst2Phone,courseInst2Email);
            schedulerRepository.insertCourse(newCourse);

            Toast.makeText(this,"Course created.",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CourseDetailActivity.this, CourseListActivity.class);
            intent.putExtra("termId",termId);
            startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        if(courseId != -1){
            getMenuInflater().inflate(R.menu.course_detail_menu,menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        schedulerRepository = new SchedulerRepository(getApplication());
        int itemId = item.getItemId();
        if(itemId == R.id.course_delete){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Course")
                    .setMessage("Are you sure you want to delete course: "+courseName+" and any associated assessments?")
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CourseEntity delCourse = schedulerRepository.getCourse(courseId);
                            schedulerRepository.deleteCourse(delCourse);

                            Toast.makeText(CourseDetailActivity.this,"Course deleted.",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(CourseDetailActivity.this, CourseListActivity.class);
                            intent.putExtra("termId",termId);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No",null).show();
        }
        if(itemId == R.id.course_share){
            String courseName = courseTitleET.getText().toString().trim();
            String courseNotes = courseNotesET.getText().toString().trim();

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,courseNotes);
            sendIntent.putExtra(Intent.EXTRA_TITLE,"Notes for: "+courseName);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent,null);
            startActivity(shareIntent);
            return true;
        }
        if(itemId == R.id.course_alert_start){
            //START alert selected for COURSE.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date courseStartDate = new Date();
            String courseStart = courseStartET.getText().toString().trim();
            try{
                courseStartDate = dateFormat.parse(courseStart);
            }
            catch(ParseException e){
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format in Start field; Please use YYYY-MM-DD.", Toast.LENGTH_LONG).show();
                return true;
            }

            Intent intent = new Intent(CourseDetailActivity.this,MyReceiver.class);
            intent.putExtra("alertType","Course start alert."); //Course Start Alert
            intent.putExtra("alertTitle", courseName+" start: "+courseStart); //[Course] start: [Date]

            PendingIntent sender = PendingIntent.getBroadcast(CourseDetailActivity.this, ++numAlert, intent,0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            date = courseStartDate.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);

            Toast.makeText(this,"Alert set.",Toast.LENGTH_LONG).show();
            return true;
        }
        if(itemId == R.id.course_alert_end){
            //END alert selected for COURSE.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date courseEndDate = new Date();
            String courseEnd = courseEndET.getText().toString().trim();
            try{
                courseEndDate = dateFormat.parse(courseEnd);
            }
            catch(ParseException e){
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format in End field; Please use YYYY-MM-DD.", Toast.LENGTH_LONG).show();
                return true;
            }

            Intent intent = new Intent(CourseDetailActivity.this, MyReceiver.class);
            intent.putExtra("alertType", "Course end alert.");
            intent.putExtra("alertTitle", courseName+" end: "+courseEnd);

            PendingIntent sender = PendingIntent.getBroadcast(CourseDetailActivity.this, ++numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            date = courseEndDate.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP,date, sender);

            Toast.makeText(this,"Alert set.",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}