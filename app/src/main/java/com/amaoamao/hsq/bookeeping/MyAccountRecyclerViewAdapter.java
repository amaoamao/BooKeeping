package com.amaoamao.hsq.bookeeping;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amaoamao.hsq.bookeeping.Entity.Account;
import com.amaoamao.hsq.bookeeping.Entity.Debt;
import com.amaoamao.hsq.bookeeping.Utils.Utils;

import java.util.List;


class MyAccountRecyclerViewAdapter extends RecyclerView.Adapter<MyAccountRecyclerViewAdapter.ViewHolder> {

    private final List<Account> mValues;

    MyAccountRecyclerViewAdapter(List<Account> items) {
        mValues = items;
    }

    List<Account> getmValues() {
        return mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tv_account_name.setText(mValues.get(position).getName());
        holder.tv_account_in.setText(String.valueOf(Utils.inThisMonth(mValues.get(holder.getAdapterPosition()).getDebts(), MainFragment.calendar)));
        holder.tv_account_out.setText(String.valueOf(Utils.outThisMonth(mValues.get(holder.getAdapterPosition()).getDebts(), MainFragment.calendar)));

        holder.mView.setOnLongClickListener(v -> {
            new MaterialDialog.Builder(v.getContext()).items("删除").itemsCallback((dialog, itemView, p, text) -> {

                switch (p) {
                    case 0:
                        int pp = holder.getAdapterPosition();
                        Account.delete(Account.class, getmValues().get(pp).getId());
                        getmValues().remove(pp);
                        notifyItemRemoved(pp);
                        break;
                    default:
                        break;
                }
            }).show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        TextView tv_account_name;
        TextView tv_account_out;
        TextView tv_account_in;
        Account mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tv_account_name = (TextView) view.findViewById(R.id.tv_account_name);
            tv_account_in = (TextView) view.findViewById(R.id.tv_account_in);
            tv_account_out = (TextView) view.findViewById(R.id.tv_account_out);
        }

    }
}
