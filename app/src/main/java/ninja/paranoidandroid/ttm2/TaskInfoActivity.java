package ninja.paranoidandroid.ttm2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import ninja.paranoidandroid.ttm2.model.ProjectTaskFile;
import ninja.paranoidandroid.ttm2.model.Task;
import ninja.paranoidandroid.ttm2.util.Constants;


public class TaskInfoActivity extends AppCompatActivity {

    //UI
    private TextView mNameTextView;
    private TextView mDescriptionTextView;
    private TextView mStartDateTextView;
    private TextView mEndDateTextView;
    private TextView mBudgetTextView;
    private TextView mNoteTextView;
    private TextView mReportTextView;
    private TextView mStatusTextView;
    private TextView mPriorityTextView;
    //private FrameLayout mAttachedFilesContainerFrameLayout;
    //FirebaseDatabase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    //Firebase UI

    //FirebaseAuth
    private FirebaseAuth mFirebaseAuth;

    //FirebaseStorage
    private FirebaseStorage mFirebaseStorage;

    private String mCurrentTaskPushid;
    private String mCurrentProjectPushId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        getCurrentTaskPushId();
        getCurrentProjectPushId();
        initFirebaseAuth();
       // initFirebaseStorage();
        initUI();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initFirebaseDatabaseElements();

        mDatabaseReference.child(Constants.Firebase.TASK + "/" + mCurrentTaskPushid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Task task = dataSnapshot.getValue(Task.class);
                mNameTextView.setText(task.getName());
                mDescriptionTextView.setText(task.getDescription());
                mStartDateTextView.setText(task.getStartDate());
                mEndDateTextView.setText(task.getEndDate());
                mBudgetTextView.setText(String.valueOf(task.getBudget()));
                mNoteTextView.setText(task.getNote());
                mReportTextView.setText(task.getReport());
                //mStatusTextView.setText(task.getSt);
                mPriorityTextView.setText(String.valueOf(task.getPriority()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        if(Constants.SubActivity.SEARCH_IMG_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode){

            writeImgToFirebaseStorage(uri);

        }else if(Constants.SubActivity.SEARCH_VIDEO_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode){

            writeVideoToFirebaseStorage(uri);

        }else if(Constants.SubActivity.SEARCH_DOCUMENT_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode){

            writeDocumentToFirebaseStorage(uri);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_current_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.menu_curent_task_save_img:
                performImgSearch();
                return true;
            case R.id.menu_curent_task_save_video:
                performVideoSearch();
                return true;
            case R.id.menu_curent_task_save_document:
                performDocumentSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI(){

        mNameTextView = (TextView) findViewById(R.id.tv_activity_task_info_name);
        mDescriptionTextView = (TextView) findViewById(R.id.tv_activity_task_info_description);
        mStartDateTextView = (TextView) findViewById(R.id.tv_activity_task_info_start_date);
        mEndDateTextView = (TextView) findViewById(R.id.tv_activity_task_info_end_date);
        mBudgetTextView = (TextView) findViewById(R.id.tv_activity_task_info_budget);
        mNoteTextView = (TextView) findViewById(R.id.tv_activity_task_info_note);
        mReportTextView = (TextView) findViewById(R.id.tv_activity_task_info_report);
        mStatusTextView = (TextView) findViewById(R.id.tv_activity_task_info_status);
        mPriorityTextView = (TextView) findViewById(R.id.tv_activity_task_info_report);
        //mAttachedFilesContainerFrameLayout = (FrameLayout) findViewById(R.id.fl_activity_task_info);
        setAttachedFilesFragmetn();

    }

    private void setAttachedFilesFragmetn(){

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(AttachedFilesFragment.newInstance(mCurrentProjectPushId, mCurrentTaskPushid), R.id.fl_activity_task_info);
        fragmentTransaction.add(R.id.fl_activity_task_info, AttachedFilesFragment.newInstance(mCurrentProjectPushId, mCurrentTaskPushid));
        fragmentTransaction.commit();

    }

    private void initFirebaseDatabaseElements(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    private void initFirebaseAuth(){
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void initFirebaseStorage(){
        mFirebaseStorage = FirebaseStorage.getInstance();
    }

    private void getCurrentTaskPushId(){
        mCurrentTaskPushid = getIntent().getStringExtra(Constants.Extra.CURRENT_TASK_KEY);
    }

    private void getCurrentProjectPushId(){
        mCurrentProjectPushId = getIntent().getStringExtra(Constants.Extra.CURRENT_PROJECT_KEY);
    }


    private void performImgSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, Constants.SubActivity.SEARCH_IMG_REQUEST_CODE);
    }

    private void performVideoSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("video/*");

        startActivityForResult(intent, Constants.SubActivity.SEARCH_VIDEO_REQUEST_CODE);
    }

    private void performDocumentSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("application/pdf");

        startActivityForResult(intent, Constants.SubActivity.SEARCH_DOCUMENT_REQUEST_CODE);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void saveBitmapInFirebaseStorage(Bitmap bitmap){


        Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In saveBitmapInFirebaseStorage(), and bitmap toString() is: " + bitmap.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference storageReference = mFirebaseStorage.getReferenceFromUrl(Constants.FirebaseStorage.URI);
        storageReference.child("test.jpg");

        UploadTask uploadTask = storageReference.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onSuccess(), img is upladed.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onFailure(), img is NOT upladed.");
            }
        });
    }

    private void writeImgToFirebaseStorage(Uri uri){
        final String fileName = uri.getLastPathSegment();
        //Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onActivityResult(), file name is: " + fileName);
        mFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = mFirebaseStorage.getReferenceFromUrl(Constants.FirebaseStorage.URI);
        StorageReference testingImg = storageReference.child("task" + "/" + mCurrentTaskPushid + "/" + fileName + ".jpg");
        UploadTask uploadTask = testingImg.putFile(uri);

        //Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onActivityResult(), img name is: " + uri.getLastPathSegment().toString());

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onSuccess(), img is upladed.");

                ProjectTaskFile projectTaskFile = new ProjectTaskFile("task" + "/" + mCurrentTaskPushid + "/" + fileName + ".jpg", fileName + ".jpg");

                DatabaseReference newProjectTaskFileRef = mDatabaseReference.child(Constants.Firebase.PROJECT_TASK_FILES + "/"
                        + mCurrentProjectPushId + "/" + mCurrentTaskPushid).push();
                newProjectTaskFileRef.setValue(projectTaskFile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onFailure(), img is NOT upladed.");
            }
        });
    }

    private void writeVideoToFirebaseStorage(Uri uri){
        final String fileName = uri.getLastPathSegment();
        //Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onActivityResult(), file name is: " + fileName);
        mFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = mFirebaseStorage.getReferenceFromUrl(Constants.FirebaseStorage.URI);
        StorageReference testingImg = storageReference.child("task" + "/" + mCurrentTaskPushid + "/" + fileName);
        UploadTask uploadTask = testingImg.putFile(uri);

        //Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onActivityResult(), img name is: " + uri.getLastPathSegment().toString());

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onSuccess(), img is upladed.");

                ProjectTaskFile projectTaskFile = new ProjectTaskFile("task" + "/" + mCurrentTaskPushid + "/" + fileName + ".jpg", fileName + ".jpg");

                DatabaseReference newProjectTaskFileRef = mDatabaseReference.child(Constants.Firebase.PROJECT_TASK_FILES + "/"
                        + mCurrentProjectPushId + "/" + mCurrentTaskPushid).push();
                newProjectTaskFileRef.setValue(projectTaskFile);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onFailure(), img is NOT upladed.");
            }
        });
    }

    private void writeDocumentToFirebaseStorage(Uri uri){
        final String fileName = uri.getLastPathSegment();
        //Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onActivityResult(), file name is: " + fileName);
        mFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = mFirebaseStorage.getReferenceFromUrl(Constants.FirebaseStorage.URI);
        StorageReference testingImg = storageReference.child("task" + "/" + mCurrentTaskPushid + "/" + fileName);
        UploadTask uploadTask = testingImg.putFile(uri);

        //Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onActivityResult(), img name is: " + uri.getLastPathSegment().toString());

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onSuccess(), img is upladed.");

                ProjectTaskFile projectTaskFile = new ProjectTaskFile("task" + "/" + mCurrentTaskPushid + "/" + fileName + ".jpg", fileName + ".jpg");

                DatabaseReference newProjectTaskFileRef = mDatabaseReference.child(Constants.Firebase.PROJECT_TASK_FILES + "/"
                        + mCurrentProjectPushId + "/" + mCurrentTaskPushid).push();
                newProjectTaskFileRef.setValue(projectTaskFile);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(Constants.Log.TAG_TASK_INFO_ACTIVITY, "In onFailure(), img is NOT upladed.");
            }
        });
    }

}
