package com.app.habittracker.presentation.screens.add_habit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.domain.model.HabitIcon
import com.app.habittracker.presentation.theme.*

@Composable
fun AddHabitScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddHabitViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }

    val appColors = LocalAppColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = appColors.textPrimary
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Add New Habit",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = appColors.textPrimary
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(40.dp))
            }

            // Habit Name Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = appColors.cardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Habit Name",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = appColors.textSecondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = uiState.habitName,
                        onValueChange = viewModel::updateHabitName,
                        placeholder = {
                            Text(
                                "Enter habit name",
                                color = appColors.textLight
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealAccent,
                            unfocusedBorderColor = appColors.inputBackground,
                            focusedContainerColor = appColors.inputBackground,
                            unfocusedContainerColor = appColors.inputBackground
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        isError = uiState.errorMessage != null
                    )

                    if (uiState.errorMessage != null) {
                        Text(
                            text = uiState.errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Choose Habits Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = appColors.cardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Choose Habit Type",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = appColors.textSecondary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Habit type cards
                    val allIcons = listOf(
                        HabitIcon.SMOKING,
                        HabitIcon.DRINKING,
                        HabitIcon.JUNK_FOOD,
                        HabitIcon.GAMING,
                        HabitIcon.COFFEE,
                        HabitIcon.SOCIAL_MEDIA,
                        HabitIcon.SHOPPING
                    )

                    allIcons.forEach { icon ->
                        HabitTypeCard(
                            icon = icon,
                            isSelected = uiState.selectedIcon == icon,
                            onClick = { viewModel.updateSelectedIcon(icon) },
                            appColors = appColors
                        )
                        if (icon != allIcons.last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            Button(
                onClick = viewModel::saveHabit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TealAccent
                ),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Save Habit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cancel button
            TextButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancel",
                    color = appColors.textSecondary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun HabitTypeCard(
    icon: HabitIcon,
    isSelected: Boolean,
    onClick: () -> Unit,
    appColors: AppColors
) {
    val gradientColors = when(icon) {
        HabitIcon.SMOKING -> listOf(SmokingGradientStart, SmokingGradientEnd)
        HabitIcon.DRINKING -> listOf(DrinkingGradientStart, DrinkingGradientEnd)
        HabitIcon.JUNK_FOOD -> listOf(JunkFoodGradientStart, JunkFoodGradientEnd)
        HabitIcon.GAMING -> listOf(GamingGradientStart, GamingGradientEnd)
        HabitIcon.COFFEE -> listOf(CoffeeGradientStart, CoffeeGradientEnd)
        HabitIcon.SOCIAL_MEDIA -> listOf(SocialMediaGradientStart, SocialMediaGradientEnd)
        HabitIcon.SHOPPING -> listOf(ShoppingGradientStart, ShoppingGradientEnd)
        else -> listOf(DefaultGradientStart, DefaultGradientEnd)
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, TealAccent, RoundedCornerShape(12.dp))
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) appColors.cardBackground else appColors.inputBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with gradient background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Brush.horizontalGradient(gradientColors)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon.icon,
                    contentDescription = icon.iconName,
                    modifier = Modifier.size(22.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = icon.iconName,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = appColors.textPrimary,
                modifier = Modifier.weight(1f)
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    modifier = Modifier.size(24.dp),
                    tint = TealAccent
                )
            }
        }
    }
}

