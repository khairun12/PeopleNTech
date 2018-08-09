package com.skh.peoplentech.peoplentech.Config;

/**
 * Created by samir on 4/15/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import static android.os.Build.VERSION.SDK;

public class FileChooser {
    private static final String PARENT_DIR = "..";

    private final Activity activity;
    private ListView list;
    private Dialog dialog;
    private File currentPath;

    // filter on file extension
    private String extension = null;

    public void setExtension(String extension) {
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
    }

    // file selection event handling
    public interface FileSelectedListener {
        void fileSelected(File file);
    }

    public FileChooser setFileListener(FileSelectedListener fileListener) {
        this.fileListener = fileListener;
        return this;
    }

    private FileSelectedListener fileListener;

    public FileChooser(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        list = new ListView(activity);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                String fileChosen = (String) list.getItemAtPosition(which);
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    refresh(chosenFile);
                } else {
                    if (fileListener != null) {
                        fileListener.fileSelected(chosenFile);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(list);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);


        refresh(Environment.getExternalStorageDirectory());


    }

    public void showDialog() {
        dialog.show();
    }


    /**
     * Sort, filter and display the files for the given path.
     */
    private void refresh(File path) {

     /*   // this.currentPath = activity.getFilesDir();
        if (path.getPath().equals("/storage/emulated/0")) {
            path = getInternalDownlodPath();
        }
*/
        this.currentPath = path;


        if (path.exists() && path.isDirectory()) {
            File[] dirs = path.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isDirectory() && file.canRead());
                }
            });
            File[] files = path.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (!file.isDirectory()) {
                        if (!file.canRead()) {
                            return false;
                        } else if (extension == null) {
                            return true;
                        } else {
                            return file.getName().toLowerCase().endsWith(extension);
                        }
                    } else {
                        return false;
                    }
                }
            });

            /*// convert to an array
            int i = 0;
            String[] fileList;
            if (path.getParentFile() == null) {
                fileList = new String[dirs.length + files.length];
            } else {
                fileList = new String[dirs.length + files.length + 1];
                fileList[i++] = PARENT_DIR;
            }
            Arrays.sort(dirs);
            Arrays.sort(files);
            for (File dir : dirs) { fileList[i++] = dir.getName(); }
            for (File file : files ) { fileList[i++] = file.getName(); }
*/

            // convert to an array
            int i = 0;
            int j = 0;
            int k = 0;
            if (dirs != null && dirs.length > 0)
                j = dirs.length;
            if (files != null && files.length > 0)
                k = files.length;
            String[] fileList;
            if (path.getParentFile() == null) {
                fileList = new String[j + k];
            } else {
                //fileList = new String[dirs.length + files.length + 1];
                fileList = new String[j + k + 1];
                fileList[i++] = PARENT_DIR;
            }
            if (dirs != null && dirs.length > 0) {
                Arrays.sort(dirs);
                for (File dir : dirs) {
                    fileList[i++] = dir.getName();
                }
            }
            if (files != null && files.length > 0) {
                Arrays.sort(files);
                for (File file : files) {
                    fileList[i++] = file.getName();
                }
            }


            // refresh the user interface
            dialog.setTitle(currentPath.getPath());

            Log.i("SKH", currentPath.getPath());

           // Log.i("SKH", currentPath.getPath());
           // Log.i("SKH", getInternalDownlodPath().getPath());



            /*if(Build.VERSION_CODES.KITKAT>=Build.VERSION.SDK_INT){
              String s=   FilePath.getPath(activity,activity.getIntent().getData());
                Log.i("SKH",s);

            }*/
            list.setAdapter(new ArrayAdapter(activity,
                    android.R.layout.simple_list_item_1, fileList) {
                @Override
                public View getView(int pos, View view, ViewGroup parent) {
                    view = super.getView(pos, view, parent);
                    ((TextView) view).setSingleLine(true);
                    return view;
                }
            });
        }
    }

    private File getInternalDownlodPath() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath + "/Download");
        if (!file.exists()) {
            file.mkdirs();
        }
        // return file.getAbsolutePath() + "";
        return file;
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath + "/Downloads");
        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4");
    }

    /**
     * Convert a relative filename into an actual File object.
     */
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) {
            return currentPath.getParentFile();
        } else {
            return new File(currentPath, fileChosen);
        }
    }
}