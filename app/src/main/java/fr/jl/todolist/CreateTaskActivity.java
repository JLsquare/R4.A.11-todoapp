package fr.jl.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * CreateTaskActivity class that allows the user to create a new task.
 * This activity provides UI components to input task details such as title, description, duration, date, and context.
 */
public class CreateTaskActivity extends AppCompatActivity {
    // UI components for task details input
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText contextEditText;
    private EditText webLinkEditText;
    private EditText dateEditText;
    private NumberPicker daysPicker;
    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // Initialize UI components
        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);
        contextEditText = findViewById(R.id.editTextContext);
        webLinkEditText = findViewById(R.id.editTextWebLink);
        dateEditText = findViewById(R.id.editTextDate);
        daysPicker = findViewById(R.id.numberPickerDays);
        hoursPicker = findViewById(R.id.numberPickerHours);
        minutesPicker = findViewById(R.id.numberPickerMinutes);

        // Set NumberPicker ranges
        daysPicker.setMinValue(0);
        daysPicker.setMaxValue(99);
        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);

        // Set click listener for date EditText to show DatePickerDialog
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    /**
     * Displays a DatePickerDialog to select a date.
     */
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        dateEditText.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    /**
     * Saves the new task with the entered details and returns the result to the calling activity.
     * @param view The view that triggers this method (e.g., a button).
     */
    public void saveTask(View view) {
        // Get task details from UI components
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String context = contextEditText.getText().toString();
        String webLink = webLinkEditText.getText().toString();
        String date = dateEditText.getText().toString();
        int duration = daysPicker.getValue() * 24 * 60 + hoursPicker.getValue() * 60 + minutesPicker.getValue();

        // Check if all required fields are filled
        if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty() && !context.isEmpty() && !webLink.isEmpty()) {
            // Create a new Task object
            Task newTask = new Task(title, description, duration, date, context, webLink);
            // Prepare the result intent
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newTask", newTask);
            setResult(Activity.RESULT_OK, resultIntent);
        }
        // Finish the activity
        finish();
    }
}
