package ru.linew.spotifyApp.ui.models.status

import ru.linew.spotifyApp.ui.models.core.TrackAnalysis

sealed class AnalysisTrackStatus{
    object Null: AnalysisTrackStatus()
    object Loading: AnalysisTrackStatus()
    class Success(val data: TrackAnalysis): AnalysisTrackStatus()
    class Error(val throwable: Throwable): AnalysisTrackStatus()
}
