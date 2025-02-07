/*
 * Copyright (c) Wolox S.A
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ar.com.wolox.wolmo.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import ar.com.wolox.wolmo.core.permission.PermissionManager
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Base implementation for [IWolmoFragment]. This is in charge of inflating the view returned
 * by [layout]. The presenter is created on [onCreate] if [handleArguments] returns true.
 * This class defines default implementations for most of the methods on [IWolmoFragment].
 */
abstract class WolmoFragment<V : ViewDataBinding, P : BasePresenter<*>> : Fragment(), IWolmoFragment {

    @Inject
    lateinit var fragmentHandler: WolmoFragmentHandler<V, P>

    @Inject
    lateinit var permissionManager: PermissionManager

    /**
     * Tries to return a non null instance of the presenter [P] for this fragment.
     * If the presenter is null this will throw a NullPointerException.
     */
    val presenter: P
        get() = fragmentHandler.presenter

    val binding: V
        get() = fragmentHandler.binding

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentHandler.onCreate(this)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return fragmentHandler.onCreateView(inflater, container)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHandler.onViewCreated(view)
    }

    @CallSuper
    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(visible)
        fragmentHandler.setMenuVisibility(visible)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        fragmentHandler.onResume()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        fragmentHandler.onPause()
    }

    @CallSuper
    override fun onDestroyView() {
        fragmentHandler.onDestroyView()
        super.onDestroyView()
    }

    /** Delegates permission handling to Wolmo's [PermissionManager]. */
    @CallSuper
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Reads [arguments] sent as a Bundle extras and returning true if this fragment contains
     * the required values, false or null otherwise. Returning false or null will end the execution.
     *
     * Default implementation returns true.
     */
    override fun handleArguments(arguments: Bundle?): Boolean? = true

    /**
     * Associates variables to views inflated from the XML resource
     * provided in [IWolmoFragment.layout]
     * Override if needed.
     */
    override fun setUi(view: View?) {}

    /**
     * Sets the listeners for the views of the fragment.
     * Override if needed.
     */
    override fun setListeners() {}

    /**
     * Populates the view elements of the fragment.
     * Override if needed.
     */
    override fun populate() {}

    /**
     * Invoked when the fragment becomes visible to the user.
     * Override if needed.
     */
    override fun onVisible() {}

    /**
     * Invoked when the fragment becomes hidden to the user.
     * Override if needed.
     */
    override fun onHide() {}

    /** @see IWolmoFragment.onBackPressed */
    override fun onBackPressed(): Boolean {
        return false
    }

    /** Returns [key] argument from intent extras as [T]. */
    protected inline fun <reified T> requireArgument(key: String) = arguments?.get(key) as T
}