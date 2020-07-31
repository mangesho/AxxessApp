package com.axxess.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axxess.myapplication.R
import com.axxess.myapplication.databinding.FragmentSearchBinding
import com.axxess.myapplication.model.SearchModel
import com.axxess.myapplication.viewmodels.SearchViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {


    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: SearchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, SearchViewModel.Factory(activity.application))
            .get(SearchViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     */
    private var searchAdapter: SearchAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.searchList.observe(viewLifecycleOwner, Observer<List<SearchModel>> { list ->
            list?.apply {
                searchAdapter?.searchList = list
                viewModel.hideProgress()
            }
        })
    }

    var isLoading: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        searchAdapter = SearchAdapter(ImageClick {

            var intent = Intent(context, ImageDetailActivity::class.java)
            intent.putExtra(ImageDetailActivity.IMAGE_DATA, it)
            startActivity(intent)

        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = searchAdapter
        }

        binding.root.findViewById<SearchView>(R.id.searchView).apply {

            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {

                    if(!query.equals("")){
                       viewModel.getSearchData("1", query.toString())
                    }else{
                        viewModel.hideProgress()
                    }


                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {

                    if(!query.equals("")){
                        viewModel.getSearchData("1", query.toString())
                    }else{
                        viewModel.hideProgress()
                    }

                    return false
                }

            })
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> { isLoading ->
            this.isLoading = isLoading
        })

        return binding.root
    }

    private fun onNetworkError() {
        Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
        viewModel.hideProgress()
    }

}