package org.kidzonshock.acase.acase.Lawyer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import org.kidzonshock.acase.acase.R;

import java.util.Calendar;

public class CreateEvent extends AppCompatActivity {

    CalendarView calendarView;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Documents");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
