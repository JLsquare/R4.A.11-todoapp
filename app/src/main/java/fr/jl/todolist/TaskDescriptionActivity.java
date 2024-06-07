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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TaskDescriptionActivity class that allows the user to view and edit task details.
 * This activity provides UI components to view and edit task details such as title, description, duration, date, context, and web link.
 */
public class TaskDescriptionActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText dateEditText;
    private EditText contextEditText;
    private EditText webLinkEditText;
    private NumberPicker daysPicker;
    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;

    private Task task;
    private int taskIndex;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        // Initialize UI components
        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);
        dateEditText = findViewById(R.id.editTextDate);
        contextEditText = findViewById(R.id.editTextContext);
        webLinkEditText = findViewById(R.id.editTextWebLink);
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

        // Get task index and task object from the intent
        taskIndex = getIntent().getIntExtra("taskIndex", -1);
        task = (Task) getIntent().getSerializableExtra("task");

        // Populate the UI components with the task data
        if (task != null) {
            titleEditText.setText(task.getTitle());
            descriptionEditText.setText(task.getDescription());
            dateEditText.setText(task.getFormattedDate());
            contextEditText.setText(task.getContext());
            webLinkEditText.setText(task.getWebLink());
            daysPicker.setValue(task.getDuration() / (24 * 60));
            hoursPicker.setValue((task.getDuration() % (24 * 60)) / 60);
            minutesPicker.setValue(task.getDuration() % 60);
        }

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
     * Saves the changes made to the task and returns the result to the calling activity.
     * @param view The view that triggers this method (e.g., a button).
     */
    public void saveChanges(View view) {
        System.out.println("Save changes");
        // Update the task object with the new details
        task.setTitle(titleEditText.getText().toString());
        task.setDescription(descriptionEditText.getText().toString());
        task.setContext(contextEditText.getText().toString());
        task.setWebLink(webLinkEditText.getText().toString());
        int duration = daysPicker.getValue() * 24 * 60 + hoursPicker.getValue() * 60 + minutesPicker.getValue();
        task.setDuration(duration);

        // Update the date of the task
        String dateString = dateEditText.getText().toString();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            task.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Prepare the result intent and set the result
        Intent resultIntent = new Intent();
        resultIntent.putExtra("taskIndex", taskIndex);
        resultIntent.putExtra("modifiedTask", task);
        setResult(Activity.RESULT_OK, resultIntent);

        // Finish the activity
        finish();
    }

    /**
     * Deletes the task and returns the result to the calling activity.
     * @param view The view that triggers this method (e.g., a button).
     */
    public void deleteTask(View view) {
        System.out.println("Delete task");
        // Prepare the result intent and set the result
        Intent resultIntent = new Intent();
        resultIntent.putExtra("taskIndex", taskIndex);
        resultIntent.putExtra("taskDeleted", true);
        setResult(Activity.RESULT_OK, resultIntent);

        // Finish the activity
        finish();
    }
}
