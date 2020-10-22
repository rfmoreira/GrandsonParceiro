package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.parceiro.Api.RetrofitClientCEP;
import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.Cep;
import com.example.parceiro.Model.FormCadastroParceiro;
import com.example.parceiro.Model.Parceiro;
import com.example.parceiro.Services.RetrofitServiceCEP;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.MetodosCadastro;
import com.example.parceiro.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DadosBancarioParceiro extends AppCompatActivity {
    private TextView textViewNome;
    private TextInputLayout textInputNomeFavorecido, textInputCpf,textInputConta,textInputAgencia;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner spinnerBancos;
    private String radioCheck;
    private String bancoSelected;
    private FormCadastroParceiro formCadastroParceiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_bancario_parceiro);

        formCadastroParceiro = getIntent().getExtras().getParcelable("parceiro");


        textViewNome = (TextView) findViewById(R.id.textViewNome);
        textViewNome.setText(formCadastroParceiro.getNome());
        textInputNomeFavorecido = (TextInputLayout) findViewById(R.id.textInputNomeFavorecido);
        textInputCpf = (TextInputLayout) findViewById(R.id.textInputCpf);
        textInputAgencia = (TextInputLayout) findViewById(R.id.textInputAgencia);
        textInputConta = (TextInputLayout) findViewById(R.id.textInputConta);

        radioGroup = (RadioGroup) findViewById(R.id.radioGrupTipo);
        spinnerBancos = (Spinner) findViewById(R.id.spinnerBancos);
        List<String> bancos = new ArrayList<>();
        bancos.add("01 - Banco do Brasil S.A");
        bancos.add("237 - Bradesco S.A");
        bancos.add("260 - Nu Pagamentos S.A (Nubank)");



        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,bancos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBancos.setAdapter(adapter);

        // MASCARA CPF
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(textInputCpf.getEditText(), simpleMaskCpf);
        textInputCpf.getEditText().addTextChangedListener(maskCpf);

        // MASCARA AGENCIA
        SimpleMaskFormatter simpleMaskAgencia= new SimpleMaskFormatter("NNNN-NN");
        MaskTextWatcher maskAgencia = new MaskTextWatcher(textInputAgencia.getEditText(), simpleMaskAgencia);
        textInputAgencia.getEditText().addTextChangedListener(maskAgencia);

        // MASCARA CONTA
        SimpleMaskFormatter simpleMaskConta= new SimpleMaskFormatter("NNNNNNNN-N");
        MaskTextWatcher maskConta = new MaskTextWatcher(textInputConta.getEditText(), simpleMaskConta);
        textInputAgencia.getEditText().addTextChangedListener(maskConta);

        spinnerBancos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bancoSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public void checkRadioButton(View v){
        radioButton =  findViewById(radioGroup.getCheckedRadioButtonId());
        radioCheck = radioButton.getText().toString();
    }

    public void onClickSalvar(View v){

        String nomeFavorecido = MetodosCadastro.unMask(textInputNomeFavorecido.getEditText().getText().toString());
        String cpf = MetodosCadastro.unMask(textInputCpf.getEditText().getText().toString());
        String agencia = MetodosCadastro.unMask(textInputAgencia.getEditText().getText().toString());
        String conta = MetodosCadastro.unMask(textInputConta.getEditText().getText().toString());
        String banco = MetodosCadastro.unMask(bancoSelected);
        String tipo = radioCheck;

        formCadastroParceiro.setCpf(cpf);
        formCadastroParceiro.setAgencia(agencia);
        formCadastroParceiro.setConta(Integer.parseInt(MetodosCadastro.unMask(conta)));
        formCadastroParceiro.setBanco(banco);
        formCadastroParceiro.setTipo(tipo);


        /*if(MetodosCadastro.isCampoVazio(cpf)){
            editTextCpf.setError("Campo  Vazio !");
        }else{
        if(MetodosCadastro.isCPF(cpf)){

            if(MetodosCadastro.isCampoVazio(agencia)){
                editTextAgencia.setError("Campo  Vazio !");
            }else{
                if (MetodosCadastro.isCampoVazio(conta)){
                    editTextConta.setError("Campo  Vazio !");
                }else {
                    if (MetodosCadastro.isCampoVazio(banco)){
                        editTextBanco.setError("Campo  Vazio !");
                    }else {
                        Intent intent = new Intent(getApplicationContext(),Grandson.class);
                        startActivity(intent);;
                    }
                }
            }
            }else {
                editTextCpf.setError("CPF Invalido");
            }
        }*/
        }


        public void salavarWS(){

            //instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

            //passando os dados para consulta
            Call<Parceiro> call = restService.cadastrarParceiro(formCadastroParceiro);

        }


    }





