package com.example.wguscheduler_marywilliams.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wguscheduler_marywilliams.Database.SchedulerDatabase;
import com.example.wguscheduler_marywilliams.Database.SchedulerRepository;
import com.example.wguscheduler_marywilliams.Entity.TermEntity;
import com.example.wguscheduler_marywilliams.R;

import java.sql.Date;

public class TermListActivity extends AppCompatActivity {
    private SchedulerRepository schedulerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        //System.out.println("Inside termlistactivity onCreate");

        schedulerRepository = new SchedulerRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.termListRecyclerView);

        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(schedulerRepository.getAllTerms());

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.term_list_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.term_add){
            CourseListActivity.termId = -1;
            Intent intent = new Intent(TermListActivity.this, TermDetailActivity.class);
            intent.putExtra("termId",-1);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}