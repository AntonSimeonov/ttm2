package ninja.paranoidandroid.ttm2.model;

/**
 * Created by anton on 03.01.17.
 */

public class ProjectTaskFile {

    private String firebaseStorageRteference;
    private String fileName;

    public ProjectTaskFile(String firebaseStorageRteference, String fileName) {
        this.firebaseStorageRteference = firebaseStorageRteference;
        this.fileName = fileName;
    }

    public ProjectTaskFile() {
    }

    public String getFirebaseStorageRteference() {
        return firebaseStorageRteference;
    }

    public void setFirebaseStorageRteference(String firebaseStorageRteference) {
        this.firebaseStorageRteference = firebaseStorageRteference;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
