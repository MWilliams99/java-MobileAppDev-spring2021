package com.example.wguscheduler_marywilliams.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wguscheduler_marywilliams.Database.SchedulerRepository;
import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.Entity.TermEntity;
import com.example.wguscheduler_marywilliams.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermDetailActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;

    private EditText termTitleET;
    private EditText termStartET;
    private EditText termEndET;
    private int termId;
    private String termName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        termTitleET = findViewById(R.id.termTitleEditText);
        termStartET = findViewById(R.id.termStartEditText);
        termEndET = findViewById(R.id.termEndEditText);

        Intent intent = getIntent();
        termId = intent.getIntExtra("termId",-1);

        if(termId == -1){
            termId = CourseListActivity.termId;
        }

        if(termId != -1){
            schedulerRepository = new SchedulerRepository(getApplication());
            TermEntity term = schedulerRepository.getTerm(termId);

            termTitleET.setText(term.getTermName());
            termName = term.getTermName();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date termStart = term.getTermStart();
            termStartET.setText(dateFormat.format(termStart));

            Date termEnd = term.getTermEnd();
            termEndET.setText(dateFormat.format(termEnd));

        }
        else{
            findViewById(R.id.courseListButton).setVisibility(View.GONE);
            setTitle("Add Term");
        }


    }

    public void toCourseList(View view){
        Intent intent = new Intent(TermDetailActivity.this, CourseListActivity.class);
        intent.putExtra("termId",termId);
        startActivity(intent);
    }

    public void saveTerm(View view){
        schedulerRepository = new SchedulerRepository(getApplication());

        String termName = termTitleET.getText().toString().trim();
        String termStart = termStartET.getText().toString().trim();
        String termEnd = termEndET.getText().toString().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date termStartDate = new Date();
        Date termEndDate = new Date();

        //Check that no fields are left empty
        if(termName.isEmpty() || termStart.isEmpty() || termEnd.isEmpty()){
            Toast.makeText(this,"Please fill out every field.",Toast.LENGTH_LONG).show();
            return;
        }

        try{
            termStartDate = dateFormat.parse(termStart);
        }
        catch(ParseException e) {
            e.printStackTrace();
            Toast.makeText(this,"Invalid date format in Start field; Please use YYYY-MM-DD.", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            termEndDate = dateFormat.parse(termEnd);
        }
        catch(ParseException e) {
            e.printStackTrace();
            Toast.makeText(this,"Invalid date format in End field; Please use YYYY-MM-DD.", Toast.LENGTH_LONG).show();
            return;
        }

        if(termId != -1){
            TermEntity updateTerm = new TermEntity(termId,termName,termStartDate,termEndDate);
            schedulerRepository.updateTerm(updateTerm);
            Toast.makeText(this, "Term updated.", Toast.LENGTH_LONG).show();
        }
        else{
            TermEntity newTerm = new TermEntity(0,termName, termStartDate,termEndDate);
            schedulerRepository.insertTerm(newTerm);

            Toast.makeText(this, "Term created.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(TermDetailActivity.this, TermListActivity.class);
            startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        if(termId != -1){
            getMenuInflater().inflate(R.menu.term_detail_menu,menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        schedulerRepository = new SchedulerRepository(getApplication());
        int itemId = item.getItemId();
        if(itemId == R.id.term_delete){
            List<CourseEntity> allCourses = schedulerRepository.getAllCourses();
            List<CourseEntity> filteredCourses = new ArrayList<>();

            for(CourseEntity course : allCourses){
                if(course.getTermId() == termId){
                    filteredCourses.add(course);
                }
            }

            if(!(filteredCourses.isEmpty())){
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Delete Term")
                        .setMessage("Can not delete a term that has courses assigned to it.")
                        .setPositiveButton("Ok",null).show();
            }
            else{
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Delete Term")
                        .setMessage("Are you sure you want to delete term: "+termName+"?")
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TermEntity delTerm = schedulerRepository.getTerm(termId);
                                schedulerRepository.deleteTerm(delTerm);

                                Toast.makeText(TermDetailActivity.this,"Term deleted.",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(TermDetailActivity.this, TermListActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",null).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}