package com.wiseass.postrainer.api;

import android.os.AsyncTask;

import com.wiseass.postrainer.model.database.AlarmDatabase;
import com.wiseass.postrainer.model.objects.Reminder;

/**
 * Created by Ryan on 01/01/2016.
 */
public class DeleteFromDatabase extends AsyncTask<AlarmDatabase, Void, Long> {

    private Reminder reminder;

    private OnDeleteComplete onDeleteComplete;

    public interface OnDeleteComplete {
        void setDeleteComplete();
    }

    public void setDeleteCompleteListener(OnDeleteComplete onDeleteComplete) {
        this.onDeleteComplete = onDeleteComplete;
    }

    public DeleteFromDatabase (Reminder reminder){
        this.reminder = reminder;
    }

    public DeleteFromDatabase (){
        //used if user wishes to delete all Reminders
    }

    @Override
    protected Long doInBackground(AlarmDatabase... params) {
        AlarmDatabase database = params[0];
        if (reminder != null){
            return database.deleteData(reminder);
        } else  {
            return database.deleteAllData();
        }
    }

    @Override
    protected void onPostExecute(Long param) {
        onDeleteComplete.setDeleteComplete();
    }
}
