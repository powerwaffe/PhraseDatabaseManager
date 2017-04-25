package edu.dtcc.spaol1.phrasedatabasemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class EditPhrase extends DialogFragment
{
    //interface to handle the Events
    interface UpdatePhraseListener
    {
        void onUpdateButtonClick(DialogFragment dialog);
    }

    //create an Instance to deliver the action
    UpdatePhraseListener updatePhraseListener;

    // Override the Fragment.onAttach() method to instantiate the SetPasswordDialogListener
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try
        {
            // Instantiate the updatePhraseListener so we can send events to the host
            updatePhraseListener = (UpdatePhraseListener) activity;
        } catch (ClassCastException e)
        {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement UpdatePhraseListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.phrase_edit, null))

                // Add action buttons
                .setPositiveButton(R.string.tv_Update, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        updatePhraseListener.onUpdateButtonClick(EditPhrase.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        EditPhrase.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}