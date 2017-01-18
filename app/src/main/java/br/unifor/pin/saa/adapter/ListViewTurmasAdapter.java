package br.unifor.pin.saa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.unifor.pin.saa.R;
import br.unifor.pin.saa.entity.Turmas;

/**
 * Classe responsavel por adaptar a lista de turmas no fragment
 * @author Victor Sales
 * Created by Victor Sales on 03/01/2017.
 */
public class ListViewTurmasAdapter extends BaseAdapter {

    private Context mContext;
    private List<Turmas> listTurmas;

    public ListViewTurmasAdapter(Context mContext, List<Turmas> listTurmas) {
        this.mContext = mContext;
        this.listTurmas = listTurmas;
    }

    @Override
    public int getCount() {
        return listTurmas.size();
    }

    @Override
    public Object getItem(int position) {

        return listTurmas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_row_turmas, null);
        }

        TextView txtLabelDisciplina = (TextView) convertView.findViewById(R.id.label_disciplina);
        TextView txtLabelProfessor = (TextView) convertView.findViewById(R.id.label_professor);
        TextView txtLabelSemestre = (TextView) convertView.findViewById(R.id.label_semestre);


        ImageView imgView = (ImageView) convertView.findViewById(R.id.imgViewTurmas);

        txtLabelDisciplina.setText(listTurmas.get(position).getDisciplina().toUpperCase());
        txtLabelProfessor.setText(listTurmas.get(position).getProfessor().getNome());
        txtLabelSemestre.setText(listTurmas.get(position).getSemestre().toString());


        imgView.setImageResource(R.drawable.ic_input_black_24dp);

        return convertView;
    }
}
