package edu.dtcc.spaol1.phrasedatabasemanager;

import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
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
        AddPhrase.AddStudentDialogListener, EditPhrase.UpdatePhraseListener,
        DeletePhrase.DeleteStudentDialogListener
{
    Button btnAddPhrase, btnEditPhrase, btnUpdatePhraseList, btnDeletePhrase;
    TextView tvPhraseList;
    private String TAG = "StudInfo";
    SQLiteDatabase dtb;
    int btnBackPressCounter = 0;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);

        btnAddPhrase = (Button)findViewById(R.id.btnAddPhrase);
        btnUpdatePhraseList = (Button)findViewById(R.id.btnUpdatePhraseList);
        btnEditPhrase = (Button)findViewById(R.id.btnEditPhrase);
        btnDeletePhrase = (Button)findViewById(R.id.btnDeletePhrase);

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
                tvPhraseList.setText("");	//	clear text area at each button press
                tvPhraseList.setPadding(5, 2, 5, 2);

                // fetch List of BlockedNumbers form DB  method - 'getAllBlockedNumbers'
                List<Phrase> studentsList = db.getAllPhraseList();

                for (Phrase phrase : studentsList)
                {
                    String phraseDetail = "\n\nID:" + phrase.get_id()+ "\n\tTITLE:" 
                            + phrase.get_phrase_title()
                            +"\n\tPHRASE:" + phrase.get_phrase();
                    tvPhraseList.append("\n"+ phraseDetail);
                }
            }
        });
    }

    /**@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */
    
    /**
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
    
    @Override
    public void onSaveButtonClick(DialogFragment dialog)
    {
        // Get phrase title
        EditText etPhraseTitle = (EditText) dialog.getDialog().findViewById(R.id.etPhraseTitle);
        String phraseTitle = etPhraseTitle.getText().toString();
        //int int_enrollNo =Integer.parseInt(etPhraseTitle.getText().toString());

        // Get Name
        EditText etPhrase = (EditText) dialog.getDialog().findViewById(R.id.etPhrase);
        String phrase = etPhrase.getText().toString();
        
        // Variables used for checking fields
        //boolean checkPhraseTitle = checkEnrollNo(phraseTitle);
        //boolean checkPhrase = checkName(phrase);
        //boolean check_phnNo = checkPhoneNo(phnNo);
        
        db.addNewPhrase(new Phrase(phraseTitle, phrase));

        Toast.makeText(getApplicationContext(),"Phrase Added",
                    Toast.LENGTH_LONG).show();
        
        Toast.makeText(getApplicationContext(),"\nTitle :" + phraseTitle + "\nPhrase: " +
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
    /**
    //Check Enrollment number
    public boolean checkEnrollNo(String enr_No)
    {
        if(enr_No == "" || enr_No.length() != 3)
        {

            return false;
        }
        else
        {
            return true;
        }

    }

    //Check Name
    public boolean checkName(String stdName)
    {
        if(stdName == "")
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //Check Phone Number
    public boolean checkPhoneNo(String phn_No)
    {
        if(phn_No == "" || phn_No.length() != 10)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    */
     
    @Override
    public void onUpdateButtonClick(DialogFragment dialog)
    {
        // Get ID
        EditText etID = (EditText) dialog.getDialog().findViewById(R.id.etEditPhraseID);
        String idNo = etID.getText().toString();
        int int_idNo =Integer.parseInt(etID.getText().toString());

        // Get phrase title
        EditText etPhraseTitle = (EditText) dialog.getDialog().findViewById(R.id.etEditPhraseTitle);
        String phraseTitle = etPhraseTitle.getText().toString();
        //int int_enrollNo =Integer.parseInt(etPhraseTitle.getText().toString());

        // Get phrase
        EditText etPhrase = (EditText) dialog.getDialog().findViewById(R.id.etEditPhrase);
        String phrase = etPhrase.getText().toString();

        boolean check_idNo = checkIDNumber(idNo);

        //boolean check_enrollNo = checkEnrollNo(phraseTitle);

        //boolean check_name = checkName(phrase);

        //boolean check_phnNo = checkPhoneNo(phnNo);

        if(check_idNo == false)
        {
            Toast.makeText(getApplicationContext(),"Incorrect ID",Toast.LENGTH_LONG).show();
        }
        else
        {
            boolean updateCheck = db.updatePhraseInfo(int_idNo, phraseTitle, phrase);

            if(updateCheck == true)
            {
                Toast.makeText(getApplicationContext(),"Phrase Added",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Edit Failed",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDeleteButtonClick(DialogFragment dialog)
    {
        // Get ID
        EditText entId = (EditText) dialog.getDialog().findViewById(R.id.etDeletePhraseID);
        String idNo = entId.getText().toString();
        int int_idNo =Integer.parseInt(entId.getText().toString());

        boolean check_idNo = checkIDNumber(idNo);

        if(check_idNo == false)
        {
            Toast.makeText(getApplicationContext(),"Enter Proper ID again..! :)",Toast.LENGTH_LONG).show();

        }
        else
        {
            boolean deleteCheck = db.deletePhrase(int_idNo);

            if(deleteCheck == true)
            {
                Toast.makeText(getApplicationContext(),"Student Deleted Successfully :)",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Stuent Deletion Fails.. :(",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}