package ru.linew.spotifyApp.ui.models.state

import ru.linew.spotifyApp.ui.models.core.TrackAnalysis

sealed class AnalysisTrackState{
    object Null: AnalysisTrackState()
    object Loading: AnalysisTrackState()
    class Success(val data: TrackAnalysis): AnalysisTrackState()
    class Error(val throwable: Throwable): AnalysisTrackState()
}
