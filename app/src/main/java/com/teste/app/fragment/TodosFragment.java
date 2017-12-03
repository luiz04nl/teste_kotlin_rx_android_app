package com.teste.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teste.app.R;
import com.teste.app.adapter.FeedRecyclyAdapter;
import com.teste.app.model.FeedModel;
import com.teste.app.model.FeedModel1994;
import com.teste.app.model.FeedModel1997;
import com.teste.app.model.FeedModel1999;
import com.teste.app.model.FeedModel2022;
import com.teste.app.task.ObterFeedTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class TodosFragment extends Fragment {

    private RecyclerView mRecyclerView;

    /**
     * Método executado na criação da view do fragmento
     *
     * @param layoutInflater
     * @param viewGroup
     * @param bundle
     * @return View?
     */
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_todos, viewGroup, false);
        this.attachUI(view);
        return view;
    }

    /**
     * Método que anexa os componentes de UI a classe atual
     * @param view
     * @return
     */
    private void attachUI(View view) {
        this.mRecyclerView = view.findViewById(R.id.recycler_view);
        this.initializeRecyclerView();
    }

    /**
     * Método que inicia o recycler view
     * @return
     */
    private void initializeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        this.mRecyclerView.setLayoutManager(layoutManager);
        this.loadData(feeds -> this.mRecyclerView.setAdapter(new FeedRecyclyAdapter((ArrayList<FeedModel>) feeds)));
    }

    /**
     * Método que faz o carregamentos dos dados a patir de uma requisição http
     * @param onNewResults
     * @return
     */
    public void loadData(Consumer<List<FeedModel>> onNewResults) {

        ObterFeedTask obterFeedTask = new ObterFeedTask(this.getActivity());
        obterFeedTask.requestData(jsonArray -> {

            List<FeedModel> feeds = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int type = jsonObject.getInt("type");
                    switch (type) {
                        case 2022: {
                            feeds.add(new FeedModel2022(jsonObject));
                            break;
                        }
                        case 1999: {
                            feeds.add(new FeedModel1999(jsonObject));
                            break;
                        }
                        case 1994: {
                            feeds.add(new FeedModel1994(jsonObject));
                            break;
                        }
                        case 1997: {
                            feeds.add(new FeedModel1997(jsonObject));
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            onNewResults.accept(feeds);

        });
    }

}