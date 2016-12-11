package ninja.paranoidandroid.ttm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.model.Project;
import ninja.paranoidandroid.ttm2.model.ProjectUser;
import ninja.paranoidandroid.ttm2.model.UserProject;
import ninja.paranoidandroid.ttm2.util.Constants;

public class ProjectList extends AppCompatActivity {

    //firebase api
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    //firebase ui
    private FirebaseListAdapter<Project> mProjectListAdapter;

    //UI
    private ListView mProjectListView;

    //User data
    private String mUserFirebaseAuthId;

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
       // project = intent.getParcelableExtra(Constants.Extra.ADD_PROJECT_NEW_PROJECT);
        if(project == null) {

            project = new Project("Proj name", "project description", "22.4.4", "3434", 3535, "result", "hjsdfj");
            UserProject newUserProject = new UserProject(project.getName());
            ProjectUser newProjectUser = new ProjectUser(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            //project
            DatabaseReference newProjectFirebaseReference = mDatabaseReference.child(Constants.Firebase.PROJECT).push();
            String newProjectFirebasePushKey = newProjectFirebaseReference.getKey();
            newProjectFirebaseReference.setValue(project);

            //in user projects note
            DatabaseReference newUserProjectFirebaseReference = mDatabaseReference.child(Constants.Firebase.USER + "/" + mUserFirebaseAuthId)
                    .child(Constants.Firebase.USER_PROJECTS + "/" + newProjectFirebasePushKey);
            newUserProjectFirebaseReference.setValue(newUserProject);

            //in project users note
            mDatabaseReference.child(Constants.Firebase.PROJECT + "/" + newProjectFirebasePushKey + "/" +
                    Constants.Firebase.PROJECT_USERS + "/" + mUserFirebaseAuthId).setValue(newProjectUser);
        }
        }

    private void getFirebaseAuthUserId(){
       mUserFirebaseAuthId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
