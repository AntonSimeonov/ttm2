package ninja.paranoidandroid.ttm2.util;

/**
 * Created by anton on 31.08.16.
 */
public final class Constants {

    private Constants(){

    }

    public static class Log{

        public final static String TAG_WELCOME = "log.WELCOME";
        public final static String TAG_LOGIN = "log.LOGIN";
        public final static String TAG_MAIN_ACTIVITY = "log.MAIN_ACTIVITY";
        public final static String TAG_ADD_PROJECT = "log.ADD_PROJECT";
        public final static String TAG_ADD_TASK = "log.ADD_TASK";
        public final static String TAG_PROJECT_LIST = "log.PROJECT_LIST";
        public final static String TAG_PROJECT_DESK = "log.PROJECT_DESK";
        public final static String TAG_TASK_LIST_FRAGMENT = "log.TASK_LIST_FRAGMENT";
        public final static String TAG_FIREBASE_QUERY = "log.FIREBASE_QUERY";
        public final static String TAG_CHAT_FRAGMENT = "log.CHAT_FRAGMENT";
        public final static String TAG_TASK_INFO_ACTIVITY = "log.TASK_INFO_ACTIVITY";
    }

    public static class Firebase{

        //project
        public final static String PROJECT = "project";
        public final static String PROJECT_USERS = "users";
        public final static String PROJECT_CHAT = "projectChat";
        //user
        public final static String USER = "user";
        public final static String USER_EMAIL = "email";
        public final static String USER_PROJECTS = "projects";
        //task
        public final static String TASK = "task";
        public final static String TASK_STATUS = "status";
        public final static String TASK_REPORT = "report";
        public final static String TASK_START_DATE = "startDate";
        public final static String TASK_END_DATE = "endDate";
        public final static String TASK_BUDGET = "budget";
        public final static String TASK_NOTE = "note";
        public final static String TASK_PRIORITY = "priority";

        public final static String PROJECT_TASKS = "projectTasks";
        public final static String PROJECT_TASKS_START_DATE = "startDate";
        public final static String PROJECT_TASKS_END_DATE = "endDate";


        public final static String TASK_IMGS = "taskImgs";
        public final static String TASK_VIDS = "taskVids";
        public final static String TASK_USERS = "taskUsers";
        public final static String CHAT = "chat";
        public final static String MESSAGE = "message";
        public final static String CHAT_MESSAGES = "chatMessages";
        public final static String PROJECT_TASK_FILES = "projectTaskFiles";

    }

    public static class FirebaseStorage{

        public final static String URI = "gs://ttm2-77004.appspot.com";

    }

    public static class SubActivity{

        //AddProject
        public final static int CREATE_PROJECT_REQUEST_CODE = 1;
        public final static int CREATE_TASK_REQUEST_CODE = 2;
        public final static int SEARCH_IMG_REQUEST_CODE = 3;
        public final static int SEARCH_VIDEO_REQUEST_CODE = 4;
        public final static int SEARCH_DOCUMENT_REQUEST_CODE = 5;
        public final static int ADD_TASK_DETAILES = 6;

    }

    public static class Extra{
        public final static String NEW_PROJECT = "extra.NEW_PROJECT";
        public final static String CURRENT_PROJECT_KEY = "extra.CURRENT_PROJECT_KEY";
        public final static String ADD_TASK = "extra.NEW_TASK";
        public final static String CHAT_ID = "extra.CHAT_ID";
        public final static String CURRENT_CHAT_KEY = "extra.CURRENT_CHAT_KEY";
        public final static String CURRENT_TASK_KEY = "extra.CURRENT_CHAT_KEY";
        public final static String ADD_TASK_DETAILS = "extra.ADD_TASK_DETAILS";
    }

    public static class Fragment{
        public final static String POSITION = "position";
        public final static String PROJECT_PUSH_ID = "PROJECT_PUSH_ID";
        public final static String CHAT_PUSH_ID = "CHAT_PUSH_ID";
    }

    public static class Dialog{
        public final static String TASK_REPORT_DIALOG_TAG = "TASK_REPORT_DIALOG_TAG";
    }

}
