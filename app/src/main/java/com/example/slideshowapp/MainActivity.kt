package com.example.slideshowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slideshowapp.ui.theme.SlideShowAppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlideShowAppTheme {
                    SlideShowApp()
                }
            }
        }
    }

@Composable
fun SlideShowApp(){
    val images = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5
    )

    val captions = listOf(
        stringResource(R.string.caption1),
        stringResource(R.string.cation2),
        stringResource(R.string.caption3),
        stringResource(R.string.caption4),
        stringResource(R.string.caption5)
    )

    var index by remember {mutableStateOf(0)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Image(
            painter = painterResource(id = images[index]),
            contentDescription = captions[index],
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = captions[index],
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = {
                index = if(index == 0) images.lastIndex else index - 1
            }) {
                Text("Back")
            }
            Button(onClick = {
                index = if(index == images.lastIndex) 0 else index + 1
            }){
                Text("Next")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        JumpToImageControl(maxImages = images.size) { newIndex ->
            index = newIndex
        }
    }
}

@Composable
fun JumpToImageControl(
    maxImages: Int,
    onGo: (Int) -> Unit
) {
    var textInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Enter image # (1-$maxImages)") },
                singleLine = true,
                modifier = Modifier.width(180.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                val num = textInput.toIntOrNull()
                if (num != null && num in 1..maxImages) {
                    onGo(num - 1) // pass zero-based index back to main function
                    textInput = "" // optional: clear input
                    errorMessage = ""
                } else {
                    errorMessage = "Please enter a number 1-$maxImages"
                }
            }) {
                Text("Go")
            }
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSlideShowApp(){
    SlideShowAppTheme {
        SlideShowApp()
    }

}