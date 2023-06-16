package com.example.infinitescroll.normalrecycler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class GameFragmentAdapter extends FragmentStateAdapter {
    // Déclaration de la liste des fragments
    private final ArrayList<GameFragment> fragmentList = new ArrayList<>();

    /**
     * Constructeur de l'adapter
     */
    public GameFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    /**
     * Creer un fragment
     * @param position postition du fragment
     * @return le fragment a la position donné
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    /**
     * Ajouter un fragment
     * @param fragment le fragment a ajouter
     */
    public void addFragment(GameFragment fragment) {
        fragmentList.add(fragment);
    }

    /**
     * Retourne le total de fragments
     * @return le total de fragments
     */
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
