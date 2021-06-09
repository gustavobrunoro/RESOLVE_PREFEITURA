package com.resolveconsultoria.resolveprefeitura.Activity.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.resolveconsultoria.resolveprefeitura.R;

public class ComunidadeFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_comunidade, container, false);

        return  view;
    }
}