package com.amaoamao.hsq.bookeeping;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amaoamao.hsq.bookeeping.Entity.Account;
import com.amaoamao.hsq.bookeeping.Entity.Debt;

public class AccountFragment extends Fragment {

    public AccountFragment() {
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        refreshRV();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new MyAccountRecyclerViewAdapter(Account.findAll(Account.class, true)));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void refreshRV() {
        if (getView() != null) {
            MyAccountRecyclerViewAdapter adapter = (MyAccountRecyclerViewAdapter) ((RecyclerView) getView()).getAdapter();
            adapter.getmValues().clear();
            adapter.getmValues().addAll(Account.findAll(Account.class, true));
            adapter.notifyDataSetChanged();
        }
    }
}
