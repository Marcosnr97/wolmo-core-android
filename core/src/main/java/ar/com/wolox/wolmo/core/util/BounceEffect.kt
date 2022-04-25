package ar.com.wolox.wolmo.core.util

import android.graphics.Canvas
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.DynamicAnimation.ViewProperty
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringAnimation.TRANSLATION_X
import androidx.dynamicanimation.animation.SpringAnimation.TRANSLATION_Y
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import ar.com.wolox.wolmo.core.di.scopes.ApplicationScope

@ApplicationScope
class BounceEffect(
    var flingTranslation: Float = 0.5f,
    var overscrollTranslation: Float = 0.1f
) : EdgeEffectFactory() {

    override fun createEdgeEffect(recyclerView: RecyclerView, directionEffect: Int): EdgeEffect {

        val orientation = (recyclerView.layoutManager as LinearLayoutManager).orientation

        return object : EdgeEffect(recyclerView.context) {
            var translationAnim: SpringAnimation? = null

            override fun onPull(deltaDistance: Float) {
                super.onPull(deltaDistance)
                handlePull(deltaDistance)
            }

            override fun onPull(deltaDistance: Float, displacement: Float) {
                super.onPull(deltaDistance, displacement)
                handlePull(deltaDistance)
            }

            private fun handlePull(deltaDistance: Float) {

                when (orientation) {
                    VERTICAL -> {
                        val translationYDelta =
                            getSign() * recyclerView.width * deltaDistance * overscrollTranslation
                        recyclerView.translationY += translationYDelta
                    }
                    HORIZONTAL -> {
                        val translationXDelta =
                            getSign() * recyclerView.height * deltaDistance * overscrollTranslation
                        recyclerView.translationX += translationXDelta
                    }
                }

                translationAnim?.cancel()
            }

            override fun onRelease() {
                super.onRelease()

                when (orientation) {
                    VERTICAL -> {
                        if (recyclerView.translationY != ZERO_F) {
                            translationAnim = createAnim()?.also { it.start() }
                        }
                    }
                    HORIZONTAL -> {
                        if (recyclerView.translationX != ZERO_F) {
                            translationAnim = createAnim()?.also { it.start() }
                        }
                    }
                }
            }

            override fun onAbsorb(velocity: Int) {
                super.onAbsorb(velocity)

                val translationVelocity = getSign() * velocity * flingTranslation
                translationAnim?.cancel()
                translationAnim =
                    createAnim().setStartVelocity(translationVelocity)?.also { it.start() }
            }

            override fun draw(canvas: Canvas?): Boolean {
                return false
            }

            override fun isFinished(): Boolean {
                return translationAnim?.isRunning?.not() ?: true
            }

            private fun createAnim() =
                SpringAnimation(recyclerView, getSpringAnimation())
                    .setSpring(
                        SpringForce()
                            .setFinalPosition(ZERO_F)
                            .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                            .setStiffness(SpringForce.STIFFNESS_LOW)
                    )

            private fun getSign(): Int {
                return when (orientation) {
                    VERTICAL -> if (directionEffect == DIRECTION_BOTTOM) NEGATIVE_SIGN else POSITIVE_SIGN
                    HORIZONTAL -> if (directionEffect == DIRECTION_LEFT) POSITIVE_SIGN else NEGATIVE_SIGN
                    else -> POSITIVE_SIGN
                }
            }

            private fun getSpringAnimation(): ViewProperty {
                return when (orientation) {
                    VERTICAL -> TRANSLATION_Y
                    HORIZONTAL -> TRANSLATION_X
                    else -> TRANSLATION_Y
                }
            }
        }
    }

    companion object {
        var POSITIVE_SIGN = 1
        var NEGATIVE_SIGN = -1
        var ZERO_F = 0F
    }
}