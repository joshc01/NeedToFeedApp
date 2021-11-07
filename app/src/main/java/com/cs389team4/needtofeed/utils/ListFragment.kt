package com.cs389team4.needtofeed.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.Model
import com.amplifyframework.datastore.DataStoreChannelEventName
import com.amplifyframework.datastore.DataStoreItemChange
import com.amplifyframework.datastore.events.NetworkStatusEvent
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.SubscriptionToken
import com.cs389team4.needtofeed.R
import com.cs389team4.needtofeed.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar

abstract class ListFragment<T : Model> : Fragment(), AdapterDelegate<T> {
    private val itemAdapter: RecyclerViewAdapter<T> = RecyclerViewAdapter(listOf(), this)
    private var itemMap = linkedMapOf<String, T>()
    private var subscriptionTokens = mutableSetOf<SubscriptionToken>()
    private lateinit var networkStatusBar: Snackbar

    private lateinit var binding: FragmentListBinding

    companion object {
        private val LOG = Amplify.Logging.forNamespace("NeedToFeed:ListFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.itemList
        recyclerView.adapter = itemAdapter

        networkStatusBar = Snackbar.make(view, "DataStore is not in sync.", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") { start() }
    }

    override fun onStart() {
        super.onStart()
        query()
        observe()
        subscribe()
    }

    override fun onStop() {
        super.onStop()
        unsubscribe()
    }

    abstract fun createModel(): T
    abstract fun updateModel(model: T): T
    abstract fun getModelClass(): Class<out T>
    abstract fun getViewModel(model: T): ViewModel<T>

    private fun query() {
        LOG.info("Query")
        Amplify.DataStore.query(getModelClass(),
            { results ->
                val itemsList = results.iterator().asSequence().toList()
                val pairsList = itemsList.map {it.id to it}
                val array = pairsList.toTypedArray()
                itemMap = linkedMapOf(*array)
                LOG.info("Query successful: ${itemMap.size} Items")
                loadContent()
            },
            {
                LOG.error("Query failed: ${it.message}", it)
            }
        )
    }

    private fun observe() {
        LOG.info("Observe")
        Amplify.DataStore.observe(getModelClass(),
            {
                LOG.info("Observe started")
            },
            { itemChange ->
                if (itemChange.initiator().equals(DataStoreItemChange.Initiator.REMOTE)) {
                    LOG.info("Observe remote item changed: ${itemChange.item()}")
                    query()
                } else {
                    LOG.info("Observe local item changed: ${itemChange.item()}")
                }
            },
            {
                LOG.error("Observe failed: ${it.message}", it)
            },
            {
                LOG.info("Observe completed")
            }
        )
    }

    private fun subscribe() {
        subscriptionTokens.add(Amplify.Hub.subscribe(HubChannel.DATASTORE) {
            LOG.debug("DataStore event: ${it.name} (${it.id}): ${it.data}")
            when (DataStoreChannelEventName.fromString(it.name)) {
                DataStoreChannelEventName.OUTBOX_MUTATION_PROCESSED -> {
                    query()
                }
                DataStoreChannelEventName.NETWORK_STATUS -> {
                    showNetworkStatusIndicator((it.data as NetworkStatusEvent).active)
                }
                else -> { }
            }
        })
        subscriptionTokens.add(Amplify.Hub.subscribe(HubChannel.API) {
            LOG.debug("API event: ${it.name} (${it.id}): ${it.data}")
        })
    }

    private fun unsubscribe() {
        subscriptionTokens.forEach { token -> Amplify.Hub.unsubscribe(token) }
        subscriptionTokens = mutableSetOf()
    }

    private fun start() {
        Amplify.DataStore.start(
            { LOG.info("Start successful") },
            { LOG.error("Start failed: ${it.message}", it) }
        )
    }

    private fun loadContent() {
        runOnUiThread {
            itemAdapter.values = itemMap.values.toList().map { getViewModel(it) }
            itemAdapter.notifyDataSetChanged()

            val emptyView = binding.emptyView
            emptyView.visibility = if (itemMap.size == 0) View.VISIBLE else View.GONE
            emptyView.text = getString(R.string.restaurants_unavailable)
        }
    }

    private fun showNetworkStatusIndicator(active: Boolean) {
        runOnUiThread {
            if (active) {
                networkStatusBar.dismiss()
            } else {
                networkStatusBar.show()
            }
        }
    }
}
