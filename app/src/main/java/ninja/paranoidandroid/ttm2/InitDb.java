package ninja.paranoidandroid.ttm2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.model.Project;
import ninja.paranoidandroid.ttm2.model.ProjectTask;
import ninja.paranoidandroid.ttm2.model.ProjectUser;

public class InitDb extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_db);

        initFirebase();
        initProject();
    }


    private void initFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatebaseReference = mFirebaseDatabase.getReference();
    }

    private void initProject(){
        Project project = new Project("First project", "My first project", "22.06.2017", "01.06.2017", 2405, " my result", "my goal");
        ProjectTask projectTask = new ProjectTask("First project", "22.06.2017", "01.06.2017", "desct");
        ProjectUser projectUser = new ProjectUser("Bob");

        DatabaseReference newProjectReference = mDatebaseReference.child("project").push();
        String newProjectKey = newProjectReference.getKey();

        //into project object
        newProjectReference.setValue(project);

        //into projectTask json object
        mDatebaseReference.child("projectTasks/" + newProjectKey).setValue(projectTask);

        //into projectuser json object
        mDatebaseReference.child("projectUsers/" + newProjectKey).setValue(projectUser);


    }

}
