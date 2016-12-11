package ninja.paranoidandroid.ttm2;

import android.app.Activity;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.model.Project;
import ninja.paranoidandroid.ttm2.util.Constants;

public class AddProject extends AppCompatActivity {

    //UI
    private EditText mName;
    private EditText mDescription;
    private EditText mStartDate;
    private EditText mEndDate;
    private EditText mBudget;
    private EditText mGoals;
    private Button mOk;
    private Button mCanacel;

    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private Project mProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        Log.i(Constants.Log.TAG_ADD_PROJECT, "onCreate() method");

        initFirebaseElements();
        initUIElEments();
        setButtonListeners();

    }

    private void initFirebaseElements(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }

    private void initUIElEments(){

        mName = (EditText) findViewById(R.id.et_activity_add_project_name);
        mDescription = (EditText) findViewById(R.id.et_activity_add_project_description);
        mStartDate = (EditText) findViewById(R.id.et_activity_add_project_start_date);
        mEndDate = (EditText) findViewById(R.id.et_activity_add_project_end_date);
        mBudget = (EditText) findViewById(R.id.et_activity_add_project_budget);
        mGoals = (EditText) findViewById(R.id.et_activity_add_project_goals);
        mOk = (Button) findViewById(R.id.b_activity_add_project_ok);
        mCanacel = (Button) findViewById(R.id.b_activity_add_project_cancel);

    }

    private void setButtonListeners(){

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProject();
                Intent intent = new Intent(AddProject.this, ProjectList.class);
                intent.putExtra(Constants.Extra.ADD_PROJECT_NEW_PROJECT, mProject);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        mCanacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED, null);
                finish();
            }
        });
    }

    private void createProject(){

        String name = mName.getText().toString();
        String description  = mDescription.getText().toString();
        String startDate = mStartDate.getText().toString();
        String endDate = mEndDate.getText().toString();
        double budget = Double.parseDouble(mBudget.getText().toString());
        String goals = mGoals.getText().toString();

        mProject = new Project(name, description, endDate, startDate, budget, null, goals);

    }
}
