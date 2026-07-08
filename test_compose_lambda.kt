import androidx.compose.material3.IconButton
import androidx.compose.material3.Text

@Composable
fun ShareButton(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Text("Share")
    }
}

@Composable
fun MainScreen() {
    val card = Card(title = "Hello")
    
    Box(modifier = Modifier.clickable { share(card) }) {
        Text("Click me")
    }
    
    Button(onClick = {
        val url = imageUrl
        if (url != null) shareWebPage(url)
    }) {
        Text("Share via QQ")
    }
}
