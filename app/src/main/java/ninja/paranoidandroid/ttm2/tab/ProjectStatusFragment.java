package ninja.paranoidandroid.ttm2.tab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ninja.paranoidandroid.ttm2.R;
import ninja.paranoidandroid.ttm2.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectStatusFragment extends Fragment {



    public ProjectStatusFragment() {
        // Required empty public constructor
    }


    public static ProjectStatusFragment newInstance(String projectPushId){
        ProjectStatusFragment fragment = new ProjectStatusFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Fragment.PROJECT_PUSH_ID, projectPushId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_status, container, false);
    }

}
