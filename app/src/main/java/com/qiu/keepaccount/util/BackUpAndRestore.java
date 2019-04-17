package com.qiu.keepaccount.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.keepaccount.KeepAccount;
import com.qiu.keepaccount.R;
import com.qiu.keepaccount.ui.dialog.FolderPicker;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BackUpAndRestore {
    private FolderPicker mFolderDialog;
    public String backupPath, restorePath;
    private TextView description;
    private EditText editPath;
    private Button btnBrowse;
    private CheckBox checkBoxSend;
    private Context context;
    private SharedPreferences saveData;
    private SharedPreferences.Editor mEditor;
    private boolean isBackup;//判断当前是否执行了备份操作
    private IListenisBackup Ilistener;

    public BackUpAndRestore(Context context, SharedPreferences saveData) {
        this.context = context;
        this.saveData = saveData;
        mEditor = saveData.edit();
    }
    //判断当前是否备份完毕需要刷新界面
    public void refreshdata(){
        Log.i("isBackup",isBackup+"");
        Log.i("Ilistener",Ilistener+"");
        if (this.isBackup == true && Ilistener!=null){
            this.Ilistener.refresh();
        }
    }
    //为Ilistener添加监听
    public void setIlistener(IListenisBackup backup){
        this.Ilistener = backup;
    }

    /**
     * @param type                    表示类型 * @param prompt提示信息
     * @param activity
     * @category 备份还原对话框
     */
    public void backupAndRestoreData(int type, String prompt, final boolean firstUse,
                                     final String fileSize, final String fileChangeTime,
                                     final Activity activity) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View outputView = factory.inflate(R.layout.fragment_back_up, null);
        btnBrowse = (Button) outputView.findViewById(R.id.brower);
        editPath = (EditText) outputView.findViewById(R.id.path);
        description = (TextView) outputView.findViewById(R.id.description);
        description.setText(prompt);
        String defaultPath = BackUpAndRestore.getStoragePath(context,true) + "/ServerMonitor";
        switch (type) {
            case 0:
                // 0代表backup,表示备份数据
                editPath.setText(saveData.getString(KeepAccount.BACKUP_PATH,
                        defaultPath));
                backupPath = editPath.getText().toString();
                btnBrowse.setOnClickListener(v -> {
                    if (!new File(BackUpAndRestore.getStoragePath(context,true)).canWrite()) {
                        // 如果无sd
                        Toast.makeText(context,
                                "无SD卡",
                                Toast.LENGTH_LONG).show();
                    } else {

                        mFolderDialog = new FolderPicker(
                                context, (dialog, which) -> {
                            backupPath = mFolderDialog.getPath();
                            Toast.makeText(context,
                                    mFolderDialog.getPath(),
                                    Toast.LENGTH_LONG).show();
                            editPath.setText(backupPath);
                        }, 0);
                        mFolderDialog.show();
                    }
                });
                // 对话框显示
                new AlertDialog.Builder(context)
                        .setTitle("数据备份")
                        .setView(outputView)
                        .setNegativeButton("取消", (dialoginterface, i) -> {

                        })
                        .setPositiveButton("确定", (dialoginterface, i) -> {
                            mEditor.putString(
                                    KeepAccount.BACKUP_PATH,
                                    editPath.getText().toString());
                            mEditor.commit();
                            //dataBackup(editPath.getText().toString());
                           /* BackUpAndRestoreSetting.BackUpAndRestoreFragment.mBackupPref
                                    .setSummary(context.getString(R.string.backup_to)
                                            + editPath.getText()
                                            .toString());*/
                        }).show();
                break;
            case 1:
                // 还原
                // 1代表restored data,恢复并覆盖当前的数据
                // 隐藏checkbox
                checkBoxSend.setVisibility(View.GONE);
                restorePath = saveData.getString(KeepAccount.RESTORE_PATH,
                        defaultPath);
                String dbRestorePath = restorePath;
                String settingsRestorePath = restorePath + "/"
                        + "com.luckyxmobile.servermonitorplus.xml";
                File dbFile = new File(dbRestorePath);
                File settingsFile = new File(settingsRestorePath);
                if (dbFile.exists() || settingsFile.exists()) {
                    editPath.setText(restorePath);
                } else {
                   // editPath.setText(context.getString(R.string.no_file_select));
                }
                btnBrowse.setOnClickListener(v -> {
                    if (!new File(BackUpAndRestore.getStoragePath(context,true)).canRead()) {
                        // 如果无sd
                        Toast.makeText(context,
                                "无SD卡",
                                Toast.LENGTH_LONG).show();
                    } else {
                        mFolderDialog = new FolderPicker(
                                context, (dialog, which) -> {
                            String path = mFolderDialog.getPath();
                            if (path == null) {
                               /* Toast.makeText(
                                        context,
                                        context.getString(R.string.no_file_select),
                                        Toast.LENGTH_LONG).show();*/
                            } else {
                                restorePath = path;
                                editPath.setText(restorePath);
                                Toast.makeText(context,
                                        restorePath,
                                        Toast.LENGTH_LONG).show();
                            }
                        }, 0, true);
                        mFolderDialog.show();
                    }
                });
                // 对话框显示
                if (firstUse) {
                    Boolean a = editPath.getText().toString()
                            .endsWith(".db");
                    Boolean b = editPath.getText().toString()
                            .endsWith(".xml");
                    String dbRestorePathfirst = null, settingsRestorePathfirst = null;
                    File dbRestoreFile = null, settingsRestoreFile = null;
                    if (a) {
                        dbRestorePathfirst = editPath.getText()
                                .toString();
                        dbRestoreFile = new File(dbRestorePathfirst);
                    } else if (b) {
                        settingsRestorePathfirst = editPath
                                .getText().toString();
                        settingsRestoreFile = new File(
                                settingsRestorePathfirst);
                    } else if (!a && !b) {
                       /* dbRestorePathfirst = editPath.getText()
                                .toString()
                                + "/"
                                + DataBaseAdapter.DATABASE_NAME;
                        settingsRestorePathfirst = editPath
                                .getText().toString()
                                + "/"
                                + SettingsActivity.PREFS_NAME;
                        dbRestoreFile = new File(dbRestorePathfirst);
                        settingsRestoreFile = new File(
                                settingsRestorePathfirst);*/
                    }
                    boolean dbRestoreFileExists = false, settingsRestoreFileExists = false;
                    if (dbRestoreFile != null) {
                        dbRestoreFileExists = dbRestoreFile
                                .exists();
                    }
                    if (settingsRestoreFile != null) {
                        settingsRestoreFileExists = settingsRestoreFile
                                .exists();
                    }
                    if (dbRestoreFileExists
                            || settingsRestoreFileExists) {
                     /*   determineWhetherRestoreData(
                                dbRestorePathfirst, settingsRestorePathfirst,
                                firstUse, fileSize, fileChangeTime, activity);*/
                    }
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("数据备份")
                            .setView(outputView)
                            .setNegativeButton("取消", (dialoginterface, i) -> {

                            })
                            .setPositiveButton("确定", (dialoginterface, i) -> {
                                Boolean a = editPath.getText().toString()
                                        .endsWith(".db");
                                Boolean b = editPath.getText().toString()
                                        .endsWith(".xml");
                                String dbRestorePath1 = null, settingsRestorePath1 = null;
                                File dbRestoreFile = null, settingsRestoreFile = null;
                                if (a) {
                                    dbRestorePath1 = editPath.getText()
                                            .toString();
                                    dbRestoreFile = new File(dbRestorePath1);
                                } else if (b) {
                                    settingsRestorePath1 = editPath
                                            .getText().toString();
                                    settingsRestoreFile = new File(settingsRestorePath1);
                                } else if (!a && !b) {
                                   /* dbRestorePath1 = editPath.getText()
                                            .toString()
                                            + "/"
                                            + DataBaseAdapter.DATABASE_NAME;
                                    settingsRestorePath1 = editPath
                                            .getText().toString()
                                            + "/"
                                            + SettingsActivity.PREFS_NAME;*/
                                    dbRestoreFile = new File(dbRestorePath1);
                                    settingsRestoreFile = new File(settingsRestorePath1);
                                }
                                boolean dbRestoreFileExists = false, settingsRestoreFileExists = false;
                                if (dbRestoreFile != null) {
                                    dbRestoreFileExists = dbRestoreFile
                                            .exists();
                                }
                                if (settingsRestoreFile != null) {
                                    settingsRestoreFileExists = settingsRestoreFile
                                            .exists();
                                }
                                if (dbRestoreFileExists
                                        || settingsRestoreFileExists) {
                                    /*determineWhetherRestoreData(dbRestorePath1, settingsRestorePath1,
                                            firstUse, fileSize, fileChangeTime, activity);*/
                                } else {
                                    /*Toast.makeText(
                                            context,
                                            context.getString(R.string.no_file_exist),
                                            Toast.LENGTH_LONG).show();*/
                                }
                                mEditor.putString(
                                        KeepAccount.RESTORE_PATH,
                                        editPath.getText().toString());
                                mEditor.commit();
                              /*  BackUpAndRestoreSetting.BackUpAndRestoreFragment.mRestorePref.setSummary(context
                                        .getString(R.string.restore_form)
                                        + editPath.getText().toString());*/
                            }).show();
                }

                break;
            default:break;
        }
    }

    /**
     * 使用该方法可以获得SD卡的路径或内部存储的路径
     * 使用此方法如果手机不含SD卡会获得内部存储路径如果手机含有内存卡默认获得SD卡路径
     * @param mContext
     * @param is_removale true为获得SD卡路径，false为获得内部存储路径
     * @return
     */

    @NonNull
    public static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    File sdcardWrite = new File(path);
                    //有的机型有SD卡路径但没有文件会报空指针，有的机型不允许写入SD卡，此时不返回SD卡路径
                    //返回内存路径
                    if (sdcardWrite.listFiles() != null && sdcardWrite.listFiles().length > 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState(new File(path)))) {
                                return path;
                            }
                        } else if (sdcardWrite.canWrite() && sdcardWrite.canRead()) {
                            return path;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (!is_removale) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return getStoragePath(mContext, false);
        }
    }


    /**
     * @return
     * @category 保存文件，备份文件，将文件备份到选择的路径中 TODO
     */
 /*   public boolean dataBackup(String backupPath) {
        File sd = new File(BackAndRestoreData.getStoragePath(context,true));
        if (sd.canWrite()) {
            File currentDB = DataCenter.getDBInData();
            File backupDB = DataCenter.getDBfileInSd(backupPath);
            File currentSettings = DataCenter.getSettingConfigDataInData();
            File backupSettings = DataCenter
                    .getSettingConfigDataInSd(backupPath);

            if (currentDB.exists()) {
                // 弹出对话框
                if (backupDB.exists()) {
                    // 判断是否已存在备份
                    sureSaveAndRecovery(R.string.warn, R.string.cover_back,
                            android.R.drawable.ic_dialog_info, R.string.sure,
                            R.string.button_cancel, currentDB, backupDB,
                            currentSettings, backupSettings);
                } else {
                    DataCenter.dataBackAndRestore(currentDB, backupDB);
                    DataCenter.dataBackAndRestore(currentSettings,
                            backupSettings);
                    isBackup = true;
                    Toast.makeText(context, R.string.success, Toast.LENGTH_LONG)
                            .show();
                    refreshdata();
                    isBackup = false;
                    if (checkBoxSend.isChecked()) {
                        sendDataByMail(context, backupDB);
                    }

                }



            }
        }
        return true;
    }*/

   /* public void determineWhetherRestoreData(final String dbRestorePath, final String settingsRestorePath,
                                            final boolean firstin, final String fileSize, final String fileChangeTime,
                                            final Activity activity) {
        if (firstin) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.restore_warning_info_firstin) + "(" + fileSize
                            + ", " + fileChangeTime + ")" + "," + " " + context.getString(R.string.click_ok_restore))
                    .setPositiveButton(R.string.button_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    if (dataRecovery(dbRestorePath,
                                            settingsRestorePath)) {
                                        //跳转之前取消timer定时操作
                                        if(MonitoringFragment.mCountDownTimer != null){
                                            MonitoringFragment.mCountDownTimer.cancel();
                                            MonitoringFragment.mCountDownTimer = null;
                                        }
                                        if(MonitoringFragment.mCountDownTask != null){
                                            MonitoringFragment.mCountDownTask.cancel();
                                            MonitoringFragment.mCountDownTask = null;
                                        }
                                        Intent intent = new Intent(activity, ServerMonitorPlusActivity.class);
                                        activity.startActivity(intent);
                                        activity.finish();
                                        Toast.makeText(context, R.string.success,
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton(R.string.button_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {

                                }
                            }).show();
        } else {
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.reminder))
                    .setMessage(context.getString(R.string.restore_warning_info))
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton(R.string.button_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    if (dataRecovery(dbRestorePath,
                                            settingsRestorePath)) {
                                        Toast.makeText(context, R.string.success,
                                                Toast.LENGTH_LONG).show();
                                        //跳转之前取消timer定时操作
                                        if(MonitoringFragment.mCountDownTimer != null){
                                            MonitoringFragment.mCountDownTimer.cancel();
                                            MonitoringFragment.mCountDownTimer = null;
                                        }
                                        if(MonitoringFragment.mCountDownTask != null){
                                            MonitoringFragment.mCountDownTask.cancel();
                                            MonitoringFragment.mCountDownTask = null;
                                        }
                                        Intent intent = new Intent(activity, ServerMonitorPlusActivity.class);
                                        activity.startActivity(intent);
                                        activity.finish();
                                    } else {
                                        Toast.makeText(
                                                context,
                                                context.getString(R.string.choose_file_error),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton(R.string.button_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {

                                }
                            }).show();
        }
    }

    *//**
     * @return
     * @category 恢复文件, 从指定的数据库中恢复
     *//*

    public boolean dataRecovery(String restoredDBPath,
                                String restoredSettingsPath) {
        try {
            Boolean a = false, b = false; // a:数据库文件是否复制成功 b:配置文件是否复制成功
            if (restoredDBPath != null) {
                File srcDB = new File(restoredDBPath);
               *//* File dstDB = DataCenter.getDBInData();
                a = DataCenter.dataBackAndRestore(srcDB, dstDB);
                try {
                    DataBaseAdapter dbAdapter = new DataBaseAdapter(context);
                    dbAdapter.updateBackup();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.e("升级备份数据库", "升级备份数据库出错");
                    e.printStackTrace();

                }*//*
            }
            if (restoredSettingsPath != null) {
                File srcSettings = new File(restoredSettingsPath);
                File dstSettings = DataCenter.getSettingConfigDataInData();
                b = DataCenter.dataBackAndRestore(srcSettings, dstSettings);
            }
            if (a || b) {
                // ((Preferences) context).onResume();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    *//**
     * TODO
     *
     * @param title
     * @param message
     * @param titleIcon
     * @param pButton
     * @param nButton
     * @param currentDB
     * @param backupDB
     * @category 弹出文件保存或者回复的对话框
     *//*
    private void sureSaveAndRecovery(Integer title, Integer message,
                                     Integer titleIcon, Integer pButton, Integer nButton,
                                     final File currentDB, final File backupDB,
                                     final File currentSettings, final File backupSettings) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(pButton,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                if (DataCenter.dataBackAndRestore(currentDB,
                                        backupDB)
                                        && DataCenter
                                        .dataBackAndRestore(
                                                currentSettings,
                                                backupSettings))
                                {
                                    isBackup = true;
                                    Toast.makeText(context, R.string.success,
                                            Toast.LENGTH_LONG).show();
                                    refreshdata();
                                    isBackup = false;
                                }

                                if (checkBoxSend.isChecked()) {
                                    sendDataByMail(context, backupDB);
                                }
                                // ((AlarmList) context).onResume();
                            }
                        })
                .setNegativeButton(nButton,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {


                            }
                        }).show();
    }

    *//**
     * 邮件发送
     *
     * @param
     * @param
     *//*
    public void sendDataByMail(Context context, File backupDB) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            PackageManager manager = context.getPackageManager();
            String versionNameValue = "";
            try {
                PackageInfo info = manager.getPackageInfo(
                        context.getPackageName(), 0);
                versionNameValue = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
            }
            Calendar calendar = Calendar.getInstance();
            int format = context.getSharedPreferences(
                    SettingsActivity.PREFS_NAME, 0).getInt(
                    TimeFormatter.DATE_FORMAT, 0);
            intent.putExtra(
                    "subject",
                    "ServerMonitorPlus"
                            + " "
                            + versionNameValue
                            + " "
                            + TimeFormatter.format(true, calendar.getTime(),
                            format)); //
            intent.putExtra("body", backupDB.getName()); // 正文
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(backupDB)); // 添加附件，附件为file对象
            intent.setType("application/octet-stream");
            context.startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(context,
                    context.getString(R.string.unable_open_mail),
                    Toast.LENGTH_LONG).show();

        }
    }*/
    public interface IListenisBackup {
        void refresh();
    }
}
