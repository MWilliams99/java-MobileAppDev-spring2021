package com.example.wguscheduler_marywilliams.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wguscheduler_marywilliams.Database.SchedulerRepository;
import com.example.wguscheduler_marywilliams.Entity.AssessmentEntity;
import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentDetailActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;

    Long date;

    private EditText assessmentTitleET;
    private EditText assessmentStartET;
    private EditText assessmentEndET;
    //radio buttons?? or nah here
    private RadioButton assessmentPreA;
    private RadioButton assessmentPA;
    private RadioButton assessmentOA;

    static int courseId;
    private int assessmentId;

    private String courseName;
    private String assessmentType;
    private String assessmentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        schedulerRepository = new SchedulerRepository(getApplication());

        assessmentTitleET = findViewById(R.id.assessmentTitleEditText);
        assessmentStartET = findViewById(R.id.assessmentStartEditText);
        assessmentEndET = findViewById(R.id.assessmentEndEditText);

        //radio buttons
        assessmentPreA = findViewById(R.id.radio_PreA);
        assessmentPA = findViewById(R.id.radio_PA);
        assessmentOA = findViewById(R.id.radio_OA);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);
        assessmentId = intent.getIntExtra("assessmentId",-1);

        //Testing output/logic flow
        //System.out.println(courseId+" "+assessmentId);

        if(assessmentId != -1){
            AssessmentEntity assessment = schedulerRepository.getAssessment(assessmentId);
            CourseEntity course = schedulerRepository.getCourse(assessment.getCourseId());

            assessmentTitleET.setText(assessment.getAssessmentName());
            assessmentName = assessment.getAssessmentName();
            courseName = course.getCourseName();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date assessmentStart = assessment.getAssessmentStart();
            assessmentStartET.setText(dateFormat.format(assessmentStart));
            Date assessmentEnd = assessment.getAssessmentEnd();
            assessmentEndET.setText(dateFormat.format(assessmentEnd));

            //RADIO BUTTON SELECTION HERE
            assessmentType = assessment.getAssessmentType();
            if(assessmentType.equalsIgnoreCase("Pre-Assessment")){
                assessmentPreA.setChecked(true);
                assessmentPA.setChecked(false);
                assessmentOA.setChecked(false);
            }
            else if(assessmentType.equalsIgnoreCase("Performance Assessment")){
                assessmentPreA.setChecked(false);
                assessmentPA.setChecked(true);
                assessmentOA.setChecked(false);
            }
            else if(assessmentType.equalsIgnoreCase("Objective Assessment")){
                assessmentPreA.setChecked(false);
                assessmentPA.setChecked(false);
                assessmentOA.setChecked(true);
            }
            else{
                assessmentPreA.setChecked(false);
                assessmentPA.setChecked(false);
                assessmentOA.setChecked(false);
            }
        }
        else{
            setTitle("Add Assessment");
        }
    }

    public void saveAssessment(View view){
        schedulerRepository = new SchedulerRepository(getApplication());

        String assessmentName = assessmentTitleET.getText().toString().trim();
        String assessmentStart = assessmentStartET.getText().toString().trim();
        String assessmentEnd = assessmentEndET.getText().toString().trim();

        assessmentPreA = findViewById(R.id.radio_PreA);
        assessmentPA = findViewById(R.id.radio_PA);
        assessmentOA = findViewById(R.id.radio_OA);
        if(assessmentPreA.isChecked()){
            assessmentType = "Pre-Assessment";
        }
        else if(assessmentPA.isChecked()){
            assessmentType = "Performance Assessment";
        }
        else if(assessmentOA.isChecked()){
            assessmentType = "Objective Assessment";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date assessmentStartDate = new Date();
        Date assessmentEndDate = new Date();

        //Check anything left empty
        if(assessmentName.isEmpty() || assessmentStart.isEmpty() || assessmentEnd.isEmpty()){
            Toast.makeText(this, "Please fill out every field.",Toast.LENGTH_LONG).show();
            return;
        }

        try{
            assessmentStartDate = dateFormat.parse(assessmentStart);
        }
        catch(ParseException e){
            e.printStackTrace();
            Toast.makeText(this,"Invalid date format in Start field; Please use YYYY-MM-DD", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            assessmentEndDate= dateFormat.parse(assessmentEnd);
        }
        catch(ParseException e){
            e.printStackTrace();
            Toast.makeText(this,"Invalid date format in End field; Please use YYYY-MM-DD", Toast.LENGTH_LONG).show();
            return;
        }

        //GET RADIO BUTTON SELECTIONS
        //String assessType = assessmentType;

        if(assessmentId != -1){
            AssessmentEntity updateAssessment = new AssessmentEntity(assessmentId,courseId,assessmentName,assessmentType,assessmentStartDate,assessmentEndDate);
            schedulerRepository.updateAssessment(updateAssessment);
            Toast.makeText(this, "Assessment updated.", Toast.LENGTH_LONG).show();
        }
        else{
            AssessmentEntity newAssessment = new AssessmentEntity(0, courseId,assessmentName,assessmentType,assessmentStartDate,assessmentEndDate);
            schedulerRepository.insertAssessment(newAssessment);

            Toast.makeText(this, "Assessment created.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentListActivity.class);
            intent.putExtra("courseId",courseId);
            startActivity(intent);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        if(assessmentId != -1){
            getMenuInflater().inflate(R.menu.assessment_detail_menu,menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        schedulerRepository = new SchedulerRepository(getApplication());
        int itemId = item.getItemId();
        if(itemId == R.id.assessment_delete){
            //Confirmation dialog - Are you sure you'd like to delete assessment: [Name] ? -- Yes/No
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Assessment")
                    .setMessage("Are you sure you want to delete assessment: "+assessmentName+"?")
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes - delete assessment - return to assessmentlist
                            AssessmentEntity delAssessment = schedulerRepository.getAssessment(assessmentId);
                            schedulerRepository.deleteAssessment(delAssessment);

                            Toast.makeText(AssessmentDetailActivity.this,"Assessment deleted.", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentListActivity.class);
                            intent.putExtra("courseId",courseId);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", null).show();
        }
        if(itemId == R.id.assessment_alert_start){
            //START alert selected for ASSESSMENT.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date assessmentStartDate = new Date();
            String assessmentStart = assessmentStartET.getText().toString().trim();
            try{
                assessmentStartDate = dateFormat.parse(assessmentStart);
            }
            catch(ParseException e){
                e.printStackTrace();
                Toast.makeText(this,"Invalid date format in Start field; Please use YYYY-MM-DD.", Toast.LENGTH_LONG).show();
                return true;
            }

            Intent intent = new Intent(AssessmentDetailActivity.this, MyReceiver.class);
            intent.putExtra("alertType","Assessment start alert.");
            intent.putExtra("alertTitle",courseName+" assessment - "+assessmentName+" start: "+assessmentStart); //[CourseName] assessment - [AssessmentName] start: [Date]

            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, ++CourseDetailActivity.numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            date = assessmentStartDate.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP,date,sender);

            Toast.makeText(this,"Alert set.",Toast.LENGTH_LONG).show();
            return true;
        }
        if(itemId == R.id.assessment_alert_end){
            //END alert selected for ASSESSMENT.
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date assessmentEndDate = new Date();
            String assessmentEnd = assessmentEndET.getText().toString().trim();
            try{
                assessmentEndDate = dateFormat.parse(assessmentEnd);
            }
            catch(ParseException e){
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format in End field; Please use YYYY-MM-DD", Toast.LENGTH_LONG).show();
                return true;
            }

            Intent intent = new Intent(AssessmentDetailActivity.this, MyReceiver.class);
            intent.putExtra("alertType","Assessment end alert.");
            intent.putExtra("alertTitle",courseName+" assessment - "+assessmentName+" end: "+assessmentEnd); //[CourseName] assessment - [AssessmentName] end: [Date]

            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, ++CourseDetailActivity.numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            date = assessmentEndDate.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP,date,sender);

            Toast.makeText(this,"Alert set.",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}