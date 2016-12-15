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
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.ProjectDesk;
import ninja.paranoidandroid.ttm2.R;
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

    //Activity reference
    private ProjectDesk mProjectDesk;
    private String mProjectPushId;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        mTaskListView = (ListView) view.findViewById(R.id.lv_fragment_task_list);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onStart() {
        super.onStart();
        //Here we implement
        DatabaseReference projectTasksReference = mDatabaseReference.child(Constants.Firebase.PROJECT_TASKS + "/" + mProjectDesk.getProjectPushId());
        mFirebaseListAdapter = new FirebaseListAdapter<ProjectTask>(mProjectDesk, ProjectTask.class, R.layout.task_listview_row, projectTasksReference) {
            @Override
            protected void populateView(View v, ProjectTask model, int position) {

                mNameTextView = (TextView) v.findViewById(R.id.tv_task_listview_row_name);
                mNameTextView.setText(model.getName());

            }
        };
        mTaskListView.setAdapter(mFirebaseListAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mProjectDesk = (ProjectDesk) context;
        mProjectPushId = mProjectDesk.getProjectPushId();
        Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "onAttach() method mProjectPushId is: " + mProjectPushId);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setTask(Task task, String projectPushId){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        if(mDatabaseReference == null){
            Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "mDatabseReference is null!!!");
        }else{
            Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "mDatabseReference is NOT null!!!");
            mTask = task;
            ProjectTask projectTask = new ProjectTask(mTask.getName(), mTask.getStartDate(), mTask.getEndDate());
            DatabaseReference newTaskReference = mDatabaseReference.child(Constants.Firebase.TASK).push();
            String newTaskPushKey = newTaskReference.getKey();
            newTaskReference.setValue(mTask);

            Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "project push id is: " + projectPushId);

            //Log.i(Constants.Log.TAG_TASK_LIST_FRAGMENT, "conacat value is: " + Constants.Firebase.PROJECT_TASKS + "/" + projectPushId + "/" + newTaskPushKey);
            DatabaseReference newProjectTaskReference = mDatabaseReference.child(Constants.Firebase.PROJECT_TASKS + "/" + projectPushId + "/" + newTaskPushKey);
            newProjectTaskReference.setValue(projectTask);

        }


    }
}
