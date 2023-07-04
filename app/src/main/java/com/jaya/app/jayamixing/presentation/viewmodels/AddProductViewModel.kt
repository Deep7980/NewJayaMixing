package com.jaya.app.jayamixing.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.DialogData
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.domain.model.CuttingManTypes
import com.jaya.app.core.domain.model.FloorManagerType
import com.jaya.app.core.domain.model.MixingLabourList
import com.jaya.app.core.domain.model.MixingMantype
import com.jaya.app.core.domain.model.OvenManType
import com.jaya.app.core.domain.model.PackingSupervisorTypes
import com.jaya.app.core.domain.model.ProductType
import com.jaya.app.core.domain.useCases.AddProductUseCases
import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.jayamixing.extensions.MyDialog
import com.jaya.app.jayamixing.extensions.castListToRequiredTypes
import com.jaya.app.jayamixing.extensions.castValueToRequiredTypes
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCases: AddProductUseCases,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    val addProductBack = mutableStateOf<MyDialog?>(null)

    private val _productTypes = MutableStateFlow(emptyList<ProductType>())
    val productTypes = _productTypes.asStateFlow()

    val selectedProduct = mutableStateOf("Products")
    val selectedMixingMan = mutableStateOf("Mixing Man")
    val selectedCuttingMan = mutableStateOf("Cutting Man")
    val selectedOvenMan = mutableStateOf("Oven Man")
    val selectedPackingSupervisor = mutableStateOf("Packing Supervisor")

    var selectedFloor = mutableStateOf("Floor Manager")
    var selectedPlant = mutableStateOf("Select Plant")
    var isShiftAselected = mutableStateOf(true)
    var shiftABtnBackColor = mutableStateOf(SplashGreen)
    var shiftATxtColor = mutableStateOf(Color.White)

    var isShiftBselected = mutableStateOf(false)
    var shiftBBtnBackColor = mutableStateOf(Color.White)
    var shiftBTxtColor = mutableStateOf(Color.DarkGray)

    var isShiftCselected = mutableStateOf(false)
    var shiftCBtnBackColor = mutableStateOf(Color.White)
    var shiftCTxtColor = mutableStateOf(Color.DarkGray)

    var initialName = mutableStateOf("Ramesh Saha")

    private val _floorTypesList = MutableStateFlow(emptyList<FloorManagerType>())
    val floorTypes = _floorTypesList.asStateFlow()

    private val _mixingManList = MutableStateFlow(emptyList<MixingMantype>())
    val MixingManTypes = _mixingManList.asStateFlow()

    private val _cuttingManList = MutableStateFlow(emptyList<CuttingManTypes>())
    val CuttingManTypes = _cuttingManList.asStateFlow()

    private val _ovenManList = MutableStateFlow(emptyList<OvenManType>())
    val OvenManTypes = _ovenManList.asStateFlow()

    private val _packingSupervisorList = MutableStateFlow(emptyList<PackingSupervisorTypes>())
    val PackingSupervisorTypes = _packingSupervisorList.asStateFlow()

    private val _mixingLabourList = MutableStateFlow(emptyList<MixingLabourList>())
    val MixingLabourTypes = _mixingLabourList.asStateFlow()
    //val baseViewModel:BaseViewModel?=null

    init {
        getFloorTypes()
        getProductTypes()
        getMixingManTypes()
        getCuttingManTypes()
        getOvenManTypes()
        getPackingSupervisorTypes()
        getMixingLabourTypes()
//        baseViewModel?.userName = initialName.value
//        Log.d("Username from BaseViewModel", ": ${baseViewModel?.userName}")
    }


    fun onBackDialog() {
        addProductBack.value = MyDialog(
            data = DialogData(
                title = "Jaya Mixing Supervisor App",
                message = "Are you sure you want to exit ?",
                positive = "Yes",
                negative = "No",
            )
        )
        handleDialogEvents()
    }

    fun navigate(){
        appNavigator.tryNavigateTo(
            route = Destination.Dashboard(),
            // popUpToRoute = Destination.Dashboard(),
            isSingleTop = true,
            inclusive = true
        )
    }

    private fun handleDialogEvents() {
        addProductBack.value?.onConfirm = {

        }
        addProductBack.value?.onDismiss = {
            addProductBack.value?.setState(MyDialog.Companion.State.DISABLE)
        }
    }

    private fun getProductTypes() {
        addProductUseCases.getProductTypes()
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it.type) {

                    EmitType.PRODUCT_TYPES -> {
                        it.value?.castListToRequiredTypes<ProductType>()?.let { products ->
                            _productTypes.update { products }
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }


                    else -> {}
                }
            }.launchIn(viewModelScope)
    }
    private fun getFloorTypes() {
        addProductUseCases.getFloorTypes()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EmitType.FLOOR_LIST ->{
                        it.value?.castListToRequiredTypes<FloorManagerType>()?.let { floors ->
                            _floorTypesList.update { floors }
                            Log.d("FloorTypeList", "getFloorTypes: ${floors.toList()}")
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getMixingManTypes() {
        addProductUseCases.getMixingManTypes()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EmitType.MIXING_MAN_LIST ->{
                        it.value?.castListToRequiredTypes<MixingMantype>()?.let { man ->
                            _mixingManList.update { man }
                            Log.d("FloorTypeList", "getFloorTypes: ${man.toList()}")
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getCuttingManTypes() {
        addProductUseCases.getCuttingManTypes()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EmitType.CUTTING_MAN_LIST ->{
                        it.value?.castListToRequiredTypes<CuttingManTypes>()?.let { cuttingMan ->
                            _cuttingManList.update { cuttingMan }
                            Log.d("FloorTypeList", "getFloorTypes: ${cuttingMan.toList()}")
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getOvenManTypes() {
        addProductUseCases.getOvenManTypes()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EmitType.OVEN_MAN_LIST ->{
                        it.value?.castListToRequiredTypes<OvenManType>()?.let { ovenMan ->
                            _ovenManList.update { ovenMan }
                            Log.d("FloorTypeList", "getFloorTypes: ${ovenMan.toList()}")
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getPackingSupervisorTypes() {
        addProductUseCases.getPackingSupervisorTypes()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EmitType.PACKING_SUPERVISOR_LIST ->{
                        it.value?.castListToRequiredTypes<PackingSupervisorTypes>()?.let { packingSupervisor ->
                            _packingSupervisorList.update { packingSupervisor }
                            Log.d("PackagingSupervisorList", "PackagingSupervisorList: ${packingSupervisor.toList()}")
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getMixingLabourTypes() {
        addProductUseCases.getMixingLabourTypes()
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){
                    EmitType.MIXING_LABOUR_LIST ->{
                        it.value?.castListToRequiredTypes<MixingLabourList>()?.let { mixingLabour ->
                            _mixingLabourList.update { mixingLabour }
                            Log.d("MixingLabourList", "Mixing Labour List: ${mixingLabour.toList()}")
                        }
                    }

                    EmitType.NetworkError -> {
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {

                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }
}