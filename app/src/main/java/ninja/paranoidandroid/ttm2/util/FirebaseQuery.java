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

    private static String mChatId;

    public String getChatId(){
        return mChatId;
    }


    public String extractChatId(String projectPushId){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        final ValContainer<String> chatIdContainer = new ValContainer<>();

        //Log.i(Constants.Log.TAG_FIREBASE_QUERY, "child item is: " + Constants.Firebase.PROJECT + "/" + projectPushId + Constants.Firebase.PROJECT_CHAT);
        DatabaseReference referenceWithChatId = databaseReference.child(Constants.Firebase.PROJECT + "/" + projectPushId + "/" + Constants.Firebase.PROJECT_CHAT);
        referenceWithChatId.addListenerForSingleValueEvent(new ValueEventListener() {
           // private String chatId;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseQuery.mChatId = dataSnapshot.getValue(String.class);
                chatIdContainer.setVal(mChatId);
                Log.i(Constants.Log.TAG_FIREBASE_QUERY, "In onDataChange(), chat id: " + chatIdContainer.getVal());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            public String getChatId(){

                return null;
            }
        });

        Log.i(Constants.Log.TAG_FIREBASE_QUERY, "In extractChatId(), chat id: " + FirebaseQuery.mChatId);
        return mChatId;
    }

    public class ValContainer<T> {
        private T val;

        public ValContainer() {
        }

        public ValContainer(T v) {
            this.val = v;
        }

        public T getVal() {
            return val;
        }

        public void setVal(T val) {
            this.val = val;
        }
    }

}
