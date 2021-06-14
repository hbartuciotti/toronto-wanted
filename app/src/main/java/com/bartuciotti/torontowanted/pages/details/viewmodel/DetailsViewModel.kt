package com.bartuciotti.torontowanted.pages.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bartuciotti.torontowanted.data.Repository
import com.bartuciotti.torontowanted.pages.investigations.data.Suspect
import com.bartuciotti.torontowanted.pages.investigations.data.Victim
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    suspend fun getVictimsForCase(caseId: Int): List<Victim> {
        val result = viewModelScope.async(Dispatchers.IO) {
            repository.getVictimsForCase(caseId)
        }
        return result.await()
    }

    suspend fun getSuspectsForCase(caseId: Int): List<Suspect> {
        val result = viewModelScope.async(Dispatchers.IO) {
            repository.getSuspectsForCase(caseId)
        }
        return result.await()
    }
}