package com.example.myapplicationcomposesample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.myapplicationcomposesample.challenges.SelectChallengeListActivity
import com.example.myapplicationcomposesample.ui.MyApplicationComposeSampleTheme
import com.example.myapplicationcomposesample.ui.typography
import com.example.myapplicationcomposesample.wikitude.ObjectTrackingActivity
import com.example.myapplicationcomposesample.wikitude.SimpleImageTrackingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationComposeSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun App() {
        val padding = 50.dp
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { startActivity(Intent(this@MainActivity, SelectChallengeListActivity::class.java)) },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "Challenge", style = typography.button)
            }
            Spacer(Modifier.preferredSize(padding))
            Button(
                onClick = { startActivity(Intent(this@MainActivity, SimpleImageTrackingActivity::class.java)) },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "Image Tracking", style = typography.button)
            }
            Spacer(Modifier.preferredSize(padding))
            Button(
                onClick = { startActivity(Intent(this@MainActivity, ObjectTrackingActivity::class.java)) },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "Object Tracking", style = typography.button)
            }
        }
    }
}
