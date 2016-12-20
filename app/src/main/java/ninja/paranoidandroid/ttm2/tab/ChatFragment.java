package ninja.paranoidandroid.ttm2.tab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ninja.paranoidandroid.ttm2.ProjectDesk;
import ninja.paranoidandroid.ttm2.R;
import ninja.paranoidandroid.ttm2.model.ChatMessage;
import ninja.paranoidandroid.ttm2.model.Message;
import ninja.paranoidandroid.ttm2.util.Constants;
import ninja.paranoidandroid.ttm2.util.FirebaseQuery;
import ninja.paranoidandroid.ttm2.util.TimeStamp;


public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    //Firebase UI
    private FirebaseListAdapter<ChatMessage> mFirebaseListAdapter;

    //Ui
    private ListView mMessagesListView;
    private EditText mMessageEditText;
    private Button mSendButton;

    //UI row views
    private TextView mMessageText;

    private String mProjectPushId;

    //
    private ProjectDesk mProjectDesk;

    public ChatFragment() {
        // Required empty public constructor
    }



    public static ChatFragment newInstance(int position){
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Fragment.POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public static ChatFragment newInstance(String projectPushId){
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(Constants.Fragment.PROJECT_PUSH_ID, projectPushId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFirebaseElements();

//        Bundle bundle = getArguments();
//        mProjectPushId = bundle.getString(Constants.Fragment.PROJECT_PUSH_ID);
        //Log.i(Constants.Log.TAG_CHAT_FRAGMENT, "In onCreate(), project push id is: " + mProjectPushId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = (View) inflater.inflate(R.layout.fragment_chat, container, false);
        mMessagesListView = (ListView) layout.findViewById(R.id.lv_fragment_chat);
        mMessageEditText = (EditText) layout.findViewById(R.id.et_fragment_chat_message);
        mSendButton = (Button) layout.findViewById(R.id.b_fragment_chat_send);
        setUIListeners();
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onStart() {
        super.onStart();

        initFirebaseElements();

        DatabaseReference referenceWithChatId = mDatabaseReference.child(Constants.Firebase.PROJECT + "/" + getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID) + "/" + Constants.Firebase.PROJECT_CHAT);
        referenceWithChatId.addListenerForSingleValueEvent(new ValueEventListener() {
            // private String chatId;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String chatId = dataSnapshot.getValue(String.class);
                Log.i(Constants.Log.TAG_CHAT_FRAGMENT, "In onDataChange(), chat id is: " + chatId);

                Log.i(Constants.Log.TAG_CHAT_FRAGMENT, "In onDataChange(), concatenated string is: " + Constants.Firebase.CHAT_MESSAGES + "/" + getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID) + "/" + chatId);
                DatabaseReference chatMessagesList = mDatabaseReference.child(Constants.Firebase.CHAT_MESSAGES + "/" + chatId);
                mFirebaseListAdapter = new FirebaseListAdapter<ChatMessage>(mProjectDesk, ChatMessage.class, R.layout.message_listview_row, chatMessagesList) {
                    @Override
                    protected void populateView(View v, ChatMessage model, int position) {

                        mMessageText = (TextView) v.findViewById(R.id.tv_message_listview_row_text);
                        mMessageText.setText(model.getText());
                        Log.i(Constants.Log.TAG_CHAT_FRAGMENT, "In populateView(), model.getText(0 is: " + model.getText());

                    }
                };

                mMessagesListView.setAdapter(mFirebaseListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProjectDesk = (ProjectDesk) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void initFirebaseElements(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    private void setUIListeners(){

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we write mesage to database


                final String messageStr = getCurrentMessage();
                TimeStamp timeStamp = new TimeStamp();
                final String currentTime = timeStamp.getCurrentTime();
                Log.i(Constants.Log.TAG_CHAT_FRAGMENT, "In onStart(), current time is: " + currentTime + "and project push id is: " + getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID));

                DatabaseReference referenceWithChatId = mDatabaseReference.child(Constants.Firebase.PROJECT + "/" + getArguments().getString(Constants.Fragment.PROJECT_PUSH_ID) + "/" + Constants.Firebase.PROJECT_CHAT);
                referenceWithChatId.addListenerForSingleValueEvent(new ValueEventListener() {
                    // private String chatId;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String chatId = dataSnapshot.getValue(String.class);
                        Log.i(Constants.Log.TAG_CHAT_FRAGMENT, "In onDataChange(), chat id is: " + chatId);

                        Message message = new Message(mFirebaseAuth.getCurrentUser().getEmail(), messageStr, currentTime);
                        DatabaseReference newMessageReference = mDatabaseReference.child(Constants.Firebase.MESSAGE).push();
                        String newMessagePushKey = newMessageReference.getKey();
                        newMessageReference.setValue(message);

                        ChatMessage chatMessage = new ChatMessage(mFirebaseAuth.getCurrentUser().getEmail(), messageStr, currentTime);
                        DatabaseReference newChatMessageReferense = mDatabaseReference.child(Constants.Firebase.CHAT_MESSAGES + "/" + chatId + "/" + newMessagePushKey);
                        newChatMessageReferense.setValue(chatMessage);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });


            }
        });

    }

    private String getCurrentMessage(){
        String message = mMessageEditText.getText().toString();
        return message;
    }


}
