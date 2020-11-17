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

import com.example.parceiro.Model.ServicosAceitos;
import com.example.parceiro.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListVewServicosAgendados extends ArrayAdapter<ServicosAceitos> {

    Context context;
    List<ServicosAceitos> lServicosAgendados;
    //int lImagem[];

    public AdapterListVewServicosAgendados(@NonNull Context context, List<ServicosAceitos> lServicosAgendados) {
        super(context, R.layout.row_list_view_parceiro, lServicosAgendados);
        this.context = context;
        this.lServicosAgendados = lServicosAgendados;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_list_view_parceiro, parent, false);
        TextView nomeParceiro = view.findViewById(R.id.NomeParceiro);
        TextView nota = view.findViewById(R.id.txtAvaliacao);
        CircleImageView imgPerfCliente = view.findViewById(R.id.imgPerfCliente);
        ServicosAceitos p = lServicosAgendados.get(position);
        //Log.i("Nome Parciero", p.getNome());

        if(p.getFoto().getData() != null){
            String foto = p.getFoto().getData();
            //Decondificando imagem recebida do JSON
            byte[]  stringDecodificada = Base64.decode(foto, Base64.DEFAULT);
            Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

            imgPerfCliente.setImageBitmap(imgbtmap);
        }

        nomeParceiro.setText(p.getNome());
        String v = p.getNota();
        if (v.length() == 1){
            nota.setText(p.getNota()+",0");
        }else {
            nota.setText(p.getNota());
        }

        return view;
    }
}