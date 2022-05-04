package com.example.wguscheduler_marywilliams.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wguscheduler_marywilliams.Database.SchedulerRepository;
import com.example.wguscheduler_marywilliams.Entity.AssessmentEntity;
import com.example.wguscheduler_marywilliams.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class AssessmentListActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;
    //int termId;
    static int courseId;

    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        schedulerRepository = new SchedulerRepository(getApplication());

        Intent intent = getIntent();
        //termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);

        if(courseId == -1){
            courseId = AssessmentDetailActivity.courseId;
        }

        courseName = schedulerRepository.getCourse(courseId).getCourseName();

        List<AssessmentEntity> allAssessments = schedulerRepository.getAllAssessments();
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();

        for(AssessmentEntity assessment : allAssessments){
            if(assessment.getCourseId() == courseId){
                filteredAssessments.add(assessment);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);

        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(filteredAssessments);


    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_list_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        schedulerRepository = new SchedulerRepository(getApplication());
        int itemId = item.getItemId();
        if(itemId == R.id.assessment_add){
            Intent intent = new Intent(AssessmentListActivity.this, AssessmentDetailActivity.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }

        if(itemId == R.id.assessment_all_delete){
            //Check that there are even any assessments to delete - no? let user know
            List<AssessmentEntity> allAssessments = schedulerRepository.getAllAssessments();
            List<AssessmentEntity> filteredAssessments = new ArrayList<>();

            for(AssessmentEntity assessment : allAssessments){
                if(assessment.getCourseId() == courseId){
                    filteredAssessments.add(assessment);
                }
            }

            if(filteredAssessments.isEmpty()){
                //Dialog alert there are no assessments to delete
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Delete Assessments")
                        .setMessage("There are no assessments to delete.")
                        .setPositiveButton("Ok",null).show();
            }
            else{
                //Confirm delete all assessments associated with course:
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Delete Assessments")
                        .setMessage("Are you sure you want to delete all assessments associated with course: "+courseName+"?")
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Yes - delete all assessments - refresh page??
                                for(AssessmentEntity delAssessment : filteredAssessments){
                                    schedulerRepository.deleteAssessment(delAssessment);
                                }

                                Toast.makeText(AssessmentListActivity.this,"Assessments deleted.", Toast.LENGTH_LONG).show();

                                finish();
                                overridePendingTransition(0,0);
                                startActivity(getIntent());
                                overridePendingTransition(0,0);
                            }
                        })
                        .setNegativeButton("No",null).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}