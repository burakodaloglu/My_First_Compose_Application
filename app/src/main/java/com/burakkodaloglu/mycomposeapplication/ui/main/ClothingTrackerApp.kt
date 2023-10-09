package com.burakkodaloglu.mycomposeapplication.ui.main

import android.widget.ImageView
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.burakkodaloglu.mycomposeapplication.R
import com.burakkodaloglu.mycomposeapplication.data.Clothing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingTrackerApp(modifier: Modifier = Modifier) {
    //remember burada bulunan verileri ve değişiklikleride aklında tutuyor
    val clothingList = remember {
        mutableStateListOf(
            Clothing(1, "Tişört", "Rahat ve pamuklu tişört."),
            Clothing(2, "Kot Pantolon", "Klasik kesim, rahat bir kot pantolon."),
            Clothing(3, "Elbise", "Şık ve zarif gece elbisesi."),
            Clothing(4, "Ceket", "İnce dokulu ceket, her mevsim kullanılabilir."),
            Clothing(5, "Gömlek", "Beyaz güzel gömlek."),
            Clothing(6, "Çorap", "Kalın, sıcak tutan çorap."),
            Clothing(7, "Mont", "Deri, Sıcak tutan kapüşonlu mont."),
            Clothing(8, "Kazak", "Yün kazak"),
            Clothing(9, "Saat", "Güzel gözüken saat.")
        )
    }

    Scaffold { contentPadding ->
        Column(modifier = modifier.padding(contentPadding)) {
            ClothingTrackList(clothing = clothingList, onEdit = {}, onDelete = {
                clothingList.remove(it)
            })
        }
    }
}

@Composable
fun ClothingTrackList(
    clothing: List<Clothing>,
    onEdit: (Clothing) -> Unit,
    onDelete: (Clothing) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp)) {
        items(items = clothing) { clothing ->
            ClothingItem(
                clothing = clothing,
                onEdit = onEdit,
                onDelete = onDelete,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ClothingItem(
    clothing: Clothing,
    onEdit: (Clothing) -> Unit,
    onDelete: (Clothing) -> Unit,
    modifier: Modifier = Modifier
) {
    //DEğişen durumun son halini bile aklında tutuyor ve değişmiyor.rememberSaveable
    var isExpended by rememberSaveable { mutableStateOf(false) }

    val expandAnim by animateDpAsState(
        if (isExpended) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Column {
        Row(modifier = modifier
            .fillMaxWidth()
            .clickable {
                onEdit(clothing)
            }
            .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_clothing),
                contentDescription = stringResource(id = R.string.clothing_icon)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = clothing.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .clickable {
                        isExpended = !isExpended
                    }
                    .background(color = Color.Black, shape = CircleShape)
                    .padding(8.dp),
                color = Color.White,
                text = stringResource(if (isExpended) R.string.hide_description else R.string.show_description)
            )

            Spacer(modifier = Modifier.width(8.dp))

            AndroidView(factory = { context ->
                //Factory AndroidView ın çizildiği yer
                ImageView(context).apply {
                    setImageResource(R.drawable.ic_delete)
                    contentDescription = context.getString(R.string.delete_icon)
                }
            },
                update = {
                    //AndroidView a tıklanınca yapacakları,daha doğrusu herhangi bir değişiklik olursa dinleyeceği yer
                    it.setOnClickListener {
                        onDelete(clothing)
                    }
                }
            )
        }
        if (isExpended) Text(
            text = clothing.description,
            modifier = Modifier
                .fillMaxWidth()
                .size(expandAnim)
                .padding(horizontal = 12.dp)
        )
    }
}