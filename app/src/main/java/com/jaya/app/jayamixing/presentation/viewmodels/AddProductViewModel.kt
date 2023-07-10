package com.jaya.app.jayamixing.presentation.viewmodels

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.Destination
import com.jaya.app.core.common.DialogData
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.domain.model.CuttingLabourList
import com.jaya.app.core.domain.model.CuttingManTypes
import com.jaya.app.core.domain.model.FloorManagerType
import com.jaya.app.core.domain.model.MixingLabourList
import com.jaya.app.core.domain.model.MixingMantype
import com.jaya.app.core.domain.model.OvenManType
import com.jaya.app.core.domain.model.PackingSupervisorTypes
import com.jaya.app.core.domain.model.ProductType
import com.jaya.app.core.domain.model.SupervisorPrefilledDataModel
import com.jaya.app.core.domain.model.SupervisorPrefilledDataResponse
import com.jaya.app.core.domain.useCases.AddProductUseCases
import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.jayamixing.R
import com.jaya.app.jayamixing.extensions.MyDialog
import com.jaya.app.jayamixing.extensions.castListToRequiredTypes
import com.jaya.app.jayamixing.extensions.castValueToRequiredTypes
import com.jaya.app.jayamixing.presentation.viewStates.ImageSource
import com.jaya.app.jayamixing.ui.theme.SplashGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCases: AddProductUseCases,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var mixingLabourSearchQuery by mutableStateOf(TextFieldValue(""))
    var cuttingLabourSearchQuery by mutableStateOf(TextFieldValue(""))
    var isDataLoaded = mutableStateOf(false)
    var showDialog = mutableStateOf(false)
    //var mixingLabourTextValue = mixingLabourSearchQuery.text

//    private val _floorTypesList = MutableStateFlow(emptyList<FloorManagerType>())
//    val floorTypes = _floorTypesList.asStateFlow()

    val addProductBack = mutableStateOf<MyDialog?>(null)
    var mixingLabourList = mutableStateListOf<String>()
    var cuttingLabourList = mutableStateListOf<String>()
    var mixingLabourTextName = mutableStateOf("")
    var cuttingLabourName = mutableStateOf("")

    var leftAddedKG = mutableStateOf("")
    var brokenAddedKG = mutableStateOf("")

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

    var isShiftCselected = mutableStateOf(true)
    var shiftCBtnBackColor = mutableStateOf(Color.White)
    var shiftCTxtColor = mutableStateOf(Color.DarkGray)

    var initialName = mutableStateOf("Ramesh Saha")
    var productDesc = mutableStateOf("")
    val isSelectedMixingLabour = mutableStateOf(false)
    val isSelectedCuttingLabour = mutableStateOf(false)

    var loadMixingLabourListState = mutableStateOf(false)
    var loadCuttingLabourListState = mutableStateOf(false)
    val uploadImageState = mutableStateOf(false)

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
    val mixingLabours = _mixingLabourList.asStateFlow()

    private val _cuttingLabourList = MutableStateFlow(emptyList<CuttingLabourList>())
    val cuttingLabours = _cuttingLabourList.asStateFlow()

    private val _capturedImages = MutableStateFlow(mutableListOf<Bitmap>())
    val capturedImages = _capturedImages.asStateFlow()
    var capturedImagesList= mutableStateListOf<Bitmap>()

    val selectedMixingLabourList = mutableStateListOf<String>()
    val selectedCuttingLabourList = mutableStateListOf<String>()


    val superVisorName = mutableStateOf("")

    private val _MixingManList = MutableStateFlow(emptyList<String>())
    val MixingManList = _MixingManList.asStateFlow()

    private val _CuttingManList = MutableStateFlow(emptyList<String>())
    val cuttingManList = _CuttingManList.asStateFlow()

    private val _OvenManList = MutableStateFlow(emptyList<String>())
    val ovenManList = _OvenManList.asStateFlow()

    private val _PackingSupervisorList = MutableStateFlow(emptyList<String>())
    val packingSupervisorList = _PackingSupervisorList.asStateFlow()


    val chooseImage = mutableStateOf<MyDialog?>(null)
    val imageChooserDialogHandler = mutableStateOf<MyDialog?>(null)
    val selectedImageSource = mutableStateOf(ImageSource.None)
    val cameraStateOpen = mutableStateOf(false)
    var dataLoading by mutableStateOf(false)
        private set

    //val mixingLabourListNames = mutableStateOf("")
    //val baseViewModel:BaseViewModel?=null
    val userId = "1234"
    init {
//        getFloorTypes()
//        getProductTypes()
//        getMixingManTypes()
//        getCuttingManTypes()
//        getOvenManTypes()
//        getPackingSupervisorTypes()
        getSupervisorPrefilledDataTypes(userId)

        //getMixingLabourTypes()
//        baseViewModel?.userName = initialName.value
//        Log.d("Username from BaseViewModel", ": ${baseViewModel?.userName}")
    }

//    fun cameraIconClick() {
//        cameraStateOpen.value = true
//        showDialog.value = false
//    }

    fun addMixingLabourToList() {
        if (mixingLabourSearchQuery.text.isNotEmpty()) {
            selectedMixingLabourList.add(mixingLabourSearchQuery.text)
            mixingLabourSearchQuery = mixingLabourSearchQuery.copy(
                text = ""
            )
        }
    }

    fun addCuttingLabourToList() {

        if (cuttingLabourSearchQuery.text.isNotEmpty()) {
            selectedCuttingLabourList.add(cuttingLabourSearchQuery.text)
            cuttingLabourSearchQuery = cuttingLabourSearchQuery.copy(
                text = ""
            )
        }
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

    fun backToDashboardPage() {
        appNavigator.tryNavigateTo(
            route = Destination.Dashboard(),
            //popUpToRoute = Destination.AddProduct(),
            isSingleTop = true,
            inclusive = true
        )
        viewModelScope.launch {
            delay(2000L)
            dataLoading = false
        }
    }

    fun navigate() {
        appNavigator.tryNavigateTo(
            route = Destination.Dashboard(),
            // popUpToRoute = Destination.Dashboard(),
            isSingleTop = true,
            inclusive = true
        )
    }

    fun confirmSubmit() {
        appNavigator.tryNavigateTo(
            route = Destination.Completed(),
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

    private fun getSupervisorPrefilledDataTypes(userId:String){
        addProductUseCases.getSupervisorPrefilledData(userId)
            .flowOn(Dispatchers.IO)
            .onEach {
                when(it.type){

                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let { it ->
                            dataLoading = it
                        }
                    }

                    EmitType.FLOOR_LIST ->{
                        it.value?.castListToRequiredTypes<FloorManagerType>()?.let { data->
                            _floorTypesList.update { data }
                        }
                    }
                    EmitType.PRODUCT_TYPES -> {
                        it.value?.castListToRequiredTypes<ProductType>()?.let { data ->
                            _productTypes.update { data }
                        }
                    }
                    EmitType.SUPERVISOR_NAME -> {
                        it.value?.castValueToRequiredTypes<String>()?.let { data ->
                            superVisorName.value=data
                        }
                    }
                    EmitType.MIXING_MAN_LIST -> {
                        it.value?.castListToRequiredTypes<MixingMantype>()?.let { data ->
                            _mixingManList.update { data }
                        }
                    }
                    EmitType.CUTTING_MAN_LIST -> {
                        it.value?.castListToRequiredTypes<CuttingManTypes>()?.let { data ->
                            _cuttingManList.update { data }
                        }
                    }
                    EmitType.OVEN_MAN_LIST -> {
                        it.value?.castListToRequiredTypes<OvenManType>()?.let { data ->
                            _ovenManList.update { data }
                        }
                    }
                    EmitType.PACKING_SUPERVISOR_LIST -> {
                        it.value?.castListToRequiredTypes<PackingSupervisorTypes>()?.let { data ->
                            _packingSupervisorList.update { data }
                        }
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
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

                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let { it ->
                            dataLoading = it
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
                when (it.type) {
                    EmitType.FLOOR_LIST -> {
                        it.value?.castListToRequiredTypes<FloorManagerType>()?.let { floors ->
                            _floorTypesList.update { floors }
                            Log.d("FloorTypeList", "getFloorTypes: ${floors.toList()}")
                        }
                    }

                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let { it ->
                            dataLoading = it
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
                when (it.type) {
                    EmitType.MIXING_MAN_LIST -> {
                        it.value?.castListToRequiredTypes<MixingMantype>()?.let { man ->
                            _mixingManList.update { man }
                            Log.d("FloorTypeList", "getFloorTypes: ${man.toList()}")
                        }
                    }

                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let { it ->
                            dataLoading = it
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
                when (it.type) {
                    EmitType.CUTTING_MAN_LIST -> {
                        it.value?.castListToRequiredTypes<CuttingManTypes>()?.let { cuttingMan ->
                            _cuttingManList.update { cuttingMan }
                            Log.d("FloorTypeList", "getFloorTypes: ${cuttingMan.toList()}")
                        }
                    }

                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let { it ->
                            dataLoading = it
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
                when (it.type) {
                    EmitType.OVEN_MAN_LIST -> {
                        it.value?.castListToRequiredTypes<OvenManType>()?.let { ovenMan ->
                            _ovenManList.update { ovenMan }
                            Log.d("FloorTypeList", "getFloorTypes: ${ovenMan.toList()}")
                        }
                    }

                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let { it ->
                            dataLoading = it
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
                when (it.type) {
                    EmitType.PACKING_SUPERVISOR_LIST -> {
                        it.value?.castListToRequiredTypes<PackingSupervisorTypes>()
                            ?.let { packingSupervisor ->
                                _packingSupervisorList.update { packingSupervisor }
                                Log.d(
                                    "PackagingSupervisorList",
                                    "PackagingSupervisorList: ${packingSupervisor.toList()}"
                                )
                            }
                    }

                    EmitType.Loading -> {
                        it.value?.castValueToRequiredTypes<Boolean>()?.let { it ->
                            dataLoading = it
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

    @OptIn(FlowPreview::class)
    fun getMixingLabourTypes(query: TextFieldValue) {
        Log.d("TESTING", "loadData: $query")
        mixingLabourSearchQuery = query
        loadMixingLabourListState.value = true
        addProductUseCases.getMixingLabourTypes(query.text)
            .flowOn(Dispatchers.IO)
            .debounce(500L)
            .onEach {
                when (it.type) {
                    EmitType.MIXING_LABOUR_LIST -> {
                        it.value?.castListToRequiredTypes<MixingLabourList>()?.let { mixingLabour ->
                            _mixingLabourList.update { mixingLabour }
                            // mixingLabourTextName.value = mixingLabourName
                            Log.d(
                                "MixingLabourList",
                                "Mixing Labour List: ${mixingLabour.toList()}"
                            )
                        }
//
                    }

                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                isDataLoaded.value = it
                            }
                        }
                    }

                    else -> {

                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onSelectMixingLabour(item: MixingLabourList) {
//        mixingLabourTextName.value=item.name
//        Log.d("mixingLabourTextName", "onSelectMixingLabour: ${mixingLabourTextName.value}")
        mixingLabourSearchQuery = mixingLabourSearchQuery.copy(
            text = item.name,
            selection = TextRange(item.name.length)
        )
        isSelectedMixingLabour.value = true
        loadMixingLabourListState.value = false
//        selectedMixingLabourList.add(item.name)
//        Log.d("selectedVendorList", "selectedVendorList: ${selectedMixingLabourList.toList()}")

    }

    @OptIn(FlowPreview::class)
    fun getCuttingLabourTypes(query: TextFieldValue) {
        Log.d("TESTING", "loadData: $query")
        cuttingLabourSearchQuery = query
        loadCuttingLabourListState.value = true
        addProductUseCases.getCuttingLabourTypes(query.text)
            .flowOn(Dispatchers.IO)
            .debounce(500L)
            .onEach {
                when (it.type) {
                    EmitType.CUTTING_LABOUR_LIST -> {
                        it.value?.castListToRequiredTypes<CuttingLabourList>()
                            ?.let { cuttingLabour ->
                                _cuttingLabourList.update { cuttingLabour }
                                // mixingLabourTextName.value = mixingLabourName
                                Log.d(
                                    "MixingLabourList",
                                    "Mixing Labour List: ${cuttingLabour.toList()}"
                                )
                            }
//
                    }

                    EmitType.Loading -> {
                        it.value?.apply {
                            castValueToRequiredTypes<Boolean>()?.let {
                                isDataLoaded.value = it
                            }
                        }
                    }

                    else -> {

                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onSelectCuttingLabour(item: CuttingLabourList) {
//        mixingLabourTextName.value=item.name
//        Log.d("mixingLabourTextName", "onSelectMixingLabour: ${mixingLabourTextName.value}")
        cuttingLabourSearchQuery = cuttingLabourSearchQuery.copy(
            text = item.name,
            selection = TextRange(item.name.length)
        )
        isSelectedCuttingLabour.value = true
        loadCuttingLabourListState.value = false
//        selectedMixingLabourList.add(item.name)
//        Log.d("selectedVendorList", "selectedVendorList: ${selectedMixingLabourList.toList()}")

    }

    fun onImageCaptured(bitmap: Bitmap?) {
        showDialog.value = false
        if (bitmap != null) {
            capturedImagesList.add(bitmap)
            _capturedImages.update {
                val tmp = it.toMutableList()
                tmp.add(bitmap)
                tmp
            }
        }
    }

    fun onGalleryImageCaptured(uri: Uri?){
        showDialog.value = false
        if(uri != null){
            if (Build.VERSION.SDK_INT < 28)
            {
                MediaStore.Images.Media.getBitmap(com.jaya.app.jayamixing.application.JayaMixingApp.app?.applicationContext?.contentResolver,uri)
            } else {
                val source = com.jaya.app.jayamixing.application.JayaMixingApp.app?.applicationContext?.let {
                    ImageDecoder.createSource(it.contentResolver,uri)
                }
                if (source != null) {
                    val bitmapImage = ImageDecoder.decodeBitmap(source)
                    capturedImagesList.add(bitmapImage)
                    _capturedImages.update {
                        val tmp = it.toMutableList()
                        tmp.add(bitmapImage)
                        tmp
                    }
                }
            }
        }
    }
}