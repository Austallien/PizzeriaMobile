package com.example.pizzeriamobile.core.fragment.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.view.activity.ActivityDialog;
import com.example.pizzeriamobile.core.activity.dialog.Appearance;
import com.example.pizzeriamobile.model.http.receive.Order;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    LayoutInflater inflater;
    ArrayList<Order> order;
    View.OnClickListener listener;

    public OrderListAdapter(Context context, ArrayList<Order> data){
        inflater = LayoutInflater.from(context);
        order = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_order_order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    /*public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        this.listener = listener;
    }*/

    protected class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView textViewNumber;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewTotal;
        TextView textViewStatus;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            textViewNumber = view.findViewById(R.id.textViewFragmentOrderNumberValue);
            textViewDate = view.findViewById(R.id.textViewFragmentOrderDateValue);
            textViewTime = view.findViewById(R.id.textViewFragmentOrderTimeValue);
            textViewTotal = view.findViewById(R.id.textViewFragmentOrderTotalValue);
            textViewStatus = view.findViewById(R.id.textViewFragmentOrderStatusValue);
        }

        public void bind(int position) {
            Order order = OrderListAdapter.this.order.get(position);

            String textNumber = order.id + "";
            String textDate = new Date(order.registrationDate / 10000).toString();
            String textTime = new Time(order.receivingTime).toString();
            String textTotal = (order.total * (1 - order.discount.Value)) + "";
            String textStatus = order.status.Name;

            view.setBackgroundColor(position % 2 == 0 ?
                    view.getContext().getResources().getColor(R.color.list_item_0, null) :
                    view.getContext().getResources().getColor(R.color.list_item_1, null));

            textViewNumber.setText(textNumber);
            textViewDate.setText(textDate);
            textViewTime.setText(textTime);
            textViewTotal.setText(textTotal);
            textViewStatus.setText(textStatus);

            view.setOnClickListener(listener);
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(view.getContext(), ActivityDialog.class);
                    intent.putExtra(Appearance.KEY, Appearance.ORDER_SHOW);
                    intent.putExtra(Appearance.OrderShow.KEY, getAdapterPosition());
                    view.getContext().startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
}
