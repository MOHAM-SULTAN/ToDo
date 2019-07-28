package com.sultan.todo;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView titletxt;
    TextView titlelist;
    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter1;
    ArrayAdapter<String> mAdapter2;
    ListView listTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        listTask = (ListView)findViewById(R.id.listTask);

        toolbar = (Toolbar)findViewById(R.id.tool);


        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/title.ttf");
        //Typeface typeface1 = Typeface.createFromAsset(this.getAssets(), "font/hel.ttf");

        titletxt = (TextView)findViewById(R.id.title);
        titlelist = (TextView)findViewById(R.id.tasktitle);
        //titlelist.setTypeface(typeface1);

        titletxt.setTypeface(typeface);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        loadTaskList();

        FloatingActionButton floatButton = (FloatingActionButton) findViewById(R.id.action1);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast("fab1");

            }
        });

        FloatingActionButton floatButton2 = (FloatingActionButton) findViewById(R.id.action2);

        floatButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             alertBox();
            }
        });


    }


    public void alertBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText taskTitleEditText = new EditText(this);
        builder.setTitle("Add New Task");

        LinearLayout lin = new LinearLayout(this);
        lin.setOrientation(LinearLayout.VERTICAL);
        lin.addView(taskTitleEditText);

        builder.setView(lin);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskTitle = String.valueOf((taskTitleEditText.getText()));
                dbHelper.insertNewTask(taskTitle);
                loadTaskList();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create();
        builder.show();

//        AlertDialog dialog  = new AlertDialog.Builder(this)
//                .setTitle("Add New Task")
//                .setMessage("Title")
//                .setView(taskTitleEditText)
//                .setMessage("Add a Note")
//                .setView(taskNoteEditText)
//                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String taskTitle = String.valueOf((taskTitleEditText.getText()));
//                        String taskNote = String.valueOf((taskNoteEditText.getText()));
//                        dbHelper.insertNewTask(taskTitle, taskNote);
//                        loadTaskList();
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .create();
//        dialog.show();
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)findViewById(R.id.tasktitle);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }

    private void loadTaskList() {

        ArrayList<String> taskListTitle = dbHelper.getTaskListTitle();
        if(mAdapter1 == null)
        {
            mAdapter1 = new ArrayAdapter<String>(this, R.layout.row, R.id.tasktitle, taskListTitle);
            listTask.setAdapter(mAdapter1);
        }
        else
        {
            mAdapter1.clear();
            mAdapter1.addAll(taskListTitle);
            mAdapter1.notifyDataSetChanged();
        }
    }

    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
