package malok.testtask.pokemonapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: PokemonViewModel) {
    val lazyPagingItems = viewModel.pokemons.collectAsLazyPagingItems()
    val isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        bottomBar = { BottomNavBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HeaderSearch()

            PullToRefreshBox(
                state = pullRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = { lazyPagingItems.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val count = lazyPagingItems.itemCount
                    items(count) { index ->
                        lazyPagingItems[index]?.let { PokemonCard(it) }
                    }

                    if (lazyPagingItems.loadState.append is LoadState.Loading) {
                        item(span = { GridItemSpan(2) }) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}