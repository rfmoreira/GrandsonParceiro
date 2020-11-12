package com.example.parceiro.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parceiro.Api.RetrofitClientCEP;
import com.example.parceiro.Api.RetrofitClientGrandson;
import com.example.parceiro.Model.Bancos;
import com.example.parceiro.Model.Cep;
import com.example.parceiro.Model.FormCadastroParceiro;
import com.example.parceiro.Model.Foto;
import com.example.parceiro.Model.Parceiro;
import com.example.parceiro.Model.Resposta;
import com.example.parceiro.Services.RetrofitServiceCEP;
import com.example.parceiro.Services.RetrofitServiceGrandson;
import com.example.parceiro.Utils.FileUtil;
import com.example.parceiro.Utils.MetodosCadastro;
import com.example.parceiro.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadosBancarioParceiro extends AppCompatActivity {
    private TextView textViewNome;
    private TextInputLayout textInputNomeFavorecido, textInputCpf,textInputConta,textInputAgencia;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner spinnerBancos;
    private String radioCheck;
    private String bancoSelected;
    private FormCadastroParceiro formCadastroParceiro;

    private Uri imagenUri;
    private File file;
    private Resposta resposta;

    List<String> str = new ArrayList<>();
    List<Bancos> bancos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_bancario_parceiro);

        formCadastroParceiro = getIntent().getExtras().getParcelable("parceiro");
        imagenUri = Uri.parse(formCadastroParceiro.getUriFoto());

        textViewNome = (TextView) findViewById(R.id.textViewNome);
        textViewNome.setText(formCadastroParceiro.getNome());
        textInputNomeFavorecido = (TextInputLayout) findViewById(R.id.textInputNomeFavorecido);
        textInputCpf = (TextInputLayout) findViewById(R.id.textInputCpf);
        textInputAgencia = (TextInputLayout) findViewById(R.id.textInputAgencia);
        textInputConta = (TextInputLayout) findViewById(R.id.textInputConta);

        radioGroup = (RadioGroup) findViewById(R.id.radioGrupTipo);
        spinnerBancos = (Spinner) findViewById(R.id.spinnerBancos);

       /* bancos.add("01 - Banco do Brasil S.A");
        bancos.add("237 - Bradesco S.A");
        bancos.add("260 - Nu Pagamentos S.A (Nubank)");*/

        getListaBancos();

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



    }
    public void checkRadioButton(View v){
        radioButton =  findViewById(radioGroup.getCheckedRadioButtonId());
        radioCheck = radioButton.getText().toString();
    }

        public void onClickSalvar(View v){

        String nomeFavorecido = MetodosCadastro.unMask(textInputNomeFavorecido.getEditText().getText().toString());
        String cpf = MetodosCadastro.unMask(textInputCpf.getEditText().getText().toString());
        int agencia = Integer.parseInt(MetodosCadastro.unMask(textInputAgencia.getEditText().getText().toString()));
        int conta = Integer.parseInt(MetodosCadastro.unMask(textInputConta.getEditText().getText().toString()));
        String banco = bancoSelected;
        String tipo = radioCheck;

        formCadastroParceiro.setNomeBeneficiario(nomeFavorecido);
        formCadastroParceiro.setCpf(cpf);
        formCadastroParceiro.setAgencia(agencia);
        formCadastroParceiro.setConta(conta);
        formCadastroParceiro.setBanco(banco);
        formCadastroParceiro.setTipo(tipo);

        salavarParceiro();


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

        //Metodo para Salvar Parceiro POST
        public void salavarParceiro(){

            //instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

            //passando os dados para consulta
            Call<Resposta> call = restService.cadastrarParceiro(formCadastroParceiro);

            call.enqueue(new Callback<Resposta>() {
                @Override
                public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                    resposta = response.body();
                    if(response.isSuccessful()){
                        if(imagenUri.toString().isEmpty()){
                            Log.i("Foto : ","Sem Foto");
                        }else {
                            salvarFoto(resposta.getObject());
                        }

                        Intent intent = new Intent(DadosBancarioParceiro.this,LoginGrandson.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        Toast.makeText(DadosBancarioParceiro.this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(DadosBancarioParceiro.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Resposta> call, Throwable t) {
                    Toast.makeText(DadosBancarioParceiro.this, "Falha", Toast.LENGTH_SHORT).show();
                }
            });

        }

        // Post Multipart para Salvar Imagem
        private void salvarFoto(int id){
            file = FileUtil.getFile(this,imagenUri);
            final RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(imagenUri)),file);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("foto",file.getName(),requestBody);

            //Instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

            //RequestBody bodyId = RequestBody.create(MediaType.parse("path"), String.valueOf(id));

            //Passando os dados para consulta
            Call<Foto> call = restService.uploadImagem(body,id);

           /* Gson gson = new Gson();
            String json = gson.toJson(body);

            Log.i("Json",json);
            Log.i("GEt Name",call.toString());
            Log.i("requestBody",requestBody.toString());*/
            call.enqueue(new Callback<Foto>() {
                @Override
                public void onResponse(Call<Foto> call, Response<Foto> response) {
                    if(response.isSuccessful()){
                        Foto foto = response.body();
                        Log.i("Sucesso", foto.getData());

                    }else {
                        //Toast.makeText(DadosBancarioCliente.this, "Erro"+response.message(), Toast.LENGTH_SHORT).show();
                        Log.i("Erro ",response.message());
                    }
                }

                @Override
                public void onFailure(Call<Foto> call, Throwable t) {
                    //Toast.makeText(DadosBancarioCliente.this, "Erro 2"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("Falha",t.getMessage());
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
                            adapter = new ArrayAdapter<>(DadosBancarioParceiro.this,android.R.layout.simple_spinner_item,str);
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





