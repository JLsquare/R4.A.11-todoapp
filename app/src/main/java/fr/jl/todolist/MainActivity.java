package fr.jl.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * MainActivity class that displays the list of tasks.
 * This activity is the main screen of the app and shows a list of tasks that the user has created.
 */
public class MainActivity extends AppCompatActivity {
    ListView listView;
    TaskAdapter adapter;
    ArrayList<Task> tasks = new ArrayList<>();

    private ActivityResultLauncher<Intent> createTaskLauncher;
    private ActivityResultLauncher<Intent> editTaskLauncher;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        Button addButton = findViewById(R.id.addButton);

        loadTasks();

        // Register activity result launchers
        createTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Task newTask = (Task) result.getData().getSerializableExtra("newTask");
                        tasks.add(newTask);
                        adapter.notifyDataSetChanged();
                        saveTasks();
                    }
                }
        );

        // Register activity result launcher for editing or deleting a task
        editTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        int taskIndex = result.getData().getIntExtra("taskIndex", -1);
                        boolean isTaskDeleted = result.getData().getBooleanExtra("taskDeleted", false);

                        if (isTaskDeleted) {
                            tasks.remove(taskIndex);
                        } else {
                            Task updatedTask = (Task) result.getData().getSerializableExtra("modifiedTask");
                            if (taskIndex != -1) {
                                tasks.set(taskIndex, updatedTask);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        saveTasks();
                    }
                }
        );

        // Set up the adapter for the ListView, passing in the list of tasks and the editTaskLauncher
        adapter = new TaskAdapter(this, tasks, editTaskLauncher);
        listView.setAdapter(adapter);

        // Set click listener for the add button to start the CreateTaskActivity
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
            createTaskLauncher.launch(intent);
        });
    }

    /**
     * Saves the list of tasks to SharedPreferences.
     */
    private void saveTasks() {
        SharedPreferences prefs = getSharedPreferences("tasks", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        editor.putString("taskList", json);
        editor.apply();
    }

    /**
     * Loads the list of tasks from SharedPreferences.
     */
    private void loadTasks() {
        SharedPreferences prefs = getSharedPreferences("tasks", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("taskList", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        tasks = gson.fromJson(json, type);

        if (tasks == null) {
            tasks = new ArrayList<>();
        }
    }
}

