package com.example.malal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.malal.data.repository.ShopRepository
import com.example.malal.model.OrderModel
import com.example.malal.model.PaymentModel
import com.example.malal.model.ProductModel
import com.example.malal.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val shopRepository:ShopRepository) : ViewModel()
{

    private val _orderProductsLiveData = MutableLiveData<Resource<OrderModel>>()
    val orderProductsLiveData:LiveData<Resource<OrderModel>> = _orderProductsLiveData


    fun pushUserOrder(cartProductsList: Array<ProductModel>, userLocation: String, totalCost: Float)
    {
        _orderProductsLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _orderProductsLiveData.postValue(shopRepository.uploadProductsToOrders(cartProductsList, userLocation, totalCost))
        }
    }
}