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

    }

    public static class Firebase{

        //project
        public final static String PROJECT = "project";
        public final static String PROJECT_USERS = "users";
        //user
        public final static String USER = "user";
        public final static String USER_EMAIL = "email";
        public final static String USER_PROJECTS = "projects";
        //task
        public final static String TASK = "task";


        public final static String PROJECT_TASKS = "projectTasks";
        public final static String TASK_IMGS = "taskImgs";
        public final static String TASK_VIDS = "taskVids";
        public final static String TASK_USERS = "taskUsers";
        public final static String CHAT = "chat";
        public final static String MESSAGE = "message";
        public final static String CHAT_MESSAGES = "chatMessages";

    }

    public static class SubActivity{

        //AddProject
        public final static int CREATE_PROJECT_REQUEST_CODE = 1;

    }

    public static class Extra{
        public final static String ADD_PROJECT_NEW_PROJECT = "extra.NEW_PROJECT";
    }

}
