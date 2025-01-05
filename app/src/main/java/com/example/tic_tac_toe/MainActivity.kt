package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe.ui.theme.Complementry
import com.example.tic_tac_toe.ui.theme.CompleteState
import com.example.tic_tac_toe.ui.theme.LastSelected
import com.example.tic_tac_toe.ui.theme.NextButtonColor
import com.example.tic_tac_toe.ui.theme.Primary
import com.example.tic_tac_toe.ui.theme.ResetButtonColor
import com.example.tic_tac_toe.ui.theme.RestartButtonColor
import com.example.tic_tac_toe.ui.theme.Secondary
import com.example.tic_tac_toe.ui.theme.Tertiary
import com.example.tic_tac_toe.ui.theme.TicTacToeTheme
import com.example.tic_tac_toe.ui.theme.UsualState
import com.example.tic_tac_toe.ui.theme.circleColor
import com.example.tic_tac_toe.ui.theme.crossColor

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
    var value: MutableState<Int> = mutableStateOf(0)

    constructor(inp: MutableState<Int>) : this() {
        value = inp
    }

    private var ui_state: Int = R.drawable.clear
        get() = when (this.value.value) {
            0 -> R.drawable.clear
            1 -> R.drawable.cross
            2 -> R.drawable.circle
            else -> R.drawable.error
        }
    private var ui_color: Color = Color.Black
        get() = when (this.value.value) {
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
        value.value = inp
    }

    fun reset() {
        value.value = 0
    }
}

class Player(var name: MutableState<String>, val setter: Int) {
    var score: MutableState<Int> = mutableStateOf(0)
    var next: Player? = null
}

@Composable
fun Main_Body() {

    val spacer_val = 3.dp
    val vert_spacer_ratio = .90f
    val player1 by remember { mutableStateOf(Player(mutableStateOf("Player 1"), 1)) }
    val player2 by remember { mutableStateOf(Player(mutableStateOf("Player 2"), 2)) }
    player1.next = player2
    player2.next = player1
    var prevGamePlayer by remember { mutableStateOf(player1) }
    var currentPlayer by remember { mutableStateOf(player1) }
    var topText by remember { mutableStateOf("'s Turn") }
    var topColor by remember { mutableStateOf(Complementry) }
    var indic by remember {
        mutableStateOf(
            Square(mutableIntStateOf(currentPlayer.setter))
        )
    }
    var next_check by remember { mutableStateOf(false) }
    var cell_enabled by remember { mutableStateOf(true) }
    val buttonStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        color = Complementry
    )

    val gameMatrix by remember { mutableStateOf(
        Array<Array<Square>>(3){ Array<Square>(3){Square(mutableStateOf(0))} }
    ) }

    val matrix_cell_back by remember {  mutableStateOf( Array<Array<MutableState<Color>>>(3){
        Array<MutableState<Color>>(3){ mutableStateOf(UsualState) }
    } )}

    fun check_full(): Boolean {
        for (i in gameMatrix) {
            for (j in i) {
                if (j.value.value == 0) {
                    return false
                }
            }
        }
        return true
    }

    fun validDir(coord: Pair<Int, Int>, dir: Pair<Int, Int>): Boolean {
        val postMoveX = coord.first + dir.first
        val postMoveY = coord.second + dir.second
        if (postMoveX > 2 || postMoveX < 0) {
            return false
        }
        if (postMoveY > 2 || postMoveY < 0) {
            return false
        }
        return true
    }

    fun setWinUI(cells: Array<Pair<Int, Int>>) {
        for ((i, j) in cells) {
            matrix_cell_back[i][j].value = CompleteState
        }
    }

    fun winCheck(coord: Pair<Int, Int>): Boolean {
        val directions = arrayOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1), Pair(0, 1),
            Pair(1, -1), Pair(1, 0), Pair(1, 1)
        )
        for (i in directions) {
            if (validDir(coord, i)) {
                val selctedCellValue = gameMatrix[coord.first][coord.second].value.value
                val adjacentCellCoord = Pair(coord.first + i.first, coord.second + i.second)
                if (gameMatrix[adjacentCellCoord.first][adjacentCellCoord.second].value.value == selctedCellValue) {
                    val nextAdjacentCoord =
                        Pair(adjacentCellCoord.first + i.first, adjacentCellCoord.second + i.second)
                    val prevAdjacentCoord = Pair(coord.first - i.first, coord.second - i.second)
                    if (validDir(nextAdjacentCoord, Pair(0, 0))) {
                        if (selctedCellValue == gameMatrix[nextAdjacentCoord.first][nextAdjacentCoord.second].value.value) {
                            setWinUI(arrayOf(coord, adjacentCellCoord, nextAdjacentCoord))
                            return true
                        }
                    } else if (validDir(prevAdjacentCoord, Pair(0, 0))) {
                        if (selctedCellValue == gameMatrix[prevAdjacentCoord.first][prevAdjacentCoord.second].value.value) {
                            setWinUI(arrayOf(coord, adjacentCellCoord, prevAdjacentCoord))
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun CellStateSet(Coord: Pair<Int, Int>) {
        if (gameMatrix[Coord.first][Coord.second].value.value == 0) {
            //Updating Selected Cell
            gameMatrix[Coord.first][Coord.second].value.value = currentPlayer.setter
            matrix_cell_back.forEach { row -> row.forEach { it.value = UsualState } }
            matrix_cell_back[Coord.first][Coord.second].value = LastSelected

            if (winCheck(Coord)) {
                next_check = true
                currentPlayer.score.value++
                cell_enabled = false
                topText = " Won!!"
                topColor = CompleteState
            } else if (check_full()) {
                next_check = true
            } else {
                //Passing Control to Next Player
                currentPlayer = currentPlayer.next!!
                indic.value.value = currentPlayer.setter
            }
        }
    }

    @Composable
    fun Cell(Coord: Pair<Int, Int>) {
        Box(
            modifier = Modifier
                .fillMaxWidth((1 / (3 - Coord.second.toFloat())))
                .fillMaxHeight()
                .padding(5.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(matrix_cell_back[Coord.first][Coord.second].value)
                .padding(15.dp)
                .clickable(enabled = cell_enabled) {
                    CellStateSet(Coord)
                }
        ) {
            gameMatrix[Coord.first][Coord.second].Ui_square()
        }
    }

    @Composable
    fun CellRow(Coord: Int) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight((1 / (3 - Coord.toFloat())))
                .fillMaxWidth()
//                        .background(Color.Green)
        ) {
            for (i in 0..2) {
                Cell(Pair(Coord, i))
                if (i <= 1) {
                    VertSpacer(spacer_val, vert_spacer_ratio, 0, Color.Gray)
                }
            }
        }
    }

    @Composable
    fun controlButton(
        size: Float,
        color: Color,
        enabled: Boolean,
        image: Int,
        text: String,
        click: () -> Unit = {}
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(size)
                .wrapContentHeight()
                .padding(5.dp)
        ) {
            Button(
                enabled = enabled,
                onClick = click,
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color
                ),
                modifier = Modifier
                    .fillMaxHeight(.85f)
                    .aspectRatio(1f)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(image), contentDescription = null
                )
            }
            Text(text = text, style = buttonStyle)
        }
    }

    fun matrixClear() {
        gameMatrix.forEach { row -> row.forEach { it.value.value = 0 } }
        matrix_cell_back.forEach { row -> row.forEach { it.value = UsualState } }
    }

    fun restart() {
        matrixClear()
        currentPlayer = prevGamePlayer.next!!
        indic.value.value = currentPlayer.setter
        prevGamePlayer = prevGamePlayer.next!!
        next_check = false
        cell_enabled = true
        topText = "'s Turn"
        topColor = Complementry
    }

    fun reset() {
        restart()
        player1.score.value = 0
        player2.score.value = 0
    }

    fun changeName(player: Player, name: String) {
        player.name.value = name
    }

    @Composable
    fun scoreName(player: Player) {
        val focusRequester = remember { FocusRequester() }
        Row(
            modifier = Modifier.fillMaxHeight(1 / 2f)
                .wrapContentWidth()
        ) {
            Column {
                val focusMan = LocalFocusManager.current
                var displayName by remember { mutableStateOf(player.name.value) }
                BasicTextField(
                    value = TextFieldValue(
                        text = displayName,
                        selection = TextRange(displayName.length)
                    ),
                    onValueChange = { it ->
                        if (it.text.length <= 10) {
                            displayName = it.text
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(color = Primary, fontSize = 15.sp),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusMan.clearFocus()
                            if (displayName == "") {
                                player.name.value = "Player 1"
                                displayName = "Player 1"
                            } else {
                                player.name.value = displayName
                            }
                        }
                    ),
                    modifier = Modifier.fillMaxWidth(2 / 5f)
                        .focusRequester(focusRequester)
                )
                HoriSpacer(1.dp, 2 / 5f, roundness = 100, color = Secondary)
            }
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.edit),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Secondary),
                modifier = Modifier.fillMaxHeight(4 / 5f)
                    .clickable { focusRequester.requestFocus() }
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
            .background(Secondary)
            .padding(20.dp, 80.dp)
    ) {
        TopText@ Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(0.dp, 15.dp)
                .wrapContentHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(100))
                .background(topColor)
                .padding(15.dp)
        ) {
            Text(
                text = "${currentPlayer.name.value}$topText",
                style = TextStyle(fontSize = 22.sp, color = Primary)
            )
            Spacer(Modifier.width(20.dp))
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

                for (i in 0..2) {
                    CellRow(i)
                    if (i <= 1) {
                        Horizontal_line(spacer_val)
                    }
                }

            }

            HoriSpacer(spacer_val)
            Info_box@ Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
                    .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
                    .background(Complementry)
                    .padding(10.dp)
            ) {
                Column {
                    Text("Scores:", style = TextStyle(color = Primary, fontSize = 20.sp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(1 / 2f)
                        ) {
                            scoreName(player1)
                            Text(
                                text = "${player1.score.value}",
                                style = TextStyle(color = Primary, fontSize = 20.sp)
                            )
                        }
                        VertSpacer(spacer_val, 1f, 100)
                        Column(
                            modifier = Modifier.fillMaxWidth(1 / 1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            scoreName(player2)
                            Text(
                                text = "${player2.score.value}",
                                style = TextStyle(color = Primary, fontSize = 20.sp),
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            Control_box@ Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
            ) {
                controlButton(
                    1 / 4f, NextButtonColor, next_check, R.drawable.next, "Next"
                ) { restart() }
                controlButton(
                    1 / 3f, RestartButtonColor, true, R.drawable.restart, "Restart"
                ) { restart() }
                controlButton(
                    1 / 2f, ResetButtonColor, true, R.drawable.reset, "Reset"
                ) { reset() }
            }
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
fun Horizontal_line(spacer_val: androidx.compose.ui.unit.Dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(spacer_val)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(1 / 3f)
                .padding(5.dp, 0.dp),
        ) {
            HoriSpacer(spacer_val, roundness = 100, color = Color.Gray)
        }
        VertSpacer(spacer_val, 1f, 100, Color.Gray)
        Box(
            modifier = Modifier
                .fillMaxWidth(1 / 2f)
                .padding(5.dp, 0.dp)
        ) {
            HoriSpacer(spacer_val, roundness = 100, color = Color.Gray)
        }
        VertSpacer(spacer_val, 1f, 100, Color.Gray)
        Box(
            modifier = Modifier
                .fillMaxWidth(1 / 1f)
                .padding(5.dp, 0.dp)
        ) {
            HoriSpacer(spacer_val, roundness = 100, color = Color.Gray)
        }
    }
}

