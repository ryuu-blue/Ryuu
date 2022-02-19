package com.joellui.ryuu

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joellui.ryuu.adapter.GridRandomAdapter
import com.joellui.ryuu.model.AnimeDetails
import com.joellui.ryuu.repository.Repository
import com.joellui.ryuu.viewModel.MainViewModel
import com.joellui.ryuu.viewModel.MainViewModelFactory


class MainFragment : Fragment(), GridRandomAdapter.OnClickListener {

    private lateinit var viewModel: MainViewModel

    //    50 random anime list
    var AnimeMutableList = mutableListOf<AnimeDetails>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("MSS", "Initializing Main Fragment")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val btnSearch = view.findViewById<ImageButton>(R.id.btn_search)
        val btnProfile = view.findViewById<ImageButton>(R.id.btn_profile)
        val randomAnimeStage = view.findViewById<RecyclerView>(R.id.rv_randomAnime)

//      navigator
        btnSearch.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_searchFragment)
        }
        btnProfile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_profileFragment)
        }

//        viewModel for calling the Anime
        val repo = Repository()
        val viewModelFactory = MainViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

//        check for INTERNET CONNECTION
        context?.let {
            if (viewModel.isOnline(it)) {
                if (AnimeMutableList.isEmpty())
                viewModel.randomAnime()
                else
                    AnimeMutableList.clear()
            } else {
//            isOffline the dialog box to indicate
                val offlineAlert = View.inflate(context, R.layout.dialogbox, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(offlineAlert)
                val dialog = builder.create()
                dialog.show()

            }
        }

        //for 50 random anime
        viewModel.RandomAnimeMLD.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {

                Log.v("MSS", "Get Response")
                AnimeMutableList.addAll(response.body()!!.data)
                val gAdapter = GridRandomAdapter(AnimeMutableList, this)
                randomAnimeStage.adapter = gAdapter
                randomAnimeStage.layoutManager = GridLayoutManager(activity, 3)
            } else {
                Log.d("Response", response.message())
            }
        })

        return view
    }

    override fun onClick(position: Int) {
        val id = AnimeMutableList[position].id
        Toast.makeText(context, id.toString(), Toast.LENGTH_SHORT).show()
        val action = MainFragmentDirections.actionMainFragmentToAnimeDetailsFragment(id)
        Navigation.findNavController(this.requireView()).navigate(action)

    }

}