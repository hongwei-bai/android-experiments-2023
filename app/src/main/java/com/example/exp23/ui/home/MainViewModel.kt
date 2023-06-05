package com.example.exp23.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exp23.TestSingleton
import com.example.exp23.data.Exp23AppRepository
import com.example.exp23.data.model.Exp23AppModel
import com.example.exp23.payment.PaymentsUtilMy
import com.example.exp23.payment.PaymentsUtilMy.LOAD_PAYMENT_DATA_REQUEST_CODE
import com.example.exp23.ui.MainActivity
import com.example.exp23.ui.shared.UiState
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val exp23AppRepository: Exp23AppRepository
) : ViewModel() {
    private lateinit var paymentsClient: PaymentsClient

    private val _isGooglePayReady = MutableStateFlow(false)
    val isGooglePayReady: StateFlow<Boolean> = _isGooglePayReady.asStateFlow()

    init {
        onCreateForGooglePay(TestSingleton.activity)
        possiblyShowGooglePayButton()
    }

    val fuelAppModelFlow = exp23AppRepository.flow.map { model ->
        when (model) {
            is Exp23AppModel -> HomeUiState(
                state = UiState.Success,
                headerName = model.header.name,
                headerDate = model.header.date,
                headerDescription = model.header.description,
                cards = model.cards.map {
                    Card(
                        name = it.name,
                        isEnabled = it.isActivated,
                        isHighlight = it.isHighlight,
                        expire = it.expire
                    )
                }
            )
            else -> HomeUiState(
                state = UiState.Error
            )
        }
    }

    fun loadMainData() {
        viewModelScope.launch(Dispatchers.IO) {
            exp23AppRepository.fetchData()
        }
    }

    fun updateMainData() {
        viewModelScope.launch(Dispatchers.IO) {
            exp23AppRepository.fetchData(false)
        }
    }

    private fun onCreateForGooglePay(activity: MainActivity) {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
//        .setEnvironment(WalletConstants.ENVIRONMENT_PRODUCTION)
            .build()

        paymentsClient = Wallet.getPaymentsClient(
            activity,
            walletOptions
        )
    }

    fun requestPaymentVerify() {
        PaymentsUtilMy.isReadyToPayRequest()
    }

    fun requestPayment() {

        // Disables the button to prevent multiple clicks.
//        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
//        val garmentPrice = selectedGarment.getDouble("price")
//        val priceCents = Math.round(garmentPrice * PaymentsUtil.CENTS.toLong()) + SHIPPING_COST_CENTS

        val priceCents = "$1.00"

        val paymentDataRequestJson = PaymentsUtilMy.getPaymentDataRequest(priceCents)
        if (paymentDataRequestJson == null) {
            Log.e("RequestPayment", "Can't fetch payment data request")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        if (request != null) {
            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                TestSingleton.activity,
                LOAD_PAYMENT_DATA_REQUEST_CODE
            )
        }
    }

    private fun possiblyShowGooglePayButton() {

        val isReadyToPayJson = PaymentsUtilMy.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                // Process error
                Log.w("isReadyToPay failed", exception)
            }
        }
    }

    private fun setGooglePayAvailable(isReady: Boolean) {
        _isGooglePayReady.value = isReady
    }
}