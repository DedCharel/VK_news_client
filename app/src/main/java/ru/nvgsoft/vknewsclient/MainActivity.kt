package ru.nvgsoft.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            VkNewsClientTheme {
//                // A surface container using the 'background' color from the theme
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.background)
//                        .padding(8.dp),
//                ) {
//                    PostCard()
//                }
//            }
            Test()
        }
    }
}

@Composable
private fun Test(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Example3()
    }
}

@Composable
private fun Example1(){
    OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(4.dp)) {
        Text(text = "Hello world")
    }   
}

@Composable
private fun Example2(){
    TextField(
        value = "Value",
        onValueChange = {},
        label = { Text(text = "Label")},
        )
}

@Composable
private fun Example3(){
    val openAlertDialog = remember {
        mutableStateOf(true)
    }

    when{
        openAlertDialog.value ->{
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                confirmButton =  {
                    Text(text = "Yes")
                },
                dismissButton =  {
                    Text(text = "No")
                },
                title = {
                    Text(text = "Are you sure?")
                        },
                text = {
                    Text(text = "Do you want to delete this file?")
                       },
                )
        }
    }


    
}


