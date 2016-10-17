package com.example.teo.clientewebservice;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Runnable {

    EditText edtMsg;
    Button btnConsultar,btnEnviarMensagem;
    TextView tvResposta;
    private Handler handler = new Handler(); //para controlar a thread
    private ProgressDialog dialog; //Janela para mostrar a progressão da consulta WS
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtMsg = (EditText)findViewById(R.id.edtMsg);
        tvResposta = (TextView)findViewById(R.id.tvResposta);
        btnEnviarMensagem = (Button)findViewById(R.id.btnEnviarMsg);

        btnEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 0;
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Processando...");
                dialog.setTitle("Consulta Web Service");
                dialog.show();
                Thread t = new Thread(MainActivity.this);
                t.start(); //executa a thread método run()
            }
        });


        btnConsultar = (Button)findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 1;
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Processando...");
                dialog.setTitle("Consulta Web Service");
                dialog.show();
                Thread t = new Thread(MainActivity.this);
                t.start(); //executa a thread método run()
            }
        });

    }

    @Override
    public void run() { //cria um thread tarefa paralela

        if(x==0) {
            String mensagem = edtMsg.getText().toString();
            WSCliente enviar = new WSCliente();
            try {

                final boolean r = enviar.novaMsg(mensagem);
                handler.post(new Runnable() { //cria uma sub thread para controlar a janela de progresso
                    @Override
                    public void run() {
                        if (r) {
                            Toast.makeText(MainActivity.this, "Mensagem enviada com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Erro ao enviar a mensagem!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                dialog.dismiss();
            }
        }
        if(x==1) {
            WSCliente cliente = new WSCliente(); //cria obejto da classe WSCliente
            String msg = edtMsg.getText().toString();

            try {

                final String resposta = cliente.recebeMsg(); //chama o método da classe Wscliente

                handler.post(new Runnable() { //cria uma sub thread para controlar a janela de progresso
                    @Override
                    public void run() {
                        tvResposta.setText("Resposta: " + resposta);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                dialog.dismiss(); //fecha a janela de dialogo
            }
        }
    }
}
