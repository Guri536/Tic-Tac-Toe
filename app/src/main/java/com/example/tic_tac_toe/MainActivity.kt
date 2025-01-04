package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe.ui.theme.Complementry
import com.example.tic_tac_toe.ui.theme.CompleteState
import com.example.tic_tac_toe.ui.theme.LastSelected
import com.example.tic_tac_toe.ui.theme.Primary
import com.example.tic_tac_toe.ui.theme.Secondary
import com.example.tic_tac_toe.ui.theme.Tertiary
import com.example.tic_tac_toe.ui.theme.TicTacToeTheme
import com.example.tic_tac_toe.ui.theme.UsualState
import com.example.tic_tac_toe.ui.theme.circleColor
import com.example.tic_tac_toe.ui.theme.crossColor
import kotlin.math.round

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


class Square() {
    var value: Int = 0

    constructor(inp: Int) : this() {
        value = inp
    }

    private var ui_state: Int = R.drawable.clear
        get() = when (this.value) {
            0 -> R.drawable.clear
            1 -> R.drawable.cross
            2 -> R.drawable.circle
            else -> R.drawable.error
        }
    private var ui_color: Color = Color.Black
        get() = when (this.value) {
            0 -> Color.Black
            1 -> crossColor
            2 -> circleColor
            else -> Color.Red
        }

    @Composable
    fun Ui_square(modifier: Modifier = Modifier) {
        Box(modifier = modifier) {
            Image(
                imageVector = ImageVector.vectorResource(ui_state), contentDescription = null,
                colorFilter = ColorFilter.tint(ui_color), contentScale = ContentScale.Fit
            )
        }
    }

    fun val_set(inp: Int) {
        value = inp
    }

    fun reset() {
        value = 0
    }
}

class Player(var name: String, val setter: Int) {
    var score: Int = 0
    var next: Player? = null
}

@Composable
fun Main_Body() {

    val spacer_val = 3.dp
    val vert_spacer_ratio = .90f
    val player1 = Player("Player 1", 1)
    val player2 = Player("Player 2", 2)
    player1.next = player2
    player2.next = player1
    var actionno by remember { mutableStateOf(0) }
    var prevGamePlayer by remember { mutableStateOf(player1) }
    var currentPlayer by remember { mutableStateOf(player1) }
    val gameMatrix = arrayOf(
        arrayOf(Square(1), Square(1), Square(1)),
        arrayOf(Square(3), Square(3), Square(3)),
        arrayOf(Square(2), Square(2), Square(2)),
    )

    val matrix_cell_back = arrayOf(
        arrayOf(remember { mutableStateOf(UsualState) },
            remember { mutableStateOf(UsualState) },
            remember { mutableStateOf(UsualState) }),
        arrayOf(remember { mutableStateOf(UsualState) },
            remember { mutableStateOf(UsualState) },
            remember { mutableStateOf(UsualState) }),
        arrayOf(remember { mutableStateOf(UsualState) },
            remember { mutableStateOf(UsualState) },
            remember { mutableStateOf(UsualState) }),
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Secondary)
            .padding(20.dp, 80.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(0.dp, 15.dp)
                .wrapContentHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(100))
                .background(Complementry)
                .padding(15.dp)
        ) {
            Text(
                text = "${currentPlayer.name}'s Turn",
                style = TextStyle(fontSize = 22.sp, color = Color.Black)
            )
            Spacer(Modifier.width(20.dp))
            val indic = Square(currentPlayer.setter)
            indic.Ui_square(
                modifier = Modifier
                    .height(30.dp)
                    .aspectRatio(1f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(35.dp))
                .background(Primary)
                .padding(20.dp, 20.dp)
        ) {

            Tic_box@ Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                    .background(Tertiary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                row1@ Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight(1 / 3f)
                        .fillMaxWidth()
//                        .background(Color.Blue)
                ) {
                    box_0_0@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 3f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[0][0].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[0][0].Ui_square()
                    }
                    VertSpacer(spacer_val, vert_spacer_ratio, 100, Color.Gray)
                    box_0_1@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 2f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[0][1].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[0][1].Ui_square()
                    }
                    VertSpacer(spacer_val, vert_spacer_ratio, 100, Color.Gray)
                    box_0_2@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[0][2].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[0][2].Ui_square()
                    }
                }

                Horizontal_line(spacer_val)

                row2@ Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight(.5f)
                        .fillMaxWidth()
//                        .background(Color.Green)
                ) {
                    box_1_0@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 3f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[1][0].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[1][0].Ui_square()
                    }
                    VertSpacer(spacer_val, vert_spacer_ratio, 0, Color.Gray)
                    box_1_1@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 2f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[1][1].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[1][1].Ui_square()
                    }
                    VertSpacer(spacer_val, vert_spacer_ratio, 0, Color.Gray)
                    box_1_2@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[1][2].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[1][2].Ui_square()
                    }
                }

                Horizontal_line(spacer_val)

                row3@ Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .fillMaxWidth()
//                        .background(Color.Red)
                ) {
                    box_2_0@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 3f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[2][0].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[2][0].Ui_square()
                    }
                    VertSpacer(spacer_val, vert_spacer_ratio, 100, Color.Gray)
                    box_2_1@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 2f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[2][1].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[2][1].Ui_square()
                    }
                    VertSpacer(spacer_val, vert_spacer_ratio, 100, Color.Gray)
                    box_2_2@ Box(
                        modifier = Modifier
                            .fillMaxWidth(1 / 1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(matrix_cell_back[2][2].value)
                            .padding(20.dp)
                    ) {
                        gameMatrix[2][2].Ui_square()
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .height(spacer_val)
                    .fillMaxWidth()
                    .background(Color.Black)
            )
            val Info_box = Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
                    .background(Complementry)
            ) { }
            Spacer(
                modifier = Modifier
                    .height(spacer_val)
                    .fillMaxWidth()
                    .background(Color.Black)
            )
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

@Composable
fun VertSpacer(
    width: androidx.compose.ui.unit.Dp,
    max: Float = 1f,
    roundness: Int = 0,
    color: Color = Color.Black,
    offset: Pair<Int, Int> = Pair(0, 0),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Spacer(
        Modifier
            .then(modifier)
            .fillMaxHeight(max)
            .width(width)
            .clip(RoundedCornerShape(roundness))
            .offset(offset.first.dp, offset.second.dp)
            .background(color)
    )
}

@Composable
fun HoriSpacer(
    height: androidx.compose.ui.unit.Dp,
    max: Float = 1f,
    roundness: Int = 0,
    color: Color = Color.Black,
    offset: Pair<Int, Int> = Pair(0, 0),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Spacer(
        modifier = Modifier
            .then(modifier)
            .height(height)
            .fillMaxWidth(max)
            .clip(RoundedCornerShape(roundness))
            .background(color)
            .offset(offset.first.dp, offset.second.dp)
    )
}

@Composable
fun Horizontal_line(spacer_val: androidx.compose.ui.unit.Dp){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(spacer_val)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(1 / 3f)
            .padding(5.dp, 0.dp),
        ) {
            HoriSpacer(spacer_val, roundness = 100, color = Color.Gray)
        }
        VertSpacer(spacer_val, 1f, 100, Color.Gray)
        Box(modifier = Modifier
            .fillMaxWidth(1 / 2f)
            .padding(5.dp, 0.dp)) {
            HoriSpacer(spacer_val, roundness = 100, color = Color.Gray)
        }
        VertSpacer(spacer_val, 1f, 100, Color.Gray)
        Box(modifier = Modifier
            .fillMaxWidth(1 / 1f)
            .padding(5.dp, 0.dp)) {
            HoriSpacer(spacer_val, roundness = 100, color = Color.Gray)
        }
    }
}