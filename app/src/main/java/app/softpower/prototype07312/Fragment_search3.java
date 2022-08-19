package app.softpower.prototype07312;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.softpower.prototype07312.databinding.FragmentSearch3Binding;

public class Fragment_search3 extends Fragment {
    private FragmentSearch3Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearch3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView mRecyclerView = binding.recyclerView2;

        // initiate adapter
        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter();

        // initiate recyclerView
        mRecyclerView.setAdapter(myRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // adapt data
        myRecyclerAdapter.setStringList(getResources().getStringArray(R.array.function));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
