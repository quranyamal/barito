package org.tangaya.barito.view.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.SourceAdapter;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.model.Source;
import org.tangaya.barito.utlls.ApiResponse;
import org.tangaya.barito.viewmodel.MainViewModel;

import java.util.ArrayList;

import timber.log.Timber;

public class SourcesFragment extends Fragment {

    private MainViewModel viewModel;
    private SourceAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_sources, container, false);

        viewModel = ((MainActivity) getActivity()).getMainViewModel();

        adapter = new SourceAdapter();
        viewModel.getSources().observe(getActivity(), sources -> {
            if (sources != null) {
                adapter.setData(sources);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getSourceApiResponse().observe(this, this::consumeResponse);

        RecyclerView recyclerView = rootView.findViewById(R.id.sources_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {
            case LOADING:
//                progressDialog.show();
                break;
            case SUCCESS:
//                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.data);
                break;
            case ERROR:
//                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Coba lagi", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        Timber.d("onRenderSuccessResponse");
        if (!response.isJsonNull()) {

            JsonElement articlesJson = response.getAsJsonObject().getAsJsonArray("sources");
            Gson gson = new Gson();
            ArrayList<Source> sources = gson.fromJson(articlesJson, new TypeToken<ArrayList<Source>>(){}.getType());

            viewModel.getSources().setValue(sources);

            adapter.setData(sources);
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getActivity(), "terdapat kesalahan :(", Toast.LENGTH_SHORT).show();
        }
    }
}
