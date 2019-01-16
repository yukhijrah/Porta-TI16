package rakhadiasry.app.portalti16.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rakhadiasry.app.portalti16.R;


public class MahasiswaHolder extends RecyclerView.ViewHolder {
    public TextView txtnama, txtnim;
    public Button btndelete;
    public Button btnFav;

    public MahasiswaHolder(View itemView){

        super(itemView);

        btndelete = (Button) itemView.findViewById(R.id.btn_delete);
        btnFav = (Button) itemView.findViewById(R.id.btn_fav);

        txtnama = (TextView) itemView.findViewById(R.id.txt_nama);
        txtnim = (TextView) itemView.findViewById(R.id.txt_nim);

    }
}
