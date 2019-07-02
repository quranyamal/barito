package org.tangaya.barito.view.ui;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.tangaya.barito.BuildConfig;
import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.utlls.ApiResponse;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;
import org.tangaya.barito.viewmodel.MainViewModel;

import java.util.ArrayList;

import timber.log.Timber;


public class HeadlineFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    private MainViewModel mViewModel;

    ProgressDialog progressDialog;

    public HeadlineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading news...");
        progressDialog.setCancelable(false);

        mViewModel = ((MainActivity) getActivity()).getMainViewModel();
        mViewModel.init();

        adapter = new ArticleAdapter(getActivity());

        setupRecyclerView(getView());

        mViewModel.getHeadlineApiResponse().observe(this, this::consumeResponse);
        mViewModel.hitHeadlineApi("us", BuildConfig.API_KEY);
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {
            case LOADING:
                progressDialog.show();
                break;
            case SUCCESS:
                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.data);
                break;
            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Coba lagi", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        Timber.d("onRenderSuccessResponse");
        if (!response.isJsonNull()) {

            JsonElement articlesJson = response.getAsJsonObject().getAsJsonArray("articles");
            Gson gson = new Gson();
            ArrayList<Article> articles = gson.fromJson(articlesJson, new TypeToken<ArrayList<Article>>(){}.getType());

            mViewModel.getHeadlineArticles().setValue(articles);

            adapter.setData(articles);
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getActivity(), "terdapat kesalahan :(", Toast.LENGTH_SHORT).show();
        }
    }

    public static HeadlineFragment newInstance() {
        return new HeadlineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_headline, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        setupRecyclerView(view);
    }

    private void setupRecyclerView(View parent) {
        recyclerView = parent.findViewById(R.id.headline_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
                recyclerView, (view, position) -> {
                    String url = mViewModel.getHeadlineArticles().getValue().get(position).getUrl();
                    Timber.d("addOnItemTouchListener. url: " + url);
                    if (!url.equals("")) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), NewsPageActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
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
