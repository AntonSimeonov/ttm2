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
 * Use the {@link FilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilesFragment extends Fragment {


    public FilesFragment() {
        // Required empty public constructor
    }


    public static FilesFragment newInstance(String projectPushId){
        FilesFragment fragment = new FilesFragment();
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
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

}
