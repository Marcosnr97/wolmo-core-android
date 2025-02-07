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
package ar.com.wolox.wolmo.core.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import ar.com.wolox.wolmo.core.di.scopes.ApplicationScope
import javax.inject.Inject

/**
 * An utility class to work with Android's [Toast] messages
 */
class ToastFactory @Inject constructor(private val context: Context) {

    /** Displays a message from a [resourceId] inside a [Toast], briefly. */
    fun show(@StringRes resourceId: Int) = Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show()

    /** Displays a given [message] inside a [Toast], briefly. */
    fun show(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    /** Displays a message from a [resourceId] inside a [Toast], during a longer interval. */
    fun showLong(@StringRes resourceId: Int) = Toast.makeText(context, resourceId, Toast.LENGTH_LONG).show()

    /** Displays a given [message] inside a [Toast], during a longer interval.*/
    fun showLong(message: String) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
