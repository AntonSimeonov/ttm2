package ninja.paranoidandroid.ttm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.model.Task;
import ninja.paranoidandroid.ttm2.util.Constants;


public class AddTask extends AppCompatActivity {

    //UI
    private EditText mName;
    private EditText mDescription;
    private EditText mStartDate;
    private EditText mEndDate;
    private EditText mBudget;
    private EditText mNote;
    private EditText mReport;
    private EditText mStatus;
    private EditText mPriority;
    private Button mOk;
    private Button mCancel;

    private Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Log.i(Constants.Log.TAG_ADD_TASK, "onCreate() method");
        initUI();

    }

    private void initUI(){

        mName = (EditText) findViewById(R.id.et_activity_add_task_name);
        mDescription = (EditText) findViewById(R.id.et_activity_add_task_description);
//        mStartDate = (EditText) findViewById(R.id.et_activity_add_task_start_date);
//        mEndDate = (EditText) findViewById(R.id.et_activity_add_task_end_date);
//        mBudget = (EditText) findViewById(R.id.et_activity_add_task_budget);
//        mNote = (EditText) findViewById(R.id.et_activity_add_task_note);
//        mReport = (EditText) findViewById(R.id.et_activity_add_task_report);
//        mStatus = (EditText) findViewById(R.id.et_activity_add_task_status);
//        mPriority = (EditText) findViewById(R.id.et_activity_add_task_priority);
        mOk = (Button) findViewById(R.id.b_activity_add_task_ok);
        mCancel = (Button) findViewById(R.id.b_activity_add_task_cancel);

        setClickListeners();

    }

    private void setClickListeners(){

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(Constants.Log.TAG_ADD_TASK, "ok button is clicked");

                createNewTask();

                Intent intent  = new Intent(AddTask.this, ProjectDesk.class);
                intent.putExtra(Constants.Extra.ADD_TASK, mTask);

                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void createNewTask(){

        String name = mName.getText().toString();
        String description = mDescription.getText().toString();
//        String startDate =  mStartDate.getText().toString();
//        String endDate = mEndDate.getText().toString();
//        double budget = Double.parseDouble(mBudget.getText().toString());
//        String note = mNote.getText().toString();
//        String report = mReport.getText().toString();
//        boolean status = false;
//        int priority = Integer.parseInt(mPriority.getText().toString());
        String startDate = "init";
        String endDate = "No finish date.";
        double budget = 0;
        String note = "init";
        String report = "init";
        boolean status = false;
        int priority = 1;

        mTask = new Task(name, description, startDate, endDate, budget, note, report, status, priority);

    }
}
