package kr.ryan.retrofitmodule

import kotlinx.coroutines.delay
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * MyFoodCalorie
 * Class: NetWorkResponseCall
 * Created by pyg10.
 * Created On 2021-09-23.
 * Description:
 */

class NetWorkResponseCall<T> constructor(
    private val callDelegate: Call<T>
): Call<NetWorkResult<T>>{
    private var tryCount = 0

    override fun clone(): Call<NetWorkResult<T>> = NetWorkResponseCall(callDelegate.clone())

    override fun execute(): Response<NetWorkResult<T>> = throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun enqueue(callback: Callback<NetWorkResult<T>>) = callDelegate.enqueue(object : Callback<T>{
        override fun onResponse(
            call: Call<T>,
            response: Response<T>
        ) {
            response.body()?.let {
                when(response.code()){
                    in 200..299 -> callback.onResponse(this@NetWorkResponseCall, Response.success(NetWorkResult.Success(it, response.code())))
                    in 400..409 -> {
                        if (tryCount < 2)
                            retry(callback)
                        else{
                            callback.onResponse(this@NetWorkResponseCall, Response.success(NetWorkResult.ApiError(response.message(), response.code())))
                            call.cancel()
                        }

                    }
                    else -> {
                        if (tryCount < 2)
                            retry(callback)
                        else{
                            callback.onResponse(this@NetWorkResponseCall, Response.success(NetWorkResult.ApiError(response.message(), response.code())))
                            call.cancel()
                        }
                    }
                }
            } ?: callback.onResponse(this@NetWorkResponseCall, Response.success(NetWorkResult.NullResult()))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            if (tryCount < 2)
                retry(callback)
            else{
                callback.onResponse(this@NetWorkResponseCall, Response.success(NetWorkResult.NetWorkError(t)))
                call.cancel()
            }
        }
    })

    private fun retry(callback: Callback<NetWorkResult<T>>){
        tryCount++
        clone().enqueue(callback)
    }

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()
}