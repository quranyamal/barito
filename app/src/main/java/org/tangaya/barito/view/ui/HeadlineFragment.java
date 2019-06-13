package org.tangaya.barito.view.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.source.NewsService;
import org.tangaya.barito.data.source.RESTClient;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeadlineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeadlineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeadlineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String searchKeyword;

    public HeadlineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeadlineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeadlineFragment newInstance(String param1, String param2) {
        HeadlineFragment fragment = new HeadlineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_headline, container, false);


//        handleIntent(getActivity().getIntent());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("mengambil data artikel...");
        progressDialog.show();

        NewsService service = RESTClient.getInstance().create(NewsService.class);

        //Call<APIResponse> articlesCall = service.getHeadlineNews();

        Call<APIResponse> articlesCall = service.getHeadlineNews();
        articlesCall.enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                Log.d("enqueue", "onResponse. response: " + response);

                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Ups, terdapat kesalahan :(", Toast.LENGTH_SHORT).show();

                Log.d("onFailure call", call.toString());
                Log.d("onFailure throwable", t.toString());
            }
        });

        return rootView;
    }

//    private void handleIntent(Intent intent) {
//
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//
//            Toast.makeText(getActivity().getApplicationContext(), "search for " + query, Toast.LENGTH_SHORT).show();
//            if (searchKeyword == null) {
//                searchKeyword = query;
//            }
//        }
//    }

    private void generateDataList(final APIResponse apiResponse) {

        Log.d("SearchResultActivity", "articleList size: " + apiResponse.getArticles().size());
        recyclerView = getActivity().findViewById(R.id.headline_recycler);

        adapter = new ArticleAdapter(getActivity(), apiResponse.getArticles());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
                recyclerView, new NewsRecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewsPageActivity.class);
                intent.putExtra("url", apiResponse.getArticles().get(position).getUrl());

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

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
