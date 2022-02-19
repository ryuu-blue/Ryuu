package com.joellui.ryuu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.joellui.ryuu.repository.Repository
import com.joellui.ryuu.viewModel.AnimeDetailsViewModel
import com.joellui.ryuu.viewModel.AnimeDetailsViewModelFactory
import com.joellui.ryuu.viewModel.MainViewModelFactory


class AnimeDetailsFragment : Fragment() {

    private lateinit var viewModel: AnimeDetailsViewModel
    val args: AnimeDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_anime_details, container, false)

        val title = view.findViewById<TextView>(R.id.tv_Title)
        val status = view.findViewById<TextView>(R.id.tv_status)
        val format = view.findViewById<TextView>(R.id.tv_format)
        val year = view.findViewById<TextView>(R.id.tv_year)
        val cover = view.findViewById<ImageView>(R.id.iv_coverImage)
        val banner = view.findViewById<ImageView>(R.id.iv_bannerImage)

//        viewModel for calling Anime
        val repo = Repository()
        val viewModelFactory = AnimeDetailsViewModelFactory(repo)
        viewModel = ViewModelProvider(this,viewModelFactory).get(AnimeDetailsViewModel::class.java)

//        calling the anime
        viewModel.getAnime(args.id)


//        observing the result
        viewModel.AnimeInfo.observe(viewLifecycleOwner, Observer { response->
            if (response.isSuccessful){
                Log.d("Response", response.message())
                Log.d("Response",response.body()?.data!!.id.toString())
                if (response.body()?.data!!.titles.en != null)
                    title.text = response.body()?.data!!.titles.en
                else
                    title.text = response.body()?.data!!.titles.jp
                status.text = response.body()?.data!!.status.toString()
                format.text = response.body()?.data!!.format.toString()
                year.text = response.body()?.data!!.season_year.toString()

                cover.load(response.body()?.data!!.cover_image)
                if(response.body()?.data!!.banner_image != null)
                    banner.load(response.body()?.data!!.banner_image)
                else
                    banner.load(response.body()?.data!!.cover_image)
            }
        })

        return view
    }


}