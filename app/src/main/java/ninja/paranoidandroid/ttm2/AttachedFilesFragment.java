package ninja.paranoidandroid.ttm2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ninja.paranoidandroid.ttm2.model.ProjectTaskFile;
import ninja.paranoidandroid.ttm2.util.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AttachedFilesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttachedFilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttachedFilesFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private String mCurrentProjectPushId;
    private String mCurrentTaskPushId;

    private OnFragmentInteractionListener mListener;

    //UI
    private GridView mAttachedFilesGridView;
    private TextView mFileNameTextView;

    //Firebasedatabase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDtabaseReference;

    //Firebase UI
    private FirebaseListAdapter<ProjectTaskFile> mFileFirebaseListAdapter;

    //Containing activity
    private TaskInfoActivity mTaskInfoActivity;

    public AttachedFilesFragment() {
        // Required empty public constructor
    }

    public static AttachedFilesFragment newInstance(String projectKey, String taskKey) {
        AttachedFilesFragment fragment = new AttachedFilesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Extra.CURRENT_PROJECT_KEY, projectKey);
        args.putString(Constants.Extra.CURRENT_TASK_KEY, taskKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentProjectPushId = getArguments().getString(Constants.Extra.CURRENT_PROJECT_KEY);
            mCurrentTaskPushId = getArguments().getString(Constants.Extra.CURRENT_TASK_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_attached_files, container, false);
        mAttachedFilesGridView = (GridView) view.findViewById(R.id.gv_fragment_attached_files);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mTaskInfoActivity = (TaskInfoActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();

        initFirebasedatabaseElements();

        DatabaseReference databaseReference = mDtabaseReference.child(Constants.Firebase.PROJECT_TASK_FILES + "/" + mCurrentProjectPushId + "/" + mCurrentTaskPushId);
        mFileFirebaseListAdapter = new FirebaseListAdapter<ProjectTaskFile>(mTaskInfoActivity, ProjectTaskFile.class, R.layout.attached_files_listview_row , databaseReference) {
            @Override
            protected void populateView(View v, ProjectTaskFile model, int position) {
                mFileNameTextView = (TextView) v.findViewById(R.id.tv_attached_files_listview_row_file_name);

                mFileNameTextView.setText(model.getFileName());
            }
        };

        mAttachedFilesGridView.setAdapter(mFileFirebaseListAdapter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initFirebasedatabaseElements(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDtabaseReference = mFirebaseDatabase.getReference();
    }
}
