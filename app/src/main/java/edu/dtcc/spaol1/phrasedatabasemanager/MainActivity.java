package edu.dtcc.spaol1.phrasedatabasemanager;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ArrowKeyMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AddPhrase.AddPhraseListener, EditPhrase.UpdatePhraseListener,
        DeletePhrase.DeletePhraseDialogListener
{
    Button btnAddPhrase, btnEditPhrase, btnUpdatePhraseList, btnDeletePhrase;
    TextView tvPhraseList;
    private String TAG = "PHRASE DATABASE";
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this);

        // Set variables to buttons
        btnAddPhrase = (Button)findViewById(R.id.btnAddPhrase);
        btnUpdatePhraseList = (Button)findViewById(R.id.btnUpdatePhraseList);
        btnEditPhrase = (Button)findViewById(R.id.btnEditPhrase);
        btnDeletePhrase = (Button)findViewById(R.id.btnDeletePhrase);

        /** Button listeners */
        btnAddPhrase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddPhrase dialog = new AddPhrase();
                dialog.show(getFragmentManager(), TAG);
            }
        });

        btnEditPhrase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditPhrase updateDialog = new EditPhrase();
                updateDialog.show(getFragmentManager(),TAG);
            }
        });

        btnDeletePhrase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DeletePhrase deleteDialog = new DeletePhrase();
                deleteDialog.show(getFragmentManager(),TAG);
            }
        });

        btnUpdatePhraseList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // View Block Number List in the Text View Widget
                tvPhraseList = (TextView) findViewById(R.id.tvPhraseList);
                tvPhraseList.setMovementMethod(ArrowKeyMovementMethod.getInstance());
                tvPhraseList.setText("");	//clear text area at each button press
                tvPhraseList.setPadding(5, 2, 5, 2);

                // fetch List of BlockedNumbers form DB  method - 'getAllBlockedNumbers'
                List<Phrase> phraseList = db.getAllPhraseList();

                for (Phrase phrase : phraseList)
                {
                    // Display each database entry into text view
                    String phraseDetail = "\n\nID:" + phrase.get_id()+ "\n\tTITLE:" 
                            + phrase.get_phrase_title()
                            +"\n\tPHRASE:" + phrase.get_phrase();
                    tvPhraseList.append("\n"+ phraseDetail);
                }
            }
        });
    }
    
    @Override
    public void onAddButtonClicked(DialogFragment dialog)
    {
        // Get phrase title
        EditText etPhraseTitle = (EditText) dialog.getDialog().findViewById(R.id.etPhraseTitle);
        String phraseTitle = etPhraseTitle.getText().toString();

        // Get Name
        EditText etPhrase = (EditText) dialog.getDialog().findViewById(R.id.etPhrase);
        String phrase = etPhrase.getText().toString();

        // Update with a toast message to validate adding
        db.addNewPhrase(new Phrase(phraseTitle, phrase));
         Toast.makeText(getApplicationContext(), "Phrase Added",
                 Toast.LENGTH_LONG).show();

        // Second toast that displays the entry added
        Toast.makeText(getApplicationContext(), "\nTitle :" + phraseTitle + "\nPhrase: " +
                phrase, Toast.LENGTH_LONG).show();
    }
    
    public boolean checkIDNumber(String Id_No)
    {
        if(Id_No == "")
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //Check phrase
    public boolean checkPhraseTitle(String phraseTitle)
    {
        if(phraseTitle == "")
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onUpdateButtonClick(DialogFragment dialog)
    {
        try
        {
            // Get ID
            EditText etID = (EditText) dialog.getDialog().findViewById(R.id.etEditPhraseID);
            String idNo = etID.getText().toString();
            int int_idNo = Integer.parseInt(etID.getText().toString());

            // Get phrase title
            EditText etPhraseTitle = (EditText) dialog.getDialog().findViewById(R.id.etEditPhraseTitle);
            String phraseTitle = etPhraseTitle.getText().toString();

            // Get phrase
            EditText etPhrase = (EditText) dialog.getDialog().findViewById(R.id.etEditPhrase);
            String phrase = etPhrase.getText().toString();

            boolean check_idNo = checkIDNumber(idNo);

            if (check_idNo == false)
            {
                // Display error due to incorrect ID entered
                Toast.makeText(getApplicationContext(), "Incorrect ID", Toast.LENGTH_LONG).show();
            }
            else
            {
                // Do final check
                boolean updateCheck = db.editPhraseInfo(int_idNo, phraseTitle, phrase);

                if (updateCheck == true)
                {
                    // Successful phrase edit
                    Toast.makeText(getApplicationContext(), "Phrase Edit Successful",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Complete failure to edit
                    Toast.makeText(getApplicationContext(), "Edit Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e)
        {
            // Detects if an actual ID is entered and also prevents app crash
            Toast.makeText(getApplicationContext(), "No ID entered, so no changes made",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteButtonClick(DialogFragment dialog)
    {
        try
        {
            // Get ID
            EditText entId = (EditText) dialog.getDialog().findViewById(R.id.etDeletePhraseID);
            String idNo = entId.getText().toString();
            int int_idNo = Integer.parseInt(entId.getText().toString());

            boolean check_idNo = checkIDNumber(idNo);

            if (check_idNo == false)
            {
                Toast.makeText(getApplicationContext(), "Incorrect ID entered", Toast.LENGTH_LONG).show();

            }
            else
            {
                boolean deleteCheck = db.deletePhrase(int_idNo);

                if (deleteCheck == true)
                {
                    Toast.makeText(getApplicationContext(), "Deletion Successful",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Deletion Failed",
                            Toast.LENGTH_LONG).show();
                }

            }
        }
        catch (Exception e)
        {
            // Detects if an actual ID is entered and also prevents app crash
            Toast.makeText(getApplicationContext(), "No ID entered, so no changes made",
                    Toast.LENGTH_LONG).show();
        }
    }
}