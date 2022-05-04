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
import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;
    static int termId;

    private String termName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        schedulerRepository = new SchedulerRepository(getApplication());

        Intent intent = getIntent();
        termId = intent.getIntExtra("termId", -1);

        if(termId == -1){
            termId = CourseDetailActivity.termId;
        }

        termName = schedulerRepository.getTerm(termId).getTermName();

        List<CourseEntity> allCourses = schedulerRepository.getAllCourses();
        List<CourseEntity> filteredCourses = new ArrayList<>();

        for(CourseEntity course : allCourses){
            if(course.getTermId() == termId){
                filteredCourses.add(course);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);

        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(filteredCourses);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.course_list_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        schedulerRepository = new SchedulerRepository(getApplication());
        int itemId = item.getItemId();
        if(itemId == R.id.course_add){
            AssessmentListActivity.courseId = -1;
            Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
            intent.putExtra("termId", termId);
            startActivity(intent);
        }
        if(itemId == R.id.course_all_delete){
            List<CourseEntity> allCourses = schedulerRepository.getAllCourses();
            List<CourseEntity> filteredCourses = new ArrayList<>();

            for(CourseEntity course : allCourses){
                if(course.getTermId() == termId){
                    filteredCourses.add(course);
                }
            }

            if(filteredCourses.isEmpty()){
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Delete Courses")
                        .setMessage("There are no courses to delete.")
                        .setPositiveButton("Ok",null).show();
            }
            else{
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Delete Courses")
                        .setMessage("Are you sure you want to delete all courses and associated assessments in term: "+termName+"?")
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for(CourseEntity delCourse : filteredCourses){
                                    schedulerRepository.deleteCourse(delCourse);
                                }

                                Toast.makeText(CourseListActivity.this,"Courses deleted.",Toast.LENGTH_LONG).show();

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