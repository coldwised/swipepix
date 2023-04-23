package com.coldwised.swipepix.presentation.gallery.full_screen.components

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Parcelable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.Constants
import com.coldwised.swipepix.R
import com.coldwised.swipepix.util.Extension.trySystemAction


private fun getIntentChooser(context: Context, intent: Intent, chooserTitle: CharSequence? = null): Intent? {
    var resolveInfos: MutableList<ResolveInfo> = mutableListOf()
    trySystemAction {
        resolveInfos = context.packageManager.queryIntentActivities(intent, 0)
    }
    val excludedComponentNames = HashSet<ComponentName>()
    val targetIntents: MutableList<Intent> = ArrayList()
    resolveInfos.forEach {
        val activityInfo = it.activityInfo
        val packageName = activityInfo.packageName
        val componentName = ComponentName(packageName, activityInfo.name)
        if(packageName == context.packageName) {
            excludedComponentNames.add(componentName)
        } else {
            val targetIntent = Intent(intent)
            targetIntent.setPackage(packageName)
            targetIntent.component = componentName
            // wrap with LabeledIntent to show correct name and icon
            val labeledIntent = LabeledIntent(
                targetIntent,
                packageName,
                it.labelRes,
                it.icon
            )
            // add filtered intent to a list
            targetIntents.add(labeledIntent)
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Intent.createChooser(intent, chooserTitle)
            .putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS, excludedComponentNames.toTypedArray())
    } else {
        // deal with M list seperate problem
        val chooserIntent: Intent = Intent.createChooser(Intent(), chooserTitle)
            ?: return null
        // add initial intents
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            targetIntents.toTypedArray<Parcelable>()
        )
        return chooserIntent
    }
}

@Composable
fun OfferFloatingButton(isVisible: Boolean, price: Float) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(Constants.TOP_BAR_VISIBILITY_ENTRY_ANIMATION_TIME)
        ),
        exit = fadeOut(
            animationSpec = tween(Constants.TOP_BAR_VISIBILITY_EXIT_ANIMATION_TIME)
        )
    ) {
        val verticalScroll = rememberScrollState()
        var fabExtended by remember { mutableStateOf(true) }

        LaunchedEffect(verticalScroll) {
            var prev = 0
            snapshotFlow { verticalScroll.value }
                .collect {
                    fabExtended = it <= prev
                    prev = it
                }
        }
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                    ,
            visible = fabExtended
        ) {
            ExtendedFloatingActionButton(
                content = {
                    Text(
                        text = stringResource(R.string.offer_cart_button_text, price)
                    )
                },
                onClick = { /*TODO*/ }
            )
        }
    }
}