package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BaseDatos baseDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creación base de datos al lanzar la primera activity
        crearBD();
    }

    /**
     * Validar campos
     */
    public void validarCampos(View view) {
        Context context = getApplicationContext();
        EditText userText = findViewById(R.id.editUsuario);
        String user = userText.getText().toString();
        EditText passText = findViewById(R.id.editContrasinal);
        String pass = passText.getText().toString();

        //comprobar si existe el usuario
        Cursor cursor = consultarUsuario(user,pass);
        if(cursor.getCount()!=1) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "login incorrecto", duration);
            toast.show();
        }else if(cursor.getColumnIndex("es_admin") ==1){//admin
            Intent panelAdmin = new Intent(this, AdminPanel.class);
            startActivity(panelAdmin);
        }else{//user
            Intent hacerPedido = new Intent(this, Cliente1Panel.class);
            startActivity(hacerPedido);
        }

        //reseteo los valores  y pongo el foco en el primer campo
        userText.setText("");
        passText.setText("");
        userText.requestFocus();
    }

    /* Botón rexistrarse*/
    public void rexistrar(View view){
       startActivity(new Intent(this,Rexistro.class));
    }

    public void crearBD(){
        baseDatos = new BaseDatos(getApplicationContext(),"USUARIOS",null,1);
        //guardado referencia a la base de datos
        SQLiteDatabase operacionsBD = baseDatos.getWritableDatabase();
        baseDatos.asigarSQLiteDatabase(operacionsBD);
    }

    public Cursor consultarUsuario(String usuario,String contrasinal){
        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select es_admin from USUARIOS where nome='"+usuario+"' " +
                "AND contrasinal ='" + contrasinal +"'", null);
        System.out.println("count" + cursor.getCount());
        return cursor;
    }





}
