package br.unifor.pin.saa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.entity.Aula;

/**
 * Classe reponsavel por Adaptar a lista
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class ListViewAulaAdapter extends BaseAdapter {

    private Context mContext;
    private List<Aula> listAula;

    public ListViewAulaAdapter(Context mContext, List<Aula> listAula) {
        this.mContext = mContext;
        this.listAula = listAula;
    }

    @Override
    public int getCount() {
        return listAula.size();
    }

    @Override
    public Object getItem(int position) {
        return listAula.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_row_aula, null);
        }

        TextView txtDescricaoAula = (TextView) convertView.findViewById(R.id.desc_aula);
       // TextView txtLabelStatusAgendamento = (TextView) convertView.findViewById(R.id.label_status_agendamento);
       // TextView txtLabelDataAgendamento = (TextView) convertView.findViewById(R.id.label_data_agendamento);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imgViewAula);

       // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);

        txtDescricaoAula.setText("Aula:  " + listAula.get(position).getDescricao());
//        txtLabelStatusAgendamento.setText("Status: " + listAgendmaneto.get(position).getStatusAgendamento().getDescricao());
//        txtLabelDataAgendamento.setText("Data: " +dateFormat.format(listAgendmaneto.get(position).getDataInicio()));
        imgView.setImageResource(R.drawable.arrow);

        return convertView;
    }
}
