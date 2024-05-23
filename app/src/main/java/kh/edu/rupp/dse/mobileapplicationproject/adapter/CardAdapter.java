package kh.edu.rupp.dse.mobileapplicationproject.adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kh.edu.rupp.dse.mobileapplicationproject.Helper.ChangeNumberItemsListener;
import kh.edu.rupp.dse.mobileapplicationproject.Helper.ManagementCart;
import kh.edu.rupp.dse.mobileapplicationproject.domain.Foods;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.Viewholder{

    ArrayList<Foods> listItemSelected;

    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    @NonNull
    @Override
    public CardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext().inflate(R.layout.viewholder_, parent, attchToRoot: false);
        return Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholder {
        
    }
}
