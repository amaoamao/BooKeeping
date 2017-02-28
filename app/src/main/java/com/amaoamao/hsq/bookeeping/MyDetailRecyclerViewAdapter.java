package com.amaoamao.hsq.bookeeping;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.amaoamao.hsq.bookeeping.Entity.Debt;

import java.util.List;

class MyDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyDetailRecyclerViewAdapter.ViewHolder> {

    private final List<Debt> mValues;
    private final DetailFragment.OnListFragmentInteractionListener mListener;

    MyDetailRecyclerViewAdapter(List<Debt> items, DetailFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public List<Debt> getmValues() {
        return mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mDebt = mValues.get(position);
        holder.tv_content.setText(mValues.get(position).getDescription());
        holder.tv_amount.setText((mValues.get(position).getIn() ? "+   " : "-   ") + mValues.get(position).getAmount());

        holder.mView.setOnClickListener(v -> {

                Snackbar.make(v, mValues.get(holder.getAdapterPosition()).getDescription(), Snackbar.LENGTH_SHORT).show();
        });
        holder.mView.setOnLongClickListener(v -> {
            new MaterialDialog.Builder(v.getContext()).items("删除").itemsCallback((dialog, itemView, p, text) -> {

                switch (p) {
                    case 0:
                        int pp = holder.getAdapterPosition();
                        Debt.delete(Debt.class, getmValues().get(pp).getId());
                        getmValues().remove(pp);
                        if (null != mListener) {
                            mListener.onListFragmentInteraction(holder.mDebt);
                        }
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
        TextView tv_content;
        TextView tv_amount;
        Debt mDebt;
        View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tv_content = (TextView) view.findViewById(R.id.content);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
        }

    }
}
