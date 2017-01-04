package ninja.paranoidandroid.ttm2.tab;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.ProjectDesk;
import ninja.paranoidandroid.ttm2.R;
import ninja.paranoidandroid.ttm2.TaskInfoActivity;
import ninja.paranoidandroid.ttm2.model.ProjectTask;
import ninja.paranoidandroid.ttm2.model.Task;
import ninja.paranoidandroid.ttm2.util.Constants;


public class TaskListFragment extends Fragment {

    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;


    //Firebase UI
    private FirebaseListAdapter<ProjectTask> mFirebaseListAdapter;

    //UI
    private ListView mTaskListView;
    private TextView mNameTextView;
    private TextView mNumberTextView;
    private TextView mDescriptionTextView;
    private TextView mEndDateTextView;

    //Activity reference
    private ProjectDesk mProjectDesk;
    private String mProjectPushId;
    //private String mChatPushId;

    private Task mTask;

    public TaskListFragment() {
        // Required empty public constructor
    }


    public static TaskListFragment newInstance(int position){
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Fragment.POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskListFragment newInstance(String projectPushId){
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Fragment.PROJECT_PUSH_ID, projectPushId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference();

//        Bundle bundle = getArguments();
//        mProjectPushId = bundle.getString(Constants.Fragment.PROJECT_PUSH_ID);
        //mChatPushId = bundle.getString(Constants.Fragment.CHAT_PUSH_ID);

        //Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "In onCreate(), project push id is: " + mProjectPushId);
       // Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "In onCreate(), chat push id is: " + mChatPushId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        mTaskListView = (ListView) view.findViewById(R.id.lv_fragment_task_list);
        setOnItemClickListener();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Here we implement

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        DatabaseReference projectTasksReference = mDatabaseReference.child(Constants.Firebase.PROJECT_TASKS + "/" + getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID));
        mFirebaseListAdapter = new FirebaseListAdapter<ProjectTask>(mProjectDesk, ProjectTask.class, R.layout.task_listview_row, projectTasksReference) {
            @Override
            protected void populateView(View v, ProjectTask model, int position) {



                mNameTextView = (TextView) v.findViewById(R.id.tv_task_listview_row_name);
                mNumberTextView = (TextView) v.findViewById(R.id.tv_task_listview_row_number);
                mDescriptionTextView = (TextView) v.findViewById(R.id.tv_task_listview_row_description);
                mEndDateTextView = (TextView) v.findViewById(R.id.tv_task_listview_row_end_date);

                mNameTextView.setText(model.getName());
                mEndDateTextView.setText(model.getEndDate());

                int taskNumber = position + 1;
                mNumberTextView.setText(taskNumber + ". ");




                String descriptionString = model.getDescription();
                if(descriptionString != null){
                    if(descriptionString.length() >= 30){
                        mDescriptionTextView.setText(descriptionString.substring(0, 30) + "...");
                    }else{
                        mDescriptionTextView.setText(descriptionString);
                    }

                }
            }
        };
        mTaskListView.setAdapter(mFirebaseListAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mProjectDesk = (ProjectDesk) context;
//        mProjectPushId = mProjectDesk.getProjectPushId();
//        Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "onAttach() method mProjectPushId is: " + mProjectPushId);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setTask(Task task){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        if(mDatabaseReference == null){
            Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "mDatabseReference is null!!!");
        }else{
            Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "mDatabseReference is NOT null!!!");
            mTask = task;
            ProjectTask projectTask = new ProjectTask(mTask.getName(), mTask.getStartDate(), mTask.getEndDate(), mTask.getDescription());
            DatabaseReference newTaskReference = mDatabaseReference.child(Constants.Firebase.TASK).push();
            String newTaskPushKey = newTaskReference.getKey();
            newTaskReference.setValue(mTask);

            //Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "In setTask(), project push id is: " + getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID));
            //Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "In setTask(), chat push id is: " + mChatPushId);

            //Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "conacat value is: " + Constants.Firebase.PROJECT_TASKS + "/" + mProjectPushId + "/" + newTaskPushKey);
            DatabaseReference newProjectTaskReference = mDatabaseReference.child(Constants.Firebase.PROJECT_TASKS + "/" + getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID) + "/" + newTaskPushKey);
            newProjectTaskReference.setValue(projectTask);

        }
    }

    private void setOnItemClickListener(){

        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String taskKey = mFirebaseListAdapter.getRef(position).getKey();

                Intent intent = new Intent(mProjectDesk, TaskInfoActivity.class);
                intent.putExtra(Constants.Extra.CURRENT_PROJECT_KEY, getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID));
                intent.putExtra(Constants.Extra.CURRENT_TASK_KEY, taskKey);
                startActivity(intent);

            }
        });

    }

}
