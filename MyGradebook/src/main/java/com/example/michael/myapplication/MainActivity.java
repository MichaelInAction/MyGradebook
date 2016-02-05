package com.example.michael.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Main activity of the app
 * @author Michael Read
 * @version 15/6/15
 */
public class MainActivity extends Activity {

    private LinearLayout root;
    private ArrayList<Course> classList = new ArrayList<>();
    private ArrayList<Assignment> assignmentList = new ArrayList<>();
    private ArrayList<Assignment> temp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildChangeCourse();
    }

    /**
     * builds the main view of the app, where you see the assignments in a given course
     * @param courseName the name of the course you are viewing
     */
    private void buildMainView(final String courseName) {
        assignmentList.clear();
        temp.clear();
        try {
            File file = new File(this.getFilesDir(), "myAssignments");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String next;
            while((next = reader.readLine()) != null) {
                assignmentList.add(new Assignment(next));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        root = (LinearLayout) findViewById(R.id.root);
        final ListView listview = (ListView) findViewById(R.id.listview);
        for(int i = 0; i < assignmentList.size(); i++)
        {
            if(assignmentList.get(i).getCourse().equals(courseName)) temp.add(assignmentList.get(i));
        }
        final ArrayAdapter<Assignment> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temp);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("what do you want to do?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                for(int i = 0; i < assignmentList.size(); i++)
                                {
                                    if(assignmentList.get(i).getName().equals(temp.get(position).getName()) && assignmentList.get(i).getCourse().equals(courseName))
                                    {
                                        assignmentList.remove(i);
                                        try {
                                            File file = new File(MainActivity.this.getFilesDir(), "myAssignments");
                                            PrintWriter writer = new PrintWriter(new FileWriter(file));
                                            for(Assignment assignment : assignmentList)
                                            {
                                                writer.println(assignment.toFile());
                                            }
                                            writer.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                }
                                buildMainView(courseName);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        Button change = (Button) findViewById(R.id.change);
        change.setText("Change Course");
        change.setTextSize(10);
        Button addCourse = (Button) findViewById(R.id.addCourse);
        addCourse.setText("Add a Course");
        addCourse.setTextSize(10);
        Button addAssign = (Button) findViewById(R.id.addAssign);
        addAssign.setText("Add an Assignment");
        addAssign.setTextSize(10);
        final TextView text = (TextView) findViewById(R.id.text);
        text.setText(courseName);
        change.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                buildChangeCourse();
            }
        });
        addCourse.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                buildAddCourse();
            }
        });
        addAssign.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                buildAddAssignment(courseName);
            }
        });
        TextView avg = (TextView) findViewById(R.id.avg);
        double average = 0;
        double total = 0;
        for(int i = 0; i < temp.size(); i++)
        {
            average += temp.get(i).getPointsRecieved();
            total += temp.get(i).getPointsTotal();
        }
        if(total > 0) average /= total;
        average *= 100;
        avg.setText("" + average + "%");
        setContentView(root);
    }

    /**
     * builds the Change Course view, where you select what course to view
     */
    public void buildChangeCourse()
    {
        classList.clear();
        try {
            File file = new File(this.getFilesDir(), "myCourses");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String next;
            while((next = reader.readLine()) != null) {
                classList.add(new Course(next));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.change_course);
        root = (LinearLayout) findViewById(R.id.changeClassLayout);
        Button b = (Button) findViewById(R.id.changeAddCourse);
        ListView listView = (ListView) findViewById(R.id.classList);
        final ArrayAdapter<Course> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("What do you want to do?")
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                classList.remove(position);
                                try {
                                    File file = new File(MainActivity.this.getFilesDir(), "myCourses");
                                    PrintWriter writer = new PrintWriter(new FileWriter(file));
                                    for(Course course : classList)
                                    {
                                        writer.println(course.toFile());
                                    }
                                    writer.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                buildChangeCourse();
                            }
                        })
                        .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                buildMainView(classList.get(position).getCourse());
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        b.setText("Add Course");
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                buildAddCourse();
            }
        });
        setContentView(root);
    }

    /**
     * builds the Add Course view, where you add a course to your list of courses
     */
    private void buildAddCourse()
    {
        setContentView(R.layout.add_course);
        root = (LinearLayout) findViewById(R.id.addCourseLayout);
        TextView view = (TextView) findViewById(R.id.courseName);
        view.setText("Enter Course Name:");
        view = (TextView) findViewById(R.id.teacherName);
        view.setText("Enter Teacher Name:");
        Button b = (Button) findViewById(R.id.doneAddCourse);
        view = (TextView) findViewById(R.id.term);
        view.setText("Enter Term:");
        b.setText("Add Course");
        final EditText courseName = (EditText) findViewById(R.id.enterCourseName);
        final EditText teacherName = (EditText) findViewById(R.id.enterTeacherName);
        final EditText term = (EditText) findViewById(R.id.enterTerm);
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!courseName.getText().toString().trim().equals("") && !teacherName.getText().toString().trim().equals("") && !term.getText().toString().trim().equals("")) {
                    try {
                        classList.add(new Course(courseName.getText().toString(), teacherName.getText().toString(), term.getText().toString()));
                        File file = new File(MainActivity.this.getFilesDir(), "myCourses");
                        PrintWriter writer = new PrintWriter(new FileWriter(file));
                        for(Course course : classList)
                        {
                            writer.println(course.toFile());
                        }
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buildChangeCourse();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("You must fill out all fields before submission")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        setContentView(root);
    }

    /**
     * builds the Add Assignment view, where you add an assignment to a given course
     * @param courseName the course you are adding the assignment to
     */
    private void buildAddAssignment(final String courseName)
    {
        setContentView(R.layout.add_assignment);
        root = (LinearLayout) findViewById(R.id.addAssignmentLayout);
        TextView view = (TextView) findViewById(R.id.assignmentName);
        view.setText("Enter Assignment Name:");
        view = (TextView) findViewById(R.id.pointsRecieved);
        view.setText("Enter Points Recieved:");
        view = (TextView) findViewById(R.id.pointsPossible);
        view.setText("Enter Points Possible:");
        view = (TextView) findViewById(R.id.dueDate);
        view.setText("Enter Due Date:");
        final EditText name = (EditText) findViewById(R.id.enterAssignmentName);
        final EditText due = (EditText) findViewById(R.id.enterDueDate);
        final EditText pointsPossible = (EditText) findViewById(R.id.enterPointsPossible);
        final EditText pointsRecieved = (EditText) findViewById(R.id.enterPointsRecieved);
        Button b = (Button) findViewById(R.id.doneAddAssignment);
        b.setText("Add Assignment");
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!name.getText().toString().trim().equals("") && !due.getText().toString().trim().equals("") && !pointsRecieved.getText().toString().trim().equals("") && !pointsPossible.getText().toString().trim().equals("")) {
                    try {
                        assignmentList.add(new Assignment(name.getText().toString(), due.getText().toString(), Integer.parseInt(pointsRecieved.getText().toString()), Integer.parseInt(pointsPossible.getText().toString()),
                                courseName));
                        File file = new File(MainActivity.this.getFilesDir(), "myAssignments");
                        PrintWriter writer = new PrintWriter(new FileWriter(file));
                        for(Assignment assignment : assignmentList)
                        {
                            writer.println(assignment.toFile());
                        }
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buildMainView(courseName);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("You must fill out all fields before submission")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        setContentView(root);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



