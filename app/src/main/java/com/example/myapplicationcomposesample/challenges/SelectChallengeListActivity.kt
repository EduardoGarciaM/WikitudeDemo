package com.example.myapplicationcomposesample.challenges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.myapplicationcomposesample.ui.MyApplicationComposeSampleTheme
import com.example.myapplicationcomposesample.ui.shapes
import com.example.myapplicationcomposesample.ui.typography
import com.example.myapplicationcomposesample.R

class SelectChallengeListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent {
            MyApplicationComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    populateLevels()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun populateLevels() {
        setUpChallenges(challenges = ChallengeMockSource.getChallenges()) {}
    }

    @Composable
    private fun setUpChallenges(
        challenges: List<Challenge>,
        onSelected: (Challenge) -> Unit
    ) {
        ScrollableRow(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            challenges.forEach {
                LevelCard(it) { onSelected(it) }
            }
        }
    }

    @Composable
    private fun LevelCard(
        challenge: Challenge,
        onClick: () -> Unit
    ) {
        val padding = 20.dp
        val dm = resources.displayMetrics
        val level = dm.widthPixels/dm.density
        Column(
            Modifier
                .width(level.dp)
                .padding(padding)
                .clickable(onClick = onClick)
        ) {
            Text(text = challenge.name, style = typography.button, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            Spacer(Modifier.preferredSize(padding))
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = shapes.medium,
                elevation = 4.dp
            ) {
                Column() {
                    Image(
                        asset = imageResource(id = challenge.getImage()),
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .weight(3f)

                    )
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .weight(1f)
                            .fillMaxWidth(),
                        enabled = !challenge.disabled
                    ) {
                        Text(text = "Let's Build!", style = typography.button)
                    }
                }
            }
        }
    }
}