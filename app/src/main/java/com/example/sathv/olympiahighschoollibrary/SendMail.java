package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/8/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask<Void, Void, Void> {

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;
    private String code;

    EditText input;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendMail(Context context, String email, String subject, String message, String code) {
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.code = code;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context, "Sending message", "Please wait...", false, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context, "Please check the sent email for the activation code ", Toast.LENGTH_LONG).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Verification code");

        input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String enteredCode = input.getText().toString();

                if (enteredCode.equals(code)) {
                    Intent activities = new Intent(context, Activities.class);
                    context.startActivity(activities);

                    Toast.makeText(context, "Welcome to the Olympia High School Library app!", Toast.LENGTH_LONG).show();

                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, "code is incorrect", Toast.LENGTH_LONG).show();
                    input.setText("");
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);

            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
