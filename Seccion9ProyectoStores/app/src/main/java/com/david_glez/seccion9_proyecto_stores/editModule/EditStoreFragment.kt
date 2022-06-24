package com.david_glez.seccion9_proyecto_stores.editModule

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.david_glez.seccion9_proyecto_stores.common.entities.StoreEntity
import com.david_glez.seccion9_proyecto_stores.mainModule.MainActivity
import com.david_glez.seccion9_proyecto_stores.R
import com.david_glez.seccion9_proyecto_stores.StoreApplication
import com.david_glez.seccion9_proyecto_stores.databinding.FragmentEditStoreBinding
import com.david_glez.seccion9_proyecto_stores.editModule.viewModel.EditStoreViewModel
import com.david_glez.seccion9_proyecto_stores.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreFragment : Fragment() {

    private lateinit var mBinding: FragmentEditStoreBinding
    private var mActivity: MainActivity? = null
    private var mIsEditMode: Boolean = false
    private var mStoreEntity: StoreEntity? = null

    // MVVM
    private lateinit var mEditStoreViewModel: EditStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mEditStoreViewModel = ViewModelProvider(requireActivity()).get(EditStoreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentEditStoreBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //MVVM
        setUpViewModel()

        setupTextFields()
    }

    private fun setUpViewModel() {
        mEditStoreViewModel.getStoreSelected().observe(viewLifecycleOwner){
            mStoreEntity = it
            if (it.id!= 0L){
                mIsEditMode = true
                setUiStore(it)
            } else {
                mIsEditMode = false
            }

            setupActionBar()
        }
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = if (mIsEditMode) getString(R.string.edit_store_title_edit)
        else getString(R.string.edit_store_title_add)

        setHasOptionsMenu(true)
    }

    private fun setupTextFields() {
        with(mBinding){
            etName.addTextChangedListener { validateFields(tilName) }
            etPhone.addTextChangedListener { validateFields(tilPhone) }
            etPhotoUrl.addTextChangedListener {
                validateFields(tilPhotoUrl)
                loadImage(it.toString().trim())
            }
        }
    }

    private fun loadImage(url: String){
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop().into(mBinding.imgPhoto)
    }

    private fun setUiStore(storeEntity: StoreEntity) {
        with(mBinding){
            etName.text = storeEntity.name.editable()
            etPhone.text = storeEntity.phone.editable()
            etPhotoUrl.text = storeEntity.photoUrl.editable()
            etWebSite.text = storeEntity.webSite.editable()
        }
    }

    //Extension clase string
    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {
                if (mStoreEntity != null && validateFields(mBinding.tilPhotoUrl, mBinding.tilPhone,
                    mBinding.tilName)){
                    with(mStoreEntity!!){
                        name = mBinding.etName.text.toString().trim()
                        phone = mBinding.etPhone.text.toString().trim()
                        webSite = mBinding.etWebSite.text.toString().trim()
                        photoUrl = mBinding.etPhotoUrl.text.toString().trim()
                    }

                    doAsync {
                        if (mIsEditMode) StoreApplication.dataBase.storeDao().updateStore(mStoreEntity!!)
                        else mStoreEntity!!.id = StoreApplication.dataBase.storeDao().addStore(mStoreEntity!!)

                        uiThread {
                            hideKeyBoard()
                            if (mIsEditMode){
                                // FIXME: aaaa me duele  
                                //mActivity?.updateStore(mStoreEntity!!)
                                Snackbar.make(mBinding.root,
                                    getString(R.string.edit_store_message_update_success),
                                    Snackbar.LENGTH_SHORT).show()
                            } else {
                                //mActivity?.addStore(mStoreEntity!!)
                                Toast.makeText(context, R.string.edit_store_message_save_success,
                                    Toast.LENGTH_SHORT).show()
                                mActivity?.onBackPressed()
                            }
                        }
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean{
        var isValid = true

        for (textField in textFields){
            if (textField.editText?.text.toString().trim().isEmpty()){
                textField.error = getString(R.string.helper_required)
                isValid = false
            } else textField.error = null
        }
        if(!isValid) Snackbar.make(mBinding.root, R.string.edit_store_message_message_valid,
            Snackbar.LENGTH_SHORT).show()
        return isValid
    }

    private fun validateFields(): Boolean {
        var isValid = true
        if (mBinding.etPhotoUrl.text.toString().trim().isEmpty()){
            mBinding.tilPhotoUrl.error = getString(R.string.helper_required)
            mBinding.etPhotoUrl.requestFocus()
            isValid = false
        }

        if (mBinding.etPhone.text.toString().trim().isEmpty()){
            mBinding.tilPhone.error = getString(R.string.helper_required)
            mBinding.etPhone.requestFocus()
            isValid = false
        }

        if (mBinding.etName.text.toString().trim().isEmpty()){
            mBinding.tilName.error = getString(R.string.helper_required)
            mBinding.etName.requestFocus()
            isValid = false
        }

        return isValid
    }

    private fun hideKeyBoard(){ //Ocultar el teclado cuando damos hacia atras
        val imn = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDestroyView() {
        hideKeyBoard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        // FIXME: me duele ayuda
        mEditStoreViewModel.setShowFab(true)
        setHasOptionsMenu(false)
        super.onDestroy()
    }
}