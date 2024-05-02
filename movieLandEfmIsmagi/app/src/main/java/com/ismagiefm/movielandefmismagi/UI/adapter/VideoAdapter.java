package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ismagiefm.movielandefmismagi.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<String> videoIds;

    public VideoAdapter(List<String> videoIds) {
        this.videoIds = videoIds;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String videoId = videoIds.get(position);
        // Utilisez l'ID de vidéo pour charger la vidéo
        // holder.youTubePlayerView.loadVideo(videoId, 0);
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView youTubePlayerView;

        VideoViewHolder(View view) {
            super(view);
            youTubePlayerView = view.findViewById(R.id.youtube_player_view);

        }
    }
}

