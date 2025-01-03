package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe.ui.theme.Complementry
import com.example.tic_tac_toe.ui.theme.Primary
import com.example.tic_tac_toe.ui.theme.Purple40
import com.example.tic_tac_toe.ui.theme.Purple80
import com.example.tic_tac_toe.ui.theme.PurpleGrey40
import com.example.tic_tac_toe.ui.theme.Secondary
import com.example.tic_tac_toe.ui.theme.Tertiary
import com.example.tic_tac_toe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .statusBarsPadding()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Primary)
                            .padding(innerPadding)
                    ) {
                        Top_bar()
                        Main_Body()
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun Top_bar() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Primary)
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Text(
            "Tic-Tac-Toe",
            style = TextStyle(
                fontSize = 22.sp
            )
        )
    }
}

@Composable
fun Main_Body() {

    val spacer_val = 3.dp

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Secondary)
            .padding(20.dp, 100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(35.dp))
                .background(Primary)
                .padding(20.dp, 20.dp)
        ) {
            val Tic_box = Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                    .background(Tertiary)
            ) {
                
            }
            Spacer(modifier = Modifier
                .height(spacer_val)
                .fillMaxWidth()
                .background(Color.Black))
            val Info_box = Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
                    .background(Complementry)
            ) { }
            Spacer(modifier = Modifier
                .height(spacer_val)
                .fillMaxWidth()
                .background(Color.Black))
            val Control_box = Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
                    .background(Complementry)
            ) { }
        }
    }
}