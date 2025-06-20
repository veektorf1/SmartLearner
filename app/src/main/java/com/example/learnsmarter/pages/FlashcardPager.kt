import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learnsmarter.models.Flashcard
import com.example.learnsmarter.ui.theme.LightColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardPager(navController: NavController,flashcards: List<Flashcard>) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { flashcards.size })
    println("Flashcard size: ${flashcards.size}")

    HorizontalPager(
        state = pagerState,
        pageSpacing = 16.dp,
        modifier = Modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) { page ->
        val flashcard = flashcards[page]
        var isFlipped by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("${page + 1}/${flashcards.size}")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = LightColorScheme.secondaryContainer,
                        titleContentColor = LightColorScheme.onSecondary
                    )
                )
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clickable { isFlipped = !isFlipped },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (!isFlipped) flashcard.wordEng else flashcard.wordPol,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
        )
    }
}
