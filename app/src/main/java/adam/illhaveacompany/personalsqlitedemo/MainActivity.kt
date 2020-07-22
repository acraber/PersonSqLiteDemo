package adam.illhaveacompany.personalsqlitedemo

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_enter.setOnClickListener {
            addRecord()
        }

        btn_edit.setOnClickListener{

        }

        btn_delete.setOnClickListener{
            deleteRecordChosen()
        }
    }

    fun addRecord() {
        val name = et_name.text.toString()
        val email = et_email.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        if(!name.isEmpty() && !email.isEmpty()) {

            val status = databaseHandler.addEmployee(EmpModelClass(0, name, email))

            if (status > -1) {
                Toast.makeText(applicationContext, "Record save attempted", Toast.LENGTH_LONG).show()
                et_name.text?.clear()
                et_email.text?.clear()
            }
        }else{
            Toast.makeText(applicationContext,"No data inserted", Toast.LENGTH_LONG).show()
        }
    }

    fun getItemsList() : ArrayList<EmpModelClass>{
       val databaseHandler : DatabaseHandler = DatabaseHandler(this)
        return databaseHandler.viewEmployee()
    }//1

    //used if I don't need to pick the record??
    fun deleteRecordChosenSimply() {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        databaseHandler.deleteEmployee(EmpModelClass(3, "", ""))
    }

    //needs lots of work
    fun deleteRecordChosen() {
        val builder = AlertDialog.Builder(this)



        //I can do val builder1 = builder.setTitle("Delete Record") -- could use this val to add something new to SQL through it.
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete it?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            //the 1 is where I choose which record I delete - can probably change to fun deleteRecordAlertDialog(empModelClass:EmpModelClass) {
            //and give tell it which one to delete in the argument -- THIS IS WHERE I CHANGE WHICH ONE I WANT TO DELETE
            val status = databaseHandler.deleteEmployee(EmpModelClass(1, "", ""))

            //I don't need the if statement right now but if I want to add the functionality to choose certain
            //records I'll need it.
            if(status > -1){
                Toast.makeText(applicationContext,"Attempted record deletion", Toast.LENGTH_LONG).show()
            }
            dialogInterface.dismiss()
        }

        //builder seems SUPER powerful
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }






}