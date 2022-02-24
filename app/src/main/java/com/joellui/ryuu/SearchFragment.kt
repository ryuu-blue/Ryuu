package com.joellui.ryuu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joellui.ryuu.adapter.SearchResultAdapter
import com.joellui.ryuu.model.AnimeDetails
import com.joellui.ryuu.repository.Repository
import com.joellui.ryuu.viewModel.SearchViewModel
import com.joellui.ryuu.viewModel.SearchViewModelFactory


class SearchFragment : Fragment(),SearchResultAdapter.OnClickListener {

    var search_result = mutableListOf<AnimeDetails>()
    private lateinit var viewModel: SearchViewModel
    var isScrolling: Boolean = false
    var currentItems: Int = 0
    var totalItems: Int = 0
    var scrolledOutItems: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val searchResult = view.findViewById<RecyclerView>(R.id.rv_searchAnime)
        val searchBox = view.findViewById<SearchView>(R.id.sv_searchBox)

        val repository = Repository()
        val viewModelFactory = SearchViewModelFactory(repository)

//        search api call
        searchBox.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    searchResult.visibility = View.VISIBLE
                    viewModel.getSearchAnime(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        viewModel.searchResult.observe(viewLifecycleOwner, Observer { result->
            if (result.isSuccessful){
                val searchResultAdapter = SearchResultAdapter(result.body()?.data?.documents!!,this@SearchFragment)
                if (search_result.isEmpty()){
                    search_result.addAll(result.body()?.data?.documents!!)
                    val totalPage:Int? = result.body()?.data?.last_page
                    searchResult.adapter = searchResultAdapter
                    searchResult.layoutManager = GridLayoutManager(activity,3)


                }else{
                    search_result.clear()
                    search_result.addAll(result.body()?.data?.documents!!)
                    searchResult.adapter = searchResultAdapter
                    searchResult.layoutManager = GridLayoutManager(activity, 3)
                }
            } else {
                Toast.makeText(context, result.message(), Toast.LENGTH_SHORT).show()
            }

        })

        searchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val layoutManager = searchResult.layoutManager as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        //show your loading view 
                        // load content in background
//                            TODO: infinite Scroll
                        Toast.makeText(context, "Your reached the END", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

        return view
    }

    override fun searchResultClick(position: Int) {
        val id = search_result[position].id
        Toast.makeText(context,id.toString(),Toast.LENGTH_SHORT).show()
        val action = SearchFragmentDirections.actionSearchFragmentToAnimeDetailsFragment(id)
        Navigation.findNavController(this.requireView()).navigate(action)
    }

}