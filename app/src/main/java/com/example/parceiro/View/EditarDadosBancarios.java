package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.Bancos;
import com.example.parceiro.Model.DadosBancarios;
import com.example.parceiro.Model.FormEditarDadosBancarios;
import com.example.parceiro.R;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarDadosBancarios extends AppCompatActivity {

    private TextView txtValorCarteira;
    private TextInputLayout textInputNomeFavorecido,textInputConta,textInputAgencia;
    private RadioGroup radioGroup;
    private RadioButton radioButton, radio_Corrente, radio_Poupanca;
    private Spinner spinnerBancos;
    private String radioCheck;
    private String bancoSelected;
    private String auth;

    private Button bt_edit_banco,bt_salvar_banco;

    private DadosBancarios dadosBancarios;
    private FormEditarDadosBancarios formEditarDadosBancarios;
    private ProgressDialog progressDialog;

    List<String> str = new ArrayList<>();
    List<Bancos> bancos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_dados_bancarios);

        //TOKEN
        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        txtValorCarteira = (TextView) findViewById(R.id.txtValorCarteira);
        textInputNomeFavorecido = (TextInputLayout) findViewById(R.id.textInputNomeFavorecido);
        textInputAgencia = (TextInputLayout) findViewById(R.id.textInputAgencia);
        textInputConta = (TextInputLayout) findViewById(R.id.textInputConta);

        radioGroup = (RadioGroup) findViewById(R.id.radioGrupTipo);
        radio_Corrente = (RadioButton) findViewById(R.id.radio_Corrente);
        radio_Poupanca = (RadioButton) findViewById(R.id.radio_Poupanca);

        spinnerBancos = (Spinner) findViewById(R.id.spinnerBancos);

        spinnerBancos.setEnabled(false);

        bt_edit_banco = (Button) findViewById(R.id.bt_edit_banco);
        bt_salvar_banco = (Button) findViewById(R.id.bt_salvar_banco);

        getCarteira();

        // MASCARA AGENCIA
        SimpleMaskFormatter simpleMaskAgencia= new SimpleMaskFormatter("NNNN-NN");
        MaskTextWatcher maskAgencia = new MaskTextWatcher(textInputAgencia.getEditText(), simpleMaskAgencia);
        textInputAgencia.getEditText().addTextChangedListener(maskAgencia);

        // MASCARA CONTA
        SimpleMaskFormatter simpleMaskConta= new SimpleMaskFormatter("NNNNNNNN-N");
        MaskTextWatcher maskConta = new MaskTextWatcher(textInputConta.getEditText(), simpleMaskConta);
        textInputConta.getEditText().addTextChangedListener(maskConta);

        spinnerBancos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bancoSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bt_edit_banco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textInputNomeFavorecido.getEditText().setFocusableInTouchMode(true);
                textInputNomeFavorecido.getEditText().requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(textInputNomeFavorecido.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                textInputAgencia.getEditText().setFocusableInTouchMode(true);
                textInputConta.getEditText().setFocusableInTouchMode(true);
                spinnerBancos.setEnabled(true);
                radio_Corrente.setEnabled(true);
                radio_Poupanca.setEnabled(true);

                getListaBancos();

                bt_edit_banco.setEnabled(false);
                bt_salvar_banco.setEnabled(true);
            }
        });

        bt_salvar_banco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

    }

    public void checkRadioButton(View v){
        radioButton =  findViewById(radioGroup.getCheckedRadioButtonId());
        radioCheck = radioButton.getText().toString();
    }

    //Validar alteracoes dados bancarios
    private void validarCampos() {

        formEditarDadosBancarios = new FormEditarDadosBancarios();

        String banco = bancoSelected;
        String tipo = radioCheck;


            if(MetodosCadastro.isCampoVazio(textInputNomeFavorecido.getEditText().getText().toString())) {
                textInputNomeFavorecido.getEditText().setError("Campo  Vazio !");
            }else {
                formEditarDadosBancarios.setNome(textInputNomeFavorecido.getEditText().getText().toString());
                if (MetodosCadastro.isCampoVazio(MetodosCadastro.unMask(textInputAgencia.getEditText().getText().toString()))){
                    textInputAgencia.getEditText().setError("Campo  Vazio !");
                } else {
                    formEditarDadosBancarios.setAgencia(Integer.parseInt(MetodosCadastro.unMask(textInputAgencia.getEditText().getText().toString())));
                    if (MetodosCadastro.isCampoVazio(MetodosCadastro.unMask(textInputConta.getEditText().getText().toString()))) {
                        textInputConta.getEditText().setError("Campo  Vazio !");
                    } else {
                        formEditarDadosBancarios.setConta(Integer.parseInt(MetodosCadastro.unMask(textInputConta.getEditText().getText().toString())));
                        if (MetodosCadastro.isCampoVazio(banco)) {
                            Toast.makeText(this, "Selecione um Banco.", Toast.LENGTH_SHORT).show();
                        } else {
                            formEditarDadosBancarios.setBanco(banco);
                            formEditarDadosBancarios.setTipo(tipo);
                            salvarAlteracao();
                        }
                    }
                }
            }

    }

    //Salvar alteracao dados bancarios
    private void salvarAlteracao() {
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<DadosBancarios> call = restService.alterarCartao("Bearer "+auth, formEditarDadosBancarios);

        call.enqueue(new Callback<DadosBancarios>() {
            @Override
            public void onResponse(Call<DadosBancarios> call, Response<DadosBancarios> response) {
                if(response.isSuccessful()){
                    dadosBancarios = response.body();

                    Toast.makeText(EditarDadosBancarios.this, "Dados Bancários alterados com Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                    //progressDialog.dismiss();
                }else {
                    Toast.makeText(EditarDadosBancarios.this, "Erro ao alterar Dados", Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DadosBancarios> call, Throwable t) {
                Toast.makeText(EditarDadosBancarios.this, "Erro Servidor", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        });

    }

    //Recuperar dados Bancarios
    private void getCarteira(){

        // Instanciando cliente WS
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<DadosBancarios> call = restService.getCarteira("Bearer "+auth);

        call.enqueue(new Callback<DadosBancarios>() {
            @Override
            public void onResponse(Call<DadosBancarios> call, Response<DadosBancarios> response) {

                if(response.isSuccessful()){
                    dadosBancarios = response.body();

                    DecimalFormat df = new DecimalFormat("0.00");
                    txtValorCarteira.setText(String.valueOf(df.format(Double.valueOf(dadosBancarios.getValor()))));

                    textInputNomeFavorecido.getEditText().setText(dadosBancarios.getNome());
                   // textInputCpf.getEditText().setText(dadosBancarios.getCPF);
                    textInputAgencia.getEditText().setText(String.valueOf(dadosBancarios.getAgencia()));
                    textInputConta.getEditText().setText(String.valueOf(dadosBancarios.getConta()));

                    str.add(dadosBancarios.getBanco());
                    if(!str.isEmpty()) {
                        ArrayAdapter<String> adapter;
                        adapter = new ArrayAdapter<>(EditarDadosBancarios.this, android.R.layout.simple_spinner_item, str);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerBancos.setAdapter(adapter);
                        spinnerBancos.setSelection(0);
                    }
                   if(dadosBancarios.getTipo().equals("Corrente")){
                       radio_Corrente.setChecked(true);
                    }else {
                       radio_Poupanca.setChecked(true);
                    }

                }else {

                }
            }
            @Override
            public void onFailure(Call<DadosBancarios> call, Throwable t) {

            }
        });

    }

    //Metodo para buscar lista de bancos
    private void getListaBancos(){

        //instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

        //passando os dados para consulta
        Call<List<Bancos>> call = restService.getBancos();

        call.enqueue(new Callback<List<Bancos>>() {
            @Override
            public void onResponse(Call<List<Bancos>> call, Response<List<Bancos>> response) {
                if (response.isSuccessful()){

                    bancos = response.body();

                    for(int i = 0; i < bancos.size(); i++){
                        str.add(bancos.get(i).getCodigo()+" - "+bancos.get(i).getBanco());
                    }

                    if(!str.isEmpty()){
                        ArrayAdapter<String> adapter;
                        adapter = new ArrayAdapter<>(EditarDadosBancarios.this,android.R.layout.simple_spinner_item,str);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerBancos.setAdapter(adapter);
                    }

                }else {

                }
            }

            @Override
            public void onFailure(Call<List<Bancos>> call, Throwable t) {

            }
        });


    }

}