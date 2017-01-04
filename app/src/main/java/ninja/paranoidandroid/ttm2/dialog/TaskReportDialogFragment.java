package ninja.paranoidandroid.ttm2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.R;
import ninja.paranoidandroid.ttm2.util.Constants;

/**
 * Created by anton on 04.01.17.
 */

public class TaskReportDialogFragment extends DialogFragment {

    //Firebasedatabase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    EditText mTaskReportEditText;

    public static TaskReportDialogFragment newInstance(String projectKey, String taskKey) {
        TaskReportDialogFragment fragment = new TaskReportDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.CURRENT_PROJECT_KEY, projectKey);
        args.putString(Constants.Extra.CURRENT_TASK_KEY, taskKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        initFirebaseDatabaseElements();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_task_report, null);
        mTaskReportEditText = (EditText) view.findViewById(R.id.et_dialog_task_report);

        builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String reportString = mTaskReportEditText.getText().toString();

                mDatabaseReference.child(Constants.Firebase.TASK + "/" + getArguments().getString(Constants.Extra.CURRENT_TASK_KEY) + "/"
                        + Constants.Firebase.REPORT).setValue(reportString);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    private void initFirebaseDatabaseElements(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }
}
