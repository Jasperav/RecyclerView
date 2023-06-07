package com.beezleapp.recyclvertest

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beezleapp.recyclvertest.ui.theme.RecyclverTestTheme
import com.google.android.material.shape.MaterialShapeDrawable

fun createChat(context: Context): ConstraintLayout {
    val recyclerView = createRecyclerView(context)

    return recyclerView
}

fun createRecyclerView(context: Context): ConstraintLayout {
    val constraintLayout = createConstraintLayout(context)
    val parent = applyDefaults(constraintLayout.constraintLayout, View(context), MATCH_PARENT, MATCH_PARENT)

    parent.setBackgroundColor(context.getColor(R.color.yellow))

    constraintLayout.fill(parent, constraintLayout.constraintLayout)

    val recyclerView = applyDefaults(constraintLayout.constraintLayout, RecyclerView(context), MATCH_PARENT, MATCH_PARENT)

    recyclerView.setBackgroundColor(context.getColor(R.color.purple_500))

    constraintLayout.fill(recyclerView, parent)

    val shapeDrawable = MaterialShapeDrawable()

    shapeDrawable.fillColor = ColorStateList.valueOf(context.getColor(R.color.teal_200))
    shapeDrawable.setStroke(5f, context.getColor(R.color.red))

    recyclerView.background = shapeDrawable
    recyclerView.adapter = ChatAdapter()
    recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    return constraintLayout.constraintLayout
}

class ChatCell : View {
    private val textView: TextView

    constructor(parent: ViewGroup) : super(parent.context) {
        println("Created")

        val constraintLayout = createConstraintLayout(context)

        textView = applyDefaults(constraintLayout.constraintLayout, TextView(context), MATCH_PARENT, MATCH_PARENT)

        constraintLayout.constraintSet.constrainHeight(textView.id, 100)
        constraintLayout.constraintSet.applyTo(constraintLayout.constraintLayout)

        constraintLayout.fill(textView, constraintLayout.constraintLayout)

        textView.text = "test"
        textView.setBackgroundColor(context.getColor(R.color.red))
        setBackgroundColor(context.getColor(R.color.red))
    }

    fun x() {
    }
}

class ViewHolderTemp : RecyclerView.ViewHolder {
    val chatCell: ChatCell

    constructor(chatCell: ChatCell) : super(chatCell) {
        this.chatCell = chatCell
    }
}

class ChatAdapter : RecyclerView.Adapter<ViewHolderTemp>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTemp {
        val chatCell = ChatCell(parent)

        return ViewHolderTemp(chatCell)
    }

    override fun getItemCount() = 2

    override fun onBindViewHolder(holder: ViewHolderTemp, position: Int) {
        holder.chatCell.x()
    }
}

class Layout(val constraintLayout: ConstraintLayout, val constraintSet: ConstraintSet)

fun createConstraintLayout(context: Context): Layout {
    val c = ConstraintLayout(context).apply {
        id = View.generateViewId()
        layoutParams = ViewGroup.LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT,
        )
    }

    val set = ConstraintSet()

    set.clone(c)

    return Layout(c, set)
}


fun <T: View> applyDefaults(constraintLayout: ConstraintLayout, view: T, width: Int, height: Int): T {
    view.id = View.generateViewId()
    view.layoutParams = ConstraintLayout.LayoutParams(
        width,
        height,
    )

    constraintLayout.addView(view)

    return view
}

fun Layout.fill(view: View, inView: View) {
    val constraintSet = ConstraintSet()

    constraintSet.clone(constraintLayout)

    constraintSet.connect(view.id, ConstraintSet.LEFT, inView.id, ConstraintSet.LEFT);
    constraintSet.connect(view.id, ConstraintSet.TOP, inView.id, ConstraintSet.TOP);
    constraintSet.connect(view.id, ConstraintSet.RIGHT, inView.id, ConstraintSet.RIGHT);
    constraintSet.connect(view.id, ConstraintSet.BOTTOM, inView.id, ConstraintSet.BOTTOM);

    constraintSet.applyTo(constraintLayout)
}

@Composable
fun GreetingX() {
    AndroidView(factory = { context ->
        createChat(context)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewX() {
    RecyclverTestTheme {
        GreetingX()
    }
}