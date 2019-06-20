package org.tangaya.barito.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;
import org.tangaya.barito.viewmodel.MainViewModel;
import java.util.ArrayList;


public class HeadlineFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    private MainViewModel mViewModel;

    public HeadlineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static HeadlineFragment newInstance() {
        return new HeadlineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel.init();
        adapter = new ArticleAdapter(getActivity());

        mViewModel.getHeadlines().observe(getActivity(), articles -> {
            if (articles != null) {
                adapter.setData(articles);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_headline, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        setupRecyclerView(view);
    }

    private void setupRecyclerView(View parent) {
        recyclerView = parent.findViewById(R.id.headline_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
                recyclerView, (view, position) -> {
                    Intent intent = new Intent(getActivity().getApplicationContext(), NewsPageActivity.class);
                    intent.putExtra("url", mViewModel.getHeadlines().getValue().get(position).getUrl());

                    startActivity(intent);
                }));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
