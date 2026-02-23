package com.visionary.numberguessinggame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visionary.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumberGuessingGameTheme {
                NumberGuess()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NumberGuess() {
    var number by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Guess the secret number between 1-100") }
    var secretNumber by remember { mutableIntStateOf(Random.nextInt(1, 101)) }
    var textColor by remember { mutableStateOf(Color.Black) }
    var highScore by rememberSaveable { mutableIntStateOf(0) }
    var buttonState by remember { mutableStateOf(true) }
    var buttonText by remember { mutableStateOf("Check") }
    var attempt by remember { mutableIntStateOf(0) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 100.dp),
            text = "Number Guessing Game",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = message,
            fontSize = 16.sp,
            color = textColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "High Score : $highScore attempts",
            fontSize = 18.sp,

        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = number,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black

            ),
            onValueChange = { number = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(35.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
            onClick = {
                val n = number.toIntOrNull()

                if (n == null || n !in 1..100) {
                    message = "Please enter a valid number from 1 to 100 "
                    textColor= Color.Black
                    return@Button
                }

                attempt++

                if (n < secretNumber) {
                    message = "Sorry, your guess is low."
                    textColor= Color.Red
                } else if (n > secretNumber) {
                    message = "Sorry, your guess is high."
                    textColor= Color.Blue
                } else {
                    message = "You guessed the number in $attempt attempts!"
                    textColor= Color.Green

                    if (attempt<highScore || highScore==0){
                        highScore=attempt
                    }
                    buttonState=false
                    buttonText="Click New Game"
                }
                number = ""
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                disabledContentColor = Color.Gray,
                contentColor = Color.White
            ),
            enabled = buttonState

        ) {
            Text(text = buttonText, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp),
            onClick = {
                number=""
                message="New game started! Guess the number"
                secretNumber = Random.nextInt(1, 101)
                attempt=0
                textColor= Color.Black
                buttonState=true
                buttonText="Check"

            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text("New Game")
        }
    }
}

