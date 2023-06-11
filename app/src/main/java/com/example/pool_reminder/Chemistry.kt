package com.example.pool_reminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pool_reminder.adapters.ChemistryAdapter
import com.example.pool_reminder.databinding.FragmentChemistryBinding
import com.example.pool_reminder.models.ChemistryModel
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.io.IOException

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Chemistry : Fragment() {

    private var _binding: FragmentChemistryBinding? = null
    private val binding get() = _binding!!
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var fetchValueJob: Job? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var chemistryAdapter: ChemistryAdapter
    private var dataList= mutableListOf<ChemistryModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChemistryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateTextView()

        recyclerView=binding.rvChemistry
        recyclerView.layoutManager=GridLayoutManager(view.context,2)
        chemistryAdapter=ChemistryAdapter(view.context)
        recyclerView.adapter=chemistryAdapter

        dataList.add(ChemistryModel("Agicide","",10.0,R.drawable.ic_launcher_background))
        dataList.add(ChemistryModel("Chlore","",12.0,R.drawable.ic_launcher_background))
        dataList.add(ChemistryModel("Oxygen","",48.0,R.drawable.ic_launcher_background))

        chemistryAdapter.setDataList(dataList)
    }
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    private fun updateTextView() {
        if (isInternetAvailable(requireContext())) {
            fetchValueJob = coroutineScope.launch {
                val value = fetchValueFromWebsite()
                binding.courselbl.text = value

                val course = value.toDoubleOrNull() ?: return@launch

                for (item in dataList) {
                    item.price = item.price * course
                }
                chemistryAdapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(requireContext(), "No internet connection. Please check your network settings.", Toast.LENGTH_LONG).show()
        }
    }


    private suspend fun fetchValueFromWebsite(): String {
        return withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect("https://kurs.com.ua/").get()
                val docu = doc.getElementsByClass("course").get(2).text().split(" ")[0]
                docu
            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Error while fetching data from the website. Please try again later.", Toast.LENGTH_LONG).show()
                "N/A"
            } catch (e: Exception) {
                "N/A"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fetchValueJob?.cancel()
        _binding = null
    }
}
