package fr.jl.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;

import java.util.ArrayList;

/**
 * TaskAdapter class that acts as a bridge between the data (tasks) and the ListView that displays them.
 * This adapter is responsible for creating and populating the views for each task item in the list.
 */
public class TaskAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Task> tasks;
    private LayoutInflater inflater;
    private ActivityResultLauncher<Intent> editTaskLauncher;

    /**
     * Constructor for the TaskAdapter class.
     * @param context The context in which the adapter is created.
     * @param tasks The list of tasks to be displayed.
     * @param editTaskLauncher The activity result launcher for editing a task.
     */
    public TaskAdapter(Context context, ArrayList<Task> tasks, ActivityResultLauncher<Intent> editTaskLauncher) {
        this.context = context;
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
        this.editTaskLauncher = editTaskLauncher;
    }

    /**
     * Returns the number of items in the list of tasks.
     * @return The number of tasks in the list.
     */
    @Override
    public int getCount() {
        return tasks.size();
    }

    /**
     * Returns the task at the specified position in the list.
     * @param position The position of the task in the list.
     * @return The task at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    /**
     * Returns the ID of the task at the specified position in the list.
     * @param position The position of the task in the list.
     * @return The ID of the task at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns the view for the task item at the specified position in the list.
     * @param position The position of the task in the list.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return The view for the task item at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
        }

        TextView titleView = convertView.findViewById(R.id.taskTitle);
        TextView descriptionView = convertView.findViewById(R.id.taskDescription);
        TextView dateView = convertView.findViewById(R.id.taskDate);
        TextView contextView = convertView.findViewById(R.id.taskContext);
        Button openLinkButton = convertView.findViewById(R.id.buttonOpenLink);

        Task task = tasks.get(position);

        titleView.setText(task.getTitle());
        descriptionView.setText(task.getDescription());
        dateView.setText(task.getFormattedDate());
        contextView.setText(task.getContext());

        // Set up the button to open the web link associated with the task
        String webLink = task.getWebLink();
        if (webLink != null && !webLink.isEmpty()) {
            openLinkButton.setVisibility(View.VISIBLE);
            openLinkButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", webLink);
                context.startActivity(intent);
            });
        } else {
            openLinkButton.setVisibility(View.GONE);
        }

        // Set click listener for the task item to open the TaskDescriptionActivity
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TaskDescriptionActivity.class);
            intent.putExtra("taskIndex", position);
            intent.putExtra("task", task);
            editTaskLauncher.launch(intent);
        });

        return convertView;
    }
}

