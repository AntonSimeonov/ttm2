package ninja.paranoidandroid.ttm2;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ninja.paranoidandroid.ttm2.model.Task;
import ninja.paranoidandroid.ttm2.tab.ChatFragment;
import ninja.paranoidandroid.ttm2.tab.DrawFragment;
import ninja.paranoidandroid.ttm2.tab.TaskListFragment;
import ninja.paranoidandroid.ttm2.util.Constants;
import ninja.paranoidandroid.ttm2.util.FirebaseQuery;

public class ProjectDesk extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton mFab;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private String mProjectPushId;
    private String mProjectChatPushId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_desk);
        Log.i(Constants.Log.TAG_PROJECT_DESK, "onCreate method");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        extractProjectPushId();
        setProjectChatPushId();
//        // Create the adapter that will return a fragment for each of the three
//        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

//        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProjectDesk.this, AddTask.class);
                startActivityForResult(intent, Constants.SubActivity.CREATE_TASK_REQUEST_CODE);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_desk, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void extractProjectPushId(){
        mProjectPushId = getIntent().getStringExtra(Constants.Extra.CURRENT_PROJECT_KEY);
        //Log.i(Constants.Log.TAG_PROJECT_DESK, "extractProjectPushId() method mProjectPushId is: " + mProjectPushId);

    }

    private void setProjectChatPushId(){
        mProjectChatPushId = FirebaseQuery.getChatId(mProjectPushId);
    }

    public String getProjectChatPushId(){
        return mProjectChatPushId;
    }

    public String getProjectPushId(){
        return mProjectPushId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Constants.SubActivity.CREATE_TASK_REQUEST_CODE == requestCode){
            if(Activity.RESULT_OK == resultCode){
                Log.i(Constants.Log.TAG_PROJECT_DESK, "in onActivityResult() method");

                TaskListFragment taskListFragment = (TaskListFragment) mSectionsPagerAdapter.getItem(0);
                Task task = data.getParcelableExtra(Constants.Extra.ADD_TASK);

                taskListFragment.setTask(task, mProjectPushId);
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:

                    return TaskListFragment.newInstance(position);

                case 1:

                    return ChatFragment.newInstance(position);

                case 2:

                    return DrawFragment.newInstance(position);
                default:

                    break;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Task list";
                case 1:
                    return "Chat";
                case 2:
                    return "Draw";
            }
            return null;
        }
    }
}
