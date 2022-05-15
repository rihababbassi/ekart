package com.test.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText enteredPhoneNumber, ansEditText1, ansEditText2, ansEditText3;
    private Button verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_activity);
        getSupportActionBar().hide();


        enteredPhoneNumber = findViewById(R.id.enter_phone_editTxt_resetPass);
        ansEditText1 = findViewById(R.id.ans1_editTxt_resetPass);
        ansEditText2 = findViewById(R.id.ans2_editTxt_resetPass);
        ansEditText3 = findViewById(R.id.ans3_editTxt_resetPass);
        verifyBtn = findViewById(R.id.verify_btn);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswers();
            }
        });
    }

    private void verifyAnswers() {
        String phoneNumber = enteredPhoneNumber.getText().toString();
        String answer1 = ansEditText1.getText().toString();
        String answer2 = ansEditText2.getText().toString();
        String answer3 = ansEditText3.getText().toString();

        if(phoneNumber.equals(""))
            Toast.makeText(ResetPasswordActivity.this, "Entrez votre numéro de téléphone", Toast.LENGTH_SHORT).show();
        else if(answer1.equals("") || answer2.equals("") || answer3.equals(""))
            Toast.makeText(ResetPasswordActivity.this, "Veuillez répondre à toutes les questions", Toast.LENGTH_SHORT).show();
        else{
            checkPhoneAndAnswers(phoneNumber, answer1, answer2, answer3);
        }
    }

    private void checkPhoneAndAnswers(final String phoneNumber, final String answer1, final String answer2, final String answer3) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(phoneNumber).exists()){

                    if(snapshot.child(phoneNumber).hasChild("Security Questions"))
                    {
                        String ans1 = snapshot.child(phoneNumber).child("Security Questions").child("answer1").getValue().toString();
                        String ans2 = snapshot.child(phoneNumber).child("Security Questions").child("answer2").getValue().toString();
                        String ans3 = snapshot.child(phoneNumber).child("Security Questions").child("answer3").getValue().toString();

                        if(!ans1.equalsIgnoreCase(answer1))
                            Toast.makeText(ResetPasswordActivity.this, "Votre 1ère réponse est fausse", Toast.LENGTH_SHORT).show();
                        else if(!ans2.equalsIgnoreCase(answer2))
                            Toast.makeText(ResetPasswordActivity.this, "Votre 2éme réponse est fausse", Toast.LENGTH_SHORT).show();
                        else if(!ans3.equalsIgnoreCase(answer3))
                            Toast.makeText(ResetPasswordActivity.this, "Votre 3éme réponse est fausse", Toast.LENGTH_SHORT).show();
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                            builder.setTitle("Réinitialisation du mot de passe");

                            final EditText newPassword = new EditText(ResetPasswordActivity.this);
                            newPassword.setHint("Écrivez le mot de passe ici...");

                            builder.setView(newPassword);
                            builder.setPositiveButton("Changer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    if(!newPassword.getText().toString().equals(""))
                                    {
                                        ref.child(phoneNumber)
                                                .child("password").setValue(newPassword.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            dialog.cancel();
                                                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                            startActivity(intent);
                                                            Toast.makeText(ResetPasswordActivity.this, "Le mot de passe a été changé avec succès", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                    }

                                }
                            });
                            builder.setNegativeButton("\n" +
                                    "Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    }
                    else
                        Toast.makeText(ResetPasswordActivity.this, "Désolé Ce compte n'a pas de questions de sécurité, contactez-nous pour résoudre le problème.", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(ResetPasswordActivity.this, "Le numéro de téléphone n'existe pas", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}