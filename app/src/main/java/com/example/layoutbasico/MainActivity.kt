package com.example.layoutbasico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutbasico.ui.theme.LayoutBasicoTheme

/*
 * Objeto Spacing:
 * Define constantes para os espaçamentos utilizados em todo o aplicativo, evitando números "mágicos" espalhados pelo código.
 */
object Spacing {
    val extraSmall = 4.dp
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
}

/*
 * MainActivity:
 * Atividade principal do aplicativo. Aqui é configurado o conteúdo usando Jetpack Compose.
 * A função enableEdgeToEdge permite que o conteúdo seja desenhado atrás do sistema de barras (status/navigation),
 * e setContent inicia a composição chamando o composable CalmariaApp().
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalmariaApp()
        }
    }
}

// ------------------------------------------------------------
// COMPONENTES (Composables)
// ------------------------------------------------------------

/*
 * SearchBar:
 * Composable que representa uma barra de pesquisa.
 * - searchText: O texto atual da pesquisa.
 * - onSearchTextChange: Callback chamado quando o usuário altera o texto.
 * - modifier: Permite modificar a aparência e o layout do componente.
 *
 * Utiliza um TextField do Material3 com um ícone de busca à esquerda e configura as cores
 * para os estados focado e não focado, aproveitando as cores definidas no MaterialTheme.
 */
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()    // Ocupa toda a largura disponível
            .heightIn(min = 56.dp) // Altura mínima do componente
    )
}

/*
 * AlignYourBodyElement:
 * Composable que exibe um elemento composto por uma imagem circular e um texto abaixo.
 * - imagem: Recurso drawable da imagem.
 * - texto: Recurso de string para o texto.
 * - modifier: Modificador para customizar o layout.
 *
 * A imagem é recortada em formato circular e o texto é posicionado com um padding definido a partir da baseline.
 */
@Composable
fun AlignYourBodyElement(
    @DrawableRes imagem: Int,
    @StringRes texto: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Centraliza os itens horizontalmente
        modifier = modifier
    ) {
        Image(
            painter = painterResource(imagem),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ajusta a imagem para cobrir o espaço definido
            modifier = Modifier
                .size(88.dp)  // Define o tamanho da imagem
                .clip(CircleShape) // Recorta a imagem com uma forma circular
        )
        Text(
            text = stringResource(texto),
            modifier = Modifier.paddingFromBaseline(
                top = Spacing.large,
                bottom = Spacing.small
            ),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/*
 * FavoriteCollectionCard:
 * Composable que representa um card para uma coleção favorita.
 * - imagem: Recurso drawable da imagem da coleção.
 * - texto: Recurso de string que descreve a coleção.
 * - modifier: Permite customizar o layout do card.
 *
 * O card utiliza um Surface com uma forma definida e uma cor de fundo (surfaceVariant).
 * Dentro do Surface há um Row que alinha horizontalmente a imagem e o texto.
 */
@Composable
fun FavoriteCollectionCard(
    @DrawableRes imagem: Int,
    @StringRes texto: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium, // Forma do card definida pelo tema
        color = MaterialTheme.colorScheme.surfaceVariant, // Cor de fundo do card
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // Centraliza verticalmente os itens
            modifier = Modifier.width(255.dp) // Largura fixa para o card
        ) {
            Image(
                painter = painterResource(imagem),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp) // Define o tamanho da imagem dentro do card
            )
            Text(
                text = stringResource(texto),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = Spacing.medium) // Espaçamento horizontal entre a imagem e o texto
            )
        }
    }
}

/*
 * AlignYourBodyRow:
 * Composable que exibe uma linha (LazyRow) de elementos do tipo AlignYourBodyElement.
 * - searchText: Texto de pesquisa usado para filtrar os elementos.
 * - modifier: Modificador para customizar o layout da linha.
 *
 * Filtra os itens com base no texto de pesquisa e exibe-os com um espaçamento definido.
 */
@Composable
fun AlignYourBodyRow(
    searchText: String,
    modifier: Modifier = Modifier
) {
    // Filtra os itens da lista de acordo com o texto de pesquisa (ignora caixa)
    val filteredItems = alignYourBodyData.filter {
        stringResource(it.text).contains(searchText, ignoreCase = true)
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.small), // Espaçamento entre os itens
        contentPadding = PaddingValues(horizontal = Spacing.medium), // Padding horizontal na lista
        modifier = modifier
    ) {
        items(filteredItems) { item ->
            AlignYourBodyElement(item.drawable, item.text)
        }
    }
}

/*
 * FavoriteCollectionsGrid:
 * Composable que exibe uma grade horizontal (LazyHorizontalGrid) de cards de coleções favoritas.
 * - modifier: Modificador para customizar o layout da grade.
 *
 * A grade é organizada em 2 linhas fixas e os itens são espaçados tanto horizontalmente quanto verticalmente.
 */
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2), // Define 2 linhas na grade
        contentPadding = PaddingValues(horizontal = Spacing.medium), // Padding horizontal para a grade
        horizontalArrangement = Arrangement.spacedBy(Spacing.medium), // Espaçamento entre colunas
        verticalArrangement = Arrangement.spacedBy(Spacing.medium),   // Espaçamento entre linhas
        modifier = modifier.height(168.dp) // Altura definida para a grade
    ) {
        items(favoriteCollectionsData) { item ->
            FavoriteCollectionCard(item.drawable, item.text)
        }
    }
}

/*
 * HomeSection:
 * Composable que agrupa uma seção da tela inicial, contendo um título e um conteúdo composable.
 * - title: Recurso de string para o título da seção.
 * - modifier: Modificador para customizar o layout da coluna.
 * - content: Bloco composable que representa o conteúdo da seção.
 *
 * O título utiliza um padding a partir da baseline e a cor é definida de acordo com o tema.
 */
@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // Aplica o padding horizontal para toda a seção
    Column(
        modifier = modifier
            .padding(horizontal = Spacing.medium)
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground, // Define a cor do texto conforme o tema
            modifier = Modifier.paddingFromBaseline(
                top = 40.dp,           // Espaço a partir da baseline superior
                bottom = Spacing.medium // Espaço abaixo do título
            )
        )
        // Exibe o conteúdo passado para a seção (pode ser uma lista, grid, etc.)
        content()
    }
}

/*
 * HomeScreen:
 * Composable que representa a tela inicial do aplicativo.
 *
 * Possui:
 * - Uma barra de pesquisa (SearchBar)
 * - Seções que exibem os elementos "AlignYourBody" e "FavoriteCollections"
 * - Um Scroll vertical para permitir a visualização de todo o conteúdo
 *
 * Utiliza a cor de fundo do tema para suportar dark mode.
 */
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // Variável de estado para o texto de pesquisa
    var searchText by remember { mutableStateOf("") }

    Surface(
        color = MaterialTheme.colorScheme.background, // Cor de fundo conforme o tema (suporte dark mode)
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState()) // Permite scroll vertical
        ) {
            Spacer(modifier = Modifier.height(Spacing.medium)) // Espaçamento superior
            SearchBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                modifier = Modifier.padding(horizontal = Spacing.medium) // Padding horizontal para a SearchBar
            )
            // Seção "Align Your Body"
            HomeSection(R.string.align_your_body) {
                AlignYourBodyRow(searchText)
            }
            // Seção "Favorite Collections"
            HomeSection(R.string.favorite_collections) {
                FavoriteCollectionsGrid()
            }
            Spacer(modifier = Modifier.height(Spacing.medium)) // Espaçamento inferior
        }
    }
}

/*
 * HomeScreenPreview:
 * Função de preview que permite visualizar a tela inicial (HomeScreen) no Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LayoutBasicoTheme {
        HomeScreen()
    }
}

/*
 * BarraNavegacao:
 * Composable que exibe a barra de navegação inferior.
 *
 * Utiliza NavigationBar e NavigationBarItem para criar os botões de navegação.
 * Cada item possui um ícone e um rótulo, e a cor de fundo é definida pelo tema.
 */
@Composable
private fun BarraNavegacao(modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant, // Cor de fundo da barra de navegação
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_home))
            },
            selected = true, // Indica que este item está selecionado
            onClick = {}    // Ação ao clicar (a ser implementada)
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_profile))
            },
            selected = false, // Este item não está selecionado
            onClick = {}      // Ação ao clicar (a ser implementada)
        )
    }
}

/*
 * CalmariaApp:
 * Composable que organiza a estrutura geral do aplicativo utilizando um Scaffold.
 *
 * - O Scaffold define a barra de navegação inferior (bottomBar) e o conteúdo principal (HomeScreen),
 *   garantindo que os paddings necessários sejam aplicados.
 */
@Composable
fun CalmariaApp() {
    LayoutBasicoTheme {
        Scaffold(
            bottomBar = {
                BarraNavegacao()
            }
        ) { padding ->
            // O conteúdo principal da tela recebe os paddings definidos pelo Scaffold
            HomeScreen(Modifier.padding(padding))
        }
    }
}

// ------------------------------------------------------------
// DADOS
// ------------------------------------------------------------

/*
 * Lista de dados para os elementos "Align Your Body":
 * Cada item é um par de recursos, contendo um drawable e um string.
 */
private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

/*
 * Lista de dados para os cards de "Favorite Collections":
 * Cada item é um par de recursos, contendo um drawable e um string.
 */
private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

/*
 * Data class DrawableStringPair:
 * Representa um par de recursos, contendo um drawable (imagem) e um string (texto).
 */
private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)