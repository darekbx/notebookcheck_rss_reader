package com.darekbx.notebookcheckreader.domain

import com.darekbx.notebookcheckreader.repository.local.FavouritesDao
import kotlinx.coroutines.flow.Flow

class FetchFavouritesCountUseCase(
    private val favouritesDao: FavouritesDao
) {
    operator fun invoke(): Flow<Int> {
        return favouritesDao.fetchCount()
    }
}
