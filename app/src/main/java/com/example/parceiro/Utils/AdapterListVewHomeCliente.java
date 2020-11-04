package com.example.parceiro.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.parceiro.Model.ListaCliente;
import com.example.parceiro.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterListVewHomeCliente extends ArrayAdapter<ListaCliente> {

    Context context;
    List<ListaCliente> lListaClientes;
    //int lImagem[];

    public AdapterListVewHomeCliente(@NonNull Context context, List<ListaCliente> lListaClientes) {
        super(context,R.layout.row_list_view_parceiro, lListaClientes);
        this.context = context;
        this.lListaClientes = lListaClientes;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_list_view_parceiro, parent, false);
        TextView nomeParceiro = view.findViewById(R.id.NomeParceiro);
        TextView nota = view.findViewById(R.id.txtAvaliacao);
        CircleImageView imgPerfCliente = view.findViewById(R.id.imgPerfCliente);
        ListaCliente p = lListaClientes.get(position);
        //Log.i("Nome Parciero", p.getNome());
        String foto = p.getFoto().getData();
        if(foto != null){
            //Decondificando imagem recebida do JSON
            byte[]  stringDecodificada = Base64.decode(foto, Base64.DEFAULT);
            Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

            imgPerfCliente.setImageBitmap(imgbtmap);
        }

        nomeParceiro.setText(p.getNome());
        nota.setText(p.getNota());

        return view;
    }
}
