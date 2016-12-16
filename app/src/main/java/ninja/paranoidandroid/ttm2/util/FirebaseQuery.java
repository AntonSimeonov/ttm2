package ninja.paranoidandroid.ttm2.util;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by anton on 16.12.16.
 */

public class FirebaseQuery {

    public static String getChatId(String projectPushId){

        //final String chatId = null;

        final StringTransport stringTransport = new StringTransport();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        //Log.i(Constants.Log.TAG_FIREBASE_QUERY, "child item is: " + Constants.Firebase.PROJECT + "/" + projectPushId + Constants.Firebase.PROJECT_CHAT);
        DatabaseReference referenceWithChatId = databaseReference.child(Constants.Firebase.PROJECT + "/" + projectPushId + "/" + Constants.Firebase.PROJECT_CHAT);
        referenceWithChatId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String chatId = dataSnapshot.getValue(String.class);
                stringTransport.setmChatId(chatId);
                //Log.i(Constants.Log.TAG_FIREBASE_QUERY, "query for chat id: " + chatId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return stringTransport.getmChatId();
    }

    private static class StringTransport{

        private String mChatId;

        public StringTransport(String mChatId) {
            this.mChatId = mChatId;
        }

        public StringTransport() {
        }

        public String getmChatId() {
            return mChatId;
        }

        public void setmChatId(String mChatId) {
            this.mChatId = mChatId;
        }
    }

}
