package com.dhiman.iptv.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dhiman.iptv.R
import net.cachapa.expandablelayout.ExpandableLayout
import java.text.SimpleDateFormat
import java.util.*

fun Activity.hideStatusBar() {
    if (Build.VERSION.SDK_INT < 16) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

//    requestWindowFeature(Window.FEATURE_NO_TITLE);
//    window.setFlags(
//        WindowManager.LayoutParams.FLAG_FULLSCREEN,
//        WindowManager.LayoutParams.FLAG_FULLSCREEN);

}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Date.toSpecificString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}


fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun Activity.manageDialog(dialog: AlertDialog) {
    val widthPecent = 0.60f
    getScreenHeightWidth { _, width ->
        dialog.window?.setLayout((width * widthPecent).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        //  dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    dialog.window?.setGravity(Gravity.CENTER)
    //dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setBackgroundDrawableResource(R.color.black)
}

fun Activity.getScreenHeightWidth(size: (Int, Int) -> Unit) {
    val displayMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val display = display
        display?.getRealMetrics(displayMetrics)
    } else {
        val display = windowManager.defaultDisplay
        display.getMetrics(displayMetrics)
    }
    size(displayMetrics.heightPixels, displayMetrics.widthPixels)
}


/*interface GetViewWidthHeightCallBack {
    fun viewDimension(width: Int, height: Int)
}*/

fun View.getViewWidthHeight(viewDimension: (Int, Int) -> Unit) {
    val vto = viewTreeObserver
    vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            val width: Int = measuredWidth
            val height: Int = measuredHeight
            viewDimension(width, height)
        }
    })
}

fun Activity.dpToPixel(size: Float): Int {
    val scale = resources.displayMetrics.density
    return (size * scale).toInt()
}


fun ExpandableLayout.expandableLayoutCallBack(
    timeDuration: Int = 500,
    imageView: ImageView?,
    downArrow: Int,
    upArrow: Int
) {
    duration = timeDuration
    if (isExpanded) {
        collapse()
        rotateIconDown(imageView, downArrow)
    } else {
        expand()
        rotateIconUp(imageView, upArrow)

    }
}

fun rotateIconUp(imageView: ImageView?, arrowImage: Int) {
    imageView?.setImageResource(arrowImage)
    // imageView?.setColorFilter(ContextCompat.getColor(imageView.context, R.color.neon_carrot), android.graphics.PorterDuff.Mode.SRC_IN);
    val animSet = AnimationSet(true)
    animSet.interpolator = DecelerateInterpolator()
    animSet.fillAfter = true
    animSet.isFillEnabled = true
    val animRotate = RotateAnimation(
        0.0f, -180.0f,
        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
        RotateAnimation.RELATIVE_TO_SELF, 0.5f
    )
    animRotate.duration = 500
    animRotate.fillAfter = true
    animSet.addAnimation(animRotate)
    imageView?.startAnimation(animSet)
}

fun rotateIconDown(imageView: ImageView?, arrowImage: Int) {
    imageView?.setImageResource(arrowImage)
    //  imageView?.setColorFilter(ContextCompat.getColor(imageView.context, R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
    val animSet = AnimationSet(true)
    animSet.interpolator = DecelerateInterpolator()
    animSet.fillAfter = true
    animSet.isFillEnabled = true
    val animRotate = RotateAnimation(
        -180.0f, 0.0f,
        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
        RotateAnimation.RELATIVE_TO_SELF, 0.5f
    )
    animRotate.duration = 500
    animRotate.fillAfter = true
    animSet.addAnimation(animRotate)
    imageView?.startAnimation(animSet)
}

fun String.logs() {
    Log.d("App_Log", this)
}

fun String.longToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun String.shortToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.decodeBase64(): String {
    return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
}

@SuppressLint("SimpleDateFormat")
fun String.getConvertedDateTime(alreadyPattern: String, desirePattern: String): String {
    return try {
        val parser = SimpleDateFormat(alreadyPattern)
        val formatter = SimpleDateFormat(desirePattern)
        val formattedDate = parser.parse(this)?.let { formatter.format(it) }
        formattedDate.toString()
    } catch (e: Exception) {
        ""
    }
}

@Suppress("DEPRECATION")
fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities: NetworkCapabilities
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)!!
    } else {
        return connectivityManager.activeNetworkInfo?.isConnected!!
    }
    when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}