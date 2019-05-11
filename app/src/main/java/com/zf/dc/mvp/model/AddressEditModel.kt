package com.zf.dc.mvp.model

import com.zf.dc.base.BaseBean
import com.zf.dc.mvp.bean.AddAddressBean
import com.zf.dc.mvp.bean.EditAddressBean
import com.zf.dc.mvp.bean.RegionBean
import com.zf.dc.net.RetrofitManager
import com.zf.dc.scheduler.SchedulerUtils
import io.reactivex.Observable

class AddressEditModel{

    fun requestAddressEdit(consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String): Observable<BaseBean<AddAddressBean>> {
        return RetrofitManager.service.setAddressList(consignee,mobile,province,city,district,address,label,is_default)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestDelAddressModel(id:String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delAddress(id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestRegion(id:String): Observable<BaseBean<List<RegionBean>>> {

        return RetrofitManager.service.getRegion(id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestEditAddress(id:String,consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String):Observable<BaseBean<EditAddressBean>>{
        return RetrofitManager.service.editAddress(id,consignee,mobile,province,city,district,address,label,is_default)
            .compose(SchedulerUtils.ioToMain())
    }

}