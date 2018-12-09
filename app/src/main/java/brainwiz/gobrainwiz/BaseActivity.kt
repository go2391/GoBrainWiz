package brainwiz.gobrainwiz

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import brainwiz.gobrainwiz.utils.KeyBoardUtils
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import brainwiz.gobrainwiz.utils.DDAlerts
import brainwiz.gobrainwiz.utils.SharedPrefUtils
import brainwiz.gobrainwiz.utils.SharedPrefUtils.*


/**
 */
open class BaseActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null


    /**
     * @param context
     * *
     * @param activityClass  - target fragment
     * *
     * @param bundle
     * *
     * @param finish         - is this current activity can be finish
     * *
     * @param animationStyle - animation style : left to right...
     */
    fun launchActivity(context: Activity, activityClass: Class<*>, bundle: Bundle?, finish: Boolean, animationStyle: Int) {
        KeyBoardUtils.hideKeyboard(context)
        val intent = Intent(this as Context, activityClass)

        intent.putExtras(bundle ?: Bundle())
        startActivity(intent)
        if (finish) {
            context.finish()
        }
    }

    fun onUserError(context: Activity, message: String) {
        context.finish()
        clearUserData(this@BaseActivity)
        finish()
        DDAlerts.showAlert(this@BaseActivity, message, getString(R.string.ok))
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    public fun clearUserData(context: Context) {
        SharedPrefUtils.putData(context, IS_LOGIN, false)
        SharedPrefUtils.putData(context, USER_EMAIL, "")
        SharedPrefUtils.putData(context, USER_ID, "")
        SharedPrefUtils.putData(context, USER_MOBILE, "")
        SharedPrefUtils.putData(context, USER_NAME, "")
        SharedPrefUtils.putData(context, USER_TOKEN, "")
        SharedPrefUtils.putData(context, USER_COLLEGE, "")
    }


    fun fragmentTransaction(newFragment: Fragment, container: Int, addToStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container, newFragment)
        if (addToStack) {
            transaction.addToBackStack(newFragment.javaClass.simpleName)
        }

        transaction.commit()
    }

    fun fragmentTransaction(newFragment: Fragment, container: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container, newFragment)
        transaction.commit()
    }


    fun fragmentTransaction(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, newFragment)
        transaction.addToBackStack(newFragment.javaClass.simpleName);
        transaction.commit()
    }

    fun fragmentTransactionStateLoss(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, newFragment)
        transaction.addToBackStack(newFragment.javaClass.simpleName);
        transaction.commitAllowingStateLoss()
    }

    /**
     * @param editText
     * *
     * @return check is fiels is empty  if empty request focus to that field
     */
    internal fun isEmptyField(editText: EditText): Boolean {
        val empty = TextUtils.isEmpty(getFieldText(editText))
        if (empty)
            editText.requestFocus()
        return empty
    }

    override fun onResume() {
        super.onResume()
    }

    internal fun getFieldText(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    /*  */
    /**
     * show progress dialog for api calls
     *//*
    protected fun showProgress() {
        dialog = ProgressDialog(this as Context)
        dialog!!.setMessage("Please wait...")
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.show()

    }

    */
    /**
     * dismiss progress dialog after api calls
     *//*
    protected fun dismissProgress() {
        if (dialog != null) {
            dialog!!.cancel()
            dialog!!.dismiss()
        }
    }*/


    /**
     * animation styles
     */
    interface IAnimations {
        companion object {
            val LeftToRight = 0
            val RightToLeft = 1
            val TopToBottom = 2
            val BottomToTop = 3
        }
    }

    /**
     * @param view use this method to dissmiss keyboard on taping outside and call this method where you have the edit fields on onResume method.
     */
    protected fun setupUI(view: View) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {

            view.setOnTouchListener { v, event ->
                KeyBoardUtils.hideKeyboard(this@BaseActivity as Activity)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {

            for (i in 0..view.childCount - 1) {

                val innerView = view.getChildAt(i)

                setupUI(innerView)
            }
        }
    }


    fun configureProgress(dialog: Dialog) {


        val window = dialog.window

        if (window != null) {

            window.setBackgroundDrawableResource(android.R.color.transparent)
            val size = Point()
            val display = window.windowManager.defaultDisplay
            display.getSize(size)
            window.setLayout((size.x * 0.80).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
        }

    }

}
