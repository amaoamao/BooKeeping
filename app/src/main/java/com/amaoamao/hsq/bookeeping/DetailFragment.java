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

import com.amaoamao.hsq.bookeeping.Entity.Debt;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;


public class DetailFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new MyDetailRecyclerViewAdapter(Stream.of(Debt.order("id desc").find(Debt.class))
                    .filter(debt -> MainFragment.formateYYMM.format(debt.getTimeCreated()).equals(MainFragment.formateYYMM.format(MainFragment.calendar.getTime()))).collect(Collectors.toList())
                    , mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void refreshRV() {
        if (getView() != null) {
            MyDetailRecyclerViewAdapter adapter = (MyDetailRecyclerViewAdapter) ((RecyclerView) getView()).getAdapter();
            adapter.getmValues().clear();
            adapter.getmValues().addAll(Stream.of(Debt.order("id desc").find(Debt.class))
                    .filter(debt -> MainFragment.formateYYMM.format(debt.getTimeCreated()).equals(MainFragment.formateYYMM.format(MainFragment.calendar.getTime()))).collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Debt item);
    }
}
