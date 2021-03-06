package ninja.paranoidandroid.ttm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.model.Chat;
import ninja.paranoidandroid.ttm2.model.Project;
import ninja.paranoidandroid.ttm2.model.ProjectChat;
import ninja.paranoidandroid.ttm2.model.ProjectUser;
import ninja.paranoidandroid.ttm2.model.UserProject;
import ninja.paranoidandroid.ttm2.util.Constants;
import ninja.paranoidandroid.ttm2.util.FirebaseQuery;

public class ProjectList extends AppCompatActivity {

    //firebase api
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    //firebase ui
    private FirebaseListAdapter<UserProject> mProjectListAdapter;

    //UI
    private ListView mProjectListView;
    private TextView mProjectNameTextView;

    //User data
    private String mUserFirebaseAuthId;

    private String mPickedprojectChatPushId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        Log.i(Constants.Log.TAG_PROJECT_LIST, "onCreate()");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFirebaseElements();
        getFirebaseAuthUserId();
        initUIElements();
        setListViewOnItemClickListener();

        if(mFirebaseAuth.getCurrentUser() == null){
            //go to login screen
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new project

                Intent intent = new Intent(ProjectList.this, AddProject.class);
                startActivityForResult(intent, Constants.SubActivity.CREATE_PROJECT_REQUEST_CODE);
                Log.i(Constants.Log.TAG_PROJECT_LIST, "after fab is clicked");
            }
        });


    }

    private void initFirebaseElements(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    private void initUIElements(){

        mProjectListView = (ListView) findViewById(R.id.lv_content_project_list);

    }

    private void setFirebaseUI(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Constants.SubActivity.CREATE_PROJECT_REQUEST_CODE == requestCode){

            if(Activity.RESULT_OK == resultCode){
                writeNewProjectToFireBase(data);
            }else if(Activity.RESULT_CANCELED == resultCode){

            }

        }
    }

    private void writeNewProjectToFireBase(Intent intent){
        Project project = null;
        project = intent.getParcelableExtra(Constants.Extra.NEW_PROJECT);
        if(project != null) {

           //project = new Project("Proj name", "project description", "22.4.4", "3434", 3535, "result", "hjsdfj");
            UserProject newUserProject = new UserProject(project.getName());
            ProjectUser newProjectUser = new ProjectUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            //project
            DatabaseReference newProjectFirebaseReference = mDatabaseReference.child(Constants.Firebase.PROJECT).push();
            String newProjectFirebasePushKey = newProjectFirebaseReference.getKey();
            newProjectFirebaseReference.setValue(project);

            //in user projects node
            DatabaseReference newUserProjectFirebaseReference = mDatabaseReference.child(Constants.Firebase.USER + "/" + mUserFirebaseAuthId)
                    .child(Constants.Firebase.USER_PROJECTS + "/" + newProjectFirebasePushKey);
            newUserProjectFirebaseReference.setValue(newUserProject);

            //in project users node
            mDatabaseReference.child(Constants.Firebase.PROJECT + "/" + newProjectFirebasePushKey + "/" +
                    Constants.Firebase.PROJECT_USERS + "/" + mUserFirebaseAuthId).setValue(newProjectUser);
            //CHAT setup
            //in chat node
            DatabaseReference newChatReference = mDatabaseReference.child(Constants.Firebase.CHAT).push();
            String newChatFirebasePushKey = newChatReference.getKey();
            newChatReference.setValue(new Chat(project.getName()));

            //in project chat node
            mDatabaseReference.child(Constants.Firebase.PROJECT + "/" + newProjectFirebasePushKey + "/" + Constants.Firebase.PROJECT_CHAT)
                    .setValue(newChatFirebasePushKey);

            // in chat messageds node
            //mDatabaseReference.child(Constants.Firebase.CHAT_MESSAGES + "/" + newProjectFirebasePushKey + "/" + newChatFirebasePushKey);
        }
        }

    private void getFirebaseAuthUserId(){
       mUserFirebaseAuthId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getPickedprojectChatPushId(){
        return mPickedprojectChatPushId;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.i(Constants.Log.TAG_PROJECT_LIST, "path is " + Constants.Firebase.USER + "/" + mUserFirebaseAuthId + "/" + Constants.Firebase.USER_PROJECTS);

        showProjectList();
    }

    private void showProjectList(){
        //Log.i(Constants.Log.TAG_PROJECT_LIST, "path is " + Constants.Firebase.USER + "/" + mUserFirebaseAuthId + "/" + Constants.Firebase.USER_PROJECTS);

        DatabaseReference userProjectReference = mDatabaseReference.child(Constants.Firebase.USER + "/" + mUserFirebaseAuthId + "/" + Constants.Firebase.USER_PROJECTS);
        mProjectListAdapter = new FirebaseListAdapter<UserProject>(this, UserProject.class, R.layout.project_listview_row, userProjectReference) {
            @Override
            protected void populateView(View v, UserProject model, int position) {
                Log.i(Constants.Log.TAG_PROJECT_LIST, " get model value " + model.getName());
                mProjectNameTextView = (TextView) v.findViewById(R.id.tv_project_listview_row_name);
                mProjectNameTextView.setText(model.getName());
            }
        };
        mProjectListView.setAdapter(mProjectListAdapter);
    }

    private void setListViewOnItemClickListener(){

        mProjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String projectKey = mProjectListAdapter.getRef(i).getKey();

                //mPickedprojectChatPushId = new FirebaseQuery().extractChatId(projectKey);

                //Log.i(Constants.Log.TAG_PROJECT_LIST, "chat key is :" + mPickedprojectChatPushId);

                Intent intent  = new Intent(ProjectList.this, ProjectDesk.class);
                intent.putExtra(Constants.Extra.CURRENT_PROJECT_KEY, projectKey);
               // intent.putExtra(Constants.Extra.CURRENT_CHAT_KEY, chatKey);
                startActivity(intent);
            }
        });

    }

}
