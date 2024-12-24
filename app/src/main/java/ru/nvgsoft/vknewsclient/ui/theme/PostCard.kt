package ru.nvgsoft.vknewsclient.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.nvgsoft.vknewsclient.R

@Composable
fun postCard() {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        Column(modifier = Modifier
            .padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.post_comunity_thumbnail),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "/dev/null",
                        color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "14:00",
                        color = MaterialTheme.colorScheme.onSecondary)
                }
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = ""
                )
            }
            Text(text = "кабаныч когда узнал, что если сотрудникам не платить они начнут умирать от голода")
//            Image(
//                modifier = Modifier.fillMaxWidth(),
//                alignment = Alignment.Center,
//                painter = painterResource(id = R.drawable.post_content_image),
//                contentDescription = ""
//            )
//            Row(modifier = Modifier
//                .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically)
//            {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(text = "120")
//                    Image(
//                        modifier = Modifier
//                            .size(15.dp),
//                        painter = painterResource(id = R.drawable.ic_views_count),
//                        contentDescription = ""
//                    )
//                }
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(text = "206")
//                    Image(
//                        modifier = Modifier
//                            .size(15.dp),
//                        painter = painterResource(id = R.drawable.ic_share),
//                        contentDescription = ""
//                    )
//
//                    Text(text = "11")
//                    Image(
//                        modifier = Modifier
//                            .size(15.dp),
//                        painter = painterResource(id = R.drawable.ic_comment),
//                        contentDescription = ""
//                    )
//                    Text(text = "491")
//                    Image(
//                        modifier = Modifier
//                            .size(15.dp),
//                        painter = painterResource(id = R.drawable.ic_like),
//                        contentDescription = ""
//                    )
//                }
//
//            }
        }

    }
}

@Preview
@Composable
fun postCardDarkTheme(){
    VkNewsClientTheme(darkTheme = true, dynamicColor = false) {
        postCard()
    }
}

@Preview
@Composable
fun postCardLightTheme(){
    VkNewsClientTheme(darkTheme = false, dynamicColor = false) {
        postCard()
    }
}