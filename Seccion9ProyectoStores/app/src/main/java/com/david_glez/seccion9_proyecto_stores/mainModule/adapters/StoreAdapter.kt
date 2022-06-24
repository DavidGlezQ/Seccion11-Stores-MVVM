package com.david_glez.seccion9_proyecto_stores.mainModule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import com.david_glez.seccion9_proyecto_stores.R
import com.david_glez.seccion9_proyecto_stores.databinding.ItemStoreBinding

class StoreAdapter(private var stores: MutableList<StoreEntity>, private var listener: OnClickListener):
    RecyclerView.Adapter<StoreAdapter.ViewHolder>(){

    private lateinit var mContext: Context // La 'm' se refiere a que es un miembro de la clase

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemStoreBinding.bind(view) // Creamos en binding para el item

        fun setListener(storeEntity: StoreEntity){
            with(mBinding.root){
                setOnClickListener { listener.onClick(storeEntity) }
                setOnLongClickListener{
                    listener.onDeleteStore(storeEntity)
                    true
                }
            }
            mBinding.cbFavorite.setOnClickListener {
                listener.onFavoriteStore(storeEntity)
            }
        }
    }

    //Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_store, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = stores.get(position)

        with(holder){
            setListener(store)

            mBinding.tvName.text = store.name
            mBinding.cbFavorite.isChecked = store.isFavorite
            Glide.with(mContext)
                .load(store.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imgPhoto)
        }
    }

    override fun getItemCount(): Int = stores.size

    fun add(storeEntity: StoreEntity) {
        if (storeEntity.id != 0L){
            if (!stores.contains(storeEntity)){
                stores.add(storeEntity)
                notifyItemChanged(stores.size -1)
            } else {
                update(storeEntity)
            }
        }
    }

    fun setStores(stores: List<StoreEntity>){
        this.stores = stores as MutableList<StoreEntity>
        notifyDataSetChanged()
    }

    private fun update(storeEntity: StoreEntity){
        val index = stores.indexOf(storeEntity)
        if (index != -1){
            stores.set(index, storeEntity)
            notifyItemChanged(index)
        }
    }
}